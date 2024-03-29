package com.example.acelanandroid.screens.home

import androidx.lifecycle.ViewModel
import com.example.acelanandroid.retrofit.AppRetrofit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {
}