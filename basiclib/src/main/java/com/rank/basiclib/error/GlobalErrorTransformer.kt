package com.rank.basiclib.error

import io.reactivex.*

typealias OnNextInterceptor<T, K> = (T) -> Observable<K>
typealias OnErrorResumeNext<T> = (Throwable) -> Observable<T>
typealias OnErrorRetrySupplier = (Throwable) -> RetryConfig
typealias OnErrorConsumer = (Throwable) -> Unit

/**
 * 扩展Rx处理错误的能力，参考自 <p> https://github.com/qingmei2/RxWeaver </p>
 * onNextInterceptor: 提前判断数据业务逻辑，筛选出错误拦截并抛出异常
 * onErrorResumeNext：出现错误后再次发射一个正常数据，默认处理是 empty()
 * onErrorRetrySupplier: 重试条件
 * onErrorConsumer：自己二次处理错误实现
 */
class GlobalErrorTransformer<T, K> constructor(
        private val onNextInterceptor: OnNextInterceptor<T, K>,
        private val onErrorResumeNext: OnErrorResumeNext<K> = { Observable.empty<K>() },
        private val onErrorRetrySupplier: OnErrorRetrySupplier = { RetryConfig.none() },
        private val onErrorConsumer: OnErrorConsumer = { }
) : ObservableTransformer<T, K>, FlowableTransformer<T, K>, SingleTransformer<T, K>,
        MaybeTransformer<T, K>, CompletableTransformer {

    override fun apply(upstream: Observable<T>): Observable<K> =
            upstream
                    .flatMap {
                        onNextInterceptor(it)
                    }
                    .retryWhen(ObservableRetryDelay(onErrorRetrySupplier))
                    .doOnError(onErrorConsumer)
                    .onErrorResumeNext { throwable: Throwable ->
                        onErrorResumeNext(throwable)
                    }


    override fun apply(upstream: Completable): Completable =
            upstream
                    .retryWhen(FlowableRetryDelay(onErrorRetrySupplier))
                    .doOnError(onErrorConsumer)
                    .onErrorResumeNext {
                        onErrorResumeNext(it).ignoreElements()
                    }

    override fun apply(upstream: Flowable<T>): Flowable<K> =
            upstream
                    .flatMap {
                        onNextInterceptor(it)
                                .toFlowable(BackpressureStrategy.BUFFER)
                    }
                    .retryWhen(FlowableRetryDelay(onErrorRetrySupplier))
                    .doOnError(onErrorConsumer)
                    .onErrorResumeNext { throwable: Throwable ->
                        onErrorResumeNext(throwable)
                                .toFlowable(BackpressureStrategy.BUFFER)
                    }

    override fun apply(upstream: Maybe<T>): Maybe<K> =
            upstream
                    .flatMap {
                        onNextInterceptor(it)
                                .firstElement()
                    }
                    .retryWhen(FlowableRetryDelay(onErrorRetrySupplier))
                    .doOnError(onErrorConsumer)
                    .onErrorResumeNext { throwable: Throwable ->
                        onErrorResumeNext(throwable)
                                .firstElement()
                    }

    override fun apply(upstream: Single<T>): Single<K> =
            upstream
                    .flatMap {
                        onNextInterceptor(it)
                                .firstOrError()
                    }
                    .retryWhen(FlowableRetryDelay(onErrorRetrySupplier))
                    .doOnError(onErrorConsumer)
                    .onErrorResumeNext { throwable ->
                        onErrorResumeNext(throwable)
                                .firstOrError()
                    }
}