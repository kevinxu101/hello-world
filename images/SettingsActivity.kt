package com.example.sampleapi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sampleapi.databinding.ActivityEntry2Binding.inflate
import com.example.sampleapi.databinding.ActivityMainBinding
import com.example.sampleapi.databinding.ActivitySettingsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    lateinit var sharedPreferences: SharedPreferences
    private val btnFinish by lazy{binding.btnFinish}
    private val txtEthAddress by lazy {binding.txtEthAddress}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences("Hashrate", Context.MODE_PRIVATE)
        val savedEthAddress:String? = sharedPreferences.getString("EthAddress",null)
        txtEthAddress.setText(savedEthAddress.toString())


        btnFinish.setOnClickListener {
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
                                val sharedPreferences:SharedPreferences = getSharedPreferences("EthAddress",Context.MODE_PRIVATE)

                                val editor: SharedPreferences.Editor? = sharedPreferences.edit()
                                editor?.apply{
                                    putString("EthAddress",txtEthAddress.text.trim().toString())
                                }?.apply()


                                Toast.makeText(applicationContext,"New Address Saved", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@SettingsActivity,MainActivity::class.java)
                                startActivity(intent)
                                finish()


                            }else{
                                Toast.makeText(applicationContext,"Invalid Eth Address",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Log.e("RETROFIT_ERROR", response.code().toString())
                    }
                }
            }else Toast.makeText(applicationContext,"Invalid Eth Address", Toast.LENGTH_SHORT).show()
        }
    }
}