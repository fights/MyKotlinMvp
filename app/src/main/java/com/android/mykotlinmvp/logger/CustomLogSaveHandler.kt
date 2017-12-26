package com.android.mykotlinmvp.logger

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * Created by zhangguanjun on 2017/12/26.
 *
 * 自定义日志保存
 */
class CustomLogSaveHandler(looper: Looper, folder: String, maxFileSize: Int) : Handler(looper) {
    private var folder: String = folder
    private var maxFileSize = maxFileSize

    override fun handleMessage(msg: Message?) {
        val content = msg?.obj as String

        var fileWriter: FileWriter? = null
        val logFile = getLogFile(folder, "logs")

        try {
            fileWriter = FileWriter(logFile, true)

            writeLog(fileWriter, content)

            fileWriter.flush()
            fileWriter.close()
        } catch (e: IOException) {
            if (fileWriter != null) {
                try {
                    fileWriter.flush()
                    fileWriter.close()
                } catch (e1: IOException) { /* fail silently */
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun writeLog(fileWriter: FileWriter, content: String) {
        fileWriter.append(content)
    }

    private fun getLogFile(folderName: String, fileName: String): File {

        val folder = File(folderName)
        if (!folder.exists()) {
            folder.mkdirs()
        }

        var newFileCount = 0
        var newFile: File
        var existingFile: File? = null

        newFile = File(folder, String.format("%s_%s.csv", fileName, newFileCount))
        while (newFile.exists()) {
            existingFile = newFile
            newFileCount++
            newFile = File(folder, String.format("%s_%s.csv", fileName, newFileCount))
        }

        return if (existingFile != null) {
            if (existingFile.length() >= maxFileSize) {
                newFile
            } else existingFile
        } else newFile

    }
}