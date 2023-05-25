package com.example.collageit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collageit.databinding.ActivityCollageDetailBinding
import com.example.collageit.databinding.ActivityLoginBinding
import com.squareup.picasso.Picasso

class CollageDetail : AppCompatActivity() {
    companion object {
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
        const val EXTRA_IMAGE = "EXTRA_IMAGE"
        const val EXTRA_USERNAME = "EXTRA_USERNAME"

    }
    private lateinit var binding: ActivityCollageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.getStringExtra(CollageAdapter.EXTRA_TITLE)
        val description = intent.getStringExtra(CollageAdapter.EXTRA_DESCRIPTION)
        val image = intent.getStringExtra(CollageAdapter.EXTRA_IMAGE)
        val username = intent.getStringExtra(CollageAdapter.EXTRA_USERNAME)
        binding.detailName.setText(name)
        binding.detailDescription.setText(description)
        binding.detailUsername.setText(username)

        if (image != null) {
            Picasso.get().load(image).into(binding.detailImage)
        }


    }
}