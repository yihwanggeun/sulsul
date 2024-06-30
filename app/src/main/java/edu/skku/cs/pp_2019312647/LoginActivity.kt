package edu.skku.cs.pp_2019312647

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                //Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            }
            else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)

                intent.putExtra("loginType","kakao")
                startActivity(intent)
                //finish()
            }
        }
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(ContentValues.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("loginType","kakao")
                startActivity(intent)
            }
        }
        val kakaoLoginButton = findViewById<ImageButton>(R.id.kakaoLoginButton)
        val withoutButton = findViewById<Button>(R.id.withoutbutton)
        withoutButton.setOnClickListener {
            println("TEST")
            val intent = Intent(this, SignInActivity::class.java)
            intent.putExtra("loginType","without")
            //startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            startActivity(intent)
        }

        kakaoLoginButton.setOnClickListener {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    //Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
                } else if (tokenInfo != null) {
                    Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }
            }

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

    }
}