package com.example.bodcalc

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes :TextView? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnDatePicker :Button = findViewById(R.id.btnDatePicker)

        // textView objects
        tvSelectedDate = findViewById(R.id.tvSelectedDate)

        tvAgeInMinutes = findViewById(R.id.AgeInMinutes)



        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    //Function To call The DatePicker.
    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickDatePicker(){

        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)
        var dpd = DatePickerDialog(this ,
            // all are selected year month and day
            DatePickerDialog.OnDateSetListener{ view ,year, month,dayOfMonth ->

                Toast.makeText(this,
                    "Year was $year , Month was ${month+1}, Day of month was $dayOfMonth",
                    Toast.LENGTH_LONG).show()
                val selectedDate = "$dayOfMonth/${month+1}/$year"

                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)

                theDate?.let {
                    val selectedDateInMinutes = theDate.time/ 60000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate.let {
                        val currentDateInMinutes = currentDate.time/60000

                        val diffrenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        tvAgeInMinutes?.text = diffrenceInMinutes.toString()
                    }
                }


            } ,
            year,
            month,
            day
            )
            dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
            dpd.show()

    }

}