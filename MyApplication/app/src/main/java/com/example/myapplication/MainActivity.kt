package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.network.ApiIoMainTransformer
import com.example.myapplication.network.ApiService
import com.example.myapplication.network.BaseOberver
import rxhttp.wrapper.param.RxHttp

//import rxhttp.wrapper.param.RxHttp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RxHttp.setOnParamAssembly {
            it.addHeader(
                "Authorization",
                "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6Ijk3ODM4ZjM5LTc4ZDgtNGEzYi1iYjdkLWE1NjIzODIzOTAyMiJ9.Iu7jhnQUn6Xiz0CYRzTG5-KuU9NfCfPSQPbODArr9dRXY0_Vc_2-ccpQrWH49AtQsnTH2vJoJfyzKTkoQbEjvw"
            )
        }
        RxHttp.setDebug(true, true)
    }

    var map =
        mutableMapOf("Authorization" to "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6Ijk3ODM4ZjM5LTc4ZDgtNGEzYi1iYjdkLWE1NjIzODIzOTAyMiJ9.Iu7jhnQUn6Xiz0CYRzTG5-KuU9NfCfPSQPbODArr9dRXY0_Vc_2-ccpQrWH49AtQsnTH2vJoJfyzKTkoQbEjvw")

    override fun onResume() {
        super.onResume()
//        Map<String,String> map=HashMap();
//        RxHttp.get("http://scm-test.pin-dao.cn/procurement/v1/store/getUserStoreList")
//            .setAllHeader(
//            map)
//            .asResponseList(StoreInfo::class.java).subscribeOn(
//            Schedulers.io()
//        ).observeOn(AndroidSchedulers.mainThread()).subscribe({ list ->
//            for (element in list) {
//                Log.i("TEST","${element.storeName}-${element.storeCode}")
//            }
//            list.forEach {
//                Log.i("TEST","${it.storeName}---${it.storeCode}")
//            }
//        }, {
//            Log.e("TEST","eroooor:${it?.message}")
//        })
        ApiService.getUserStoreList()
            .compose(ApiIoMainTransformer())
            .subscribe(object : BaseOberver<List<StoreInfo>>() {
                override fun onSuccess(data: List<StoreInfo>) {
                    for (element in data) {
                        Log.e("SUCCESS", "${element.storeName}-${element.storeCode}")
                    }
                }
            })

        var attachments: ArrayList<Map<String, Double>> = ArrayList()
        attachments.add(mutableMapOf("123.0" to 123.0))
        attachments.add(mutableMapOf("456.0" to 456.0))
//        attachments[0] = mutableMapOf("123.0" to 123.0)
//        attachments[1] = mutableMapOf("456.0" to 456.0)
        ApiService.relateAttachments("1234", attachments)
            .compose(ApiIoMainTransformer())
            .subscribe(object : BaseOberver<String>() {
                override fun onSuccess(data: String) {
//                    for (element in data) {
//                        Log.e("SUCCESS", "${element.storeName}-${element.storeCode}")
//                    }
                }
            })


//        RxHttp.get("http://scm-test.pin-dao.cn/procurement/v1/store/getUserStoreList").setAllHeader(
//            map).asString().subscribeOn(
//            Schedulers.io()
//        ).observeOn(AndroidSchedulers.mainThread()).subscribe({
//            it.forEach { (element) -> print("${element.storeName}-${element.code}") }
//            for (element in it) {
//                print("${element.storeName}-${element.storeCode}")
//            }
//                                                              Log.i("TEST","right>>>>${it}")

//        }, {
//            Log.e("TEST","eroooor:${it?.message}")
//        })
    }
}