package com.google.assignment.di

import com.google.assignment.ui.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {

    val viewModelModule = module {
        viewModel { ListViewModel(get()) }
    }

}
