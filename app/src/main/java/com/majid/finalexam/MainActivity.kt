package com.majid.finalexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.majid.finalexam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val diceOptions = arrayOf("D4", "D6", "D8", "D10", "D12", "D20")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var convertFromString:String = ""

        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, diceOptions)
        binding.spinnerDice.adapter = arrayAdapter
        binding.spinnerDice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // When user selects a unit a toast message will be shown
                Toast.makeText(
                    applicationContext,
                    "Selected Unit is " + diceOptions[position],
                    Toast.LENGTH_SHORT
                ).show()
                convertFromString = diceOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(
                    applicationContext,
                    "Nothing Selected! Please select a unit",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}
