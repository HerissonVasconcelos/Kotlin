package com.financas.viewmodel

import com.financas.data.repository.TransacaoRepository
import com.financas.data.room.TransacaoEntity
import com.financas.viewmodel.data.TransacaoList
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit


class TransacaoViewModel(val transacaoRepository: TransacaoRepository) {

    fun getTransacoes(): Observable<TransacaoList> {

        return transacaoRepository.getTransacoes()
                .debounce(400, TimeUnit.MILLISECONDS)
                .map {
                    Timber.d("Mapping to UIData...")
                    TransacaoList(it, message = "Top 10")
                }
                .onErrorReturn {
                    TransacaoList(emptyList(), "An error occurred", it)
                }
    }

    fun insertTransacao(transacao : TransacaoEntity){
        transacaoRepository.insertTransacaoDB(transacao)
    }

    fun updateTransacao(transacao : TransacaoEntity){
        transacaoRepository.updateTransacaoDB(transacao)
    }

    fun deleteTransacao(transacao : TransacaoEntity){
        transacaoRepository.deleteTransacaoDB(transacao)
    }

}





