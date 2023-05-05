package com.example.collageit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CollageAdapter (var dataSet: List<Collage>) : RecyclerView.Adapter<CollageAdapter.ViewHolder>(){
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
    }
    override fun getItemCount() = dataSet.size
}