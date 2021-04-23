package com.example.sampleapi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.sampleapi.databinding.ActivityMainBinding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://api.nanopool.org/v1/eth/user/"
const val COMPUTATION_BASE_URL = "https://api.nanopool.org/v1/eth/approximated_earnings/"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences

    private val btnRefresh by lazy{binding.btnRefresh}
    private val txtAvgHashrate by lazy {binding.txtAverageHashrate}
    private val txtLastReportedHashrate by lazy{binding.txtLastReportedHashrate}
    private val txtCurrentAverageHashrate by lazy{binding.txtCurrentAverageHashrate}
    private val txtConfirmedBalance by lazy{binding.txtConfirmedBalance}
    private val txtAverageHashrate24Hours_main by lazy{binding.txtAverageHashrate24HoursMain}

    private val txtCoinMinute by lazy {binding.coinsMinute}
    private val txtCoinHour by lazy {binding.coinsHour}
    private val txtCoinDay by lazy {binding.coinsDay}
    private val txtCoinWeek by lazy {binding.coinsWeek}
    private val txtCoinMonth by lazy {binding.coinsMonth}

    private val txtBtcMinute by lazy {binding.btcMinute}
    private val txtBtcHour by lazy {binding.btcHour}
    private val txtBtcDay by lazy {binding.btcDay}
    private val txtBtcWeek by lazy {binding.btcWeek}
    private val txtBtcMonth by lazy {binding.btcMonth}

    private val txtUsdMinute by lazy {binding.usdMinute}
    private val txtUsdHour by lazy {binding.usdHour}
    private val txtUsdDay by lazy {binding.usdDay}
    private val txtUsdWeek by lazy {binding.usdWeek}
    private val txtUsdMonth by lazy {binding.usdMonth}
    //Im so sorry for using findviewbyid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences("Hashrate", Context.MODE_PRIVATE)

        retrieveAPIdata()
        retrieveComputations()

        btnRefresh.setOnClickListener{
            retrieveAPIdata()
            retrieveComputations()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this@MainActivity,SettingsActivity::class.java)
        startActivity(intent)
        finish()

        return super.onOptionsItemSelected(item)
    }
    private fun retrieveAPIdata() {

        val api: ApiRequests = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {

            var sharedPreferences: SharedPreferences = getSharedPreferences("EthAddress", Context.MODE_PRIVATE)
            val savedEthAddress:String? = sharedPreferences.getString("EthAddress",null)


            val response: Response<MiningInformationModel> = api.getNanoPoolAPI(savedEthAddress.toString()).execute()
            if (response.isSuccessful) {
                val data: MiningInformationModel = response.body()!!
                withContext(Dispatchers.Main){
                    txtAvgHashrate.text = response.body()!!.data.avgHashrate.h6
                    txtLastReportedHashrate.text = response.body()!!.data.avgHashrate.h1
                    txtCurrentAverageHashrate.text = response.body()!!.data.hashrate
                    txtConfirmedBalance.text = response.body()!!.data.balance + " ETH"
                    txtAverageHashrate24Hours_main.text = response.body()!!.data.avgHashrate.h24

                    sharedPreferences = getSharedPreferences("Hashrate", Context.MODE_PRIVATE)

                    val editor: SharedPreferences.Editor? = sharedPreferences.edit()
                    editor?.apply{
                        putString("Hashrate",response.body()!!.data.avgHashrate.h6)
                    }?.apply()
                }

                val Hashrate:String? = sharedPreferences.getString("Hashrate",null)

            }else{
                Log.e("RETROFIT_ERROR", response.code().toString())
            }
        }

    }

    private fun retrieveComputations(){

        val api: ApiRequests = Retrofit.Builder()
                .baseUrl(COMPUTATION_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {


            val Hashrate:String? = sharedPreferences.getString("Hashrate",null)

            val response: Response<CalculationResultModel>  = api.getNanoPoolComputation(Hashrate.toString()).execute()
            if (response.isSuccessful) {
                val data: CalculationResultModel = response.body()!!
                withContext(Dispatchers.Main){
                    txtCoinMinute.text = String.format("%.2f", response.body()!!.data.minute.coins)
                    txtCoinHour.text = String.format("%.2f", response.body()!!.data.hour.coins)
                    txtCoinDay.text = String.format("%.2f", response.body()!!.data.day.coins)
                    txtCoinWeek.text = String.format("%.2f", response.body()!!.data.week.coins)
                    txtCoinMonth.text = String.format("%.2f", response.body()!!.data.month.coins)

                    txtBtcMinute.text = String.format("%.4f", response.body()!!.data.minute.bitcoins)
                    txtBtcHour.text = String.format("%.4f", response.body()!!.data.hour.bitcoins)
                    txtBtcDay.text = String.format("%.4f", response.body()!!.data.day.bitcoins)
                    txtBtcWeek.text = String.format("%.4f", response.body()!!.data.week.bitcoins)
                    txtBtcMonth.text = String.format("%.4f", response.body()!!.data.month.bitcoins)

                    txtUsdMinute.text = String.format("%.2f", response.body()!!.data.minute.dollars)
                    txtUsdHour.text = String.format("%.2f", response.body()!!.data.hour.dollars)
                    txtUsdDay.text = String.format("%.2f", response.body()!!.data.day.dollars)
                    txtUsdWeek.text = String.format("%.2f", response.body()!!.data.week.dollars)
                    txtUsdMonth.text = String.format("%.2f", response.body()!!.data.month.dollars)
                }

                Log.d("AAA", response.body()!!.data.minute.coins.toString())
            }else{
                Log.e("RETROFIT_ERROR", response.code().toString())

            }
        }

    }
}