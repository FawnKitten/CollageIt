package com.example.collageit

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.common.io.Files.getFileExtension
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.FileNotFoundException
import java.net.URI


class ImageUploadActivity : AppCompatActivity() {

    companion object {
        val TAG = "ImageUploadActivity"
        val PHOTO_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_upload)

        findViewById<TextView>(R.id.textView8).setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, PHOTO_REQUEST)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PHOTO_REQUEST) if (resultCode == RESULT_OK) {
            val selectedImage: Uri? = data?.data
            val filePath = getPath(selectedImage)
            val file_extn = filePath.substring(filePath.lastIndexOf(".") + 1)
            Log.d(TAG, "onActivityResult: $filePath")
            try {
                if (file_extn == "img" || file_extn == "jpg" || file_extn == "jpeg" || file_extn == "gif" || file_extn == "png") {
                    //FINE
                    Log.d(TAG, "onActivityResult: in the if with $filePath $file_extn")
                } else {
                    //NOT IN REQUIRED FORMAT
                    Log.d(TAG, "onActivityResult: not in required format")
                }
            } catch (e: FileNotFoundException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }
    }

    fun getPath(uri: Uri?): String {
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        var columnIndex = cursor
            .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        cursor.moveToFirst()
        var imagePath = cursor.getString(columnIndex)
        return cursor.getString(columnIndex)
    }
    fun upload(imageUri : Uri) {
        if (imageUri != null) {
            val fileRef = FirebaseStorage.getInstance()
                    // change to desired reference
                .getReference(System.currentTimeMillis().toString() + getFileExtension(imageUri))
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

                        db.collection("Posts").get().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                for (document in task.result) {
                                    count++
                                }
                                Log.d("TAG", count.toString() + "")
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.exception)
                            }
                        }
                        if (auth1 != null) {
                            db.collection("user").document(auth1).collection("collages").document(
                                count.toString()
                            ).set(users)
                                .addOnCompleteListener(
                                    OnCompleteListener<Void?> {
                                        Toast.makeText(
                                            this@ImageUploadActivity,
                                            "Upload Sucess" + url.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    })
                        }
                    }
                }
        }
    }
}

