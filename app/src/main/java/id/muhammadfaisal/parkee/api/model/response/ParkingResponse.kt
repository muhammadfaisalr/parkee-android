package id.muhammadfaisal.parkee.api.model.response

import com.google.gson.annotations.SerializedName

data class ParkingResponse(

    @field:SerializedName("totalAmount")
    var totalAmount: Double? = null,

    @field:SerializedName("checkOutTime")
    var checkOutTime: String? = null,

    @field:SerializedName("parkingSlip")
    var parkingSlip: String? = null,

    @field:SerializedName("checkInTime")
    var checkInTime: String? = null,

    @field:SerializedName("vehicleNumber")
    var vehicleNumber: String? = null,

    @field:SerializedName("paymentMethod")
    var paymentMethod: String? = null,

    @field:SerializedName("discount")
    var discount: String? = null,

    @field:SerializedName("discountAmount")
    var discountAmount: Long? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("vehicleType")
    var vehicleType: VehicleType? = null,

    @field:SerializedName("baseAmount")
    var baseAmount: Long? = null,

    @field:SerializedName("status")
    var status: String? = null
)

data class VehicleType(

    @field:SerializedName("code")
    var code: String? = null,

    @field:SerializedName("price")
    var price: Int? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("id")
    var id: Int? = null
)
