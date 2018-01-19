@file:Suppress("UNCHECKED_CAST")

package com.android.mykotlinmvp.utils

import android.content.Context
import android.text.TextUtils
import android.util.Base64
import com.android.mykotlinmvp.MyApplication
import java.io.*

/**
 * Created by zhangguanjun on 2018/1/19.
 */
object SpUtil{

    fun putNormalValue(fileName: String, key: String, value: Any?) {
        val sp = MyApplication.context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        sp.edit().apply {
            when (value) {
                is Int -> putInt(key,value)
                is String -> putString(key,value)
                is Float -> putFloat(key,value)
                is Boolean -> putBoolean(key,value)
                is Long -> putLong(key,value)
                else -> putString(key, serialize(value))
            }
        }.apply()
    }

    fun <T> getNormalValue(fileName: String, key: String, defaultValue: T): T{
        val sp = MyApplication.context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val value: Any = sp.apply {
            when (defaultValue) {
                is Int -> getInt(key,defaultValue)
                is String -> getString(key,defaultValue)
                is Float -> getFloat(key,defaultValue)
                is Boolean -> getBoolean(key,defaultValue)
                is Long -> getLong(key,defaultValue)
                else -> deSerialization(getString(key, serialize(defaultValue)))
            }
        }
        return value as T
    }

    fun putObject(fileName: String, key: String, value: Any?) {
        val sp = MyApplication.context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        if (value == null) {
            editor.remove(key).apply()
        }

        val bos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(bos)
        oos.writeObject(value)

        //将对象转成byte数组， 并且Base64转换
        val encodeString = String(Base64.encode(bos.toByteArray(), Base64.DEFAULT))
        //关流
        oos.close()
        bos.close()

        //将其保存在sp中
        editor.putString(key, encodeString).apply()
    }

    fun getObject(fileName: String, key: String): Any?{
        val sp = MyApplication.context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val spString = sp.getString(key, "")
        if (TextUtils.isEmpty(spString)) {
            return null
        }

        //进行Base64解码
        val bytes = Base64.decode(spString, Base64.DEFAULT)
        //通过输入流转写
        val bis = ByteArrayInputStream(bytes)
        val ois = ObjectInputStream(bis)
        val readObject = ois.readObject()
        ois.close()
        bis.close()
        return readObject
    }

    fun contains(fileName: String, key: String): Boolean {
        val sp = MyApplication.context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sp.contains(key)
    }

    fun getAll(fileName: String): MutableMap<String, *>? {
        val sp = MyApplication.context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sp.all
    }

    fun remove(fileName: String, key: String) {
        val sp = MyApplication.context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        sp.edit().remove(key).apply()
    }

    fun clear(fileName: String) {
        val sp = MyApplication.context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        sp.edit().clear().apply()
    }

    /**
     * 序列化对象

     * @param person
     * *
     * @return
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun<A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(
                byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    /**
     * 反序列化对象

     * @param str
     * *
     * @return
     * *
     * @throws IOException
     * *
     * @throws ClassNotFoundException
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun<A> deSerialization(str: String): A {
        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
                redStr.toByteArray(charset("ISO-8859-1")))
        val objectInputStream = ObjectInputStream(
                byteArrayInputStream)
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }


}