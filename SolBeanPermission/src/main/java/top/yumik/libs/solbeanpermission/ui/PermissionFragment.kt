package top.yumik.libs.solbeanpermission.ui

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import top.yumik.libs.solbeanpermission.OnPermissionAction
import top.yumik.libs.solbeanpermission.OnPermissionCallback
import top.yumik.libs.solbeanpermission.OnPermissionInterceptor
import top.yumik.libs.solbeanpermission.model.PermissionConfig
import top.yumik.libs.solbeanpermission.model.PermissionResult
import top.yumik.libs.solbeanpermission.permission.IPermissionCompat
import top.yumik.libs.solbeanpermission.permission.isDangerousPermission
import top.yumik.libs.solbeanpermission.permission.isSpecialPermission
import top.yumik.libs.solbeanpermission.utils.PermissionUtils
import top.yumik.libs.solbeanpermission.utils.SolBeanLog

internal class PermissionFragment : LockScreenFragment(), Runnable {

    /** 权限组 */
    private val permissions = mutableSetOf<String>()

    /** 授权结果回调 */
    private var callback: OnPermissionCallback? = null

    /** 权限拦截器 */
    private var interceptor: OnPermissionInterceptor? = null

    /** 内部拦截器封装 */
    private val globalInterceptor: OnPermissionInterceptor = object : OnPermissionInterceptor {
        override fun onRequest(
            permissions: Set<String>,
            action: OnPermissionAction
        ) {
            interceptor?.onRequest(permissions, action) ?: action.next()
        }

        override fun onResult(
            permissions: Set<String>,
            granted: Set<String>,
            denied: Set<String>,
            neverAsk: Set<String>,
            checkFail: Set<String>
        ) {
            interceptor?.onResult(permissions, granted, denied, neverAsk, checkFail)
            callback?.onResult(permissions, granted, denied, neverAsk, checkFail)
        }

        override fun onCancel() {
            interceptor?.onCancel()
        }
    }

    companion object {

        fun launch(
            activity: FragmentActivity,
            permissions: Set<String>,
            callback: OnPermissionCallback? = null,
            interceptor: OnPermissionInterceptor? = null
        ) {
            val fragment = PermissionFragment().apply {
                this.permissions += permissions
                this.callback = callback
                this.interceptor = interceptor
            }
            fragment.attach(activity.supportFragmentManager)
        }
    }

    /** 标记是否已经开始进行权限请求 */
    private var startFlag: Boolean = false

    /** 需要处理的特殊权限列表 */
    private val specialPermissions: MutableMap<String, PermissionResult> = mutableMapOf()

    /** 需要处理的危险权限列表 */
    private val dangerousPermissions: MutableMap<String, PermissionResult> = mutableMapOf()

    /** 特殊权限请求回调 */
    private val specialLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // 不在这里检查是因为有些权限同意了，立刻查询依旧会返回未授权
        PermissionUtils.waitSpecialGranted(this)
    }

    /** 危险权限请求回调 */
    private val dangerousLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        it.forEach { (permission, isGranted) ->
            dangerousPermissions[permission] =
                PermissionUtils.getPermissionResult(requireActivity(), permission, isGranted)
        }

        twiceCheckPermissions()
    }

    override fun run() {
        if (!isAdded) {
            return;
        }

        specialPermissions.filter { it.value == PermissionResult.Requesting }
            .keys
            .forEach { permission ->
                specialPermissions[permission] =
                    PermissionUtils.getPermissionResult(requireActivity(), permission)
            }

        requestSpecialPermissions()
    }

    override fun onResume() {
        super.onResume()

        if (!startFlag) {
            startFlag = true
            optimizePermissions()
        }
    }

    private fun attach(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .add(this, this.toString())
            .commitAllowingStateLoss()
    }

    private fun detach(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .remove(this)
            .commitAllowingStateLoss()
    }

    /**
     * 第一步，整理所有的权限，对权限进行一次降级和预授权标记，检查权限是否合法，不合法权限直接标记为拒绝
     */
    private fun optimizePermissions() {
        permissions.map(PermissionUtils::getPermissionCompat)
            .forEach { currentCompat ->

                val currentConfig = currentCompat.config(requireContext())
                currentConfig.permissions
                    .map(PermissionUtils::getPermissionCompat)
                    .forEach { configCompat ->
                        if (configCompat.getResult() == null) {
                            configCompat.setResult(PermissionResult.Idle)
                        }
                    }

                when (currentConfig) {
                    is PermissionConfig.Advance -> {
                        currentCompat.setResult(PermissionResult.Advance)
                    }

                    is PermissionConfig.Replace -> {
                        currentCompat.setResult(PermissionResult.Downgrade)
                    }

                    is PermissionConfig.None -> {
                        if (currentCompat.getResult() == null) {
                            currentCompat.setResult(PermissionResult.Idle)
                        }
                    }
                }
            }

        checkPermissions()
    }

    /**
     * 第二步，检查权限是否合法，不合法的权限直接标记为拒绝
     */
    private fun checkPermissions() {
        specialPermissions.keys
            .map(PermissionUtils::getPermissionCompat)
            .filterNot { it.checkManifest(requireContext()) }
            .forEach { compat ->
                specialPermissions[compat.permission] = PermissionResult.CheckFail
            }

        dangerousPermissions.keys
            .map(PermissionUtils::getPermissionCompat)
            .filterNot { it.checkManifest(requireContext()) }
            .forEach { compat ->
                dangerousPermissions[compat.permission] = PermissionResult.CheckFail
            }

        requestSpecialPermissions()
    }

    /**
     * 第三步，请求特殊权限
     */
    private fun requestSpecialPermissions() {
        specialPermissions.filter { it.value == PermissionResult.Idle }
            .keys
            .map(PermissionUtils::getPermissionCompat)
            .forEach { compat ->
                if (compat.isGranted(requireContext())) {
                    compat.setResult(PermissionResult.Granted)
                    // continue
                    return@forEach
                }

                globalInterceptor.onRequest(setOf(compat.permission), object : OnPermissionAction {
                    override fun cancel() {
                        SolBeanLog.info("用户取消了${compat.permission}及后续的授权请求")

                        globalInterceptor.onCancel()
                        return
                    }

                    override fun next() {
                        SolBeanLog.info("用户同意了${compat.permission}的授权请求")

                        compat.setResult(PermissionResult.Requesting)
                        specialLauncher.launch(compat.getIntent(requireContext()))
                    }

                    override fun skip() {
                        SolBeanLog.info("用户跳过了${compat.permission}的授权请求")

                        compat.setResult(PermissionResult.Denied)
                        requestSpecialPermissions()
                    }
                })
                return
            }

        requestDangerousPermissions()
    }

    /**
     * 第四步，请求危险权限
     */
    private fun requestDangerousPermissions() {
        dangerousPermissions.filter { it.value == PermissionResult.Idle }
            .keys
            .map(PermissionUtils::getPermissionCompat)
            .forEach { compat ->
                if (compat.isGranted(requireContext())) {
                    compat.setResult(PermissionResult.Granted)
                    // continue
                    return@forEach
                }
            }

        // 危险权限先拉起弹窗，如果无法弹出会直接回调拒绝的，然后再检查是否被永久拒绝了
        val requestPermissions = dangerousPermissions.filter {
            it.value == PermissionResult.Idle
        }.keys

        // 如果需要请求的权限是空的，直接进行下一步就行，不要走拦截器
        if (requestPermissions.isEmpty()) {
            twiceCheckPermissions()
            return
        }

        globalInterceptor.onRequest(requestPermissions, object : OnPermissionAction {
            override fun cancel() {
                SolBeanLog.info("用户取消了${requestPermissions}及后续的授权请求")

                globalInterceptor.onCancel()
                return
            }

            override fun next() {
                SolBeanLog.info("用户同意了${requestPermissions}的授权请求")
                dangerousLauncher.launch(requestPermissions.toTypedArray())
            }

            override fun skip() {
                SolBeanLog.info("用户跳过了${requestPermissions}的授权请求")
                requestPermissions.map(PermissionUtils::getPermissionCompat)
                    .forEach { compat ->
                        compat.setResult(PermissionResult.Denied)
                    }
                requestSpecialPermissions()
            }
        })
    }

    /**
     * 第五步，二次检查所有权限
     */
    private fun twiceCheckPermissions() {
        val twiceCheckPermissions = (dangerousPermissions + specialPermissions)
            .filter { it.value == PermissionResult.Advance || it.value == PermissionResult.Downgrade }
            .keys
            .toMutableList()

        fun twiceCheckPermission(permission: String) {
            val compat = PermissionUtils.getPermissionCompat(permission)
            val config = compat.config(requireContext())
            config.permissions
                .filter { it in twiceCheckPermissions }
                .forEach { twiceCheckPermission(it) }
            val result = config.permissions
                .associateWith { PermissionUtils.getPermissionCompat(it).getResult() }
            if (PermissionResult.NeverAsk in result.values) {
                compat.setResult(PermissionResult.NeverAsk)
            } else if (PermissionResult.Denied in result.values) {
                compat.setResult(PermissionResult.Denied)
            } else {
                when (compat.getResult()) {
                    PermissionResult.Advance -> {
                        compat.setResult(PermissionResult.Idle)
                    }

                    PermissionResult.Downgrade -> {
                        compat.setResult(PermissionResult.Granted)
                    }

                    else -> {}
                }
            }
            twiceCheckPermissions.remove(compat.permission)
        }

        while (twiceCheckPermissions.isNotEmpty()) {
            twiceCheckPermission(twiceCheckPermissions.first())
        }

        if ((dangerousPermissions + specialPermissions).any { it.value == PermissionResult.Idle }) {
            requestSpecialPermissions()
        } else {
            returnResult()
        }
    }

    /**
     * 第六步，返回结果
     */
    private fun returnResult() {
        detach(parentFragmentManager)

        val donePermissions = mutableMapOf<String, PermissionResult>()
        donePermissions.putAll(dangerousPermissions.filter { it.key in permissions })
        donePermissions.putAll(specialPermissions.filter { it.key in permissions })

        val granted = donePermissions.filter { it.value == PermissionResult.Granted }.keys
        val denied = donePermissions.filter { it.value == PermissionResult.Denied }.keys
        val neverAsk = donePermissions.filter { it.value == PermissionResult.NeverAsk }.keys
        val checkFail = donePermissions.filter { it.value == PermissionResult.CheckFail }.keys

        globalInterceptor.onResult(permissions, granted, denied, neverAsk, checkFail)
    }

    private fun IPermissionCompat.getResult(): PermissionResult? {
        return when {
            this.isSpecialPermission() -> {
                specialPermissions[this.permission]
            }

            this.isDangerousPermission() -> {
                dangerousPermissions[this.permission]
            }

            else -> {
                null
            }
        }
    }

    private fun IPermissionCompat.setResult(result: PermissionResult) {
        when {
            this.isSpecialPermission() -> {
                specialPermissions[this.permission] = result
            }

            this.isDangerousPermission() -> {
                dangerousPermissions[this.permission] = result
            }
        }
    }
}