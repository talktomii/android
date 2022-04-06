package com.talktomii.utlis

import android.content.SharedPreferences
import androidx.annotation.StringDef
import com.google.gson.Gson
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsManager @Inject constructor(
    private val preferences: SharedPreferences,
    private val gson: Gson
) {
    private val sharedPrefName = "ArabLife"

    companion object {
        const val WELCOME: String = "WELCOME"
        const val USERNAME: String = "USERNAME"
        const val PREF_NOTIFICATION: String = "PREF_NOTIFICATION"
        const val PREF_USER_IMAGE: String = "PREF_USER_IMAGE"
        const val PREF_PROFILE = "PREF_PROFILE"
        const val PREF_API_TOKEN = "PREF_API_TOKEN"
        const val GUEST = "GUEST"
        const val PREF_STEPS = "STEPS"
        const val PREF_VERIFYRESPONSE = "VERIFYRESPONSE"
        const val FIRST_TIME = "FIRST_TIME"
        const val LOCAL = "LOCAL"

        @StringDef(PREF_PROFILE, PREF_API_TOKEN)
        @Retention(AnnotationRetention.SOURCE)
        annotation class PrefKey

        private lateinit var instance: PrefsManager

        private val isInitialized =
            AtomicBoolean()     // To check if instance was previously initialized or not

        fun get(): PrefsManager = instance
    }

    fun save(@PrefKey key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun save(@PrefKey key: String, value: Int?) {
        preferences.edit().putInt(key, value ?: 0).apply()
    }

    fun save(@PrefKey key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun save(@PrefKey key: String, `object`: Any?) {
        if (`object` == null) {
            throw IllegalArgumentException("object is null")
        }

        // Convert the provided object to JSON string
        save(key, gson.toJson(`object`))
    }

    fun getString(@PrefKey key: String, defValue: String): String =
        preferences.getString(key, defValue) ?: ""

    fun getInt(@PrefKey key: String, defValue: Int): Int = preferences.getInt(key, defValue)

    fun getFloat(@PrefKey key: String, defValue: Float): Float = preferences.getFloat(key, defValue)

    fun getBoolean(@PrefKey key: String, defValue: Boolean): Boolean =
        preferences.getBoolean(key, defValue)

    fun <T> getObject(@PrefKey key: String, objectClass: Class<T>): T? {
        val jsonString = preferences.getString(key, null)
        return if (jsonString == null) {
            null
        } else {
            try {
                gson.fromJson(jsonString, objectClass)
            } catch (e: Exception) {
                throw IllegalArgumentException("Object stored with key $key is instance of other class")
            }
        }
    }

    fun removeAll() {
        preferences.edit().clear().apply()
    }

    fun remove(key: String) {
        preferences.edit().remove(PREF_API_TOKEN).apply()
        preferences.edit().remove(PREF_PROFILE).apply()
        preferences.edit().remove(PREF_STEPS).apply()
        preferences.edit().remove(key).apply()
    }

    /**
     * Store boolean in preference.
     *
     * @param key   the key
     * @param value the value
     */
    fun storeBooleanInPreference(key: String, value: Boolean) {
        save(key, value)
    }

    /**
     * Gets the boolean from preference.
     *
     * @param key the key
     * @return boolean Value from preference
     */
    fun getBooleanFromPreference(key: String): Boolean {
        return getBoolean(key, false)
    }

    /**
     * Gets the int from preference.
     *
     * @param key the key
     * @return String Value from preference
     */
    fun getIntFromPreference(key: String): Int {
        return getInt(key, 0)
    }


    /**
     * Clear all preference.
     */
    fun clearAllPreference() {
        removeAll()
    }

    /**
     * Store string in preference.
     *
     * @param key   the key
     * @param value the value
     */
    fun storeStringInPreference(key: String, value: String?) {
        save(key, value)
    }

    /**
     * Store int in preference.
     *
     * @param key   the key
     * @param value the value
     */
    fun storeIntInPreference(key: String, value: Int) {
        save(key, value)
    }

    fun getBooleanFromPreferenceDefaultTrueValue(key: String): Boolean {
        return getBoolean(key, true)
    }

    //save local language
    fun saveDefaultLocal(lang: String) {
        save(LOCAL, lang)
    }

    fun getDefaultLocale(): String? {
        return getString(LOCAL, "")
    }

}