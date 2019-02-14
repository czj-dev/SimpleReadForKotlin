package com.rank.gank.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rank.gank.repository.PhotoRepository
import javax.inject.Inject


class PhotoViewModel @Inject constructor(application: Application, val repository: PhotoRepository) : AndroidViewModel(application) {


    fun loadList() = repository.loadList(1)

}