package com.rank.basiclib.di;

import android.text.TextUtils;

import com.rank.basiclib.data.Domain;
import com.rank.basiclib.data.Environment;
import com.rank.basiclib.ext.CommonExtKt;
import com.rank.basiclib.utils.Preferences;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2018/11/14
 *     desc  :
 * </pre>
 */
public class ApiManager {
    private static final String HTTP = "http";
    private static final String UCREDIT_SERVICE_CONTRACT = "creditservice";
    private static final String QUICK_PAY = "ii/quickpass/quickpassmain";
    private String host;

    private static class ApiManagerHolder {
        static final ApiManager INSTANCE = new ApiManager();
    }

    public static ApiManager getInstance() {
        return ApiManagerHolder.INSTANCE;
    }

    public String getBaseUrl() {
        if (CommonExtKt.debug()) {
            return getDomain().getApiUrl();
        }
        return Domain.URL_RELEASE_APP_DOMAIN;
    }


    public String getWebUrlHeader() {
        if (CommonExtKt.debug()) {
            return getDomain().getWebUrl();
        }
        return Domain.URL_RELEASE_WEB_DOMAIN;
    }


    public String getWebApi() {
        if (CommonExtKt.debug()) {
            return getDomain().getWebApi();
        }
        return Domain.URL_RELEASE_API_DOMAIN;
    }

    public String getWebSocketUrl() {
        if (CommonExtKt.debug()) {
            return getDomain().getWebSocketUrl();
        }
        return Domain.URL_RELEASE_WEB_SOCKET_DOMAIN;
    }

    public String getQuickPayUrl() {
        return getWebUrlHeader() + QUICK_PAY;
    }

    public String getHost() {
        if (host == null) {
            try {
                host = new URL(getBaseUrl()).getHost();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return host;
    }

    public String getUcreditServiceContractUrl() {
        return getWebUrlHeader() + UCREDIT_SERVICE_CONTRACT;
    }


    public static void setDomain(Domain value) {
        final Environment environment = Preferences.Companion.getEnvironment();
        environment.setDomain(value);
        Preferences.Companion.setEnvironment(environment);
    }


    public static Domain getDomain() {
        Environment environment = Preferences.Companion.getEnvironment();
        return environment.getDomain();
    }

    public static class Chart {
        public static final String USER_PROTOCOL = "user";
        public static final String USER_AGREEMENT = "privacyPolicy";
    }

    public static String adjustUrl(String url) {
        if (!TextUtils.isEmpty(url) && url.indexOf(HTTP) != 0) {
            url = ApiManager.getInstance().getWebUrlHeader() + url;
        }
        return url;
    }
}
