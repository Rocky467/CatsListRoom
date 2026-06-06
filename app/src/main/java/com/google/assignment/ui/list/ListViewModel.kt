package com.google.assignment.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.google.assignment.model.NetworkCat
import com.google.assignment.network.Repository
import com.google.assignment.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val catsList = repository.getCatsList().liveData.cachedIn(viewModelScope)

    private val _getCatById = MutableLiveData<Resource<NetworkCat>>()
    val getCatById: LiveData<Resource<NetworkCat>> get() = _getCatById

    fun getCatById(catId: String) = viewModelScope.launch(Dispatchers.IO) {
        _getCatById.postValue(Resource.Loading())
        _getCatById.postValue(repository.getCatById(catId))
    }

}
