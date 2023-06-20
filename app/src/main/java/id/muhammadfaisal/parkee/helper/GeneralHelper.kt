package id.muhammadfaisal.parkee.helper

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GeneralHelper {
    companion object {
        fun formatDate(s: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
            val outputFormat = SimpleDateFormat("EEE, dd-MMMM-yyyy, HH:mm:ss")


            val date = inputFormat.parse(s)
            return outputFormat.format(date)
        }

        fun getDuration(start: String, end: String): Duration {
            val startTime = LocalDateTime.parse(start)
            val endTime = LocalDateTime.parse(end)
            return Duration.between(startTime, endTime)
        }
    }
}