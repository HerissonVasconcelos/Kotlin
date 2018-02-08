package com.financas.ui

import android.support.v4.content.ContextCompat
import android.view.View
import com.condo.finanask.extension.formataMoeda
import com.financas.R
import com.financas.data.room.TransacaoEntity
import com.financas.model.Resumo
import com.financas.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView (val view : View, transacoes: List<TransacaoEntity>){

    val resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(view.context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(view.context, R.color.despesa)

    private fun adicionaTotalReceita() {
        with(view.resumo_card_receita){
            text = resumo.totalReceita.formataMoeda();
            setTextColor(corReceita)
        }
    }

    private fun adicionaTotalDespesa() {
        with(view.resumo_card_despesa){
            text = resumo.totalDespesa.formataMoeda();
            setTextColor(corDespesa)
        }
    }

    private fun adicionaTotalFinancas(){
        val total = resumo.total
        val cor = getCor(total)
        with(view.resumo_card_total){
            setTextColor(cor)
            text = total.formataMoeda()
        }
    }

    private fun getCor(valor: BigDecimal): Int {
        if (valor >= BigDecimal.ZERO) {
           return corReceita
        }
        return corDespesa
    }

    fun atualizaView(){
        adicionaTotalReceita()
        adicionaTotalDespesa()
        adicionaTotalFinancas()
    }
}