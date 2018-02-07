package com.financas.data.api

import com.financas.data.room.TransacaoEntity
import io.reactivex.Observable
import retrofit2.http.GET

interface TransacaoApi{

    @GET("transacao")
    fun getTransacoes(): Observable<List<TransacaoEntity>>

}
