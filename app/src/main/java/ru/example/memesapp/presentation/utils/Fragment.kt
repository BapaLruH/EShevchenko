package ru.example.memesapp.presentation.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

inline fun <reified VM: ViewModel> Fragment.fragmentViewModels(crossinline factory: (SavedStateHandle) -> VM) =
    viewModels<VM>(
        factoryProducer = {
            object : AbstractSavedStateViewModelFactory(this, arguments) {
                override fun <T : ViewModel?> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    if (modelClass == VM::class.java) {
                        @Suppress("UNCHECKED_CAST")
                        return factory(handle) as T
                    }
                    throw IllegalArgumentException("Unexpected argument: $modelClass")
                }
            }
        }
    )