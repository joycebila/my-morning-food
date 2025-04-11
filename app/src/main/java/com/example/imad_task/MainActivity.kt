package com.example.imad_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log // Import Log class for logging
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    // Define a TAG for logging, using the class name
    private val TAG = "MainActivity"

    // Declare UI elements - late initialize them as they are not null after onCreate
    private lateinit var timeOfDayInputLayout: TextInputLayout
    private lateinit var timeOfDayEditText: TextInputEditText
    private lateinit var suggestButton: Button
    private lateinit var mealSuggestionTextView: TextView
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Link the layout file

        // Log when the activity is created
        Log.d(TAG, "onCreate: Activity created.")

        // Initialize UI elements by finding them in the layout
        timeOfDayInputLayout = findViewById(R.id.timeOfDayInputLayout)
        timeOfDayEditText = findViewById(R.id.timeOfDayEditText)
        suggestButton = findViewById(R.id.suggestButton)
        mealSuggestionTextView = findViewById(R.id.mealSuggestionTextView)
        resetButton = findViewById(R.id.resetButton)

        // Set up listener for the Suggest button
        suggestButton.setOnClickListener {
            // Log that the suggest button was clicked
            Log.d(TAG, "suggestButton onClick: Suggest button clicked.")
            handleSuggestion() // Call function to handle the suggestion logic
        }

        // Set up listener for the Reset button
        resetButton.setOnClickListener {
            // Log that the reset button was clicked
            Log.d(TAG, "resetButton onClick: Reset button clicked.")
            resetFields() // Call function to clear fields
        }
    }

    /**
     * Handles getting input, determining the meal suggestion, and displaying it or an error.
     */
    private fun handleSuggestion() {
        // Clear any previous error messages on the input layout
        timeOfDayInputLayout.error = null

        // Get the text from the EditText, trim whitespace, and convert to lowercase for easier comparison
        // ?.trim() handles potential null input gracefully, although EditText usually returns ""
        val timeInput = timeOfDayEditText.text?.toString()?.trim() ?: ""

        // Log the input received
        Log.i(TAG, "handleSuggestion: User input received: '$timeInput'")

        // Check if the input is empty
        if (timeInput.isEmpty()) {
            // Log the empty input error
            Log.w(TAG, "handleSuggestion: Input is empty.")
            // Display error message using the TextInputLayout's error feature
            timeOfDayInputLayout.error = "Please enter a time of day."
            // Clear any previous suggestion text
            mealSuggestionTextView.text = ""
            return // Stop further processing
        }

        // Determine the meal suggestion using a 'when' statement (Kotlin's switch)
        // We compare the lowercased input for case-insensitivity
        val suggestion = when (timeInput.lowercase()) {
            "morning" -> {
                Log.d(TAG, "handleSuggestion: Matched 'morning'. Suggesting Eggs.")
                "Try having some Eggs!"
            }
            "mid-morning" -> {
                Log.d(TAG, "handleSuggestion: Matched 'mid-morning'. Suggesting Fruit.")
                "How about a healthy Fruit snack?"
            }
            "afternoon" -> {
                Log.d(TAG, "handleSuggestion: Matched 'afternoon'. Suggesting Sandwich.")
                "A Sandwich would be great for lunch."
            }
            "mid-afternoon" -> {
                Log.d(TAG, "handleSuggestion: Matched 'mid-afternoon'. Suggesting Cake.")
                "Maybe a slice of Cake?"
            }
            "dinner" -> {
                Log.d(TAG, "handleSuggestion: Matched 'dinner'. Suggesting Pasta.")
                "Pasta sounds like a good dinner option."
            }
            "after dinner" -> {
                Log.d(TAG, "handleSuggestion: Matched 'after dinner'. Suggesting Ice cream.")
                "Treat yourself with some Ice cream!"
            }
            // Handle cases where the input doesn't match any predefined time
            else -> {
                // Log the invalid input error
                Log.w(TAG, "handleSuggestion: Invalid input: '$timeInput'.")
                // Set an error message on the input layout
                timeOfDayInputLayout.error = "Invalid time. Use Morning, Mid-morning, Afternoon, Mid-afternoon, Dinner, or After Dinner."
                // Return null to indicate no valid suggestion
                null
            }
        }

        // Display the suggestion or clear the text view if input was invalid
        mealSuggestionTextView.text = suggestion ?: "" // Use Elvis operator to provide empty string if suggestion is null

        // Log the final suggestion displayed (or lack thereof)
        if (suggestion != null) {
            Log.i(TAG, "handleSuggestion: Displaying suggestion: '$suggestion'")
        } else {
            Log.i(TAG, "handleSuggestion: No valid suggestion to display due to invalid input.")
        }
    }

    /**
     * Clears the input field, the suggestion text view, and any error message.
     */
    private fun resetFields() {
        timeOfDayEditText.text?.clear() // Clear the text in the EditText
        mealSuggestionTextView.text = "" // Clear the text in the TextView
        timeOfDayInputLayout.error = null // Remove any error message from the TextInputLayout
        // Log that fields have been reset
        Log.d(TAG, "resetFields: Input and suggestion fields cleared.")
    }
}