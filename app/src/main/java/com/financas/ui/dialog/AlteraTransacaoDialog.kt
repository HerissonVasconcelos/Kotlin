package com.financas.ui.dialog

import android.app.AlertDialog

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.condo.finanask.extension.ConvertToCalendar
import com.condo.finanask.extension.formataData
import com.financas.R
import com.financas.delegate.TransacaoDelegate
import com.financas.model.Tipo
import com.financas.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AlteraTransacaoDialog(val viewGroup: ViewGroup, val context: Context) : FormularioTransacaoDialog(viewGroup, context) {

    override val tituloBotao: String
        get() = "Alterar"

    fun configuraDialog(transacao: Transacao, delagate: (transacao: Transacao) -> Unit) {

        super.configuraDialog(transacao.tipo, delagate)

        campoValor.setText(transacao.valor.toString())
        campoData.setText(transacao.data.formataData())

        val categoryArray = context.resources.getStringArray(categoriaPor(transacao.tipo))
        campoCategoria.setSelection(categoryArray.indexOf(transacao.categoria))
    }

    override fun tituloPor(tipo: Tipo): Int {
        val titulo = if (tipo == Tipo.RECEITA) {
            R.string.altera_receita
        } else {
            R.string.altera_despesa
        }
        return titulo
    }


}