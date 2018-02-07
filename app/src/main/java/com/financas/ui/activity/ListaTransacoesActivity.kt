package com.financas.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.condo.finanask.extension.ConvertToCalendar
import com.financas.App
import com.financas.R
import com.financas.dao.TransacaoDAO
import com.financas.data.room.TransacaoEntity
import com.financas.model.Tipo
import com.financas.model.Transacao
import com.financas.ui.ResumoView
import com.financas.ui.adapter.ListaTransacoesAdapter
import com.financas.ui.dialog.AdicionaTransacaoDialog
import com.financas.ui.dialog.AlteraTransacaoDialog
import com.financas.viewmodel.data.TransacaoList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import timber.log.Timber
import java.net.ConnectException


class ListaTransacoesActivity : AppCompatActivity() {

    private val dao = TransacaoDAO()
    private var transacoes = dao.transacoes
    val transacaoViewModel = App.injectTransacaoViewModel()
    val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lista_transacoes)

        initialize()

        configuraResumo()
        configuraLista()
        configuraFab()
    }

    fun subscribe(disposable: Disposable): Disposable {
        subscriptions.add(disposable)
        return disposable
    }

    private fun initialize() {
        subscribe(transacaoViewModel.getTransacoes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Received UIModel with ${it.transacoes.size} users.")
                    showTransacoes(it)
                }, {
                    Timber.w(it)
                    showError()
                }))
    }

    private fun transform(transacoes: List<TransacaoEntity>): List<Transacao> {
        val transacaoList = ArrayList<Transacao>()
        transacoes.forEach {
            val tipo = if(it.tipo == "Receita") Tipo.RECEITA else Tipo.DESPESA
            transacaoList.add(Transacao(it.valor.toBigDecimal(),tipo , it.categoria, it.data.ConvertToCalendar()))
        }
        return transacaoList
    }

    fun showTransacoes(data: TransacaoList) {
        if (data.error == null) {
            transacoes = transform(data.transacoes)
        } else if (data.error is ConnectException) {
            Timber.d("Sem conexão.")
        } else {
            showError()
        }
    }

    fun showError() {
        Toast.makeText(this, "Erro!", Toast.LENGTH_SHORT).show()
    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            adcionaDialog(Tipo.RECEITA)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {
            adcionaDialog(Tipo.DESPESA)
        }
    }

    private fun adcionaDialog(tipo: Tipo) {
        AdicionaTransacaoDialog(window.decorView as ViewGroup, this).configuraDialog(tipo, {
            dao.adiciona(it)
            atualizaTransacoes()
            lista_transacoes_adiciona_menu.close(true)
        })
    }

    private fun configuraLista() {
        with(lista_transacoes_listview) {
            adapter = ListaTransacoesAdapter(transacoes, this@ListaTransacoesActivity)
            setOnItemClickListener { parent, view, position, id ->
                val transacao = transacoes[position]
                AlteraTransacaoDialog(window.decorView as ViewGroup, this@ListaTransacoesActivity).configuraDialog(transacao, {
                    dao.altera(it, position)
                    atualizaTransacoes()
                    lista_transacoes_adiciona_menu.close(true)
                })
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val idMenu = item?.itemId

        if (idMenu == 1) {
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val position = adapterMenuInfo.position

            dao.remove(position)
            atualizaTransacoes()

        }
        return super.onContextItemSelected(item)
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(window.decorView, transacoes)
        resumoView.atualizaView()
    }

    private fun atualizaTransacoes() {
        configuraResumo()
        configuraLista()
    }

}