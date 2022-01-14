package com.google.assign.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.google.assign.network.Repository

class ListViewModel(private val repository: Repository) : ViewModel() {

    val users = repository.userList.flow.cachedIn(viewModelScope)

}
