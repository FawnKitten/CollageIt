package com.example.collageit.collageCreation

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collageit.collageCreation.collageOptionsFragments.ImageModel
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
    private lateinit var inflatedViewSubs: List<View>
    private lateinit var inflatedViewStub1: View
    private lateinit var inflatedViewStub2: View
    private lateinit var inflatedViewStub3: View
    private var currentSelectedViewIndex: Int? = null
    private var currentSelectedImageView: ImageView? = null

    private lateinit var imagesData: List<ImageModel>
    lateinit var imageOptionsAdapter: ImageOptionsListViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding
        binding = ActivityChooseCollageFormatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewStub1 = binding.viewStubCreateCollageSelectedCollageStubOption1
        viewStub2 = binding.viewStubCreateCollageSelectedCollageStubOption2
        viewStub3 = binding.viewStubCreateCollageSelectedCollageStubOption3

        handleButtonClicks()
        // get the extra from the intent here
//        val imagesData = intent.getParcelableArrayListExtra<ImageModel>("imagesData")
        imagesData = listOf(
            ImageModel("https://t4.ftcdn.net/jpg/04/80/95/25/240_F_480952585_wOmBshuqPnlIQVztf5fTSMopQnAteqeM.jpg"),
            ImageModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRYIEzx4gj6IwJz_nOp-xlWfMhIsRqyyHMrw&usqp=CAU"),
            ImageModel("https://t4.ftcdn.net/jpg/04/80/95/25/240_F_480952585_wOmBshuqPnlIQVztf5fTSMopQnAteqeM.jpg"),
            ImageModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ89tEuviOGFofplj4gKpuUcBtBm9VdvfBRWg&usqp=CAU"),
            ImageModel("https://t4.ftcdn.net/jpg/04/80/95/25/240_F_480952585_wOmBshuqPnlIQVztf5fTSMopQnAteqeM.jpg"),
            ImageModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-aBDBxegoNFLLcyAuqXtCKFOdtjJ7p_3m0g&usqp=CAU"),
            ImageModel("https://t4.ftcdn.net/jpg/04/80/95/25/240_F_480952585_wOmBshuqPnlIQVztf5fTSMopQnAteqeM.jpg"),
        )
        // do this fucking shit thank you co piolet
//        adapter = imageOptionsAdapter(imagesData, this::onClickOfCollageImage)
        imageOptionsAdapter = ImageOptionsListViewAdapter(imagesData, this::resetSelectedImage)

        binding.recyclerViewCreateCollageItemsToSelectRv.adapter = imageOptionsAdapter
        binding.recyclerViewCreateCollageItemsToSelectRv.layoutManager = LinearLayoutManager(this@ChooseCollageFormatActivity, RecyclerView.HORIZONTAL, false)

//        val imageTes1 = binding.imageViewCreateCollageTest1
//        Picasso.get().load("https://t4.ftcdn.net/jpg/04/80/95/25/240_F_480952585_wOmBshuqPnlIQVztf5fTSMopQnAteqeM.jpg").into(imageTes1)
//        val imageTest2 = binding.ImageViewCreateCollageTest2
//        Picasso.get().load("https://t4.ftcdn.net/jpg/01/30/94/51/240_F_130945151_gaeyqMhFTg8d9i620ao7vcJ5Qee4dOQ9.jpg").into(imageTest2)
//        imageTes1.setOnClickListener {
//            currentSelectedImageUrl = imageTes1.drawable.current.toBitmap()
//        }
//        imageTest2.setOnClickListener {
//            currentSelectedImageUrl = imageTest2.drawable.current.toBitmap()
//        }
    }

    private fun onClickOfCollageImage(imageView: ImageView) {
        if (currentSelectedImageView == null) {
            Log.d(TAG, "onClickOfCollageImage: currentSelectedImageUrl == null")
            return
        }
        imageView.setImageBitmap(currentSelectedImageView!!.drawable.current.toBitmap())
    }

     fun resetSelectedImage(imageView: ImageView): ImageView? {
         Log.d(TAG, "resetSelectedImage: ")
         val imageViewToReturn = currentSelectedImageView
         currentSelectedImageView = imageView
         return imageViewToReturn
    }

    private fun setImageListenersForEachOption(collageOptionsViews: List<View>) {
        // loop through all of the imageViews in the layout and set them to this image
        // this does not work with the second options
        for (j in 0..collageOptionsViews.size) {
            for (i in 1..7) {
                try {
                    val imageView = collageOptionsViews[j].findViewById<ImageView>(resources.getIdentifier("imageView_option${j + 1}_image$i", "id", packageName))
                    imageView.setOnClickListener {
                        onClickOfCollageImage(imageView)
                    }
                } catch (
                    e: Exception
                ) {
                    Log.d(TAG, "setImageListenersForOptions: exception $e")
                    continue
                }
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
       inflatedViewSubs = listOf(inflatedViewStub1, inflatedViewStub2, inflatedViewStub3)
       setImageListenersForEachOption(inflatedViewSubs)

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
           currentSelectedViewIndex = 0
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
           currentSelectedViewIndex = 1
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
           currentSelectedViewIndex = 2
       }
   }
}