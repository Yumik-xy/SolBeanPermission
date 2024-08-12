package top.yumik.libs.solbeanpermission.sample

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import top.yumik.libs.solbeanpermission.OnPermissionAction
import top.yumik.libs.solbeanpermission.OnPermissionCallback
import top.yumik.libs.solbeanpermission.OnPermissionInterceptor
import top.yumik.libs.solbeanpermission.SolBeanPermission
import top.yumik.libs.solbeanpermission.constant.AndroidVersion
import top.yumik.libs.solbeanpermission.constant.Permission
import top.yumik.libs.solbeanpermission.sample.ui.theme.SolBeanPermissionTheme
import top.yumik.libs.solbeanpermission.utils.all
import top.yumik.libs.solbeanpermission.utils.dangerous
import top.yumik.libs.solbeanpermission.utils.special

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContent {
            SolBeanPermissionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    MainScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as AppCompatActivity
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Title(
                title = "ANDROID INFO",
                subtitle = "Android detail information, including version, sdk version, etc."
            )
        }

        item {
            KeyValueItem(key = "Android version", value = AndroidVersion.getAndroidVersionCode())
        }

        item {
            KeyValueItem(
                key = "App target sdk version",
                value = AndroidVersion.getTargetSdkVersionCode(LocalContext.current)
            )
        }

        item {
            Title(title = "MORE PERMISSIONS", subtitle = "Request multiple permissions at once.")
        }

        item {
            RequestPermissionsButton(
                activity = activity,
                permissions = Permission.all.toTypedArray()
            )
        }

        item {
            RequestPermissionsButton(
                activity = activity,
                permissions = Permission.special.toTypedArray()
            )
        }

        item {
            RequestPermissionsButton(
                activity = activity,
                permissions = Permission.dangerous.toTypedArray()
            )
        }

        item {
            RequestPermissionsButton(
                activity = activity,
                permissions = arrayOf(
                    Permission.BIND_NOTIFICATION_LISTENER_SERVICE,
                    Permission.MANAGE_EXTERNAL_STORAGE
                )
            )
        }

        item {
            RequestPermissionsButton(
                activity = activity,
                permissions = arrayOf(Permission.CAMERA, Permission.MANAGE_EXTERNAL_STORAGE)
            )
        }

        item {
            RequestPermissionsButton(
                activity = activity,
                permissions = arrayOf(Permission.CAMERA, Permission.BODY_SENSORS)
            )
        }

        item {
            Title(title = "SPECIAL PERMISSIONS")
        }

        items(
            items = Permission.special,
            key = { it }
        ) { permission ->
            RequestPermissionButton(activity = activity, permission = permission)
        }

        item {
            Title(title = "DANGEROUS PERMISSIONS")
        }

        items(
            items = Permission.dangerous,
            key = { it }
        ) { permission ->
            RequestPermissionButton(activity = activity, permission = permission)
        }
    }
}

@Composable
private fun Title(title: String, subtitle: String? = null) {
    Column(
        modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        if (subtitle != null) {
            Text(text = subtitle, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun KeyValueItem(
    key: Any?,
    value: Any?
) {
    Column {
        Text(text = key.toString(), style = MaterialTheme.typography.titleMedium)
        Text(text = value.toString(), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun RequestPermissionButton(activity: AppCompatActivity, permission: String) {
    TextButton(onClick = {
        SolBeanPermission.requestPermissions(
            activity = activity,
            permission = permission,
            callback = requestPermissionCallback,
            interceptor = activity.requestPermissionInterceptor
        )
    }) {
        Text(
            text = permission.substringAfterLast(".").lowercase(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun RequestPermissionsButton(activity: AppCompatActivity, vararg permissions: String) {
    TextButton(onClick = {
        SolBeanPermission.requestPermissions(
            activity = activity,
            permissions = permissions.toSet(),
            callback = requestPermissionCallback,
            interceptor = activity.requestPermissionInterceptor
        )
    }) {
        Text(
            text = permissions.joinToString { it.substringAfterLast(".").lowercase() },
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private val requestPermissionCallback =
    OnPermissionCallback { permissions, granted, denied, neverAsk, checkFail ->
        val msg = "OnPermissionCallback!\n" +
                "    granted=$granted,\n" +
                "    denied=$denied,\n" +
                "    neverAsk=$neverAsk,\n" +
                "    checkFail=$checkFail"
        Log.d(TAG, msg)
    }

private val Activity.requestPermissionInterceptor: OnPermissionInterceptor
    get() = object : OnPermissionInterceptor {
        override fun onRequest(permissions: Set<String>, action: OnPermissionAction) {
            AlertDialog.Builder(this@requestPermissionInterceptor)
                .setTitle("请求权限")
                .setMessage("需要申请下列权限：\n" +
                        "[" + permissions.joinToString { it.substringAfterLast(".") } + "]"
                )
                .setPositiveButton("同意请求") { dialog, _ ->
                    dialog.dismiss()
                    action.next()
                }
                .setNegativeButton("跳过当前") { dialog, _ ->
                    dialog.dismiss()
                    action.skip()
                }
                .setNeutralButton("取消全部") { dialog, _ ->
                    dialog.dismiss()
                    action.cancel()
                }
                .show()
        }
    }

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SolBeanPermissionTheme {
        MainScreen()
    }
}