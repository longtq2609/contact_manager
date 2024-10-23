package com.longtq.contact_manager.data.local

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.stringWithContentsOfFile

actual class FileReader actual constructor() {
    actual suspend fun getJsonFilesFromAssets(): List<String> {
        val bundle = NSBundle.mainBundle
        val paths = bundle.pathsForResourcesOfType("json", null)
        return paths.mapNotNull { path ->
            NSURL.fileURLWithPath(path.toString()).lastPathComponent
        }


    }

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun readContactFromJson(fileName: String): String {
        val fileNameWithoutExtension = fileName.removeSuffix(".json")

        val bundle = NSBundle.mainBundle
        val filePath = bundle.pathForResource(fileNameWithoutExtension, ofType = "json")
            ?: throw IllegalArgumentException("File $fileNameWithoutExtension.json not found in bundle")

        return NSString.stringWithContentsOfFile(filePath, encoding = 4u, error = null)
            ?: throw IllegalArgumentException("Failed to read the content of file $fileNameWithoutExtension.json")
    }
}