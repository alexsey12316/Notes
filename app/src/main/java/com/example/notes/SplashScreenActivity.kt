package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.database.DBHandler
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val background= object: Thread() {
            override fun run() {
                try {
                    val dBHandler=DBHandler(baseContext)
                    sleep(1000)

                    val splashIntent= Intent(baseContext,MainActivity::class.java)
                    startActivity(splashIntent)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}