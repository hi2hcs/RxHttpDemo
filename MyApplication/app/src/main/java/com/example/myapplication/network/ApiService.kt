package com.example.myapplication.network

import com.example.myapplication.StoreInfo
import io.reactivex.Observable
import rxhttp.wrapper.param.RxHttp

object ApiService {
    private var userstorelist: String = "procurement/v1/store/getUserStoreList"
    private val relateAttachments: String = "procurement/v1/distributionOrder/relateAttachments"

    fun getUserStoreList(): Observable<List<StoreInfo>> {
        return RxHttp.get(userstorelist)
            .asResponseList(StoreInfo::class.java);
    }

    fun relateAttachments(
        code: String,
        attachments: List<Map<String, Double>>
    ): Observable<String> {
        val map = mapOf("code" to code, "attachments" to attachments)
        return RxHttp.postJson(relateAttachments)
            .addAll(map)
            .asResponse(String::class.java)
    }

}