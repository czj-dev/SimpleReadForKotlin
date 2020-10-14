package com.rank.basiclib.utils

import com.rank.basiclib.data.Domain
import com.rank.basiclib.data.Environment
import com.rank.basiclib.ext.application
import com.rank.basiclib.ext.get
import com.rank.basiclib.ext.put
import javax.inject.Inject

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/15
 *     desc  :
 * </pre>
 */
class Preferences @Inject constructor() {

    companion object {
        private const val DATA_KEY_CONFIG = "config_preferences"
        private const val DATA_KEY_USER = "user_preferences"

        const val USER = "user_info"
        const val TOKEN = "user_token"
        const val SETTING_PASSWORD_TOKEN = "setting_password_token"
        private const val ENVIRONMENT = "environment"
        const val REFRESH_TOKEN_DATE = "refresh_token_date"
        const val NOTIFICATION_PROMPT_DATE = "notification_prompt_date"
        const val KEY_SETTING = "key_setting"
        const val SETTING_HISTORY_MOBILE = "setting_history_mobile"


        fun userPreferences() = SPUtils.getInstance(DATA_KEY_USER)!!

        fun configPreferences() = SPUtils.getInstance(DATA_KEY_CONFIG)!!

        fun imagesDir() = application().getExternalFilesDir("xcar_images")!!

        fun fileDir() = application().getExternalFilesDir("xcar_files")!!

        var environment: Environment = providerEnvironment()
            set(value) {
                configPreferences().put(ENVIRONMENT, value)
                field = value
            }

        private fun providerEnvironment(): Environment {
            var environment = configPreferences().get<Environment>(ENVIRONMENT)
            if (environment == null) {
                environment = Environment(Domain.RELEASE)
                configPreferences().put(ENVIRONMENT, environment)
            }
            return environment
        }
    }
}