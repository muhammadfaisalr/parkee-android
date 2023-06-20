package id.muhammadfaisal.parkee.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.parkee.R
import id.muhammadfaisal.parkee.api.model.request.NewTransactionRequest
import id.muhammadfaisal.parkee.api.model.response.BaseResponse
import id.muhammadfaisal.parkee.databinding.FragmentAddTransactionBottomSheetDialogBinding
import id.muhammadfaisal.parkee.helper.ApiHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class AddTransactionBottomSheetDialogFragment : BottomSheetDialogFragment(),
    RadioGroup.OnCheckedChangeListener {

    private lateinit var binding: FragmentAddTransactionBottomSheetDialogBinding
    private var selectedVehicleType = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentAddTransactionBottomSheetDialogBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.buttonSave.setOnClickListener {
            this.processNewTransaction()
        }

        this.binding.imageClose.setOnClickListener {
            dismiss()
        }

        this.binding.radioGroup.setOnCheckedChangeListener(this)
    }

    private fun processNewTransaction() {

        if (this.binding.inputVehicleNumber.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Anda Belum Memasukkan Plat Nomor.", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedVehicleType == 0) {
            Toast.makeText(requireContext(), "Pilih Jenis Kendaraan!", Toast.LENGTH_SHORT).show()
            return
        }
        binding.progressBar.visibility = View.VISIBLE
        binding.buttonSave.visibility = View.GONE

        val vehicleNumber = this.binding.inputVehicleNumber.text.toString()

        val newTrxReq = NewTransactionRequest(vehicleNumber = vehicleNumber.uppercase(), vehicleType = selectedVehicleType, status = 1)

        CompositeDisposable().add(
            ApiHelper
                .newTransaction(newTrxReq)
                .subscribeWith(object: DisposableObserver<Response<BaseResponse>>() {
                    override fun onNext(t: Response<BaseResponse>) {

                        val body = t.body()

                        if (body?.code == 200) {
                            Toast.makeText(requireContext(), body.message, Toast.LENGTH_SHORT).show()
                            dismiss()
                        } else {
                            Toast.makeText(requireContext(), body?.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonSave.visibility = View.VISIBLE
                    }

                })
        )
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        val radioButtonCheckedId = group?.checkedRadioButtonId

        selectedVehicleType = when (radioButtonCheckedId) {
            R.id.rbVehicleType1 -> {
                1
            }
            R.id.rbVehicleType2 -> {
                2
            }
            R.id.rbVehicleType3 -> {
                3
            }
            R.id.rbVehicleType4 -> {
                4
            }
            else -> {
                0
            }
        }
    }
}