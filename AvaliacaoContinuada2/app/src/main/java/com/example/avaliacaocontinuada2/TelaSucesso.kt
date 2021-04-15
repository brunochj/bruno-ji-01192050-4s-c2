package com.example.avaliacaocontinuada2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TelaSucesso : AppCompatActivity() {
    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_sucesso)

        var raca1 = intent.getStringExtra("cachorroRaca1")
        var raca2 = intent.getStringExtra("cachorroRaca2")
        var preco1 = intent.getStringExtra("cachorroPreco1")
        var preco2 = intent.getStringExtra("cachorroPreco2")

        var precoTotal:Double = preco1.toString().toDouble() + preco2.toString().toDouble()

        println("teste")
        println(raca1)
        println(raca2)
        println(preco1)
        println(preco2)

        val cachorro1: TextView = findViewById(R.id.tv_cachorro1)
        val cachorro2: TextView = findViewById(R.id.tv_cachorro2)

        var mensagemDog1 = getString(R.string.cachorro1, raca1)
        cachorro1.text = mensagemDog1
        var mensagemDog2 = getString(R.string.cachorro2, raca2)
        cachorro2.text = mensagemDog2

        var mensagemPreco = getString(R.string.preco_total, precoTotal)


    }
}