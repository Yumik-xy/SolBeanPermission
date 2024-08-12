package top.yumik.libs.solbeanpermission.constant

import android.Manifest
import android.content.pm.PackageInfo

/**
 * 权限常量，完整的权限列表请参考 [Manifest.permission]
 *
 * @see <a href="https://www.taf.org.cn/upload/AssociationStandard/TTAF%20004-2017%20Android%E6%9D%83%E9%99%90%E8%B0%83%E7%94%A8%E5%BC%80%E5%8F%91%E8%80%85%E6%8C%87%E5%8D%97.pdf">Android 应用权限调用开发者指南</a>
 */
object Permission {

    /**
     * 读取应用列表权限，国产手机特有的权限，其他机型只需要拥有 QUERY_ALL_PACKAGES 权限即可
     *
     * 注意事项：
     *   1. 权限需要在 Manifest 文件中配置 QUERY_ALL_PACKAGES 权限，否则在 Android 11 以上也无法获取到列表
     *   2. 该权限在部分国产手机上的实现尚未完整，需要基于业务场景进行检查
     *
     * @see <a href="https://www.taf.org.cn/StdDetail.aspx?uid=3A7D6656-43B8-4C46-8871-E379A3EA1D48&stdType=TAF">T/TAF 108-2022 移动终端应用软件列表权限实施指南</a>
     */
    const val GET_INSTALLED_APPS = "com.android.permission.GET_INSTALLED_APPS"

    /**
     * 允许应用访问设备上的照片、媒体和文件的权限
     */
    const val QUERY_ALL_PACKAGES = "android.permission.QUERY_ALL_PACKAGES"

    /**
     * 允许应用读取用户日历数据
     */
    const val READ_CALENDAR = "android.permission.READ_CALENDAR"

    /**
     * 允许应用写入用户日历数据
     */
    const val WRITE_CALENDAR = "android.permission.WRITE_CALENDAR"

    /**
     * 允许应用使用照相设备
     */
    const val CAMERA = "android.permission.CAMERA"

    /**
     * 允许应用读取用户联系人数据。
     */
    const val READ_CONTACTS = "android.permission.READ_CONTACTS"

    /**
     * 允许应用写入用户联系人数据
     */
    const val WRITE_CONTACTS = "android.permission.WRITE_CONTACTS"

    /**
     * 访问手机中的帐户列表
     */
    const val GET_ACCOUNTS = "android.permission.GET_ACCOUNTS"

    /**
     * 允许应用访问精确位置
     */
    const val ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION"

    /**
     * 允许应用访问粗略位置信息
     */
    const val ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION"

    /**
     * 允许应用录制音频。该权限可让应用不经用户确认即可随时录制音频。
     */
    const val RECORD_AUDIO = "android.permission.RECORD_AUDIO"

    /**
     * 允许应用读取手机状态，包括手机的号码、当前蜂窝网络信息、正在进行的通话信息（是否正处于通话状态以及拨打的号码）、以及手机上注册的账户列表。
     *
     * 注意事项：
     *   1. 某些机型限制了该权限，导致该权限不会弹出授权弹窗而是直接失败。
     *   2. 部分机型针对这类权限做了特殊返回值，即使授权成功也无法获取到真实的数据。
     */
    const val READ_PHONE_STATE = "android.permission.READ_PHONE_STATE"

    /**
     * 允许应用直接拨打某个电话号码，不需要用户通过拨号用户界面确认
     */
    const val CALL_PHONE = "android.permission.CALL_PHONE"

    /**
     * 允许应用读取用户通话记录
     */
    const val READ_CALL_LOG = "android.permission.READ_CALL_LOG"

    /**
     * 允许应用写入用户的联系人数据
     */
    const val WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG"

    /**
     * 允许应用向语音信箱添加语音邮件。
     */
    const val ADD_VOICEMAIL = "com.android.voicemail.permission.ADD_VOICEMAIL"

    /**
     * 允许应用使用 SIP 服务
     */
    const val USE_SIP = "android.permission.USE_SIP"

    /**
     * 允许应用在拨出电话时查看拨打的电话号码，并选择改为拨打其他号码或者完全终止通话
     */
    const val PROCESS_OUTGOING_CALLS = "android.permission.PROCESS_OUTGOING_CALLS"

    /**
     * 允许应用存取监测身体状况的传感器所收集的数据，例如心率。
     */
    const val BODY_SENSORS = "android.permission.BODY_SENSORS"

    /**
     * 允许应用发送 SMS 信息
     */
    const val SEND_SMS = "android.permission.SEND_SMS"

    /**
     * 允许应用接收短信
     */
    const val RECEIVE_SMS = "android.permission.RECEIVE_SMS"

    /**
     * 允许应用读取 SMS 信息
     */
    const val READ_SMS = "android.permission.READ_SMS"

    /**
     * 允许应用接收 WAP 推送信息
     */
    const val RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH"

    /**
     * 允许应用监控接收 MMS 信息
     */
    const val RECEIVE_MMS = "android.permission.RECEIVE_MMS"

    /**
     * 允许程序读取设备外部存储空间
     *
     * 该权限在 [AndroidVersion.ANDROID_13] 以上废弃，调用该权限会被直接拒绝。
     * 框架会自动替换为 [READ_MEDIA_IMAGES], [READ_MEDIA_VIDEO], [READ_MEDIA_AUDIO] 权限
     */
    const val READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE"

    /**
     * 允许应用向外部存储写入数据
     */
    const val WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE"

    /**
     * 接听电话
     *
     * [AndroidVersion.ANDROID_8] 新增权限
     */
    const val ANSWER_PHONE_CALLS = "android.permission.ANSWER_PHONE_CALLS"

    /**
     * 读取手机号码权限
     *
     * [AndroidVersion.ANDROID_8] 新增权限
     *
     * 注意事项：
     *   1. 如果是适配 Android 8 及以下设备，需要同步在 Manifest 中配置 [READ_PHONE_STATE] 权限
     */
    const val READ_PHONE_NUMBERS = "android.permission.READ_PHONE_NUMBERS"

    /**
     * 允许呼叫应用继续在另一个应用中启动的呼叫
     *
     * [AndroidVersion.ANDROID_9] 新增权限
     */
    const val ACCEPT_HANDOVER = "android.permission.ACCEPT_HANDOVER"

    /**
     * 读取照片中的地理位置
     *
     * [AndroidVersion.ANDROID_10] 新增权限
     *
     * 注意事项：**只有申请了存储权限才能获取到相关数据**
     *   1. 如果适配了分区存储的情况下：
     *     - 如果项目 targetSdkVersion <= 32 需要申请 [READ_EXTERNAL_STORAGE]
     *     - 如果项目 targetSdkVersion >= 33 需要申请 [READ_MEDIA_IMAGES]
     *   2. 如果没有适配分区存储的情况下：
     *     - 如果项目 targetSdkVersion <= 29 需要申请 [READ_EXTERNAL_STORAGE]
     *     - 如果项目 targetSdkVersion >= 30 需要申请 [MANAGE_EXTERNAL_STORAGE]
     */
    const val ACCESS_MEDIA_LOCATION = "android.permission.ACCESS_MEDIA_LOCATION"

    /**
     * 获取运动步数
     *
     * [AndroidVersion.ANDROID_10] 新增权限
     */
    const val ACTIVITY_RECOGNITION = "android.permission.ACTIVITY_RECOGNITION"

    /**
     * 在后台获取位置权限
     *
     * [AndroidVersion.ANDROID_10] 新增权限
     *
     * 注意事项：
     *   1. 授权该权限时，必须要选择始终允许，否则无法获取到位置信息
     *   2. 如果是在前台获取位置信息，可以使用 [ACCESS_FINE_LOCATION] 或者 [ACCESS_COARSE_LOCATION] 权限即可
     */
    const val ACCESS_BACKGROUND_LOCATION = "android.permission.ACCESS_BACKGROUND_LOCATION"

    /**
     * 蓝牙权限
     */
    const val BLUETOOTH = "android.permission.BLUETOOTH"

    /**
     * 蓝牙管理权限
     */
    const val BLUETOOTH_ADMIN = "android.permission.BLUETOOTH_ADMIN"

    /**
     * 蓝牙扫描权限
     *
     * [AndroidVersion.ANDROID_12] 新增权限
     *
     * 注意事项：
     *   1. 需要在清单文件中加入 android:usesPermissionFlags="neverForLocation" 属性（表示不推导设备地理位置）
     *      ```xml
     *      <uses-permission android:name="android.permission.BLUETOOTH_SCAN"
     *         android:usesPermissionFlags="neverForLocation"
     *         tools:targetApi="s" />
     *      ```
     *   2. 为了兼容 Android 12 以下版本，需要在清单文件中配置 [BLUETOOTH_ADMIN], [ACCESS_FINE_LOCATION] 权限
     *
     * @see [PackageInfo.REQUESTED_PERMISSION_NEVER_FOR_LOCATION]
     */
    const val BLUETOOTH_SCAN = "android.permission.BLUETOOTH_SCAN"

    /**
     * 蓝牙链接权限
     *
     * [AndroidVersion.ANDROID_12] 新增权限
     *
     * 注意事项：为了兼容 Android 12 以下版本，需要在清单文件中配置 [BLUETOOTH] 权限
     */
    const val BLUETOOTH_CONNECT = "android.permission.BLUETOOTH_CONNECT"

    /**
     * 蓝牙广播权限
     *
     * [AndroidVersion.ANDROID_12] 新增权限
     *
     * 注意事项：为了兼容 Android 12 以下版本，需要在清单文件中配置 [BLUETOOTH_ADMIN] 权限
     */
    const val BLUETOOTH_ADVERTISE = "android.permission.BLUETOOTH_ADVERTISE"

    /**
     * 读取视频权限
     *
     * [AndroidVersion.ANDROID_13] 新增权限
     *
     * 注意事项：为了兼容 Android 13 以下版本，需要在清单文件中配置 [READ_EXTERNAL_STORAGE] 权限
     */
    const val READ_MEDIA_VIDEO = "android.permission.READ_MEDIA_VIDEO"

    /**
     * 读取音频权限
     *
     * [AndroidVersion.ANDROID_13] 新增权限
     *
     * 注意事项：为了兼容 Android 13 以下版本，需要在清单文件中配置 [READ_EXTERNAL_STORAGE] 权限
     */
    const val READ_MEDIA_AUDIO = "android.permission.READ_MEDIA_AUDIO"

    /**
     * 读取图片权限
     *
     * [AndroidVersion.ANDROID_13] 新增权限
     *
     * 注意事项：为了兼容 Android 13 以下版本，需要在清单文件中配置 [READ_EXTERNAL_STORAGE] 权限
     */
    const val READ_MEDIA_IMAGES = "android.permission.READ_MEDIA_IMAGES"

    /**
     * 后台传感器权限
     *
     * [AndroidVersion.ANDROID_13] 新增权限
     *
     * 注意事项：
     *   1. 授权该权限时，必须要选择始终允许，否则无法获取到传感器数据
     *   2. 如果是在前台获取传感器数据，可以使用 [BODY_SENSORS] 权限即可
     */
    const val BODY_SENSORS_BACKGROUND = "android.permission.BODY_SENSORS_BACKGROUND"

    /**
     * Wifi权限
     *
     * [AndroidVersion.ANDROID_13] 新增权限
     *
     * 注意事项：
     *   1. 需要在清单文件中加入 android:usesPermissionFlags="neverForLocation" 属性（表示不推导设备地理位置）
     *      ```xml
     *      <uses-permission android:name="android.permission.NEARBY_WIFI_DEVICES"
     *         android:usesPermissionFlags="neverForLocation"
     *         tools:targetApi="s" />
     *      ```
     *   2. 为了兼容 Android 13 以下版本，需要在清单文件中配置 [ACCESS_FINE_LOCATION] 权限
     */
    const val NEARBY_WIFI_DEVICES = "android.permission.NEARBY_WIFI_DEVICES"

    /**
     * 发送通知权限
     *
     * [AndroidVersion.ANDROID_13] 新增权限
     *
     * 注意事项：为了兼容 Android 13 以下版本，需要在清单文件中配置 [NOTIFICATION_SERVICE] 权限
     */
    const val POST_NOTIFICATIONS = "android.permission.POST_NOTIFICATIONS"

    /**
     * 授予对照片和视频的部分访问权限
     *
     * [AndroidVersion.ANDROID_14] 新增权限，
     *
     * - 该权限必须和 [READ_MEDIA_IMAGES] 或 [READ_MEDIA_VIDEO] 一起进行申请
     * - 当然也可以直接只申请 [READ_MEDIA_IMAGES] 或 [READ_MEDIA_VIDEO] 权限，无论是授权了部分访问或是全部访问，该权限都会回调已授权
     */
    const val READ_MEDIA_VISUAL_USER_SELECTED = "android.permission.READ_MEDIA_VISUAL_USER_SELECTED"

    ///////////////////////////////////////////////////// 危险权限 - 特殊权限 /////////////////////////////////////////////////////

    /**
     * 通知栏权限
     */
    const val NOTIFICATION_SERVICE = "android.permission.NOTIFICATION_SERVICE"

    /**
     * VPN 权限
     */
    const val BIND_VPN_SERVICE = "android.permission.BIND_VPN_SERVICE"

    /**
     * 通知栏监听权限
     */
    const val BIND_NOTIFICATION_LISTENER_SERVICE =
        "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"

    /**
     * 应用使用情况读取权限
     */
    const val PACKAGE_USAGE_STATS = "android.permission.PACKAGE_USAGE_STATS"

    /**
     * 勿扰权限
     */
    const val ACCESS_NOTIFICATION_POLICY = "android.permission.ACCESS_NOTIFICATION_POLICY"

    /**
     * 忽略电池优化权限
     */
    const val REQUEST_IGNORE_BATTERY_OPTIMIZATIONS =
        "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS"

    /**
     * 系统设置权限
     */
    const val WRITE_SETTINGS = "android.permission.WRITE_SETTINGS"

    /**
     * 悬浮窗权限
     *
     * 注意事项：
     *   1. Android 10 及以前的版本可以跳转到应用的悬浮窗权限设置页面
     *   2. Android 11 及以后的版本只能跳转到系统悬浮窗权限设置页面列表，然后需要用户手动找到应用进行设置
     */
    const val SYSTEM_ALERT_WINDOW = "android.permission.SYSTEM_ALERT_WINDOW"

    /**
     * 画中画权限
     *
     * [AndroidVersion.ANDROID_8] 新增权限
     *
     * 注意事项：该权限默认授予，无需申请，但可能会被用户关闭
     */
    const val PICTURE_IN_PICTURE = "android.permission.PICTURE_IN_PICTURE"

    /**
     * 安装应用权限
     *
     * [AndroidVersion.ANDROID_8] 新增权限
     *
     * 注意事项：Android 11 存在系统Bug，需要重启应用后才能获取权限
     */
    const val REQUEST_INSTALL_PACKAGES = "android.permission.REQUEST_INSTALL_PACKAGES"

    /**
     * 文件管理权限
     *
     * [AndroidVersion.ANDROID_11] 新增权限
     *
     * 注意事项：为了兼容 Android 11 以下版本，需要在清单文件中配置 [READ_EXTERNAL_STORAGE] 和 [WRITE_EXTERNAL_STORAGE] 权限
     *
     * @see <a href="https://www.jianshu.com/p/a228f6a46354">分区存储适配逻辑</a>
     */
    const val MANAGE_EXTERNAL_STORAGE = "android.permission.MANAGE_EXTERNAL_STORAGE"

    /**
     * 闹钟权限
     *
     * [AndroidVersion.ANDROID_12] 新增权限
     *
     * 注意事项：该权限默认授予，无需申请，但可能会被用户关闭
     */
    const val SCHEDULE_EXACT_ALARM = "android.permission.SCHEDULE_EXACT_ALARM"
}