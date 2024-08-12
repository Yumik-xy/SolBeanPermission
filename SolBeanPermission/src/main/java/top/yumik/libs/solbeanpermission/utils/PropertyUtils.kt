package top.yumik.libs.solbeanpermission.utils

import android.annotation.SuppressLint
import android.os.Environment
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

/**
 * 系统 prop 属性读取工具
 */
internal object PropertyUtils {

    /**
     * 查询对应属性的返回值
     *
     * @param propertyName 属性名
     *
     * @return 返回属性值，如果没有则返回空字符串
     */
    fun getPropertyName(propertyName: String): String {
        if (propertyName.isNotBlank()) {
            return getSystemProperty(propertyName)
        }

        return ""
    }

    /**
     * 获取系统属性的包装方法
     * 1. 通过 shell 获取系统属性
     * 2. 通过读取 build.prop 文件获取系统属性
     * 3. 通过反射获取系统属性
     */
    private fun getSystemProperty(name: String): String {
        var prop = getSystemPropertyByShell(name)
        if (prop.isBlank()) {
            prop = getSystemPropertyByStream(name)
        }
        if (prop.isBlank() && !AndroidVersion.isAndroid9OrAbove()) {
            prop = getSystemPropertyByReflect(name)
        }
        return prop
    }

    /**
     * 通过 shell 获取系统属性
     */
    private fun getSystemPropertyByShell(name: String): String {
        try {
            val process = Runtime.getRuntime().exec("getprop $name")
            val line = BufferedReader(InputStreamReader(process.inputStream), 1024).use { input ->
                input.readLine()
            }
            if (line != null) {
                return line
            }
        } catch (e: Exception) {
            SolBeanLog.warn("Failed to get system property by shell: \n" + e.localizedMessage)
        }
        return ""
    }

    /**
     * 通过读取build.prop文件获取系统属性
     */
    private fun getSystemPropertyByStream(name: String): String {
        try {
            val prop = Properties()
            FileInputStream(File(Environment.getRootDirectory(), "build.prop")).use { input ->
                prop.load(input)
            }
            return prop.getProperty(name, "")
        } catch (e: Exception) {
            SolBeanLog.warn("Failed to get system property by stream: \n" + e.localizedMessage)
        }
        return ""
    }

    /**
     * 通过反射获取系统属性
     */
    @SuppressLint("PrivateApi")
    private fun getSystemPropertyByReflect(name: String): String {
        try {
            val clz = Class.forName("android.os.SystemProperties")
            val method = clz.getMethod("get", String::class.java, String::class.java)
            return method.invoke(clz, name, "") as String
        } catch (e: Exception) {
            SolBeanLog.warn("Failed to get system property by reflect: \n" + e.localizedMessage)
        }
        return ""
    }
}