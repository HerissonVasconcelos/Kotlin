package com.financas

import android.app.Application
import android.arch.persistence.room.Room
import com.financas.data.api.TransacaoApi
import com.financas.data.repository.TransacaoRepository
import com.financas.data.room.AppDatabase
import com.financas.viewmodel.TransacaoViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class App : Application() {

    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var transacaoApi: TransacaoApi
        private lateinit var transacaoRepository: TransacaoRepository
        private lateinit var transacaoViewModel: TransacaoViewModel
        private lateinit var appDatabase: AppDatabase

        fun injectTransacaoApi() = transacaoApi

        fun injectTransacaoViewModel() = transacaoViewModel

        fun injectTransacaoDao() = appDatabase.transacaoDao()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())

        retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://35.164.170.92/api/")
                .build()

        transacaoApi = retrofit.create(TransacaoApi::class.java)

        appDatabase = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "financas").build()

        transacaoRepository = TransacaoRepository(transacaoApi, appDatabase.transacaoDao())
        transacaoViewModel = TransacaoViewModel(transacaoRepository)
    }
}