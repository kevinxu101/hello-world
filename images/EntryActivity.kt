package com.example.sampleapi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sampleapi.databinding.ActivityEntryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val CHECK_ADDRESS_BASE_URL = "https://api.nanopool.org/v1/eth/balance/"
val ethRegex = """^0x[0-9a-fA-F]{40}${'$'}""".toRegex()


class EntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryBinding
    private val btnSend by lazy{binding.btnFinish}
    private val txtEthAddress by lazy {binding.txtEthAddress}
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        sharedPreferences = getSharedPreferences("EthAddress", Context.MODE_PRIVATE)
        val savedEthAddress:String? = sharedPreferences.getString("EthAddress",null)
        txtEthAddress.setText(savedEthAddress.toString())

        btnSend.setOnClickListener {
            //1st Validation Checks if valid Eth address
            if(ethRegex.matchEntire(txtEthAddress.text) != null){

                val api: ApiRequests = Retrofit.Builder()
                    .baseUrl(CHECK_ADDRESS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiRequests::class.java)

                GlobalScope.launch(Dispatchers.IO) {
                    val response: Response<AddressValidationModel> = api.checkEthAddress().execute()
                    if (response.isSuccessful) {
                        val data: AddressValidationModel = response.body()!!
                        withContext(Dispatchers.Main){
                            Log.e("RETROFIT_ERROR", response.body()!!.status.toString())

                            if(response.body()!!.status.toString() == "true"){
                                val editor: SharedPreferences.Editor? = sharedPreferences.edit()
                                editor?.apply{
                                    putString("EthAddress",txtEthAddress.text.toString().trim())
                                }?.apply()

                                val sharedPreferences:SharedPreferences = getSharedPreferences("EthAddress",Context.MODE_PRIVATE)
                                val savedEthAddress:String? = sharedPreferences.getString("EthAddress",null)

                                Log.e("RETROFIT_ERROR", savedEthAddress.toString())
                                val intent = Intent(this@EntryActivity,MainActivity::class.java)
                                startActivity(intent)
                                finish()


                            }else if(response.body()!!.status.toString() == "false" ){
                                Toast.makeText(applicationContext,"Invalid Eth Address.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Log.e("RETROFIT_ERROR", response.code().toString())
                    }
                }

            }else Toast.makeText(applicationContext,"Invalid Eth Address",Toast.LENGTH_SHORT).show()

        }
    }
}