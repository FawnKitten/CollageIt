package com.example.collageit.ui.navigationFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.collageit.Collage
import com.example.collageit.CollageAdapter
import com.example.collageit.LoginActivity.Companion.TAG
import com.example.collageit.R
import com.example.collageit.databinding.FragmentCollageListBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CollageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CollageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var adapter: CollageAdapter
    private lateinit var db: FirebaseFirestore
    private var itemsList = mutableListOf<Collage>()

    private lateinit var binding: FragmentCollageListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCollageListBinding.inflate(layoutInflater)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

// onDestroyView.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollageListBinding.inflate(inflater, container, false)
        val view = binding!!.root

        itemsList = mutableListOf(

        )

db = FirebaseFirestore.getInstance()
    db.collection("user")
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                val username = document.getString("username")
                Log.w(TAG, "Documents: " + document.toString())
                Log.d("hello", "${document.id} => ${document.data}")
                val collagesRef = document.getReference().collection("collages")
                collagesRef.get().addOnSuccessListener { collages ->
                            Log.d("hello1", collages.documents.toString())
                    if(!collages.isEmpty) {
                        for (document1 in collages.documents) {
                            Log.d("hello3", document1.toString())
                            val imageurl = document1.get("image")
                            val imagename = document1.get("title")
                            val imagedescription = document1.get("description")



                            itemsList.add(Collage(imagename.toString(), imagedescription.toString(), imageurl.toString()))
                            adapter = CollageAdapter(itemsList)


                            binding.recyclerViewCollageListItem.adapter = adapter
                            binding.recyclerViewCollageListItem.layoutManager = LinearLayoutManager(activity)

                        }
                    }
                    }
                    .addOnFailureListener { exception ->
                        Log.w("hello2", "Error getting documents: ", exception)
                    }


            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents: ", exception)
        }
        return view
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of k
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CollageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CollageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


