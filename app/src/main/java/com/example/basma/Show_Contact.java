package com.example.basma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.basma.adaptar.RecContactAdapter;
import com.example.basma.database.DataBaseHelper;
import com.example.basma.model.contact;

import java.util.ArrayList;

public class Show_Contact extends AppCompatActivity {
    RecyclerView recyclerContact;
    RecContactAdapter adapter;
    ArrayList<contact> listContact = new ArrayList<>();
    DataBaseHelper database = DataBaseHelper.getInstance(this);
    Button add;
     SearchView SearchView  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle("Contact Person");
        setContentView(R.layout.activity_show_contact);
        recyclerContact = findViewById(R.id.Rec_Contact);
       // SearchView = findViewById(R.id.search);
        Intent intent = getIntent();
        add = findViewById(R.id.button);
//        SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                adapter.getFilter().filter(s);
//                return true;
//            }
//        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Show_Contact.this, Create_Contact.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShowBook();

    }

    private void ShowBook() {
        listContact = database.getContact();
        adapter = new RecContactAdapter(this, listContact, new RecContactAdapter.OnItemCliked() {
            @Override
            public void OnClickIem(int posation) {
                Intent i = new Intent(getBaseContext(), details_contact.class);
                i.putExtra("id", posation);
                startActivity(i);
            }
        });

        recyclerContact.setAdapter(adapter);
        recyclerContact.setLayoutManager(new LinearLayoutManager(this));
        recyclerContact.setHasFixedSize(true);
    }
}