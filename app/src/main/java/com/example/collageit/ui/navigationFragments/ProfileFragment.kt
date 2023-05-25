package com.example.collageit.ui.navigationFragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.collageit.ImageUploadActivity
import com.example.collageit.LoginActivity
import com.example.collageit.User
import com.example.collageit.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
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
        auth = FirebaseAuth.getInstance()
        val UserID = auth.currentUser?.uid
        val currUser = auth.currentUser
        database = FirebaseDatabase.getInstance().getReference();
        val db = FirebaseFirestore.getInstance()
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val view = binding.root
        if (UserID != null) {
            val docRef = db.collection("user").document(UserID)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val userData = document.data
                        if (userData != null) {
                            binding.textEditName.setText(userData["username"] as String)
                            binding.textEditDescription.setText(userData["bio"] as String)
                            if (userData["profileLink"] != "") {
                                Picasso.get().load(userData["profileLink"] as String)
                                    .into(binding.imageViewProfileProfilePicture)
                            }
                        }
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }

        val user = User(
            "John Doe",
            "john.doe",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fbodhicounseling.com%2Fwp-content%2Fuploads%2F2018%2F05%2Fblank-profile-picture-973460_960_720-300x300.png&f=1&nofb=1&ipt=c0de406be899c4b55ce869cb39cf7cd0a1726c1bf95cc6db371a65d279e0771a&ipo=images"
        )



        binding.imageViewProfileProfilePicture.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, ImageUploadActivity.PHOTO_REQUEST)
        }
        binding.saveInformationButton.setOnClickListener {
            Log.d(TAG, "instance: ${auth}")
            Log.d(TAG, "UserID: ${UserID}")
            Log.d(TAG, "curr user: ${currUser}")
            if (UserID != null) {
                val docRef = db.collection("user").document(UserID)
                var profilePictureLink: String? = getDownloadLink()
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document.get("profilePictureLink") != null) {
                            profilePictureLink = document.get("profilePictureLink") as String
                        } else {
                            Log.d(TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
                val data = hashMapOf(
                    "username" to binding.textEditName.text.toString(),
                    "bio" to binding.textEditDescription.text.toString(),
                    "profilePictureLink" to (profilePictureLink
                        ?: "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fbodhicounseling.com%2Fwp-content%2Fuploads%2F2018%2F05%2Fblank-profile-picture-973460_960_720-300x300.png&f=1&nofb=1&ipt=c0de406be899c4b55ce869cb39cf7cd0a1726c1bf95cc6db371a65d279e0771a&ipo=images")
                )

                db.collection("user").document(UserID)
                    .set(data, SetOptions.merge())
                Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show()

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

    fun upload(imageUri: Uri) {
        val fileRef = FirebaseStorage.getInstance()

            // change to desired reference
            .getReference(System.currentTimeMillis().toString()) // + fileType)
        fileRef.putFile(imageUri)
            .addOnCompleteListener {
                fileRef.downloadUrl.addOnSuccessListener { url ->
                    val user: MutableMap<String, Any> = HashMap()
                    user["profileLink"] = url
                    val auth1 = FirebaseAuth.getInstance().currentUser?.uid
                    val db = FirebaseFirestore.getInstance()
                    var count = 1

                    if (auth1 != null) {
                        db.collection("user").document(auth1).update(user)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Uploaded Profile Picture Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Log.d(
                                        ImageUploadActivity.TAG,
                                        "Error getting documents: ",
                                        task.exception
                                    )
                                }
                            }
                    }
                }
            }
    }

    fun getDownloadLink(): String {

        val auth1 = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        if (auth1 != null) {
            db.collection("user").document(auth1).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Uploaded Profile Picture Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d(ImageUploadActivity.TAG, "Error getting documents: ", task.exception)
                    }
                }
        }
        return ""
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        upload(data!!.data!!)

        if (data.data != null) {
            Picasso.get().load(data.data).into(binding.imageViewProfileProfilePicture)
        }
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