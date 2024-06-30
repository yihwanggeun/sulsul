package edu.skku.cs.pp_2019312647

import android.content.ContentValues.TAG
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.sdk.user.UserApiClient
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myApp = application as GlobalApplication
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


        val HomeFragment = HomeFragment()  // 프래그먼트 인스턴스 생성
        val WhiskeylFragment = WhiskeylFragment()  // 프래그먼트 인스턴스 생성
        val ReviewFragment = ReviewFragment()  // 프래그먼트 인스턴스 생성
        val RecommendFragment = RecommendFragment()  // 프래그먼트 인스턴스 생성


        supportFragmentManager.beginTransaction().add(R.id.main_frame,HomeFragment()).commit()

        bottomNavigationView.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.page_1 ->  {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame,HomeFragment()).commit()
                    true
                }
                R.id.page_2 ->  {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame,WhiskeylFragment()).commit()
                    true
                }
                R.id.page_3 ->  {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame,RecommendFragment()).commit()
                    true
                }
                R.id.page_4 ->  {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame,ReviewFragment).commit()
                    true
                }
                else -> false

            }
        }


    //bottomNavigationView.setOnNavigationItemSelectedListener {  }

        /*UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("Hello", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            } else {
                Log.i("Hello", "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }*/

        UserApiClient.instance.unlink { error ->
            if (error != null) {
                //Log.e(TAG, "연결 끊기 실패", error)
            }
            else {
                //Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }








    }
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, fragment)
            .commit()
    }
}