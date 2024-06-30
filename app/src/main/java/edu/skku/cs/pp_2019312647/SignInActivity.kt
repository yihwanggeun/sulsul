package edu.skku.cs.pp_2019312647

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val idEditText = findViewById<EditText>(R.id.idEditText)
        val pwdEditText = findViewById<EditText>(R.id.passwordEditText)
        val signInButton = findViewById<Button>(R.id.signinbutton)
        val signUpButton = findViewById<Button>(R.id.sharebutton)
        signUpButton.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            //startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            startActivity(intent)
        }
        signInButton.setOnClickListener {
            val id = idEditText.text.toString()
            val pwd = pwdEditText.text.toString()
            println(id)
            val okHttpClient = OkHttpClient()
            val url = "https://4vkvor4dc5.execute-api.us-east-1.amazonaws.com/default/SearchBar"
            val new_url = Uri.parse(url).buildUpon().appendQueryParameter("type","signin")
                        .appendQueryParameter("email",id).appendQueryParameter("pwd",pwd).build().toString()
            val request = Request.Builder().url(new_url).build()

            okHttpClient.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    var resp = response.body!!.string()
                    Log.d("SIGNIN",resp)
                    if(!resp.equals("[]")){

                        CoroutineScope(Dispatchers.Main).launch{
                            val myApp = application as GlobalApplication
                            myApp.setEmail(id)
                            val intent = Intent(this@SignInActivity, MainActivity::class.java)
                            //startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                            startActivity(intent)
                        }

                    }
                    else {
                        CoroutineScope(Dispatchers.Main).launch{
                            Toast.makeText(this@SignInActivity, "아이디 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                            idEditText.text.clear()
                            pwdEditText.text.clear()
                        }

                    }

                }

            })
        }
    }
}