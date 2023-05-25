package com.example.collageit


import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class CollageAdapter (var dataSet: List<Collage>) : RecyclerView.Adapter<CollageAdapter.ViewHolder>(){

    companion object {
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
        const val EXTRA_IMAGE = "EXTRA_IMAGE"
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textViewName: TextView
        val imageViewCollage: ImageView


        init{
            textViewName = view.findViewById(R.id.textView_collageName_itemCollage)
            imageViewCollage = view.findViewById(R.id.imageView_collagePicture_itemCollage)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_collagelistitem, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collage = dataSet[position]
        holder.textViewName.text = collage.name

        Picasso.get().load(collage.picture).into(holder.imageViewCollage);
        holder.itemView.setOnClickListener {
            val detailIntent = Intent(it.context, CollageDetail::class.java)
            detailIntent.putExtra(CollageDetail.EXTRA_TITLE, collage.name)
            detailIntent.putExtra(CollageDetail.EXTRA_DESCRIPTION, collage.description)
            detailIntent.putExtra(CollageDetail.EXTRA_IMAGE, collage.picture)
            it.context.startActivity(detailIntent)
        }




// Create a reference with an initial file path and name




    }
    override fun getItemCount() = dataSet.size
}

