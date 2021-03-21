package com.example.myapplication.network

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.myapplication.App
import com.google.gson.JsonSyntaxException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


abstract class BaseOberver<T> : Observer<T> {
    abstract fun onSuccess(data: T);///onNext
    var showErrorToast: Boolean = true

    open fun noErrorToast(): Observer<T> {
        showErrorToast = false
        return this
    }

    private var disposable: Disposable? = null
    override fun onSubscribe(d: Disposable) {
        disposable = d;
    }


    override fun onNext(t: T) {
        onSuccess(t);
    }

    override fun onError(throwable: Throwable) {
        var errorMsg: String? = null
        Log.e("Error", "msg::::${throwable.message}")
        if (throwable is ParseException) {
            errorMsg = throwable.message;
            Log.e("Error", "errorMsg::::${errorMsg}<><>${throwable.errorCode}")

        } else if (throwable is UnknownHostException) {
            errorMsg = if (!ExceptionHelper.isNetworkConnected(App.getInstance())) {
                "当前无网络，请检查你的网络设置"
            } else {
                "网络连接不可用，请稍后重试！"
            }
        } else if (throwable is SocketTimeoutException || throwable is TimeoutException) {
            //前者是通过OkHttpClient设置的超时引发的异常，后者是对单个请求调用timeout方法引发的超时异常
            errorMsg = "连接超时,请稍后再试"
        } else if (throwable is ConnectException) {
            errorMsg = "网络不给力，请稍候重试！"
        } else if (throwable is HttpStatusCodeException) { //请求失败异常
            val code: String? = throwable.getLocalizedMessage()
            errorMsg = if ("416" == code) {
                "请求范围不符合要求"
            } else {
                throwable.message
            }
        } else if (throwable is JsonSyntaxException) { //请求成功，但Json语法异常,导致解析失败
            errorMsg = "数据解析失败,请稍后再试"
        } else {
            errorMsg = throwable.message
        }
        if (showErrorToast) {
            if (!TextUtils.isEmpty(errorMsg)) {
                Toast.makeText(App.getInstance(), errorMsg!!, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(App.getInstance(), "网络连接错误，请稍后重试！", Toast.LENGTH_SHORT).show()
            }
        }

        Log.e("Error", "errorMsg::::${errorMsg}")
    }


    override fun onComplete() {
        dispose()
    }

    private fun dispose() {
        if (disposable != null && !(disposable!!.isDisposed)) {
            disposable!!.dispose()
            disposable = null
        }
    }
}