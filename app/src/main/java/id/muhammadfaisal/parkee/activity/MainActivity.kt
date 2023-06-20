package id.muhammadfaisal.parkee.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.internal.LinkedTreeMap
import id.muhammadfaisal.parkee.R
import id.muhammadfaisal.parkee.adapter.ParkingsAdapter
import id.muhammadfaisal.parkee.api.model.response.BaseResponse
import id.muhammadfaisal.parkee.api.model.response.ParkingResponse
import id.muhammadfaisal.parkee.api.model.response.VehicleType
import id.muhammadfaisal.parkee.databinding.ActivityMainBinding
import id.muhammadfaisal.parkee.helper.ApiHelper
import id.muhammadfaisal.parkee.util.BottomSheets
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var parkings: ArrayList<ParkingResponse> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.getAllParkings()

        this.binding.exfabAddData.setOnClickListener{
            BottomSheets.addNewTransaction(this)
        }

        this.binding.buttonCheckOut.setOnClickListener {
            startActivity(Intent(this, OutParkingActivity::class.java))
        }

        this.binding.swipe.setOnRefreshListener {
            getAllParkings()
        }
    }

    override fun onResume() {
        super.onResume()
        this.getAllParkings()
    }

    private fun getAllParkings() {

        if (this.parkings.isNotEmpty()) {
            this.parkings.clear()
        }

        CompositeDisposable().add(
            ApiHelper.getAllParkings().subscribeWith(
                object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        Log.d(MainActivity::class.java.simpleName, "onNext(); Getting All Data Parkings")

                        val body = t.body()
                        if (body?.code == 200){
                            val datas = body.data as List<LinkedTreeMap<*, *>>

                            for (data in datas) {
                                val parkingResponse = ParkingResponse()
                                parkingResponse.vehicleNumber = data["vehicleNumber"] as String
                                parkingResponse.totalAmount = data["totalAmount"] as Double?
                                parkingResponse.checkInTime = data["checkInTime"] as String
                                parkingResponse.checkOutTime = data["checkOutTime"] as String?

                                if (data["vehicleType"] != null) {
                                    val map = data["vehicleType"] as LinkedTreeMap<*, *>
                                    val name = map["name"]

                                    val vehicleType = VehicleType()
                                    vehicleType.name = name as String?

                                    parkingResponse.vehicleType = vehicleType
                                }

                                if (!parkingResponse.vehicleNumber.isNullOrBlank()) {
                                    parkings.add(parkingResponse)
                                }
                            }

                            binding.recyclerView.adapter = ParkingsAdapter(this@MainActivity, parkings)
                            binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                            binding.recyclerView.addItemDecoration(DividerItemDecoration(this@MainActivity, RecyclerView.VERTICAL))
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onComplete() {
                        this@MainActivity.binding.swipe.isRefreshing = false
                        this@MainActivity.binding.progressBar.visibility = View.GONE
                    }
                }
            )
        )
    }
}

