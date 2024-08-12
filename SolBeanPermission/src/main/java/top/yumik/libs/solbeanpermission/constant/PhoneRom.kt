package top.yumik.libs.solbeanpermission.constant

import android.annotation.SuppressLint
import android.os.Build
import top.yumik.libs.solbeanpermission.utils.PropertyUtils

/**
 * 获取手机 ROM 信息
 */
object PhoneRom {

    private val brand: String
        get() = Build.BRAND.lowercase()

    private val manufacturer: String
        get() = Build.MANUFACTURER.lowercase()

    enum class Rom(private vararg val keys: String) {
        HUAWEI("huawei"),
        XIAOMI("xiaomi"),
        OPPO("oppo"),
        VIVO("vivo"),
        LEECO("leeco", "letv"),
        ZTE("zte"),
        ONEPLUS("oneplus"),
        NUBIA("nubia"),
        SAMSUMG("samsung"),
        HONOR("honor"),
        LENOVO("lenovo");

        fun isRom(): Boolean {
            return keys.any { key ->
                brand.contains(key) || manufacturer.contains(key)
            }
        }
    }

    enum class Property(private vararg val properties: String) {
        HUAWEI("ro.build.version.emui"),
        XIAOMI("ro.build.version.incremental"),
        OPPO("ro.build.version.opporom", "ro.build.version.oplusrom.display"),
        VIVO("ro.vivo.os.build.display.id"),
        LEECO("ro.letv.release.version"),
        ZTE("ro.build.MiFavor_version"),
        ONEPLUS("ro.rom.version"),
        NUBIA("ro.build.rom.id", "ro.build.display.id"),
        MIUI("ro.miui.ui.version.code"),
        HONOR("msc.config.magic.version", "ro.build.version.magic"),
        ELSE("ro.build.display.id");

        fun hasProperty(): Boolean {
            return properties.any { property ->
                PropertyUtils.getPropertyName(property).isNotBlank()
            }
        }

        fun getPropertyValue(): String {
            return properties.map { property ->
                PropertyUtils.getPropertyName(property)
            }.firstOrNull { it.isNotBlank() }
                ?: "unknown"
        }
    }

    fun isHuaweiEmui(): Boolean {
        return !isHuaweiHarmonyOs() && Property.HUAWEI.hasProperty()
    }

    fun isHuaweiHarmonyOs(): Boolean {
        if (!AndroidVersion.isAndroid10OrAbove()) {
            return false
        }

        return try {
            val clz = Class.forName("com.huawei.system.BuildEx")
            val method = clz.getMethod("getOsBrand")
            val osBrand = method.invoke(clz) as? String
            "harmony".equals(osBrand, ignoreCase = true)
        } catch (t: Throwable) {
            false
        }
    }

    fun isXiaoMiMiui(): Boolean {
        return Property.MIUI.hasProperty()
    }

    fun isOppoColorOs(): Boolean {
        return Property.OPPO.hasProperty()
    }

    fun isVivoOriginOs(): Boolean {
        return Property.VIVO.hasProperty()
    }

    fun isSamsungOneUi(): Boolean {
        return Rom.SAMSUMG.isRom()
    }

    fun isHonorMagicOs(): Boolean {
        return Rom.HONOR.isRom()
    }

    /**
     * 是否开启了小米 MIUI 优化开关，国内版可以通过开关关闭，国际版没有开关。默认开启。
     *
     * 关闭路径：设置 -> 更多设置 -> 开发者选项 -> MIUI 优化
     */
    @SuppressLint("PrivateApi")
    fun isXiaoMiMiuiOptimization(): Boolean {
        return try {
            val clz = Class.forName("android.os.SystemProperties")
            val getMethod = clz.getMethod("get", String::class.java, String::class.java)
            val ctsValue = getMethod.invoke(clz, "ro.miui.cts", "") as? String
            val getBooleanMethod = clz.getMethod("getBoolean", String::class.java, Boolean::class.java)
            val isOptimization = getBooleanMethod.invoke(clz, "persist.sys.miui_optimization", ctsValue != "1") as? Boolean
            isOptimization == false
        } catch (t: Throwable) {
            true
        }
    }

    fun getRomVersionName(): String {
        when {
            Rom.HUAWEI.isRom() -> {
                val version = Property.HUAWEI.getPropertyValue()
                val splitVersion = version.split("_")
                return if (splitVersion.size > 1) splitVersion[1] else version
            }

            Rom.VIVO.isRom() -> {
                // 需要注意的是 vivo iQOO 9 Pro Android 12 获取到的厂商版本号是 OriginOS Ocean
                return Property.VIVO.getPropertyValue()
            }

            Rom.XIAOMI.isRom() -> {
                return Property.XIAOMI.getPropertyValue()
            }

            Rom.OPPO.isRom() -> {
                return Property.OPPO.getPropertyValue()
            }

            Rom.LEECO.isRom() -> {
                return Property.LEECO.getPropertyValue()
            }

            Rom.ZTE.isRom() -> {
                return Property.ZTE.getPropertyValue()
            }

            Rom.ONEPLUS.isRom() -> {
                return Property.ONEPLUS.getPropertyValue()
            }

            Rom.NUBIA.isRom() -> {
                return Property.NUBIA.getPropertyValue()
            }

            Rom.HONOR.isRom() -> {
                return Property.HONOR.getPropertyValue()
            }

            else -> {
                return Property.ELSE.getPropertyValue()
            }
        }
    }
}