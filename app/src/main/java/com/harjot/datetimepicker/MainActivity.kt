package com.harjot.datetimepicker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.harjot.datetimepicker.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.ibDate.setOnClickListener {
            var calendar= Calendar.getInstance()
            var datePickerDialog = DatePickerDialog(this, {_,year,month,date ->
                var simpleDateFormat = SimpleDateFormat("dd/MM/YYYY")
                calendar.set(year,month,date)
                binding.etDate.setText(simpleDateFormat.format(calendar.time))
            },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE))
            calendar.add(Calendar.DAY_OF_YEAR,-10)
            datePickerDialog.datePicker.minDate = calendar.timeInMillis

            calendar.timeInMillis = System.currentTimeMillis()

            calendar.add(Calendar.DAY_OF_YEAR, 10)
            datePickerDialog.datePicker.maxDate = calendar.timeInMillis
                datePickerDialog.show()
        }
        binding.ibTime.setOnClickListener {
            val startHour = 9  // 09:00 AM
            val startMinute = 0
            val endHour = 18   // 06:00 PM
            val endMinute = 0
            var calendar= Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                if (isTimeInRange(hourOfDay, minute, startHour, startMinute, endHour, endMinute)) {
                    var simpleDateFormat = SimpleDateFormat("hh:mm a")
                    // Time is within the allowed range
                    Toast.makeText(this, "Selected time: $hourOfDay:$minute", Toast.LENGTH_SHORT).show()
                    calendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    calendar.set(Calendar.MINUTE,minute)
                    binding.etTime.setText(simpleDateFormat.format(calendar.time))
                } else {
                    // Time is outside the allowed range
                    Toast.makeText(this, "Please select a time within range (09:00 am - 06:00 pm)", Toast.LENGTH_LONG).show()
                }
            }
//            var timePickerDialog = TimePickerDialog(this, {_,hour,minute ->
//                var simpleDateFormat = SimpleDateFormat("hh:mm a")
//                calendar.set(Calendar.HOUR_OF_DAY,hour)
//                calendar.set(Calendar.MINUTE,minute)
//                binding.etTime.setText(simpleDateFormat.format(calendar.time))
//            },
//
//                calendar.get(Calendar.HOUR_OF_DAY),
//                calendar.get(Calendar.MINUTE),
//                false)
//            timePickerDialog.show()
            val timePickerDialog = TimePickerDialog(
                this, timeSetListener, startHour, startMinute, false
            )
            timePickerDialog.show()

        }
    }
    private fun isTimeInRange(
        hourOfDay: Int, minute: Int,
        startHour: Int, startMinute: Int,
        endHour: Int, endMinute: Int
    ): Boolean {
        val selectedTime = hourOfDay * 60 + minute
        val startTime = startHour * 60 + startMinute
        val endTime = endHour * 60 + endMinute
        return selectedTime in startTime..endTime
    }

}