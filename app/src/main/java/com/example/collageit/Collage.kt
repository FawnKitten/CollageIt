package com.example.collageit

import java.util.*
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Collage (val username: String,
                    val name: String,
                    val description: String,
                    val picture: String

                    ) : Parcelable
