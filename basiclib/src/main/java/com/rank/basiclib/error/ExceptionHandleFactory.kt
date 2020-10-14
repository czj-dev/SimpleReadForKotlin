package com.rank.basiclib.error

import android.annotation.SuppressLint
import android.app.Application
import android.text.TextUtils
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.rank.basiclib.data.AppResponse
import com.rank.basiclib.data.AppResponseImpl
import com.rank.basiclib.ext.debug
import com.rank.basiclib.utils.ToastUtils
import io.reactivex.Observable
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.charset.Charset
import java.text.ParseException

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2018/8/8
 *     desc  :
 * </pre>
 */
class ExceptionHandleFactory private constructor(
    private val application: Application,
    private val exceptionHandlers: MutableList<ServiceErrorHandler>
) {

    companion object {
        private const val TAG = "Error-Log"
        private const val SERVICE_ERROR = "服务器发生错误"
        private const val LOCATION_ERROR = "请求地址不存在"
        private const val SERVICE_ACCESS_DENIED_ERROR = "服务器拒绝访问"
        private const val REQUEST_REDIRECT = "请求被重定向"
        private const val DATA_ANALYSIS_ERROR = "数据解析错误"
        private const val NETWORK_ERROR = "网络错误，请连接网络后重试"
        private const val NETWORK_TIME_OUT_ERROR = "网络连接超时，请确认网络连接环境"
        private const val ACCOUNT_UNAUTHORIZED = "账户登录已失效，请重新登录!"

        public const val CODE_ERROR_UNAUTHORIZED = 401

        @Volatile
        lateinit var instance: ExceptionHandleFactory

        fun init(application: Application, exceptionHandlers: MutableList<ServiceErrorHandler>) {
            ExceptionHandleFactory(application, exceptionHandlers).also { instance = it }
        }

        fun <T : AppResponse<*>> interceptorError(errorHandler: (e: Throwable) -> Unit = {}): GlobalErrorTransformer<T, T> =
            GlobalErrorTransformer(
                onNextInterceptor = ExceptionHandleFactory.instance.interceptorError(),
                onErrorResumeNext = ExceptionHandleFactory.instance.defaultErrorReturn(),
                onErrorConsumer = ExceptionHandleFactory.instance.defaultErrorConsumer(errorHandler),
                onErrorRetrySupplier = ExceptionHandleFactory.instance.defaultErrorRetrySupplier()
            )

        fun <T : AppResponse<K>, K> convertData(errorHandler: (e: Throwable) -> Unit = {}): GlobalErrorTransformer<T, K> =
            GlobalErrorTransformer(
                onNextInterceptor = ExceptionHandleFactory.instance.interceptorErrorAndShellData(),
                onErrorResumeNext = ExceptionHandleFactory.instance.convertErrorReturn(),
                onErrorConsumer = ExceptionHandleFactory.instance.defaultErrorConsumer(errorHandler),
                onErrorRetrySupplier = ExceptionHandleFactory.instance.defaultErrorRetrySupplier()
            )
    }


    public fun convertStatusCode(httpException: HttpException): String {
        return when {
            httpException.code() == 500 -> {
                if (debug()) {
                    val errorBody = httpException.response().errorBody()
                    var charset: Charset? = Charset.forName("UTF-8")
                    val contentType = errorBody!!.contentType()
                    if (contentType != null) {
                        charset = contentType.charset(charset)
                    }
                    val string = errorBody.source().readString(charset)
                    Timber.e(string)
                }
                return SERVICE_ERROR
            }
            httpException.code() == 404 -> LOCATION_ERROR
            httpException.code() == 403 -> SERVICE_ACCESS_DENIED_ERROR
            httpException.code() == 307 -> REQUEST_REDIRECT
            httpException.code() == 401 -> {
                handlerResponseError(
                    ServiceException(
                        AppResponseImpl(
                            CODE_ERROR_UNAUTHORIZED,
                            "",
                            null,
                            null
                        )
                    )
                )
                ACCOUNT_UNAUTHORIZED
            }
            else -> {
                var msg = ""
                try {
                    val serviceException = findServiceException(httpException)
                    if (serviceException != null) {
                        handlerResponseError(serviceException)
                    } else {
                        msg = httpException.message() ?: ""
                    }
                } catch (e: Exception) {
                    Timber.tag(TAG).e("service error cannot analysis ")
                    msg = httpException.message ?: ""
                }
                msg
            }
        }
    }

    public fun findServiceException(
        exception: Throwable
    ): ServiceException? {
        var serviceException: ServiceException? = null
        if (exception is HttpException) {
            val response = exception.response().errorBody()!!
            var responseString: String? = null
            responseString = response.string()
            if (responseString == null) {
                return null
            }
            val jsonObject = JSONObject(responseString)
            val code = jsonObject.getInt("code")
            val msg = jsonObject.getString("message") ?: ""
            serviceException = ServiceException(AppResponseImpl(code, msg, null, null))
        }
        return serviceException
    }

    @SuppressLint("CheckResult")
    public fun handlerResponseError(t: Throwable) {
        val message = when (t) {
            is ServiceException -> handlerServiceException(t)
            is HttpException -> convertStatusCode(t)
            is UnknownHostException -> NETWORK_ERROR
            is SocketTimeoutException -> NETWORK_TIME_OUT_ERROR
            // 正式环境解析失败不予展示，主要是是避免测试帐提神号的数据可能解析失败出现 Toast 提示
            is JsonParseException, is ParseException, is JSONException, is JsonIOException -> if (debug()) "$DATA_ANALYSIS_ERROR 异常：${t.message}" else ""
            else -> t.message
        }
        if (TextUtils.isEmpty(message)) {
            return
        }
        ToastUtils.showLong(message)
    }

    private fun handlerServiceException(t: ServiceException): String {
        exceptionHandlers.filter {
            it.handlerServiceException(t)
        }
        return t.message
    }

    fun <T : AppResponse<K>, K> interceptorErrorAndShellData(): (T) -> Observable<K> {
        return {
            if (it.success()) {
                Observable.just(it.data()!!)
            } else {
                Observable.error(ServiceException(it))
            }
        }
    }

    fun <T : AppResponse<K>, K> convertErrorReturn(): (Throwable) -> Observable<K> {
        return {
            Observable.empty<K>()
        }
    }

    fun <T : AppResponse<*>> interceptorError(): (T) -> Observable<T> {
        return {
            if (it.success()) {
                Observable.just(it)
            } else {
                Observable.error(ServiceException(it))
            }
        }
    }

    fun <T : AppResponse<*>> defaultErrorReturn(): (Throwable) -> Observable<T> {
        return {
            Observable.empty<T>()
        }
    }

    fun defaultErrorRetrySupplier(): (Throwable) -> RetryConfig {
        return {
            RetryConfig.none()
        }
    }

    fun defaultErrorConsumer(errorHandler: (e: Throwable) -> Unit): (Throwable) -> Unit {
        return {
            handlerResponseError(it)
            errorHandler(it)
        }
    }

}