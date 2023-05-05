package com.example.collageit.collageCreation

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewStub
import androidx.core.view.isVisible
import com.example.collageit.R
import com.example.collageit.databinding.ActivityChooseCollageFormatBinding

class ChooseCollageFormatActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ChooseCollageForm"
    }
    // binding
     private lateinit var binding: ActivityChooseCollageFormatBinding
     private lateinit var viewStub1: ViewStub
    private lateinit var viewStub2: ViewStub
    private lateinit var viewStub3: ViewStub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding
        binding = ActivityChooseCollageFormatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewStub1 = binding.viewStubCreateCollageSelectedCollageStubOption1
        viewStub2 = binding.viewStubCreateCollageSelectedCollageStubOption2
        viewStub3 = binding.viewStubCreateCollageSelectedCollageStubOption3
        handleButtonClicks()

    }
   private fun handleButtonClicks() {
       viewStub1.inflate()
       viewStub1.visibility = View.VISIBLE
       viewStub2.inflate()
       viewStub2.visibility = View.INVISIBLE
       viewStub3.inflate()
       viewStub3.visibility = View.INVISIBLE

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