package com.example.collageit


import android.annotation.SuppressLint
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
    }
    @SuppressLint("ResourceType")
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textViewName: TextView
        val imageViewCollage: ImageView
        val layout : ConstraintLayout


        init{
            textViewName = view.findViewById(R.id.textView_collageName_itemCollage)
            imageViewCollage = view.findViewById(R.id.imageView_collagePicture_itemCollage)
            layout = view.findViewById(R.layout.activity_collagelistitem)

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

        holder.layout.setOnClickListener {

        }



// Create a reference with an initial file path and name




    }
    override fun getItemCount() = dataSet.size
}

