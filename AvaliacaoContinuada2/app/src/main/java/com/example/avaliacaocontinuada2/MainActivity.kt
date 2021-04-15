package com.example.avaliacaocontinuada2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var preferencias1: SharedPreferences
    lateinit var preferencias2: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferencias1 = getSharedPreferences("id1", MODE_PRIVATE)
        preferencias2 = getSharedPreferences("id2", MODE_PRIVATE)

    }

    fun comprar(view: View) {

        val apiCachorro = ConexaoApiCachorros.criar()

        val etId1: EditText = findViewById(R.id.et_id1)
        val etId2: EditText = findViewById(R.id.et_id2)

        val id1 = etId1.text.toString().toInt()
        val id2 = etId2.text.toString().toInt()

        val id1String = etId1.text.toString()
        val id2String = etId2.text.toString()

        val edit1 = preferencias1.edit()
        val edit2 = preferencias2.edit()

        val telaSucesso = Intent(this, TelaSucesso::class.java)
        val telaErro = Intent(this, TelaErro::class.java)


        apiCachorro.get(id1).enqueue((object: Callback<Cachorro>{
            override fun onFailure(call: Call<Cachorro>, t: Throwable) {
                Toast.makeText(baseContext, "Erro: ${t.message!!}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Cachorro>, response: Response<Cachorro>) {

                edit1.putString("id1", id1String)
                edit1.commit()

                val cachorro1 = response.body()
                println("teste1")
                println(cachorro1)

                apiCachorro.get(id2).enqueue((object : Callback<Cachorro>{
                    override fun onFailure(call: Call<Cachorro>, t: Throwable) {
                        Toast.makeText(baseContext, "Erro: ${t.message!!}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Cachorro>, response: Response<Cachorro>) {

                        edit2.putString("id2", id2String)
                        edit2.commit()

                        val cachorro2 = response.body()
                        println("teste2")
                        println(cachorro2)

                        if(cachorro1 != null || cachorro2 != null){
//                            telaSucesso.putExtra("id1", id1String)
//                            telaSucesso.putExtra("id2", id2String)
                            telaSucesso.putExtra("id1", id1)
                            telaSucesso.putExtra("id2", id2)
                            startActivity(telaSucesso)
                        }else{
                            telaErro.putExtra("id1", id1String)
                            telaErro.putExtra("id2", id2String)
                            startActivity(telaErro)
                        }
                    }
                }))
            }

        }))


    }
}