package id.muhammadfaisal.parkee.api.model.request

import com.google.gson.annotations.SerializedName

data class PaymentTransactionRequest(
    @SerializedName("vehicleNumber")
    var vehicleNumber: String,

    @SerializedName("paymentMethod")
    var paymentMethod: Int
)