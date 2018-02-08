package com.financas.delegate

import com.financas.data.room.TransacaoEntity

interface TransacaoDelegate {

    fun delegate(transacao: TransacaoEntity)
}