package id.muhammadfaisal.parkee.util

import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.parkee.bottomsheet.AddTransactionBottomSheetDialogFragment

class BottomSheets {
    companion object {
        fun addNewTransaction(activity: AppCompatActivity) {
            val bottomSheets = AddTransactionBottomSheetDialogFragment()
            bottomSheets.show(activity.supportFragmentManager, BottomSheets::class.java.simpleName)
        }
    }
}