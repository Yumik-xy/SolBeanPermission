package top.yumik.libs.solbeanpermission.utils

import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.AccessCoarseLocationCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.AccessFineLocationCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.AddVoicemailCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.BluetoothAdminCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.BluetoothCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.BodySensorsCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.CallPhoneCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.CameraCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.GetAccountsCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.GetInstalledAppsCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.ProcessOutgoingCallsCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.ReadCalendarCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.ReadCallLogCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.ReadContactsCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.ReadExternalStorageCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.ReadPhoneStateCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.ReadSmsCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.ReceiveMmsCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.ReceiveSmsCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.ReceiveWapPushCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.RecordAudioCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.SendSmsCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.UseSipCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.WriteCalendarCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.WriteCallLogCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.WriteContactsCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v23.WriteExternalStorageCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v26.AnswerPhoneCallsCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v26.ReadPhoneNumbersCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v28.AccessHandoverCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v29.AccessBackgroundLocationCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v29.AccessMediaLocationCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v29.ActivityRecognitionCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v31.BluetoothAdvertiseCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v31.BluetoothConnectCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v31.BluetoothScanCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v33.BodySensorsBackgroundCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v33.NearbyWifiDevicesCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v33.PostNotificationsCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v33.ReadMediaAudioCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v33.ReadMediaImagesCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v33.ReadMediaVideoCompat
import top.yumik.libs.solbeanpermission.permission.dangerous.v34.ReadMediaVisualUserSelectedCompat
import top.yumik.libs.solbeanpermission.permission.special.v23.AccessNotificationPolicyCompat
import top.yumik.libs.solbeanpermission.permission.special.v23.BindNotificationListenerServiceCompat
import top.yumik.libs.solbeanpermission.permission.special.v23.BindVpnServiceCompat
import top.yumik.libs.solbeanpermission.permission.special.v23.NotificationServiceCompat
import top.yumik.libs.solbeanpermission.permission.special.v23.PackageUsageStatsCompat
import top.yumik.libs.solbeanpermission.permission.special.v23.RequestIgnoreBatteryOptimizationsCompat
import top.yumik.libs.solbeanpermission.permission.special.v23.SystemAlertWindowCompat
import top.yumik.libs.solbeanpermission.permission.special.v23.WriteSettingsCompat
import top.yumik.libs.solbeanpermission.permission.special.v26.PictureInPictureCompat
import top.yumik.libs.solbeanpermission.permission.special.v26.RequestInstallPackagesCompat
import top.yumik.libs.solbeanpermission.permission.special.v30.ManageExternalStorageCompat
import top.yumik.libs.solbeanpermission.permission.special.v31.ScheduleExactAlarmCompat

internal object PermissionCompat {

    internal val specialPermissionCompat = setOf(
        // v23 - Android 6
        AccessNotificationPolicyCompat(),
        BindNotificationListenerServiceCompat(),
        BindVpnServiceCompat(),
        NotificationServiceCompat(),
        PackageUsageStatsCompat(),
        RequestIgnoreBatteryOptimizationsCompat(),
        SystemAlertWindowCompat(),
        WriteSettingsCompat(),

        // v26 - Android 8
        PictureInPictureCompat(),
        RequestInstallPackagesCompat(),

        // v30 - Android 11
        ManageExternalStorageCompat(),

        // v31 - Android 12
        ScheduleExactAlarmCompat()
    )

    internal val dangerousPermissionCompat = setOf(
        // v23 - Android 6
        AccessCoarseLocationCompat(),
        AccessFineLocationCompat(),
        AddVoicemailCompat(),
        BluetoothCompat(),
        BluetoothAdminCompat(),
        BodySensorsCompat(),
        CallPhoneCompat(),
        CameraCompat(),
        GetAccountsCompat(),
        GetInstalledAppsCompat(),
        ProcessOutgoingCallsCompat(),
        ReadCalendarCompat(),
        ReadCallLogCompat(),
        ReadContactsCompat(),
        ReadExternalStorageCompat(),
        ReadPhoneStateCompat(),
        ReadSmsCompat(),
        ReceiveMmsCompat(),
        ReceiveSmsCompat(),
        ReceiveWapPushCompat(),
        RecordAudioCompat(),
        SendSmsCompat(),
        UseSipCompat(),
        WriteCalendarCompat(),
        WriteCallLogCompat(),
        WriteContactsCompat(),
        WriteExternalStorageCompat(),

        // v26 - Android 8
        AnswerPhoneCallsCompat(),
        ReadPhoneNumbersCompat(),

        // v28 - Android 9
        AccessHandoverCompat(),

        // v29 - Android 10
        AccessBackgroundLocationCompat(),
        AccessMediaLocationCompat(),
        ActivityRecognitionCompat(),

        // v31 - Android 12
        BluetoothAdvertiseCompat(),
        BluetoothConnectCompat(),
        BluetoothScanCompat(),

        // v33 - Android 13
        BodySensorsBackgroundCompat(),
        NearbyWifiDevicesCompat(),
        PostNotificationsCompat(),
        ReadMediaAudioCompat(),
        ReadMediaImagesCompat(),
        ReadMediaVideoCompat(),

        // v34 - Android 14
        ReadMediaVisualUserSelectedCompat()
    )

    internal val allPermissionCompat by lazy {
        specialPermissionCompat + dangerousPermissionCompat
    }

    internal val permissionToCompat by lazy {
        allPermissionCompat.associateBy { it.permission }
    }
}

val Permission.all: List<String>
    get() = PermissionCompat.allPermissionCompat.map { it.permission }

val Permission.special: List<String>
    get() = PermissionCompat.specialPermissionCompat.map { it.permission }

val Permission.dangerous: List<String>
    get() = PermissionCompat.dangerousPermissionCompat.map { it.permission }
