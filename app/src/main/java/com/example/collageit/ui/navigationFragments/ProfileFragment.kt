package com.example.collageit.ui.navigationFragments

import android.content.Intent
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.collageit.LoginActivity
import com.example.collageit.User
import com.example.collageit.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val TAG = "ProfileFragment"
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: FragmentProfileBinding

    private lateinit var database: DatabaseReference //<----
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val view = binding.root
//        view.setOnClickListener {
//            auth = FirebaseAuth.getInstance()
//            auth.signOut()
//        }

        val user = User(
            "John Doe",
            "john.doe",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fbodhicounseling.com%2Fwp-content%2Fuploads%2F2018%2F05%2Fblank-profile-picture-973460_960_720-300x300.png&f=1&nofb=1&ipt=c0de406be899c4b55ce869cb39cf7cd0a1726c1bf95cc6db371a65d279e0771a&ipo=images"
        )
        auth = FirebaseAuth.getInstance()
        val UserID = auth.currentUser?.uid
        val currUser = auth.currentUser
        database = FirebaseDatabase.getInstance().reference
        binding.saveInformationButton.setOnClickListener {
            Log.d(TAG, "instance: ${auth}")
            Log.d(TAG, "UserID: ${UserID}")
            Log.d(TAG, "curr user: ${currUser}")
            if (UserID != null) {
                database.child("user").child(UserID).child("username").setValue(binding.textEditName.text.toString())
                database.child("user").child(UserID).child("bio").setValue(binding.textEditDescription.text.toString())
                Log.d(TAG, "button clicked and UserID good")
            }
        }
        binding.logOutButton.setOnClickListener {
            auth.signOut()
            binding.root.setOnClickListener {
                startActivity(Intent(context, LoginActivity::class.java))
            }
        }


        //binding.textViewProfileUsername.text = user.name
        // Picasso.get().load(user.profilePictureLink).into(binding.imageViewProfileProfilePicture)

//        return inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}