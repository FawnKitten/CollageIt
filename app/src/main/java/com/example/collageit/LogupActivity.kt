package com.example.collageit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collageit.databinding.ActivityLogupBinding

class LogupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setOnClickListener {
            startActivity(Intent(this@LogupActivity, LoginActivity::class.java))
        }
    }
}