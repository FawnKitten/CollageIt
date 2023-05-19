package com.example.collageit.ui.navigationFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
//import com.bumptech.glide.Glide
import com.example.collageit.Collage
import com.example.collageit.CollageAdapter
import com.example.collageit.R
import com.example.collageit.databinding.FragmentCollageListBinding
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
    private lateinit var binding: FragmentCollageListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCollageListBinding.inflate(layoutInflater)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    // This property is only valid between onCreateView and
// onDestroyView.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollageListBinding.inflate(inflater, container, false)
        val view = binding!!.root
        adapter = CollageAdapter(listOf(
            Collage(
                "Joao",
                "https://marketplace.canva.com/EAE9rkxE9fA/1/0/1067w/canva-beige-elegant-minimalist-travel-scrapbook-photo-collage-%28portrait%29-Vbtv_yLZdys.jpg",
                "description"
            ),
            Collage(
                "Joao2",
                "https://firebasestorage.googleapis.com/v0/b/collageit-86d00.appspot.com/o/images%2Fimages.jpeg?alt=media&token=7962795c-fd2a-4efc-a129-cda403a7ab9b",
                "description"
            )
        ))


        binding.recyclerViewCollageListItem.adapter = adapter
        binding.recyclerViewCollageListItem.layoutManager = LinearLayoutManager(activity)
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


