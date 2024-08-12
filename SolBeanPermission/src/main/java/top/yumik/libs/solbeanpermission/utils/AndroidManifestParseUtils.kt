package top.yumik.libs.solbeanpermission.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.XmlResourceParser
import org.xmlpull.v1.XmlPullParser
import top.yumik.libs.solbeanpermission.model.AndroidManifestInfo


private const val ANDROID_MANIFEST_FILE_NAME = "AndroidManifest.xml"

private const val ANDROID_NAMESPACE_URI = "http://schemas.android.com/apk/res/android"

private const val TAG_MANIFEST = "manifest"

private const val TAG_USES_SDK = "uses-sdk"
private const val TAG_USES_PERMISSION = "uses-permission"
private const val TAG_USES_PERMISSION_SDK_23 = "uses-permission-sdk-23"
private const val TAG_USES_PERMISSION_SDK_M = "uses-permission-sdk-m"

private const val TAG_APPLICATION = "application"
private const val TAG_ACTIVITY = "activity"
private const val TAG_ACTIVITY_ALIAS = "activity-alias"
private const val TAG_SERVICE = "service"

private const val ATTR_PACKAGE = "package"
private const val ATTR_NAME = "name"
private const val ATTR_MAX_SDK_VERSION = "maxSdkVersion"
private const val ATTR_MIN_SDK_VERSION = "minSdkVersion"
private const val ATTR_TARGET_SDK_VERSION = "targetSdkVersion"
private const val ATTR_USES_PERMISSION_FLAGS = "usesPermissionFlags"
private const val ATTR_REQUEST_LEGACY_EXTERNAL_STORAGE = "requestLegacyExternalStorage"
private const val ATTR_SUPPORTS_PICTURE_IN_PICTURE = "supportsPictureInPicture"
private const val ATTR_PERMISSION = "permission"

private const val COOKIE_UNKNOWN = 0

internal object AndroidManifestParseUtils {

    private var androidManifestInfo: AndroidManifestInfo? = null

    fun parse(context: Context) = androidManifestInfo ?: AndroidManifestInfo(
        packageName = context.packageName
    ).apply {
        context.assets.openXmlResourceParser(getMyApkCookie(context), ANDROID_MANIFEST_FILE_NAME)
            .use { parser ->
                do {
                    if (parser.eventType != XmlPullParser.START_TAG) {
                        continue
                    }

                    when (parser.name) {
                        TAG_MANIFEST -> {
                            packageName = parser.parsePackageNameFromXml()
                        }

                        TAG_USES_SDK -> {
                            userSdk = parser.parseUserSdkFromXml()
                        }

                        TAG_USES_PERMISSION, TAG_USES_PERMISSION_SDK_23, TAG_USES_PERMISSION_SDK_M -> {
                            usesPermissions += parser.parseUsesPermissionFromXml()
                        }

                        TAG_APPLICATION -> {
                            application = parser.parseApplicationFromXml()
                        }

                        TAG_ACTIVITY, TAG_ACTIVITY_ALIAS -> {
                            activities += parser.parseActivityFromXml()
                        }

                        TAG_SERVICE -> {
                            services += parser.parseServiceFromXml()
                        }
                    }

                } while (parser.next() != XmlPullParser.END_DOCUMENT)
            }

        androidManifestInfo = this

    }

    private fun XmlResourceParser.parsePackageNameFromXml(): String {
        return getAttributeValue(null, ATTR_PACKAGE)
    }

    private fun XmlResourceParser.parseUserSdkFromXml(): AndroidManifestInfo.UserSdkInfo {
        val minSdkVersion = getAttributeIntValue(ANDROID_NAMESPACE_URI, ATTR_MIN_SDK_VERSION, 0)
        val targetSdkVersion =
            getAttributeIntValue(ANDROID_NAMESPACE_URI, ATTR_TARGET_SDK_VERSION, 0)
        return AndroidManifestInfo.UserSdkInfo(
            minSdkVersion = minSdkVersion,
            targetSdkVersion = targetSdkVersion
        )
    }

    private fun XmlResourceParser.parseUsesPermissionFromXml(): AndroidManifestInfo.UsesPermissionInfo {
        val name = getAttributeValue(ANDROID_NAMESPACE_URI, ATTR_NAME)
        val maxSdkVersion =
            getAttributeIntValue(ANDROID_NAMESPACE_URI, ATTR_MAX_SDK_VERSION, Int.MAX_VALUE)
        val userPermissionFlags =
            getAttributeIntValue(ANDROID_NAMESPACE_URI, ATTR_USES_PERMISSION_FLAGS, 0)
        return AndroidManifestInfo.UsesPermissionInfo(
            name = name,
            maxSdkVersion = maxSdkVersion,
            userPermissionFlags = userPermissionFlags
        )
    }

    private fun XmlResourceParser.parseApplicationFromXml(): AndroidManifestInfo.ApplicationInfo {
        val name = getAttributeValue(ANDROID_NAMESPACE_URI, ATTR_NAME) ?: ""
        val requestLegacyExternalStorage = getAttributeBooleanValue(
            ANDROID_NAMESPACE_URI,
            ATTR_REQUEST_LEGACY_EXTERNAL_STORAGE,
            false
        )
        return AndroidManifestInfo.ApplicationInfo(
            name = name,
            requestLegacyExternalStorage = requestLegacyExternalStorage
        )
    }

    private fun XmlResourceParser.parseActivityFromXml(): AndroidManifestInfo.ActivityInfo {
        val name = getAttributeValue(ANDROID_NAMESPACE_URI, ATTR_NAME) ?: ""
        val supportsPictureInPicture = getAttributeBooleanValue(
            ANDROID_NAMESPACE_URI,
            ATTR_SUPPORTS_PICTURE_IN_PICTURE,
            false
        )
        return AndroidManifestInfo.ActivityInfo(
            name = name,
            supportsPictureInPicture = supportsPictureInPicture
        )
    }

    private fun XmlResourceParser.parseServiceFromXml(): AndroidManifestInfo.ServiceInfo {
        val name = getAttributeValue(ANDROID_NAMESPACE_URI, ATTR_NAME) ?: ""
        val permission = getAttributeValue(ANDROID_NAMESPACE_URI, ATTR_PERMISSION) ?: ""
        return AndroidManifestInfo.ServiceInfo(
            name = name,
            permission = permission
        )
    }

    @SuppressLint("PrivateApi")
    private fun getMyApkCookie(context: Context): Int {
        val assert = context.assets
        val cookie = try {
            val addAssetPathMethod =
                assert.javaClass.getDeclaredMethod("addAssetPath", String::class.java)
            addAssetPathMethod.isAccessible = true
            addAssetPathMethod.invoke(assert, context.applicationInfo.sourceDir) as Int
        } catch (t: Throwable) {
            SolBeanLog.warn("Failed to get cookie from AssetManager: \n" + t.localizedMessage)
            COOKIE_UNKNOWN
        }
        return cookie
    }
}