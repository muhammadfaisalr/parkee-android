package id.muhammadfaisal.parkee.api.model.request

import com.google.gson.annotations.SerializedName

data class InquiryTransactionRequest(
    @SerializedName("vehicleNumber")
    var vehicleNumber: String
)