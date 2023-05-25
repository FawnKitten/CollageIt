package com.example.collageit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.collageit.databinding.ActivityReviewCollageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class ReviewCollageActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ReviewActivity"
        const val PASSED_IMAGE_EXTRA = "imageData"
    }
    private lateinit var binding: ActivityReviewCollageBinding

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewCollageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val passedInImageUri = intent.getParcelableExtra<Uri>(ReviewActivity.PASSED_IMAGE_EXTRA)
        imageUri = passedInImageUri
        Log.d(TAG, "onCreate: passedInImageBitmap - $passedInImageUri")
//        binding.collagePicture.setImageBitmap(passedInImageBitmap)
        Picasso.get().load(passedInImageUri).into(binding.collagePicture)

        binding.publishCollageButton.setOnClickListener {
            uploadImageToUser(imageUri!!)
            // got back to main activity
            // send the user to the collage Fragment with the new collage

        }
    }


    private fun uploadImageToUser(imageUri: Uri) {
        val fileRef = FirebaseStorage.getInstance()
            // change to desired reference
            .getReference(System.currentTimeMillis().toString()) // + fileType)
        fileRef.putFile(imageUri)
            .addOnCompleteListener {
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    val url = uri
                    val users: MutableMap<String, Any> = HashMap()
                    users["title"] = binding.textEditReviewTitleGetText.text.toString()
                    users["description"] = binding.textEditReviewDescriptGetText.text.toString()
                    users["image"] = url.toString()
                    val auth1 = FirebaseAuth.getInstance().currentUser?.uid
                    val db = FirebaseFirestore.getInstance()
                    var count = 1

                    if (auth1 != null) {
                        db.collection("user").document(auth1).collection("collages").get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    for (document in task.result) {
                                        count++
                                    }
                                    Log.d("TAG", count.toString() + "")
                                } else {
                                    Log.d(ImageUploadActivity.TAG, "Error getting documents: ", task.exception)
                                }
                            }
                    }
                    if (auth1 != null) {
                        db.collection("user").document(auth1).collection("collages").document().set(users)
                            .addOnCompleteListener {
                                Toast.makeText(
                                    this@ReviewCollageActivity,
                                    "Upload Sucess$url",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                    }
                }
            }
    }

}