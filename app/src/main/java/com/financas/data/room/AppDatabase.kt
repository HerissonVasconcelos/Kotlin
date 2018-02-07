package com.financas.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = arrayOf(TransacaoEntity::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun transacaoDao(): TransacaoDAO

}