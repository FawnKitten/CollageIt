package com.example.collageit

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.collageit.collageCreation.ChooseCollageFormatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.FileNotFoundException


class ImageUploadActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ImageUploadActivity"
        const val PHOTO_REQUEST = 1

//        fun upload(context: Context, imageUri: Uri) {
//            val fileRef = FirebaseStorage.getInstance()
//
//                // change to desired reference
//                .getReference(System.currentTimeMillis().toString()) // + fileType)
//            fileRef.putFile(imageUri)
//                .addOnCompleteListener {
//                    fileRef.downloadUrl.addOnSuccessListener { uri ->
//                        val users: MutableMap<String, Any> = HashMap()
//                        users["DOC"] = System.currentTimeMillis().toString()
//                        users["image"] = uri.toString()
//                        val auth1 = FirebaseAuth.getInstance().currentUser?.uid
//                        val db = FirebaseFirestore.getInstance()
//                        var count = 1
//
//                        if (auth1 != null) {
//                            db.collection("user").document(auth1).collection("collages").get()
//                                .addOnCompleteListener { task ->
//                                    if (task.isSuccessful) {
//                                        for (document in task.result) {
//                                            count++
//                                        }
//                                        Log.d("TAG", count.toString() + "")
//                                    } else {
//                                        Log.d(TAG, "Error getting documents: ", task.exception)
//                                    }
//                                }
//                        }
//                        if (auth1 != null) {
//                            db.collection("user").document(auth1).collection("collages").document(
//                                count.toString()
//                            ).set(users)
//                                .addOnCompleteListener {
//                                    Toast.makeText(
//                                        context,
//                                        "Upload Success $uri",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                        }
//                    }
//                }
//        }

        fun getPath(context: Context, uri: Uri): String {
            val contentResolver = context.applicationContext.contentResolver
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor = contentResolver.query(uri, projection, null, null, null)!!
            Log.d(TAG, "getPath: cursor Not Null")
            var filePath = ""
            cursor.let {
                if (it.moveToFirst()) {
                    Log.d(TAG, "getPath: moved to first")
                    val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    Log.d(TAG, "getPath: ${columnIndex}")
                    Log.d(TAG, "getPath: $cursor")
                    filePath = cursor.getString(columnIndex)!!
                    Log.d(TAG, "getPath: filepath not null")
                }
                cursor.close()
            }
            return filePath
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_upload)
        // copied
        findViewById<TextView>(R.id.button_createCollage_upload).setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.type = "image/*"
            startActivityForResult(intent, PHOTO_REQUEST)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PHOTO_REQUEST) if (resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: uri - ${data?.clipData}")
            val clipData = data?.clipData!!

            // TODO: Only accept valid file extensions
            val fileUris = (0 until clipData.itemCount).map { clipData.getItemAt(it).uri }
            val filePaths = fileUris.map { getPath(this, it) }
            Log.d(TAG, "onActivityResult: filePaths - $filePaths")
            val fileExtns = filePaths.map { it.substring(it.lastIndexOf(".") + 1) }
            Log.d(TAG, "onActivityResult: extns - $fileExtns")
            try {
                val allowedFiletypes = listOf("img", "jpg", "jpeg", "png")
                if (fileExtns.any { !allowedFiletypes.contains(it) }) {
                    //FINE
                    Log.d(TAG, "onActivityResult: in the if with $filePaths $fileExtns")
                } else {
                    //NOT IN REQUIRED FORMAT
                    Log.d(TAG, "onActivityResult: not in required format")
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }


            // TODO: Send URIS to ChooseCollageFormatActivity
            val photoUriList = arrayListOf<Uri>()
            for (uri in fileUris) {
                photoUriList.add(uri)
            }
            Log.d(TAG, "onActivityResult: photoUriList - $photoUriList")
            /***************************
             *  NOTE TO JUSTIN         *
             *  -------------          *
             *                         *
             *  INSERT EXTRAS HERE     *
             *                         *
             ***************************/

//            for (uri in fileUris) {
//                upload(this, uri)
//            }
            val intent = Intent(this, ChooseCollageFormatActivity::class.java)
            intent.putExtra(ChooseCollageFormatActivity.PASSED_IMAGES_EXTRA, photoUriList)
            startActivity(intent)

        }
    }



}

//
//    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
//        var cursor: Cursor? = null
//        return try {
//            val proj = arrayOf(MediaStore.Images.Media.DATA)
//            cursor = context.contentResolver.query(
//                contentUri, proj, null,
//                null, null
//            )
//            Log.d(TAG, "getRealPathFromURI: cursor - $cursor")
//            cursor!!
//            val columnIndex = cursor
//                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//            cursor.moveToFirst()
//            cursor.getString(columnIndex)
//        } finally {
//            cursor?.close()
//        }
//    } }

