package com.example.mymemory

import BoardSize
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymemory.utils.EXTRA_BOARD_SIZE

class CreateActivity : AppCompatActivity() {


    private lateinit var boardSize: BoardSize
    private lateinit var rvImagePickler: RecyclerView
    private lateinit var btnSave: Button
    private lateinit var etName: EditText
    private var chosenImageUris = mutableListOf<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_activty)

        btnSave = findViewById(R.id.btnSave)
        etName = findViewById(R.id.etGameName)
        rvImagePickler= findViewById(R.id.rvImagePicker)


        boardSize = intent.getSerializableExtra(EXTRA_BOARD_SIZE) as BoardSize
        supportActionBar?.title = when (boardSize) {
            BoardSize.EASY -> "Choose Pics (0 / 4)"
            BoardSize.MEDIUM -> "Choose Pics (0 / 9)"
            else -> "Choose Pics (0 /12)"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvImagePickler.hasFixedSize()
        rvImagePickler.adapter= ImagePickerAdapter(this, chosenImageUris, boardSize )
        rvImagePickler.layoutManager = GridLayoutManager(this, boardSize.getWidth())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}