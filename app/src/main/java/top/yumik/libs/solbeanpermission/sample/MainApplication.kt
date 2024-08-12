package top.yumik.libs.solbeanpermission.sample

import android.app.Application

class MainApplication : Application() {

    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}