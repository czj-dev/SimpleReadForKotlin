package com.rank.basiclib.ext

import android.content.pm.ApplicationInfo
import android.text.TextUtils
import android.util.Base64
import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import com.google.gson.Gson
import com.rank.basiclib.application.BaseApplication
import com.rank.basiclib.utils.SPUtils
import com.rank.basiclib.utils.ViewUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :3
 *     time  : 2019/1/24
 *     desc  :
 * </pre>
 */
fun application() = ViewUtils.app

fun appComponent() = (ViewUtils.app as BaseApplication).appComponent

fun debug() = application().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0

fun BaseObservable.addChangedCallback(Unit: () -> Unit): Observable.OnPropertyChangedCallback {
    val value = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            Unit()
        }
    }
    this.addOnPropertyChangedCallback(value)
    return value
}

fun SPUtils.put(key: String, any: Any): Boolean {
    val baos = ByteArrayOutputStream()
    try {   //Device为自定义类
        // 创建对象输出流，并封装字节流
        val oos = ObjectOutputStream(baos)
        // 将对象写入字节流
        oos.writeObject(any)
        // 将字节流编码成base64的字符串
        val oAuth_Base64 = String(
            Base64.encode(
                baos
                    .toByteArray(), Base64.DEFAULT
            )
        )
        this.put(key, oAuth_Base64)
        return true
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}

fun <T> SPUtils.get(key: String): T? {
    var device: T? = null
    val productBase64: String? = this.getString(key)
    if (TextUtils.isEmpty(productBase64)) {
        return null
    }
    // 读取字节
    val base64 = Base64.decode(productBase64!!.toByteArray(), Base64.DEFAULT)
    // 封装到字节流
    val bais = ByteArrayInputStream(base64)
    try {
        // 再次封装
        val bis = ObjectInputStream(bais)
        // 读取对象
        device = bis.readObject() as T
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return device

}

inline fun <reified T : Any> Gson.fromJson(json: String): T = this.fromJson(json, T::class.java)

fun Boolean.real(function: () -> Unit) {
    if (this) {
        function()
    }
}

fun Boolean.fake(function: () -> Unit) {
    if (!this) {
        function()
    }
}