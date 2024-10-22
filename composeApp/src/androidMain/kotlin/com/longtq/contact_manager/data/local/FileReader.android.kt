package com.longtq.contact_manager.data.local

import android.content.res.AssetManager
import com.longtq.contact_manager.MyApplication

actual class FileReader actual constructor() {
    actual suspend fun getJsonFilesFromAssets(): List<String> {
        val assetManager: AssetManager = MyApplication.instance.assets
        return assetManager.list("")?.filter { it.endsWith(".json") } ?: emptyList()
    }

    actual suspend fun readContactFromJson(fileName: String): String {
        val assetManager: AssetManager = MyApplication.instance.assets
        return assetManager.open(fileName).bufferedReader().use { it.readText() }
    }
}