package top.yumik.libs.solbeanpermission.permission.special.v30

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.Settings
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.permission.special.ISpecialPermissionCompat
import top.yumik.libs.solbeanpermission.utils.PermissionChecker
import top.yumik.libs.solbeanpermission.utils.PermissionUtils
import top.yumik.libs.solbeanpermission.utils.SolBeanLog

internal class ManageExternalStorageCompat : ISpecialPermissionCompat {

    override val targetApi: Int = AndroidVersion.ANDROID_11

    override val permission: String = Permission.MANAGE_EXTERNAL_STORAGE

    override fun isGranted(context: Context): Boolean {
        if (AndroidVersion.isAndroid11OrAbove()) {
            return Environment.isExternalStorageManager()
        }

        if (AndroidVersion.getAndroidVersionCode() == AndroidVersion.ANDROID_10 && !checkIsExternalStorageLegacy()) {
            return false
        }

        return super.isGranted(context)
    }

    override fun isNeverAsk(activity: Activity): Boolean {
        if (AndroidVersion.getAndroidVersionCode() == AndroidVersion.ANDROID_10 && !checkIsExternalStorageLegacy()) {
            return true
        }

        return super.isNeverAsk(activity)
    }

    @SuppressLint("NewApi")
    private fun checkIsExternalStorageLegacy(): Boolean {
        return Environment.isExternalStorageLegacy()
    }

    override fun getIntent(context: Context): Intent {
        if (AndroidVersion.isAndroid11OrAbove()) {
            var intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.setData(PermissionUtils.getPackageUri(context))
            if (PermissionChecker.checkIntentExists(context, intent)) {
                return intent
            }

            intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            if (PermissionChecker.checkIntentExists(context, intent)) {
                return intent
            }
        }

        return super.getIntent(context)
    }

    override fun checkManifest(context: Context): Boolean {
        // 如果应用的 TargetSDK 等于 Android 10，但未配置了`requestLegacyExternalStorage`则不应该申请该权限
        if (AndroidVersion.getTargetSdkVersionCode(context) == AndroidVersion.ANDROID_10 &&
            !PermissionChecker.checkManifestRequestLegacyExternalStorage(context)
        ) {
            SolBeanLog.error(
                context,
                "当前 APP 的 TargetSDK 版本为 10，必须添加 android:requestLegacyExternalStorage=\"true\" 到 AndroidManifest.xml 文件中，" +
                        "或者可以适配分区存储权限，此时无需申请该权限。"
            )
            return false
        }

        // 如果应用的 TargetSDK 大于或等于 Android 11，`requestLegacyExternalStorage`会被系统忽略，但可以通过XML读取。
        // 因此我们的SDK会基于该参数判断你是否适配了分区存储，如果未配置该值，你同样不需要申请该权限
        if (AndroidVersion.getTargetSdkVersionCode(context) >= AndroidVersion.ANDROID_11 &&
            !PermissionChecker.checkManifestRequestLegacyExternalStorage(context)
        ) {
            SolBeanLog.error(
                context,
                "当前 APP 的 TargetSDK 版本高于 11，为了保证框架逻辑一致性，你需要配置android:requestLegacyExternalStorage=\"true\"才可以申请该权限，" +
                        "否则框架会视为你已经适配了分区存储，此时不需要申请该权限。"
            )
            return false
        }

        return super.checkManifest(context)
    }

    override fun config(context: Context): PermissionConfig {
        // 如果应用的 TargetSDK 小于或等于 Android 10 则降级
        if (!AndroidVersion.isAndroid11OrAbove()) {
            return PermissionConfig.Replace(
                permissions = setOf(
                    Permission.READ_EXTERNAL_STORAGE,
                    Permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }

        return super.config(context)
    }
}