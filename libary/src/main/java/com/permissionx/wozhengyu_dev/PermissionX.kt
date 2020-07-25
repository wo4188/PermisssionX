package com.permissionx.wozhengyu_dev

import androidx.fragment.app.FragmentActivity

//ps:FragmentActivity是AppCompatActivity的父类

/*对外接口部分的代码，封装*/

//这里设置成单例类是为了让PermissionX中的接口能更加方便地被调用
object PermissionX {

    private const val TAG = "InvisibleFragment"

    fun request(activity: FragmentActivity, vararg permissions: String, callback: PermissionCallback) {

        val fragmentManager = activity.supportFragmentManager

        val existedFragment = fragmentManager.findFragmentByTag(TAG)

        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }

        // * 星号不是指针，表示将一个数组转换成可变长度参数传递，不加 * 星号不能直接传递过去
        fragment.requestNow(callback, *permissions)
    }
}