package com.pfc.android.revisionesapp.repositories

import android.content.Context

class AlbaranRepositorio (private val context: Context){

    // llamar al objeto retrofit
    private val retrofit = com.pfc.android.revisionesapp.fragments.RetrofitClient.instance
    // llamar a la interfaz
    private val apiService = retrofit.create(com.pfc.android.revisionesapp.interfaces.ApiService::class.java)

}