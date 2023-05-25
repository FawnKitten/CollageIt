package com.example.collageit.collageCreation

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collageit.R
import com.example.collageit.ReviewCollageActivity
import com.example.collageit.collageCreation.collageOptionsFragments.ImageModel
import com.example.collageit.databinding.ActivityChooseCollageFormatBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ChooseCollageFormatActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ChooseCollageForm"
        const val PASSED_IMAGES_EXTRA = "imagesData"
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
    private var currentSelectedHolderImageView: ImageOptionsListViewAdapter.ViewHolder? = null
    private var allImagesHaveBeenClicked: Boolean = false

    private lateinit var imagesData: MutableList<ImageModel>
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
        // get the intnet data based on this line of code  intent.putExtra(ChooseCollageFormatActivity.PASSED_IMAGES_EXTRA, photoUriList.toTypedArray())

        imagesData = mutableListOf()
        val extraUris = intent.getSerializableExtra(PASSED_IMAGES_EXTRA) as? ArrayList<Uri>
        if (extraUris != null && extraUris.isNotEmpty()) {
            for (uri in extraUris) {
                imagesData += ImageModel(uri)
            }
        }


//        imagesData = listOf(
//            ImageModel("https://t4.ftcdn.net/jpg/04/80/95/25/240_F_480952585_wOmBshuqPnlIQVztf5fTSMopQnAteqeM.jpg"),
//            ImageModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRYIEzx4gj6IwJz_nOp-xlWfMhIsRqyyHMrw&usqp=CAU"),
//            ImageModel("https://t4.ftcdn.net/jpg/04/80/95/25/240_F_480952585_wOmBshuqPnlIQVztf5fTSMopQnAteqeM.jpg"),
//            ImageModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ89tEuviOGFofplj4gKpuUcBtBm9VdvfBRWg&usqp=CAU"),
//            ImageModel("https://t4.ftcdn.net/jpg/04/80/95/25/240_F_480952585_wOmBshuqPnlIQVztf5fTSMopQnAteqeM.jpg"),
//            ImageModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-aBDBxegoNFLLcyAuqXtCKFOdtjJ7p_3m0g&usqp=CAU"),
//            ImageModel("https://t4.ftcdn.net/jpg/04/80/95/25/240_F_480952585_wOmBshuqPnlIQVztf5fTSMopQnAteqeM.jpg"),
//        )
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

        binding.buttonChooseCollageFormatReview.setOnClickListener {
            handleSendToReview()
        }
    }

    private fun handleSendToReview() {
        // take a screenshot of the current view
        val screenShotUri = takeScreenShopOfViewSubs()
        Log.d(TAG, "handleSendToReview: screenShotUri $screenShotUri")
        // send the screenshot to the review activity
        // start the review activity
        val intent = Intent(this, ReviewCollageActivity::class.java)
        intent.putExtra(ReviewCollageActivity.PASSED_IMAGE_EXTRA, screenShotUri)
        startActivity(intent)
    }

    private fun takeScreenShopOfViewSubs(): Uri? {
        Log.d(TAG, "takeScreenShopOfViewSubs: currentSelectedViewIndex $currentSelectedViewIndex")
        val view = inflatedViewSubs[currentSelectedViewIndex!!]
        val bitmap = view.drawToBitmap()
        Log.d(TAG, "takeScreenShopOfViewSubs: bitmap $bitmap")
        // convert bitmap to uri

        val tempFile = File.createTempFile("temprentpk", ".png")
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val bitmapData = bytes.toByteArray()

        val fileOutPut = FileOutputStream(tempFile)
        fileOutPut.write(bitmapData)
        fileOutPut.flush()
        fileOutPut.close()
        return Uri.fromFile(tempFile)
    }

    private fun checkIfAllImagesHaveBeenSet(): Boolean {
        val currentInflatedView = inflatedViewSubs[currentSelectedViewIndex!!]
        // loop through all of the image views and see if any of them are equal to drawable grey
        for (i in 1..7) {
            try {
                val imageView = currentInflatedView.findViewById<ImageView>(resources.getIdentifier("imageView_option${currentSelectedViewIndex!! + 1}_image$i", "id", packageName))
                if (imageView.drawable.current.constantState == resources.getDrawable(R.drawable.grey, null).constantState) {
                    return false
                }
            } catch (
                e: Exception
            ) {
                Log.d(TAG, "setImageListenersForOptions: exception $e")
                continue
            }
        }
        return true
    }

    private fun onClickOfCollageImage(imageView: ImageView) {
        if (currentSelectedHolderImageView == null) {
            Log.d(TAG, "onClickOfCollageImage: currentSelectedImageUrl == null")
            return
        }
        imageView.setImageBitmap(currentSelectedHolderImageView!!.imageViewCollage.drawable.current.toBitmap())
        allImagesHaveBeenClicked = checkIfAllImagesHaveBeenSet()
        if (allImagesHaveBeenClicked) {
            binding.buttonChooseCollageFormatReview.visibility = View.VISIBLE
        }
    }

     fun resetSelectedImage(selectedViewHolder: ImageOptionsListViewAdapter.ViewHolder): ImageOptionsListViewAdapter.ViewHolder? {
         Log.d(TAG, "resetSelectedImage: ")
         val imageViewToReturn = currentSelectedHolderImageView
         currentSelectedHolderImageView = selectedViewHolder
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