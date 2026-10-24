package com.example.a232notes

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), NoteAdapter.OnClickListener {


    private lateinit var noteViewModel: NoteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var getResult: ActivityResultLauncher<Intent>
    private lateinit var addButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#E3F2FD")))

        addButton = findViewById(R.id.add_button)

        recyclerView = findViewById(R.id.recycle_view)
        noteAdapter = NoteAdapter(this@MainActivity)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdapter


        noteViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]


        noteViewModel.allNotes.observe(this) {
            noteAdapter.submitList(it)
        }



        getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == Constants.ADD_REQUEST_CODE) {

                    val title = result.data?.getStringExtra(Constants.TITLE_CODE) ?: "Unknown"
                    val description =
                        result.data?.getStringExtra(Constants.DESCRIPTION_CODE) ?: "Unknown"
                    val priority = result.data?.getIntExtra(Constants.PRIORITY_CODE, -1) ?: -1

                    val note = Note(title, description, priority)

                    noteViewModel.addNote(note)
                } else if (result.resultCode == Constants.EDIT_REQUEST_CODE) {

                    val title = result.data?.getStringExtra(Constants.TITLE_CODE) ?: "Unknown"
                    val description =
                        result.data?.getStringExtra(Constants.DESCRIPTION_CODE) ?: "Unknown"
                    val priority = result.data?.getIntExtra(Constants.PRIORITY_CODE, -1) ?: -1
                    val id = result.data?.getIntExtra(Constants.ID_CODE, -1) ?: -1

                    val note = Note(title, description, priority)
                    note.id = id

                    noteViewModel.updateNote(note)

                }
            }


        addButton.setOnClickListener {

            val intent = Intent(this@MainActivity, AddEditActivity::class.java)
            getResult.launch(intent)


        }

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val removedNote = noteAdapter.getNoteAt(viewHolder.adapterPosition)
                noteViewModel.deleteNote(removedNote)

                Snackbar.make(recyclerView, "Note was deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        noteViewModel.addNote(removedNote)
                    }.show()
            }

        }).attachToRecyclerView(recyclerView)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.delete_all_notes_item -> {


                val removedNotes = noteViewModel.allNotes.value
                noteViewModel.deleteAllNotes()
                Snackbar.make(recyclerView, "All Notes were deleted !!", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {

                        removedNotes?.forEach { note ->
                            noteViewModel.addNote(note)
                        }
                    }.show()

            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(note: Note) {

        val title = note.title
        val description = note.description
        val priority = note.priority
        val id = note.id

        val intent = Intent(this@MainActivity, AddEditActivity::class.java).apply {
            putExtra(Constants.TITLE_CODE, title)
            putExtra(Constants.DESCRIPTION_CODE, description)
            putExtra(Constants.PRIORITY_CODE, priority)
            putExtra(Constants.ID_CODE, id)

        }

        getResult.launch(intent)


    }
}