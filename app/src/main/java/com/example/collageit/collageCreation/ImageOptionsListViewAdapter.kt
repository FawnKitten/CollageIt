package com.example.collageit.collageCreation

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.collageit.CollageAdapter
import com.example.collageit.R
import com.example.collageit.collageCreation.collageOptionsFragments.ImageModel
import com.squareup.picasso.Picasso
import kotlin.reflect.KFunction1


 class ImageOptionsListViewAdapter(var dataSet: List<ImageModel>, var resetSelectedImage: (imageView: ImageView) -> ImageView?) : RecyclerView.Adapter<ImageOptionsListViewAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageViewCollage: ImageView
        var currentImageSelected: ViewHolder? = null

        init {
            imageViewCollage = view.findViewById(R.id.imageView_imageItem_image)

        }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.choose_image_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val image = dataSet[position]

            Picasso.get().load(image.getImage_uri()).into(holder.imageViewCollage);

           holder.imageViewCollage.setOnClickListener{
               imageClicked(holder)
           }
            if (position == 0) {
                imageClicked(holder)

            }
        }

        fun imageClicked(holder: ViewHolder){
            // add the green border to the image
            // remove the green border from the previous image
            // set the image as the selected image
            val greenBorderDrawable = ContextCompat.getDrawable(holder.imageViewCollage.context, R.drawable.green_border)
            holder.imageViewCollage.background = greenBorderDrawable
            val pastImageSelected = this@ImageOptionsListViewAdapter.resetSelectedImage(holder.imageViewCollage)
            if (pastImageSelected !== holder.imageViewCollage) {
                pastImageSelected?.background = null
            }


        }

        override fun getItemCount() = dataSet.size
}