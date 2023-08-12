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

/*******
 * In this code user can select dice sides as well as put custom sides
 * To input a custom side you have to press custom side and a input field will be shown
 * after putting the number of sides you can press the same roll buttons for rolling the die
 *
 * This app also supports saving last user inputs
 * to see the last user inputs you have to click on tollbar and select "previous user inputs" from dropdown
 * below the custom field you will see the previous inputs
 *
 * I have made a Die.kt file to store the die Object which has roll functions
 *******/
class MainActivity : AppCompatActivity() {
    //Main actiity Binding
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var toolbar: Toolbar
//    Initializing HashSet to store unique inputs from user
    val uniqueUserInputs = LinkedHashSet<String>()
    val diceOptions = arrayOf("4", "6", "8", "10", "12", "20")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //this will make the editTextNumber which is customInput disappear from View
        binding.editTextNumber.isEnabled = false
        binding.editTextNumber.visibility = View.GONE

        toolbar = binding.myToolBar

        // Set the custom toolbar as the support action bar.
        setSupportActionBar(toolbar)

        // Set the title for the action bar.
        getSupportActionBar()?.setTitle("Dice Rolling App")

        //Initializing Shared Pref
        sharedPref = getSharedPreferences("finalexam", Context.MODE_PRIVATE)
        var convertFromString: String = ""
        var sideSelected = 0

        //Using arraAdapter to iterate and display all Dice Sides
        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, diceOptions)
        binding.spinnerDice.adapter = arrayAdapter
        val clickableText = "Custom Input?"

        // This is to make a String clickable like a link or button
        val spannableString = SpannableString(clickableText)
        binding.inputIndicatingTextView.isEnabled = false
        binding.inputIndicatingTextView.visibility = View.GONE
        binding.arrayTextView.visibility = View.GONE

//        when the text is clicked it will make the custom input box and previous
//        user inputs visible
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                if (binding.editTextNumber.isEnabled == false) {
                    binding.editTextNumber.isEnabled = true
                    binding.editTextNumber.visibility = View.VISIBLE
                    binding.inputIndicatingTextView.isEnabled = true
                    binding.inputIndicatingTextView.visibility = View.VISIBLE
                    binding.arrayTextView.visibility = View.VISIBLE
                } else {
                    binding.editTextNumber.isEnabled = false
                    binding.editTextNumber.visibility = View.GONE
                    binding.inputIndicatingTextView.isEnabled = false
                    binding.inputIndicatingTextView.visibility = View.GONE
                    binding.arrayTextView.visibility = View.GONE
                }
            }
        }
//      Setting the text view spannable/clickable
        spannableString.setSpan(
            clickableSpan,
            0,
            clickableText.length,
            SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.customInput.text = spannableString
        binding.customInput.movementMethod = LinkMovementMethod.getInstance()

//      This spinner onclick will select the dice and put a toast msg of Die selected
//      It will also save the value of die in a variable to use later
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


//      This function makes an object of Die class and uses the roll function from it
//      accordingly with the help of if else loop it will detect and roll accordingly
//      If user has put input in custom input it will roll according to that side
        fun rollDice(side: Int, rolls: Int): String {
            // to store the results
            var results = mutableListOf<Int>()
            val userInput = binding.editTextNumber.text

            // if custom input is null use spinner input
            if (userInput.toString() == "" || userInput.toString() == "0") {
                val die = Die(side)
                val roll1 = die.roll()
                val roll2 = die.roll()
                results.add(roll1)
                results.add(roll2)
            } else {
                //else use custom input
                uniqueUserInputs.add(userInput.toString())
                val die = Die(userInput.toString().toInt())
                val roll1 = die.roll()
                val roll2 = die.roll()
                results.add(roll1)
                results.add(roll2)
            }
            //display acc to the rolls needed
            if (rolls == 1) {
                return "${results[0]}"
            } else {
                return "${results[0]} and ${results[1]}"
            }

        }

        //this is for Dice rolled twice button
        // It will set the result text accordingly
        binding.rollTwiceButton.setOnClickListener {
            val resulttext = rollDice(sideSelected, 2)
            binding.resultText.text = resulttext
        }
        //this is for Dice rolled once button
        // It will set the result text accordingly
        binding.rollOnceButton.setOnClickListener {
            val resulttext = rollDice(sideSelected, 1)
            binding.resultText.text = resulttext
        }
        val savedText = sharedPref.getString("savedText", "")
        binding.arrayTextView.text = savedText

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

                //saving the previous inputs using Shared Preferences
                val editor = sharedPref.edit()
                editor.putString("savedText", formattedText)
                editor.apply()
            }

            R.id.about -> {
                // Start the AboutActivity when the "About" option is selected.
                startActivity(Intent(this, AboutActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //this fucntion will return an array to show as previous inputs
    private fun formatArrayToString(intArray: HashSet<String>): String {
        return intArray.joinToString(", ")
    }

}
