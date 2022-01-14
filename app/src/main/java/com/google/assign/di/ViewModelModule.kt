package com.google.assign.di

import com.google.assign.ui.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ListViewModel(get()) }

}