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
    val diceOptions = arrayOf("4", "6", "8", "10", "12", "20")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var convertFromString: String = ""
        var sideSelected = 0
        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, diceOptions)
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
                    "Selected Die is " + diceOptions[position],
                    Toast.LENGTH_SHORT
                ).show()
                convertFromString = diceOptions[position]
                sideSelected = diceOptions[position].toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(
                    applicationContext,
                    "Nothing Selected! Please select a unit",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        fun rollDice(side: Int,rolls: Int) :String  {
            var results = mutableListOf<Int>()
            val die = Die(side)
            val roll1 = die.roll()
            val roll2 = die.roll()
            results.add(roll1)
            results.add(roll2)

            if (rolls == 1) {
                return "${results[0]}"
            } else {
               return "${results[0]} and ${results[1]}"
            }

        }
        binding.rollTwiceButton.setOnClickListener {
            val resulttext = rollDice(sideSelected,2)
            binding.resultText.text = resulttext
        }
        binding.rollOnceButton.setOnClickListener {
            val resulttext = rollDice(sideSelected,1)
            binding.resultText.text = resulttext
        }
    }
}
