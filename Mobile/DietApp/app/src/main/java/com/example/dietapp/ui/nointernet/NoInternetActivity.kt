package com.example.dietapp.ui.nointernet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dietapp.R
import com.example.dietapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_no_internet.*
import java.lang.NullPointerException

/**
 * Only start this activity with startActivityForResult()
 */
class NoInternetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet)

        no_internet_button_go_back.setOnClickListener {
            try{
                finish()
            }
            catch (e: NullPointerException){
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}
