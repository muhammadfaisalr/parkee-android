package id.muhammadfaisal.parkee.helper

import id.muhammadfaisal.parkee.api.ApiServices
import id.muhammadfaisal.parkee.api.RetrofitBuilder
import id.muhammadfaisal.parkee.api.model.request.InquiryTransactionRequest
import id.muhammadfaisal.parkee.api.model.request.NewTransactionRequest
import id.muhammadfaisal.parkee.api.model.request.PaymentTransactionRequest
import id.muhammadfaisal.parkee.api.model.response.BaseResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class ApiHelper {
    companion object {
        private fun getServices(): ApiServices {
            return RetrofitBuilder.get().create(ApiServices::class.java)
        }

        fun getAllParkings(): Observable<Response<BaseResponse>> {
            return getServices()
                .getAllTransactions()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun newTransaction(newTransactionRequest: NewTransactionRequest): Observable<Response<BaseResponse>> {
            return getServices()
                .newTransaction(newTransactionRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun inquiryTransaction(inquiryTransactionRequest: InquiryTransactionRequest): Observable<Response<BaseResponse>> {
            return getServices()
                .inquiryTransaction(inquiryTransactionRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun paymentTransaction(paymentTransactionRequest: PaymentTransactionRequest): Observable<Response<BaseResponse>> {
            return getServices()
                .paymentTransaction(paymentTransactionRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }
    }
}