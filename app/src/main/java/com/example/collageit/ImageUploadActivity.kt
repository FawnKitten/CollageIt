package com.example.collageit

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.FileNotFoundException

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
            val fileUris = (0 until clipData.itemCount).map { clipData.getItemAt(it).uri }.debug()
            val filePaths = fileUris.map { getRealPathFromURI(this, it.debug()) }
            val fileExtns = filePaths.map { it.substring(it.lastIndexOf(".") + 1) }
            Log.d(TAG, "onActivityResult: $filePaths")
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
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }
    }

    private fun getPath(uri: Uri?): String {
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        val columnIndex = cursor
            .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        cursor.moveToFirst()
        val imagePath = cursor.getString(columnIndex).debug()
        return imagePath
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(
                contentUri, proj, null,
                null, null
            )
            Log.d(TAG, "getRealPathFromURI: cursor - $cursor")
            cursor!!
            val column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }
}