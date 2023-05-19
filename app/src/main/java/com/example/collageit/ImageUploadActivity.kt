package com.example.collageit

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.collageit.collageCreation.ChooseCollageFormatActivity
import java.io.FileNotFoundException


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

        findViewById<Button>(R.id.button2).setOnClickListener {
            val intent = Intent(this, ChooseCollageFormatActivity::class.java)
            startActivity(intent)
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
}

