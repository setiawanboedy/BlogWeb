package com.tafakkur.blogweb.core.storage

import kotlinx.browser.window


class LocalStorageManager {
    fun setItem(key: String, value: String) {
        window.localStorage.setItem(key, value)
    }

    // Ambil item dari LocalStorage
    fun getItem(key: String): String? {
        return window.localStorage.getItem(key)
    }

    // Hapus item dari LocalStorage
    fun removeItem(key: String) {
        window.localStorage.removeItem(key)
    }
}