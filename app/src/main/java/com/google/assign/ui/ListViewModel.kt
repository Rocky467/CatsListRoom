package com.google.assign.ui

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

    val users = repository.userList.flow.cachedIn(viewModelScope)


    private val _getCatById = MutableLiveData<NetworkCat>()
    val getCatById: LiveData<NetworkCat> get() = _getCatById

    fun getCatById(catId: String) {
        viewModelScope.launch {
            val netRes = withContext(Dispatchers.IO) {
                repository.getCatById(catId)
            }

            if (netRes.status == Resource.Status.SUCCESS) {
                val subList = netRes.data
                subList?.let {
                    _getCatById.value = it

                }
            }
        }
    }


}
