package com.example.collageit.collageCreation

import android.graphics.Bitmap
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import com.example.collageit.R
import com.example.collageit.databinding.ActivityChooseCollageFormatBinding
import com.squareup.picasso.Picasso

class ChooseCollageFormatActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ChooseCollageForm"
    }
    // binding
     private lateinit var binding: ActivityChooseCollageFormatBinding
     private lateinit var viewStub1: ViewStub
    private lateinit var viewStub2: ViewStub
    private lateinit var viewStub3: ViewStub
    private lateinit var inflatedViewStub1: View
    private lateinit var inflatedViewStub2: View
    private lateinit var inflatedViewStub3: View
    private var currentSelectedImageUrl: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding
        binding = ActivityChooseCollageFormatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewStub1 = binding.viewStubCreateCollageSelectedCollageStubOption1
        viewStub2 = binding.viewStubCreateCollageSelectedCollageStubOption2
        viewStub3 = binding.viewStubCreateCollageSelectedCollageStubOption3

        handleButtonClicks()

        val imageTes1 = binding.imageViewCreateCollageTest1
        Picasso.get().load("https://t4.ftcdn.net/jpg/04/80/95/25/240_F_480952585_wOmBshuqPnlIQVztf5fTSMopQnAteqeM.jpg").into(imageTes1)
        val imageTest2 = binding.ImageViewCreateCollageTest2
        Picasso.get().load("https://t4.ftcdn.net/jpg/01/30/94/51/240_F_130945151_gaeyqMhFTg8d9i620ao7vcJ5Qee4dOQ9.jpg").into(imageTest2)
        imageTes1.setOnClickListener {
            currentSelectedImageUrl = imageTes1.drawable.current.toBitmap()
        }
        imageTest2.setOnClickListener {
            currentSelectedImageUrl = imageTest2.drawable.current.toBitmap()
        }
    }

    private fun onClickOfCollageImage(imageView: ImageView) {
        if (currentSelectedImageUrl == null) {
            Log.d(TAG, "onClickOfCollageImage: currentSelectedImageUrl == null")
            return
        }
        imageView.setImageBitmap(currentSelectedImageUrl)
    }




    private fun setImageForOption1Test() {
        // loop through all of the imageViews in the layout and set them to this image
        for (i in 1..7) {
            val imageView = inflatedViewStub1.findViewById<ImageView>(resources.getIdentifier("imageView_option1_image$i", "id", packageName))
            Picasso.get().load("https://marketplace.canva.com/EAE9rkxE9fA/1/0/1067w/canva-beige-elegant-minimalist-travel-scrapbook-photo-collage-%28portrait%29-Vbtv_yLZdys.jpg").into(imageView)
            imageView.setOnClickListener {
                onClickOfCollageImage(imageView)
            }
        }
    }
   private fun handleButtonClicks() {
       inflatedViewStub1 = viewStub1.inflate()
       viewStub1.visibility = View.INVISIBLE
       inflatedViewStub2 = viewStub2.inflate()
       viewStub2.visibility = View.INVISIBLE
       inflatedViewStub3 = viewStub3.inflate()
       viewStub3.visibility = View.INVISIBLE
       setImageForOption1Test()

       binding.buttonCreateCollageOption1.setOnClickListener {

           if (viewStub1.parent != null) {
               viewStub1.inflate()
               Log.d(TAG, "onCreate: viewStub1.parent != null")
               // set others invisible
               viewStub2.visibility = View.INVISIBLE
               viewStub3.visibility = View.INVISIBLE
           } else {
               viewStub1.setVisibility(View.VISIBLE);
               Log.d(TAG, "onCreate: viewStub1.parent == null")
               viewStub2.visibility = View.INVISIBLE
               viewStub3.visibility = View.INVISIBLE
           }
       }

       binding.buttonCreateCollageOption2.setOnClickListener {
           if (viewStub2.parent != null) {
               Log.d(TAG, "onCreate: viewStub2.parent != null")
               viewStub2.inflate()
               // set others invisible
               viewStub1.visibility = View.INVISIBLE
               viewStub3.visibility = View.INVISIBLE
           } else {
               Log.d(TAG, "onCreate: viewStub2.parent == null")
               viewStub2.setVisibility(View.VISIBLE);

               viewStub1.visibility = View.INVISIBLE
               viewStub3.visibility = View.INVISIBLE
           }
       }

       binding.buttonCreateCollageOption3.setOnClickListener {
           if (viewStub3.parent != null) {
               Log.d(TAG, "onCreate: viewStub3.parent != null")
               viewStub3.inflate()
               // set others invisible
               viewStub1.visibility = View.INVISIBLE
               viewStub2.visibility = View.INVISIBLE
           } else {
               Log.d(TAG, "onCreate: viewStub3.parent == null")
               viewStub3.setVisibility(View.VISIBLE);

               viewStub1.visibility = View.INVISIBLE
               viewStub2.visibility = View.INVISIBLE
           }
       }
   }
}