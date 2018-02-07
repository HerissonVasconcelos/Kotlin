package com.financas.viewmodel

import com.financas.data.repository.TransacaoRepository
import com.financas.viewmodel.data.TransacaoList
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit


class TransacaoViewModel(val transacaoRepository: TransacaoRepository) {

    fun getTransacoes(): Observable<TransacaoList> {
        //Create the data for your UI, the users lists and maybe some additional data needed as well
        return transacaoRepository.getTransacoes()
                //Drop DB data if we can fetch item fast enough from the API
                //to avoid UI flickers
                .debounce(400, TimeUnit.MILLISECONDS)
                .map {
                    Timber.d("Mapping users to UIData...")
                    TransacaoList(it.take(10), message = "Top 10")
                }
                .onErrorReturn {
                    TransacaoList(emptyList(), "An error occurred", it)
                }
    }

}





