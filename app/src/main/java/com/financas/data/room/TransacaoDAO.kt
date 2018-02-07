package com.financas.data.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.financas.model.Transacao
import io.reactivex.Flowable

@Dao
interface TransacaoDAO {

    @Query("select * from transacao")
    fun getAllTransacoes(): Flowable<List<TransacaoEntity>>

    @Query("select count(*) from transacao")
    fun getTotalTransacao(): Flowable<Int>

    @Insert(onConflict = REPLACE)
    fun insertTransacao(transacaoEntity: TransacaoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(transacoes: List<TransacaoEntity>)

    @Update(onConflict = REPLACE)
    fun updateTransacao(transacaoEntity: TransacaoEntity)

    @Delete
    fun deleteTransacao(idTransacao: Long)

}