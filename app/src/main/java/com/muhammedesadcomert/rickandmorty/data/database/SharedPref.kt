package com.muhammedesadcomert.rickandmorty.data.database

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Suppress("SameParameterValue")
class SharedPref @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPref: SharedPreferences

    var isFirstOpen: Boolean
        get() = getSharedPreferenceBoolean("is_first_open", true)
        set(value) = setSharedPreferenceBoolean("is_first_open", value)

    init {
        sharedPref = context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    }

    private fun setSharedPreferenceBoolean(key: String, value: Boolean) =
        sharedPref.edit().putBoolean(key, value).apply()

    private fun getSharedPreferenceBoolean(key: String?, defaultValue: Boolean): Boolean =
        sharedPref.getBoolean(key, defaultValue)
}