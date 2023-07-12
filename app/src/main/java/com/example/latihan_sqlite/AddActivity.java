package com.example.latihan_sqlite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText inputTitle, inputAuthor, inputPages;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Add Book");
        }

        inputTitle = findViewById(R.id.input_BookTitle);
        inputAuthor = findViewById(R.id.input_BookAuthor);
        inputPages = findViewById(R.id.input_BookPages);
        btnSave = findViewById(R.id.save_button);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBook();
            }
        });
    }

    private void AddBook() {
        String titleBook = inputTitle.getText().toString().trim();
        String authorBook = inputAuthor.getText().toString().trim();
        int pagesBook = Integer.parseInt(inputPages.getText().toString().trim());

        try {
            DatabaseHelper databaseHelper =new DatabaseHelper(AddActivity.this);
            databaseHelper.addBook(titleBook, authorBook, pagesBook);

            inputTitle.setText("");
            inputAuthor.setText("");
            inputPages.setText("");
        } catch (SQLiteException ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error : " + ex, Toast.LENGTH_SHORT).show();
        }
    }
}