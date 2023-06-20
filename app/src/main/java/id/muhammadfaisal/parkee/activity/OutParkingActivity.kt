package id.muhammadfaisal.parkee.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import com.google.gson.internal.LinkedTreeMap
import id.muhammadfaisal.parkee.R
import id.muhammadfaisal.parkee.api.model.request.InquiryTransactionRequest
import id.muhammadfaisal.parkee.api.model.request.PaymentTransactionRequest
import id.muhammadfaisal.parkee.api.model.response.BaseResponse
import id.muhammadfaisal.parkee.databinding.ActivityOutParkingBinding
import id.muhammadfaisal.parkee.helper.ApiHelper
import id.muhammadfaisal.parkee.helper.GeneralHelper
import id.muhammadfaisal.parkee.helper.ViewHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response
import java.time.Duration

class OutParkingActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    private lateinit var binding: ActivityOutParkingBinding

    private lateinit var checkInTime: String
    private lateinit var checkOutTime: String
    private var baseAmount: Double = 0.0
    private var totalBill: Double = 0.0

    private var vehicleNumber = ""
    private var selectedPaymentMethod = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityOutParkingBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.binding.imageBack.setOnClickListener {
            finish()
        }

        this.binding.buttonInquiry.setOnClickListener {
            this.processInquiry()
        }

        this.binding.buttonPay.setOnClickListener {
            this.processPayment()
        }

        this.binding.buttonCancel.setOnClickListener {
            this.cancelProcessPayment()
        }

        this.binding.radioGroup.setOnCheckedChangeListener(this)
    }

    private fun processPayment() {

        if (selectedPaymentMethod == 0){
            Toast.makeText(this, "Pilih Metode Pembayaran", Toast.LENGTH_SHORT).show()
            return
        }

        val paymentTransactionRequest = PaymentTransactionRequest(vehicleNumber = this.vehicleNumber, paymentMethod = this.selectedPaymentMethod)

        ViewHelper.visible(this.binding.progressPayment)
        ViewHelper.gone(this.binding.linearButton)

        var isSuccess = true
        CompositeDisposable().add(
            ApiHelper
                .paymentTransaction(paymentTransactionRequest)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()

                        if (body?.code != 200) {
                            //If Transaction Not Success
                            isSuccess = false
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(this@OutParkingActivity, e.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onComplete() {
                        if (!isSuccess) {
                            //Is Transaction Not Successful
                            ViewHelper.visible(binding.linearButton)
                            ViewHelper.gone(binding.progressPayment)
                            return
                        }

                        Toast.makeText(this@OutParkingActivity, "Transaksi Berhasil!", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                })
        )
    }

    private fun cancelProcessPayment() {
        ViewHelper.gone(this.binding.linearPayment)

        //Enabling all activity in inquiry section
        this.binding.buttonInquiry.isEnabled = true
        this.binding.inputVehicleNumber.isEnabled = true
    }

    private fun processInquiry() {
        if (this.binding.inputVehicleNumber.text.isNullOrEmpty()) {
            Toast.makeText(this, "Anda belum memasukkan Plat Nomor", Toast.LENGTH_SHORT).show()
            return
        }

        ViewHelper.gone(this.binding.linearPayment)

        this.vehicleNumber = this.binding.inputVehicleNumber.text.toString().uppercase()
        val inquiryTransactionRequest = InquiryTransactionRequest(vehicleNumber = this.vehicleNumber)

        ViewHelper.visible(binding.progressInquiry)

        var isSuccess = true

        CompositeDisposable().add(
            ApiHelper.inquiryTransaction(inquiryTransactionRequest)
                .subscribeWith(object : DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {
                        val body = t.body()
                        if (body?.code == 200) {
                            ViewHelper.visible(binding.linearPayment)
                            val data = body.data as LinkedTreeMap<*, *>

                            this@OutParkingActivity.setPaymentData(data)
                        } else {
                            isSuccess = false
                            Toast.makeText(
                                this@OutParkingActivity, body?.message, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onError(e: Throwable) {
                        isSuccess = false
                        Toast.makeText(this@OutParkingActivity, e.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onComplete() {
                        if (isSuccess) {
                            ViewHelper.visible(binding.buttonInquiry)

                            //Disabling all activity in inquiry section
                            binding.buttonInquiry.isEnabled = false
                            binding.inputVehicleNumber.isEnabled = false
                        }
                        ViewHelper.gone(binding.progressInquiry)
                    }

                })
        )
    }

    private fun setPaymentData(data: LinkedTreeMap<*, *>) {
        checkInTime = data["checkInTime"] as String
        checkOutTime = data["checkOutTime"] as String
        baseAmount = data["baseAmount"] as Double
        totalBill = data["totalAmount"] as Double

        val formattedCheckInTime = GeneralHelper.formatDate(checkInTime)
        val formattedCheckOutTime = GeneralHelper.formatDate(checkOutTime)

        val duration = GeneralHelper.getDuration(checkInTime, checkOutTime)

        this.binding.apply {
            this.textCheckIn.text = formattedCheckInTime
            this.textCheckOut.text = formattedCheckOutTime
            this.textBasePrice.text = baseAmount.toString()
            this.textTotalBill.text = totalBill.toString()
            this.textDuration.text =
                "${duration.toDays()} Hari, ${duration.toHours()} Jam, ${duration.toMinutes()} Menit"
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        selectedPaymentMethod = if (group?.checkedRadioButtonId == R.id.rbPaymentMethod1) {
            1
        } else {
            2
        }
    }
}