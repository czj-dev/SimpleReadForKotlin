package com.rank.basiclib.data

import com.google.gson.annotations.SerializedName
import com.rank.basiclib.ext.debug
import java.io.Serializable

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/4/29
 *     desc  :
 * </pre>
 */

class Environment(var domain: Domain) : Serializable {

}


data class Domain(
    @SerializedName("title") val tag: String, @SerializedName("api_url") val apiUrl: String, @SerializedName("web_url") val webUrl: String
) : Serializable {

    val webApi: String
        get() = if (debug()) "http://tapi.51ucar.cn/" else "http://api.51ucar.cn/"

    val webSocketUrl: String
        get() = if (debug()) "ws://14.29.197.207:9501" else "ws://120.79.219.3:9501"

    companion object {
        val RELEASE = Domain(
            Domain.NAME_RELEASE,
            Domain.URL_RELEASE_APP_DOMAIN,
            Domain.URL_RELEASE_WEB_DOMAIN
        )
        private const val NAME_RELEASE = "正式"
        const val URL_RELEASE_APP_DOMAIN = "https://v1.enjoyapi.cn/"
        const val URL_RELEASE_WEB_DOMAIN = "https://web.enjoycar.net/#/"
        const val URL_RELEASE_API_DOMAIN = "http://api.51ucar.cn/"
        const val URL_RELEASE_WEB_SOCKET_DOMAIN = "ws://120.79.219.3:9501"
    }
}