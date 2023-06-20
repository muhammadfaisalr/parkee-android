package id.muhammadfaisal.parkee.api.model.request

import com.google.gson.annotations.SerializedName

data class NewTransactionRequest(
    @SerializedName("vehicleNumber")
    var vehicleNumber: String,

    @SerializedName("vehicleType")
    var vehicleType: Int,

    @SerializedName("status")
    var status: Int,
)