package com.example.youraccounting

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.IllegalStateException
import java.lang.RuntimeException

class EgressViewModelFactory(
    var egressRepository: EgressRepository?
): ViewModelProvider.Factory {
    companion object{
        fun createFactory(activity: Activity): EgressViewModelFactory{
            var context: Context? = null
            context = activity.applicationContext
            if(context == null){
                throw IllegalStateException("Not yet atached to application")
            }
            return EgressViewModelFactory(EgressRepository.getInstance(context))
        }
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try{
            return modelClass.getConstructor(EgressRepository::class.java).newInstance(egressRepository)
        }catch (e: InstantiationException){
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }
}