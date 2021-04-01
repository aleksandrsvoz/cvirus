package com.alexvoz.template.repository.cache

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject


class AppFilesLocalStorage @Inject constructor(@ApplicationContext appContext: Context) {
    private var appFilesLocalStoragePath = ""

    init {
        val contextWrapper = ContextWrapper(appContext.applicationContext)
        val directory: File = contextWrapper.getDir("images", Context.MODE_PRIVATE)
        if (!directory.exists()) {
            directory.mkdir()
        }

        appFilesLocalStoragePath = directory.path
    }

    fun saveImage(responseInputStream: InputStream?, fileName: String): String {
        val file = File(appFilesLocalStoragePath, fileName)
        val bitmap = BitmapFactory.decodeStream(responseInputStream)

        try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.path
    }
}