package com.financas.delegate

import com.financas.model.Transacao

interface TransacaoDelegate {

    fun delegate(transacao: Transacao)
}