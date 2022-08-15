package com.top1shvetsvadim1.moviesebs.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApp : Application()


//GENERAL TODOs
//TODO: add loading during first loading
//TODO: add placeholders to posters
//TODO: add loading footer to feed
//TODO: there is no error when you try to load 2+ page with no internet
//TODO: there is no error and absolutely wrong UI state when you enter details with no internet connection
//TODO: save state properly of Film details.
//TODO: paddings to recyclerViews
//TODO: do your own ratingBar with animations