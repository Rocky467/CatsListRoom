package com.google.assignment.utils

import androidx.lifecycle.ViewModel
import com.google.assignment.db.Cats

class SharedViewModel : ViewModel() {
    var result = Cats()
}