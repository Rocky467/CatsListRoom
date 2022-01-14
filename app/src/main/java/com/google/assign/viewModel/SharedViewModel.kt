package com.google.assign.viewModel

import androidx.lifecycle.ViewModel
import com.google.assign.db.UserEntity
import com.google.assign.model.User


class SharedViewModel : ViewModel() {

    var user = UserEntity()

}