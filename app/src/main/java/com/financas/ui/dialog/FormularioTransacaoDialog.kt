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

abstract class FormularioTransacaoDialog(private val viewGroup: ViewGroup, private val context: Context) {

    abstract protected val tituloBotao : String
    private val viewAdicionaReceita = criaLayout()
    protected val campoCategoria = viewAdicionaReceita.form_transacao_categoria
    protected val campoValor = viewAdicionaReceita.form_transacao_valor

    protected val campoData = viewAdicionaReceita.form_transacao_data

    fun configuraDialog(tipo: Tipo, delagate: (transacao: Transacao) -> Unit) {

        configuraCampoData()

        configuraCampoCategoria(tipo)

        configuraForm(tipo, delagate)
    }

    private fun configuraForm(tipo: Tipo, delagate: (transacao: Transacao) -> Unit) {

        val titulo = tituloPor(tipo)
        AlertDialog.Builder(context).setTitle(titulo)
                .setView(viewAdicionaReceita)
                .setPositiveButton(tituloBotao, DialogInterface.OnClickListener { _, _ ->

                    var valor = convertCampoValor(campoValor.text.toString())

                    val data = campoData.text.toString().ConvertToCalendar()

                    val categoria = campoCategoria.selectedItem.toString()

                    delagate(Transacao(tipo = tipo, valor = valor, data = data, categoria = categoria))

                })
                .setNegativeButton("Cancelar", null)
                .show()
    }

    abstract protected fun tituloPor(tipo: Tipo): Int

    fun convertCampoValor(valor: String): BigDecimal {

        return try {
            BigDecimal(valor)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context, "Informe um valor!", Toast.LENGTH_LONG).show()
            BigDecimal.ZERO
        }

    }

    private fun configuraCampoCategoria(tipo: Tipo) {

        val categorias = categoriaPor(tipo)

        val adapter = ArrayAdapter.createFromResource(context, categorias, android.R.layout.simple_spinner_dropdown_item)
        campoCategoria.adapter = adapter
    }

    private fun configuraCampoData() {

        val hoje = Calendar.getInstance()
        val year = hoje.get(Calendar.YEAR)
        val month = hoje.get(Calendar.MONTH)
        val day = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.formataData());
        campoData.setOnClickListener {
            DatePickerDialog(context,
                    android.R.style.Theme_Holo_Dialog,
                    { _, yearAtual, monthofyear, dayofmonth ->

                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(yearAtual, monthofyear, dayofmonth)

                        campoData.setText(dataSelecionada.formataData())
                    }, year, month, day).show()
        }
    }

    fun criaLayout(): View {
        return LayoutInflater.from(context).inflate(R.layout.form_transacao, viewGroup, false)
    }

    protected fun categoriaPor(tipo: Tipo): Int {
        val categorias = if (tipo == Tipo.RECEITA) {
            R.array.categorias_de_receita
        } else {
            R.array.categorias_de_despesa
        }
        return categorias
    }
}