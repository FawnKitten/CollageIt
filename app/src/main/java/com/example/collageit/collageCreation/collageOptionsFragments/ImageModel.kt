package com.example.collageit.collageCreation.collageOptionsFragments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageModel (val image_uri_: String): Parcelable {

    fun getImage_uri(): String {
        return image_uri_
    }

}