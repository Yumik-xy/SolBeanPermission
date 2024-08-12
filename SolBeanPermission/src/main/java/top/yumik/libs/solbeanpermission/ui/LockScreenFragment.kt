package top.yumik.libs.solbeanpermission.ui

import android.content.Context
import android.content.pm.ActivityInfo
import androidx.fragment.app.Fragment
import top.yumik.libs.solbeanpermission.utils.ScreenUtils

open class LockScreenFragment : Fragment() {
    
    private var preScreenOrientation: Int = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        
        activity?.let {
            preScreenOrientation = it.requestedOrientation
            if (preScreenOrientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
                return
            }
            
            ScreenUtils.lockScreenOrientation(it)
        }
    }
    
    override fun onDetach() {
        super.onDetach()
        
        activity?.let {
            if (preScreenOrientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED ||
                it.requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            ) {
                return
            }
            
            ScreenUtils.unlockScreenOrientation(it)
        }
    }
}