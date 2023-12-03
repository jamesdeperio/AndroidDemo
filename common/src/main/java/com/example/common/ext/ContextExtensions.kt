package com.example.common.ext

import android.content.Context
import android.provider.Settings

fun Context.isDeveloperOptionsEnabled(): Boolean {
    return Settings.Secure.getInt(this.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) == 1
}