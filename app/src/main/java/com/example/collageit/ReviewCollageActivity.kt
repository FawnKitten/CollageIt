package com.example.collageit

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.collageit.databinding.ActivityReviewBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewCollageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val passedInImageUri = intent.getParcelableExtra<Uri>(ReviewActivity.PASSED_IMAGE_EXTRA)
        Log.d(TAG, "onCreate: passedInImageBitmap - $passedInImageUri")
//        binding.collagePicture.setImageBitmap(passedInImageBitmap)
        Picasso.get().load(passedInImageUri).into(binding.collagePicture)

        binding.publishCollageButton.setOnClickListener {

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
                    users["DOC"] = System.currentTimeMillis().toString()
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
                        db.collection("user").document(auth1).collection("collages").document(
                            count.toString()
                        ).set(users)
                            .addOnCompleteListener {
                                Toast.makeText(
                                    this@ReviewCollageActivity,
                                    "Upload Sucess$url",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
            }
    }


}