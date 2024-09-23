package com.dicoding.pokelist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val name: String,
    val description: String,
    val photo: Int,
    val height: String,
    val weight: String,
    val type: String,
    val abilities: String,
    val weaknesses: String
) : Parcelable