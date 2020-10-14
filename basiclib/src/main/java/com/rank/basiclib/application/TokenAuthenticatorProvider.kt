package com.rank.basiclib.application

import okhttp3.Authenticator

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/4/19
 *     desc  :
 * </pre>
 */
interface TokenAuthenticatorProvider {

    fun authenticator(): Authenticator

}