package com.example.basma;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.basma.database.DataBaseHelper;
import com.example.basma.model.contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Create_Contact extends AppCompatActivity {
    private ImageView img_book;
    private FloatingActionButton btn_addImg;
    private EditText username, phone , email;
    private Button btn_create , back;
    private DataBaseHelper database = DataBaseHelper.getInstance(this);
    private byte[] image_url;
    String name  ;
    String phoneN  ;
    String emaill  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle("Create new Contact");
        img_book = findViewById(R.id.im_addimgbook);
        btn_addImg = findViewById(R.id.btn_addimg);
        username = findViewById(R.id.et_userName);
        phone = findViewById(R.id.et_phoneNumber);
        btn_create = findViewById(R.id.btn_create);
        back = findViewById(R.id.btn_back);
        email = findViewById(R.id.et_email);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 12);
            }
        });
        btn_create.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                  name = username.getText().toString();
                  phoneN = phone.getText().toString();
                  emaill = email.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneN) || image_url.length == 0) {
                    Toast.makeText(Create_Contact.this, "The Data is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    contact c = new contact(name, phoneN, image_url,emaill);
                    boolean create = database.insertContact(c);
                    if (create) {
                        username.setText("");
                        phone.setText("");
                        email.setText("");
                        img_book.setImageResource(R.drawable.books);
                        Intent i = new Intent(Create_Contact.this, Show_Contact.class);
                        startActivity(i);
                        finish();
                        System.out.println("Done");
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri imageuri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageuri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_book.setImageBitmap(bitmap);
                image_url = getBytes(bitmap);
            } catch (Exception e) {
            }
        } else {
        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}