package com.financas.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.condo.finanask.extension.formataData
import com.condo.finanask.extension.formataMoeda
import com.condo.finanask.extension.limitaEmAte
import com.financas.R
import com.financas.model.Tipo
import com.financas.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*


class ListaTransacoesAdapter(val transacoes: List<Transacao>,
                             val context: Context) : BaseAdapter() {

    private val limiteCharCategoria = 20

    override fun getView(posicao: Int, view: View?, parent: ViewGroup?): View {
        val newView = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)

        if (transacoes[posicao].tipo == Tipo.RECEITA) {
            newView.transacao_valor.setTextColor(ContextCompat.getColor(context, R.color.receita))
            newView.transacao_icone.setBackgroundResource(R.drawable.icone_transacao_item_receita)
        } else {
            newView.transacao_valor.setTextColor(ContextCompat.getColor(context, R.color.despesa))
            newView.transacao_icone.setBackgroundResource(R.drawable.icone_transacao_item_despesa)
        }

        newView.transacao_valor.text = transacoes[posicao].valor.formataMoeda()
        newView.transacao_categoria.text = transacoes[posicao].categoria.limitaEmAte(limiteCharCategoria)
        newView.transacao_data.text = transacoes[posicao].data.formataData()

        return newView
    }

    override fun getItem(posicao: Int): Transacao {
        return transacoes[posicao]
    }

    override fun getItemId(posicao: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }

}