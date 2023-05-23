package com.example.collageit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collageit.databinding.ActivityCollageDetailBinding
import com.example.collageit.databinding.ActivityLoginBinding

class CollageDetail : AppCompatActivity() {
    companion object {
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
        const val EXTRA_IMAGE = "EXTRA_IMAGE"

    }
    private lateinit var binding: ActivityCollageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collage_detail)
        binding = ActivityCollageDetailBinding.inflate(layoutInflater)

    }
}