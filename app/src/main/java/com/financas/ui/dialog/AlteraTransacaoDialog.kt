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
import com.condo.finanask.extension.ConvetToEnumTipo
import com.condo.finanask.extension.formataData
import com.financas.R
import com.financas.data.room.TransacaoEntity
import com.financas.delegate.TransacaoDelegate
import com.financas.model.Tipo
import com.financas.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AlteraTransacaoDialog(val viewGroup: ViewGroup, val context: Context) : FormularioTransacaoDialog(viewGroup, context) {

    override val tituloBotao: String
        get() = "Alterar"

    fun configuraDialog(transacao: TransacaoEntity, delagate: (transacao: TransacaoEntity) -> Unit) {

        super.configuraDialog(transacao.idTransacao,transacao.tipo.ConvetToEnumTipo(), delagate)

        campoValor.setText(transacao.valor)
        campoData.setText(transacao.data)

        val categoryArray = context.resources.getStringArray(categoriaPor(transacao.tipo.ConvetToEnumTipo()))
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