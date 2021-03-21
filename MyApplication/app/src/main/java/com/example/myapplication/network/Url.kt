package com.example.myapplication.network

import rxhttp.wrapper.annotation.DefaultDomain
class  Url {
    companion object{
        @JvmField
        @DefaultDomain
        public var baseUrl:String="http://scm-test.pin-dao.cn/"
    }

}