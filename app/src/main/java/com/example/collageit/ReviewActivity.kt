package com.example.collageit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.collageit.databinding.ActivityReviewBinding
import com.squareup.picasso.Picasso

class ReviewActivity (var image: String) : AppCompatActivity() {

    companion object {
        private const val TAG = "ReviewActivity"
        const val PASSED_IMAGE_EXTRA = "imageData"
    }
   private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val passedInImageUri = intent.getParcelableExtra<android.graphics.Bitmap>(PASSED_IMAGE_EXTRA)
            Log.d(TAG, "onCreate: passedInImageBitmap - $passedInImageUri")
//        binding.collagePicture.setImageBitmap(passedInImageBitmap)
//        Picasso.get().load(image).into(binding.collagePicture)

        binding.publishCollageButton.setOnClickListener {

        }

    }




}