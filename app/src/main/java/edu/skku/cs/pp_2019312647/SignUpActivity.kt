package edu.skku.cs.pp_2019312647

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val pwdEditText = findViewById<EditText>(R.id.pwdEditText)
        val ageEditText = findViewById<EditText>(R.id.ageEditText)
        val genderEditText = findViewById<EditText>(R.id.genderEditText)
        val signUpButton = findViewById<Button>(R.id.sharebutton)
        
        signUpButton.setOnClickListener {
            var name = nameEditText.text.toString()
            var email = emailEditText.text.toString()
            var pwd = pwdEditText.text.toString()
            var age = ageEditText.text.toString()
            var gender  = genderEditText.text.toString()


            val okHttpClient = OkHttpClient()
            val url = "https://4vkvor4dc5.execute-api.us-east-1.amazonaws.com/default/SearchBar"
            val new_url = Uri.parse(url).buildUpon().appendQueryParameter("type","signin")
                .appendQueryParameter("email",email).appendQueryParameter("pwd",pwd).build().toString()
            val request = Request.Builder().url(new_url).build()

            println(request)
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {

                    var resp = response.body!!.string()
                    println(resp)
                    if(!resp.equals("[]")){

                        runOnUiThread {
                            println(resp)
                            Toast.makeText(this@SignUpActivity, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show()
                            nameEditText.text.clear()
                            emailEditText.text.clear()
                            pwdEditText.text.clear()
                            ageEditText.text.clear()
                            genderEditText.text.clear()
                        }
                    }

                    else{
                        val signup_url = Uri.parse(url).buildUpon().appendQueryParameter("type","signup")
                            .appendQueryParameter("email",email).appendQueryParameter("pwd",pwd)
                            .appendQueryParameter("age",age).appendQueryParameter("gender",gender)
                            .appendQueryParameter("nickname",name).build().toString()

                        val request2 = Request.Builder().url(signup_url).build()
                        println(request2)
                        okHttpClient.newCall(request2).enqueue(object : Callback{

                            override fun onFailure(call: Call, e: IOException) {
                            }

                            override fun onResponse(call: Call, response: Response) {
                                var resp2 = response.body!!.string()
                                val myApp = application as GlobalApplication
                                myApp.setEmail(email)
                                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                                //startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                startActivity(intent)
                                println(resp2)
                            }

                        })
                    }
                }
            })
        }
       
    }
}