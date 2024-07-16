package com.google.assign.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.google.assign.model.NetworkCat
import com.google.assign.network.Repository
import com.google.assign.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(private val repository: Repository) : ViewModel() {

    val cats = repository.catsList.flow.cachedIn(viewModelScope)

    private val _getCatById = MutableLiveData<NetworkCat>()
    val getCatById: LiveData<NetworkCat> get() = _getCatById

    fun getCatById(catId: String) {
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO) {
                repository.getCatById(catId)
            }

            if (res.status == Resource.Status.SUCCESS) {
                val subList = res.data
                subList?.let {
                    _getCatById.value = it
                }
            }
        }
    }

}
