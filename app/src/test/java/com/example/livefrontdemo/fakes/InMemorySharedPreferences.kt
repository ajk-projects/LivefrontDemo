package com.example.livefrontdemo.fakes

import android.content.SharedPreferences

class InMemorySharedPreferences : SharedPreferences {
    private var data = mutableMapOf<String?, Any?>()

    override fun getAll(): Map<String?, *>? = null

    override fun getString(key: String?, defValue: String?): String? = data[key] as? String ?: defValue

    override fun getStringSet(
        key: String?,
        defValues: Set<String?>?,
    ): Set<String?>? = defValues

    override fun getInt(key: String?, defValue: Int): Int = data[key] as? Int ?: defValue

    override fun getLong(key: String?, defValue: Long): Long = data[key] as? Long ?: defValue

    override fun getFloat(key: String?, defValue: Float): Float = data[key] as? Float ?: defValue

    override fun getBoolean(key: String?, defValue: Boolean): Boolean = data[key] as? Boolean ?: defValue

    override fun contains(key: String?): Boolean = data.containsKey(key)

    override fun edit(): SharedPreferences.Editor? = object : SharedPreferences.Editor {
        private val editedMap: MutableMap<String?, Any?> = data.toMutableMap()

        override fun putString(key: String?, value: String?): SharedPreferences.Editor? {
            editedMap[key] = value
            return this
        }

        override fun putStringSet(
            key: String?,
            values: Set<String?>?,
        ): SharedPreferences.Editor? {
            editedMap[key] = values
            return this
        }

        override fun putInt(key: String?, value: Int): SharedPreferences.Editor? {
            editedMap[key] = value
            return this
        }

        override fun putLong(key: String?, value: Long): SharedPreferences.Editor? {
            editedMap[key] = value
            return this
        }

        override fun putFloat(key: String?, value: Float): SharedPreferences.Editor? {
            editedMap[key] = value
            return this
        }

        override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor? {
            editedMap[key] = value
            return this
        }

        override fun remove(key: String?): SharedPreferences.Editor? {
            editedMap.remove(key)
            return this
        }

        override fun clear(): SharedPreferences.Editor? {
            editedMap.clear()
            return this
        }

        override fun commit(): Boolean {
            data = editedMap
            return true
        }

        override fun apply() {
            data = editedMap
        }
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {}

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {}
}