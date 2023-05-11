package com.example.collageit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.collageit.databinding.ActivityReviewBinding
import com.squareup.picasso.Picasso

class Review (var image: String) : AppCompatActivity() {
   private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Picasso.get().load(image).into(binding.collagePicture)
    }


}