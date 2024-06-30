package edu.skku.cs.pp_2019312647

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    private var email : String =""

    fun getEmail(): String {
        return email
    }

    fun setEmail(newEmail: String) {
        email = newEmail
    }
        override fun onCreate(){
            super.onCreate()

        KakaoSdk.init(this, "78b1351b7310f08b5dd40be410318582")
    }


}