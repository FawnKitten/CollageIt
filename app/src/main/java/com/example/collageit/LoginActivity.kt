package com.example.collageit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collageit.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
        // the values we send in companion objects are called extras
        val EXTRA_USERNAME = "username"
        val EXTRA_PASSWORD = "password"
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setOnClickListener {
            startActivity(Intent(this@LoginActivity, LogupActivity::class.java))
        }
    }
}