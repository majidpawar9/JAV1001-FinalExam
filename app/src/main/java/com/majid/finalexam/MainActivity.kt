package com.majid.finalexam

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.majid.finalexam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var toolbar: Toolbar
    val uniqueUserInputs = LinkedHashSet<String>()
    val diceOptions = arrayOf("4", "6", "8", "10", "12", "20")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editTextNumber.isEnabled = false
        binding.editTextNumber.visibility = View.GONE

        toolbar = binding.myToolBar
        // Set the custom toolbar as the support action bar.
        setSupportActionBar(toolbar)
        // Set the title for the action bar.
        getSupportActionBar()?.setTitle("Dice Rolling App")
        sharedPref = getSharedPreferences("finalexam", Context.MODE_PRIVATE)
        var convertFromString: String = ""
        var sideSelected = 0
        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, diceOptions)
        binding.spinnerDice.adapter = arrayAdapter
        val clickableText = "Custom Input?"
        val spannableString = SpannableString(clickableText)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                if(binding.editTextNumber.isEnabled == false){
                binding.editTextNumber.isEnabled = true
                binding.editTextNumber.visibility = View.VISIBLE
                }
                else{
                    binding.editTextNumber.isEnabled = false
                    binding.editTextNumber.visibility = View.GONE

                }
            }
        }

        spannableString.setSpan(clickableSpan, 0, clickableText.length, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.customInput.text = spannableString
        binding.customInput.movementMethod = LinkMovementMethod.getInstance()

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
            val userInput = binding.editTextNumber.text
            if(userInput.toString() == "") {
                val die = Die(side)
                val roll1 = die.roll()
                val roll2 = die.roll()
                results.add(roll1)
                results.add(roll2)
            }
            else{
                uniqueUserInputs.add(userInput.toString())
                val die = Die(userInput.toString().toInt())
                val roll1 = die.roll()
                val roll2 = die.roll()
                results.add(roll1)
                results.add(roll2)
            }


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
    // This function will inflate the menu xml file
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return true
    }

    // Handle options menu item selections.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.previousUserInput -> {
                // Start the SettingActivity when the "Setting" option is selected.
                val formattedText = formatArrayToString(uniqueUserInputs)
                binding.arrayTextView.text = formattedText
            }

            R.id.about -> {
                // Start the AboutActivity when the "About" option is selected.
                startActivity(Intent(this, AboutActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun formatArrayToString(intArray: HashSet<String>): String {
        return intArray.joinToString(", ")
    }

}
