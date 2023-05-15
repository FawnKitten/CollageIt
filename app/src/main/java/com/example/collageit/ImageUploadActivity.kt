package com.example.collageit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.common.io.Files.getFileExtension
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.FileNotFoundException
import com.example.collageit.collageCreation.ChooseCollageFormatActivity

private fun <T> T.debug(): T {
    Log.d(ImageUploadActivity.TAG, "DEBUG: $this")
    return this
}

class ImageUploadActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ImageUploadActivity"
        const val PHOTO_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_upload)

        findViewById<TextView>(R.id.textView8).setOnClickListener {
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
            val clipData = data?.clipData!!.debug()
            // TODO: Only accept valid file extensions
//            val filePaths = fileUris.map { getPath(it.debug()) }
//            Log.d(TAG, "onActivityResult: filePaths - $filePaths")
//            val fileExtns = filePaths.map { it.substring(it.lastIndexOf(".") + 1) }
//            Log.d(TAG, "onActivityResult: $filePaths")
//            try {
//                val allowedFiletypes = listOf("img", "jpg", "jpeg", "png")
//                if (fileExtns.any { !allowedFiletypes.contains(it) }) {
//                    //FINE
//                    Log.d(TAG, "onActivityResult: in the if with $filePaths $fileExtns")
//                } else {
//                    //NOT IN REQUIRED FORMAT
//                    Log.d(TAG, "onActivityResult: not in required format")
//                }
//            } catch (e: FileNotFoundException) {
//                // TODO Auto-generated catch block
//                e.printStackTrace()
//            }


            // TODO: Send URIS to ChooseCollageFormatActivity
            /***************************
             *  NOTE TO JUSTIN         *
             *  -------------          *
             *                         *
             *  INSERT EXTRAS HERE     *
             *                         *
             ***************************/

            val fileUris = (0 until clipData.itemCount).map { clipData.getItemAt(it).uri }.debug()
            val intent = Intent(this, ChooseCollageFormatActivity::class.java)

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

    fun upload(imageUri : Uri, fileType : String) {
        if (imageUri != null) {
            val fileRef = FirebaseStorage.getInstance()

            // change to desired reference
                .getReference(System.currentTimeMillis().toString() + fileType)
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

//    // TODO Get file path
//    private fun getPath(uri: Uri?): String {
//        Log.d(TAG, "getPath: uri - $uri")
//        val projection = arrayOf(MediaStore.MediaColumns.DATA)
//        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
//        val columnIndex = cursor
//            .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
//        cursor.moveToFirst()
//        val imagePath = cursor.getString(columnIndex).debug()
//        return imagePath
//    }
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
//    }
}
