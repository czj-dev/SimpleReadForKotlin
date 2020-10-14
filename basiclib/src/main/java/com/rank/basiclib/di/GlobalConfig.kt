package com.rank.basiclib.di

import com.rank.basiclib.error.ServiceErrorHandler
import com.rank.basiclib.error.ServiceException
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/4/19
 *     desc  :
 * </pre>
 */
data class GlobalConfig constructor(
    val httpInterceptor: MutableList<Interceptor>?,
    val handlerServiceError:ServiceErrorHandler,
    val onHttpResultResponse: (httpResult: String?, chain: Interceptor.Chain, response: Response) -> Response,
    val onHttpRequestBefore: (chain: Interceptor.Chain, request: Request) -> Request,
    val authenticator: Authenticator?
) {


    class Builder {
        private var httpInterceptor: MutableList<Interceptor>? = null
        private var handlerServiceError: ServiceErrorHandler = object : ServiceErrorHandler {
            override fun handlerServiceException(t: ServiceException): Boolean {
                return false
            }
        }
        private var handleHttpResultResponse: (httpResult: String?, chain: Interceptor.Chain, response: Response) -> Response =
            { _, _, response -> response }
        private var handleHttpRequestBefore: (chain: Interceptor.Chain, request: Request) -> Request =
            { _, request -> request }
        private var authenticator: Authenticator? = null

        fun authenticator(authenticator: Authenticator): Builder {
            this.authenticator = authenticator
            return this
        }

        fun interceptors(httpInterceptor: MutableList<Interceptor>): Builder {
            this.httpInterceptor = httpInterceptor
            return this
        }

        fun handlerServiceError(handlerServiceError: ServiceErrorHandler): Builder {
            this.handlerServiceError = handlerServiceError
            return this
        }

        fun handleHttpResultResponse(handleHttpResultResponse: (httpResult: String?, chain: Interceptor.Chain, response: Response) -> Response): Builder {
            this.handleHttpResultResponse = handleHttpResultResponse
            return this
        }

        fun handleHttpRequestBefore(handleHttpRequestBefore: (chain: Interceptor.Chain, request: Request) -> Request): Builder {
            this.handleHttpRequestBefore = handleHttpRequestBefore
            return this
        }

        fun build() =
            GlobalConfig(
                httpInterceptor,
                handlerServiceError,
                handleHttpResultResponse,
                handleHttpRequestBefore,
                authenticator
            )
    }
}