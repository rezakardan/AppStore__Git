package com.example.appstore.data.stored

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.appstore.utils.SESSION_AUTH_DATA
import com.example.appstore.utils.USER_TOKEN_DATA
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionManagerDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val appContext = context.applicationContext


    companion object {

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            SESSION_AUTH_DATA
        )

        val userToken = stringPreferencesKey(USER_TOKEN_DATA)


    }
    suspend fun saveToken(token:String){

        appContext.dataStore.edit {

            it[userToken]=token



        }



    }


    val getToken:Flow<String?> = appContext.dataStore.data.map {

        it[userToken]



    }



    suspend fun clearToken(){

        appContext.dataStore.edit {

            it.clear()


        }



    }

}