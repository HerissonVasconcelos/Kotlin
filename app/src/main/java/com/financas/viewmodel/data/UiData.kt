package com.financas.viewmodel.data

import com.financas.data.room.TransacaoEntity

data class TransacaoList(val transacoes: List<TransacaoEntity>, val message : String, val error : Throwable? = null)