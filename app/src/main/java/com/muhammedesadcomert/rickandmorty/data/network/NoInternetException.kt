package com.muhammedesadcomert.rickandmorty.data.network

import android.content.Context
import com.muhammedesadcomert.rickandmorty.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class NoInternetException @Inject constructor(@ApplicationContext private val context: Context) : IOException() {
    override val message: String = context.getString(R.string.no_internet_error)
}