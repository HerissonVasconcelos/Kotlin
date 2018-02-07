package com.financas.data.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.condo.finanask.extension.formataData
import com.financas.model.Tipo
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

@Entity(tableName = "transacao")
data class TransacaoEntity(@PrimaryKey(autoGenerate = true) var idTransacao: Long = 0,
        @ColumnInfo(name = "valor") var valor: String,
        @ColumnInfo(name = "tipo") var tipo: String,
        @ColumnInfo(name = "categoria") var categoria: String,
        @ColumnInfo(name = "data") var data: String) {

        constructor() : this(0, "", "", "", Calendar.getInstance().formataData())

}
