package id.muhammadfaisal.parkee.api

import id.muhammadfaisal.parkee.api.model.request.InquiryTransactionRequest
import id.muhammadfaisal.parkee.api.model.request.NewTransactionRequest
import id.muhammadfaisal.parkee.api.model.request.PaymentTransactionRequest
import id.muhammadfaisal.parkee.api.model.response.BaseResponse
import id.muhammadfaisal.parkee.util.Constant
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices{
    @POST(Constant.Endpoint.NEW_TRANSACTION)
    fun newTransaction(@Body newTransactionRequest: NewTransactionRequest): Observable<Response<BaseResponse>>

    @POST(Constant.Endpoint.INQUIRY_TRANSACTION)
    fun inquiryTransaction(@Body inquiryTransactionRequest: InquiryTransactionRequest): Observable<Response<BaseResponse>>

    @POST(Constant.Endpoint.PAYMENT_TRANSACTION)
    fun paymentTransaction(@Body paymentTransactionRequest: PaymentTransactionRequest): Observable<Response<BaseResponse>>

    @GET(Constant.Endpoint.ALL_TRANSACTION)
    fun getAllTransactions() : Observable<Response<BaseResponse>>
}