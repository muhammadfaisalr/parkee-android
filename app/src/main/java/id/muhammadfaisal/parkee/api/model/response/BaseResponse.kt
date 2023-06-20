package id.muhammadfaisal.parkee.api.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("code") var code: Int,
    @SerializedName("message") var message: String,
    @SerializedName("data") var data: Any
)