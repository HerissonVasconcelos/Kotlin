package com.financas.data.repository

import com.financas.data.api.TransacaoApi
import com.financas.data.room.TransacaoDAO
import com.financas.data.room.TransacaoEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TransacaoRepository(val transacaoApi: TransacaoApi, val transacaoDao: TransacaoDAO) {

    fun getTransacoes(): Observable<List<TransacaoEntity>> {
        return Observable.concatArray(
                getTransacoesFromDb(),
                getTransacoesFromApi())
    }


    fun getTransacoesFromDb(): Observable<List<TransacaoEntity>> {
        return transacaoDao.getAllTransacoes().filter { it.isNotEmpty() }
                .toObservable()
                .doOnNext {
                    Timber.d("Dispatching ${it.size} from DB...")
                }
    }

    fun getTransacoesFromApi(): Observable<List<TransacaoEntity>> {
        return transacaoApi.getTransacoes()
                .doOnNext {
                    Timber.d("Dispatching ${it.size} from API...")
                    storeTransacaoInDb(it)
                }
    }

    fun storeTransacaoInDb(transacoes: List<TransacaoEntity>) {
        Observable.fromCallable { transacaoDao.insertAll(transacoes) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Timber.d("Inserted ${transacoes.size} transactions from API in DB...")
                }
    }

    fun insertTransacaoDB(transacaoEntity: TransacaoEntity){
        Observable.fromCallable { transacaoDao.insertTransacao(transacaoEntity) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Timber.d("Inserted in DB...")
                }
    }

    fun updateTransacaoDB(transacaoEntity: TransacaoEntity){
        Observable.fromCallable { transacaoDao.updateTransacao(transacaoEntity) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Timber.d("Update in DB...")
                }
    }

    fun deleteTransacaoDB(transacaoEntity: TransacaoEntity){
        Observable.fromCallable { transacaoDao.deleteTransacao(transacaoEntity) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Timber.d("Update in DB...")
                }
    }
}
