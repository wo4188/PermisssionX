package com.permissionx.wozhengyu_dev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {

    //定义了一个函数类型的变量callback(该变量接收2个参数，并且没有返回值)，
    //作用：作为运行时权限申请结果的回调通知方式
    private var callback:PermissionCallback? = null

    fun requestNow(cb: PermissionCallback, vararg permissions: String) {
        callback = cb
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            val deniedList = ArrayList<String>()        //记录所有被用户拒绝的权限
            for ((index, result) in grantResults.withIndex()) {

                //遍历grantResults数组，将用户拒绝的权限加入到deniedList中

                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }

            val allGranted = deniedList.isEmpty()       //标识是否所有的权限均已被用户授权

            //最后，使用callback变量对运行时权限的申请结果进行回调
            callback?.let { it(allGranted, deniedList) }
        }
    }
}