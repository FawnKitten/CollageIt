package com.example.collageit.collageCreation.collageOptionsFragments

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageModel (val image_uri_: Uri): Parcelable {

    fun getImage_uri(): Uri {
        return image_uri_
    }

}