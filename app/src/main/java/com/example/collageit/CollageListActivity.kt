package com.example.collageit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.collageit.databinding.ActivityCollagelistBinding

class CollageListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCollagelistBinding
    lateinit var adapter: CollageAdapter
    companion object {
        val TAG = "CollageListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollagelistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //need to do adapter = CollageAdapter(collageList)
//        binding.recyclerViewCollageList.adapter = adapter(CollageAdapter)
        binding.recyclerViewCollageList.layoutManager = LinearLayoutManager(this)


    }
}