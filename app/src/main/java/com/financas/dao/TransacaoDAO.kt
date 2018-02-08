package com.financas.dao

import com.financas.data.room.TransacaoEntity

class TransacaoDAO {

    val transacoes: List<TransacaoEntity> = Companion.transacoes

    companion object {
       private val transacoes: MutableList<TransacaoEntity> = mutableListOf()
    }

    fun adiciona(transacao: TransacaoEntity){
        Companion.transacoes.add(transacao)
    }

    fun altera(transacao: TransacaoEntity, posicao: Int){
        Companion.transacoes[posicao] = transacao
    }

    fun remove(posicao: Int){
        Companion.transacoes.removeAt(posicao)
    }


}