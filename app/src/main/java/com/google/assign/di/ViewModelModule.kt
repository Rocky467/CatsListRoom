package com.google.assign.di

import com.google.assign.ui.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {

    val viewModelModule = module {
        viewModel { ListViewModel(get()) }
    }

}
