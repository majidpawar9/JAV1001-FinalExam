package com.majid.finalexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

/**
 * This activity displays information about the application.
 */

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        // Find and set the custom toolbar as the support action bar.
        val toolbar: Toolbar = findViewById(R.id.toolbarabout)
        setSupportActionBar(toolbar)
        // Enable the back arrow in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle back arrow click
                finish() // This will close the current activity and return to the previous one
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}