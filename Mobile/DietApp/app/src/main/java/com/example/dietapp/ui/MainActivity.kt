package com.example.dietapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dietapp.DietApp
import com.example.dietapp.api.AccountService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var apiAccountService: AccountService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as DietApp).appComponent.inject(this)
        val value = apiAccountService.getValue().enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                toast(t.message!!)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                toast(response.body()!!)
            }

        })
    }
    fun toast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
