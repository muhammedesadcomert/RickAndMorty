package com.invio.rickandmorty.data.network

import android.content.Context
import com.invio.rickandmorty.R
import java.io.IOException
import javax.inject.Inject

class NoInternetException @Inject constructor(private val context: Context) : IOException() {
    override val message: String
        get() = context.getString(R.string.no_internet_error)
}