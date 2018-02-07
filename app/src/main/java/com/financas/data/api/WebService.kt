package com.financas.data.api

import com.financas.data.room.TransacaoEntity
import com.financas.model.Transacao
import retrofit2.Call
import retrofit2.http.GET


abstract class WebService {

    @GET("transacao")
    abstract fun getTransacoes(): Call<Transacao>
}