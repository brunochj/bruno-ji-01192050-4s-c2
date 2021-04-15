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

    lateinit var preferenciasId1: SharedPreferences
    lateinit var preferenciasId2: SharedPreferences

    lateinit var preferenciasCachorroRaca1: SharedPreferences
    lateinit var preferenciasCachorroRaca2: SharedPreferences

    lateinit var preferenciasCachorroPreco1: SharedPreferences
    lateinit var preferenciasCachorroPreco2: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferenciasId1 = getSharedPreferences("id1", MODE_PRIVATE)
        preferenciasId2 = getSharedPreferences("id2", MODE_PRIVATE)

        preferenciasCachorroRaca1 = getSharedPreferences("cachorroRaca1", MODE_PRIVATE)
        preferenciasCachorroRaca2 = getSharedPreferences("cachorroRaca2", MODE_PRIVATE)

        preferenciasCachorroPreco1 = getSharedPreferences("cachorroPreco1", MODE_PRIVATE)
        preferenciasCachorroPreco2 = getSharedPreferences("cachorroPreco2", MODE_PRIVATE)

    }

    fun comprar(view: View) {

        val apiCachorro = ConexaoApiCachorros.criar()

        val etId1: EditText = findViewById(R.id.et_id1)
        val etId2: EditText = findViewById(R.id.et_id2)

        val id1 = etId1.text.toString().toInt()
        val id2 = etId2.text.toString().toInt()

        val id1String = etId1.text.toString()
        val id2String = etId2.text.toString()

        val editId1 = preferenciasId1.edit()
        val editId2 = preferenciasId2.edit()

        val editCachorroRaca1 = preferenciasCachorroRaca1.edit()
        val editCachorroRaca2 = preferenciasCachorroRaca2.edit()

        val editCachorroPreco1 = preferenciasCachorroPreco1.edit()
        val editCachorroPreco2 = preferenciasCachorroPreco2.edit()

        val telaSucesso = Intent(this, TelaSucesso::class.java)
        val telaErro = Intent(this, TelaErro::class.java)


        apiCachorro.get(id1).enqueue((object: Callback<Cachorro>{
            override fun onFailure(call: Call<Cachorro>, t: Throwable) {
                Toast.makeText(baseContext, "Erro: ${t.message!!}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Cachorro>, response: Response<Cachorro>) {

                editId1.putString("id1", id1String)
                editId1.commit()

                val cachorro1 = response.body()

                if (cachorro1 != null) {
                    editCachorroRaca1.putString("cachorroRaca1", cachorro1.raca)
                    editCachorroRaca1.commit()

                    var preco1 = cachorro1.precoMedio.toString()

                    editCachorroPreco1.putString("editCachorroPreco1", preco1)
                    editCachorroPreco1.commit()
                }

                apiCachorro.get(id2).enqueue((object : Callback<Cachorro>{
                    override fun onFailure(call: Call<Cachorro>, t: Throwable) {
                        Toast.makeText(baseContext, "Erro: ${t.message!!}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Cachorro>, response: Response<Cachorro>) {

                        editId2.putString("id2", id2String)
                        editId2.commit()

                        val cachorro2 = response.body()
                        if (cachorro2 != null) {
                            editCachorroRaca2.putString("cachorroRaca2", cachorro2.raca)
                            editCachorroRaca2.commit()


                            var preco2 = cachorro2.precoMedio.toString()

                            editCachorroPreco2.putString("editCachorroPreco2", preco2)
                            editCachorroPreco2.commit()
                        }
                        if(cachorro1 != null || cachorro2 != null){
                            telaSucesso.putExtra("cachorroRaca1", cachorro1?.raca)
                            telaSucesso.putExtra("cachorroRaca2", cachorro2?.raca)
                            telaSucesso.putExtra("cachorroPreco1", cachorro1?.precoMedio.toString())
                            telaSucesso.putExtra("cachorroPreco2", cachorro2?.precoMedio.toString())
                            startActivity(telaSucesso)
                        }else{
                            telaErro.putExtra("id1", id1)
                            telaErro.putExtra("id2", id2)
                            startActivity(telaErro)
                        }
                    }
                }))
            }

        }))


    }
}