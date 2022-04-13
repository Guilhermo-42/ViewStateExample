package br.com.exampleviewstate.delegate

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import br.com.exampleviewstate.R

interface LoadingDelegate {
    fun registerLoadingDelegate(context: Context, root: ViewGroup)
    fun onLoading()
    fun onSuccess()
    fun onFailure()
}

class LoadingDelegation : LoadingDelegate {

    private lateinit var context: Context

    private lateinit var root: ViewGroup

    private val bar by lazy {
        LayoutInflater.from(context).inflate(R.layout.default_progress, root, false)
    }

    private val successDialog by lazy {
        AlertDialog.Builder(context)
            .setMessage("Ai sim, sucesso!")
            .setTitle("Sucesso padrão")
            .create()
    }

    private val errorDialog by lazy {
        AlertDialog.Builder(context)
            .setMessage("Ai não, erro!")
            .setTitle("Erro padrão")
            .create()
    }

    override fun registerLoadingDelegate(context: Context, root: ViewGroup) {
        this.context = context
        this.root = root
    }

    override fun onLoading() {
        if (root.children.any { it == bar }) {
            bar.visibility = View.VISIBLE
        } else {
            root.addView(bar)
        }
    }

    override fun onSuccess() {
        bar.visibility = View.GONE
        successDialog.show()
    }

    override fun onFailure() {
        bar.visibility = View.GONE
        errorDialog.show()
    }

}