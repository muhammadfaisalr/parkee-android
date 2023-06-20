package id.muhammadfaisal.parkee.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.parkee.R
import id.muhammadfaisal.parkee.api.model.response.ParkingResponse
import id.muhammadfaisal.parkee.databinding.ItemParkingBinding
import id.muhammadfaisal.parkee.helper.GeneralHelper

class ParkingsAdapter(val context: Context, val parkings: ArrayList<ParkingResponse>) : RecyclerView.Adapter<ParkingsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_parking, parent, false))
    }

    override fun onBindViewHolder(holder: ParkingsAdapter.ViewHolder, position: Int) {
        holder.binding(context, parkings[position])
    }

    override fun getItemCount(): Int {
        return parkings.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemParkingBinding.bind(view)

        fun binding(context: Context, parkingResponse: ParkingResponse) {
            binding.textVehicleNumber.text = parkingResponse.vehicleNumber
            binding.textCheckInTime.text = GeneralHelper.formatDate(parkingResponse.checkInTime!!)

            this.binding.textCheckOutTime.text = "-"
            if (!parkingResponse.checkOutTime.isNullOrEmpty()) {
                binding.textCheckOutTime.text = GeneralHelper.formatDate(parkingResponse.checkOutTime!!)
            }

            binding.textVehicleType.text = parkingResponse.vehicleType?.name

            var totalAmount :Double = "0".toDouble()

            if (parkingResponse.totalAmount != null) {
                totalAmount = parkingResponse.totalAmount!!
            }
            binding.textPrice.text = totalAmount.toString()
        }

    }
}