package com.example.a232notes

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class AddEditActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var numberPicker: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#E3F2FD")))

        titleEditText = findViewById(R.id.title_edit_text)
        descriptionEditText = findViewById(R.id.description_edit_text)
        numberPicker = findViewById(R.id.number_picker)
        numberPicker.maxValue = 15
        numberPicker.minValue = 1

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (intent.hasExtra(Constants.ID_CODE)) {

            title = "Edit Note"

            titleEditText.setText(intent.getStringExtra(Constants.TITLE_CODE))
            descriptionEditText.setText(intent.getStringExtra(Constants.DESCRIPTION_CODE))
            numberPicker.value = intent.getIntExtra(Constants.PRIORITY_CODE, -1)


        } else {
            title = "Add Note"

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.add_edit_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.save_note_item -> {

                saveNote()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {

        val title = titleEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val priority = numberPicker.value

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(
                this@AddEditActivity,
                "Please insert title and description !",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val id = intent.getIntExtra(Constants.ID_CODE, -1)

        if (id != -1) {

            setResult(Constants.EDIT_REQUEST_CODE, Intent().apply {
                putExtra(Constants.TITLE_CODE, title)
                putExtra(Constants.DESCRIPTION_CODE, description)
                putExtra(Constants.PRIORITY_CODE, priority)
                putExtra(Constants.ID_CODE, id)
            })

        } else {

            setResult(Constants.ADD_REQUEST_CODE, Intent().apply {
                putExtra(Constants.TITLE_CODE, title)
                putExtra(Constants.DESCRIPTION_CODE, description)
                putExtra(Constants.PRIORITY_CODE, priority)

            })
        }

        finish()
    }
}