package com.google.assign.utils

import androidx.lifecycle.ViewModel
import com.google.assign.db.Cats

class SharedViewModel : ViewModel() {

    var result = Cats()

}