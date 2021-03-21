package com.example.myapplication.network;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.myapplication.App
import com.example.myapplication.App.Companion.context

/**
 * 异常处理帮助类
 * User: ljx
 * Date: 2019/04/29
 * Time: 11:15
 */
object ExceptionHelper {
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            var mConnectivityManager: ConnectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var mNetworkInfo = mConnectivityManager.activeNetworkInfo;
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable;
            }
        }
        return false
    }
}
