package com.example.avaliacaocontinuada2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TelaErro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_erro)

        var id1 = intent.getIntExtra("id1", 0)
        var id2 = intent.getIntExtra("id2", 0)

        val tvTituloErro: TextView = findViewById(R.id.tv_deu_ruim)

        var mensagem = getString(R.string.titulo_erro, id1, id2)

        tvTituloErro.text = mensagem
    }
}