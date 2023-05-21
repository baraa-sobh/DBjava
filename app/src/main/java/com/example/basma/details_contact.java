package com.example.basma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basma.database.DataBaseHelper;
import com.example.basma.model.contact;

import java.util.ArrayList;

public class details_contact extends AppCompatActivity {
    int id = 0;

    DataBaseHelper database = DataBaseHelper.getInstance(this);
    ArrayList<contact> contactList = new ArrayList<contact>();
    TextView tv_userName,
            tv_phoneNumber , tv_email;
    ImageView image_contact;
    Button btn_edt, button_delete,btn_email,btn_call;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle("Detail Contact Person");
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        tv_userName = findViewById(R.id.details_username);
        tv_phoneNumber = findViewById(R.id.details_phone);
        tv_email = findViewById(R.id.details_email);
        image_contact = findViewById(R.id.image_contact_details);
        button_delete = findViewById(R.id.btn_delete);
        btn_edt = findViewById(R.id.btn_edit);
        btn_call = findViewById(R.id.btn_call);
        btn_email = findViewById(R.id.btn_email);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0592516051"));
                startActivity(intent);
            }
        });
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("content://messages/"));
                startActivity(intent);
            }
        });
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean x = database.deleteCourse(name);
                System.out.println(x);
                if (x) {
                    Intent i = new Intent(details_contact.this, Show_Contact.class);
                  startActivity(i);
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showAllContact();
    }

    private void showAllContact() {
        System.out.println(id);
        contactList = database.getContact();

        contact contacts = contactList.get(id);
        System.out.println(contacts.getName());
        System.out.println(contacts.getPhone());


        Bitmap bitmap = BitmapFactory.decodeByteArray(contacts.getImageUrl(), 0, contacts.getImageUrl().length);
        image_contact.setImageBitmap(bitmap);
        tv_phoneNumber.setText(contacts.getPhone());
        tv_userName.setText(contacts.getName());
        tv_email.setText(contacts.getEmail());
        name = contacts.getName();
        btn_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent i = new Intent(details_contact.this,update_contact.class);
               i.putExtra("id",id);
               startActivity(i);
            }
        });

    }

}