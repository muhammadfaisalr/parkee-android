package id.muhammadfaisal.parkee.util

import id.muhammadfaisal.parkee.BuildConfig

class Constant {
    class Key {
        companion object {
            const val BUNDLING = "BUNDLING"
            const val TITLE = "TITLE"
            const val DESCRIPTION = "DESCRIPTION"
            const val ERROR_TYPE = "ERROR_TYPE"
            const val MENU_DETAIL = "MENU_DETAIL"
            const val STORE_INFO = "STORE_INFO"
            const val SESSION = "SESSION"
            const val NAME = "NAME"
            const val PHONE = "PHONE"
        }
    }

    class ErrorType {
        companion object {
            const val ACCOUNT_NOT_FOUND = 404
        }
    }

    class Endpoint{
        companion object {
            const val NEW_TRANSACTION = "new-transaction"
            const val INQUIRY_TRANSACTION = "inquiry-transaction"
            const val PAYMENT_TRANSACTION = "payment-transaction"
            const val ALL_TRANSACTION = "all-transaction"
        }
    }
}