package com.rank.basiclib.error

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/2/15
 *     desc  : 组件处理自己的服务异常
 * </pre>
 */
interface ServiceErrorHandler {

    /**
     * 返回 true 则表示拦截不继续向下分发
     */
    fun handlerServiceException(t: ServiceException): Boolean

}