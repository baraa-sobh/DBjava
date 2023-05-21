package com.example.basma;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.ArrayList;

public class update_contact extends AppCompatActivity {
    private ImageView image_contact;
    private FloatingActionButton btn_addImg;
    private EditText et_userName, et_phoneNumber, et_email;
    private DataBaseHelper database = DataBaseHelper.getInstance(this);
    private Button btn_Edit;
    private byte[] imgNew;
    String name;
    String phoneN;
    String emaill;
    String nameNew;
    ArrayList<contact> contactList = new ArrayList<contact>();
    int id = 0;
    contact c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        image_contact = findViewById(R.id.im_add_image_contact_update);
        btn_addImg = findViewById(R.id.btn_addimg_update);
        et_userName = findViewById(R.id.et_userName_update);
        et_phoneNumber = findViewById(R.id.et_phoneNumber_update);
        et_email = findViewById(R.id.et_email_update);
        btn_Edit = findViewById(R.id.btn_update);
        btn_addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 12);
            }
        });
        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameNew = et_userName.getText().toString();
                phoneN = et_phoneNumber.getText().toString();
                emaill = et_email.getText().toString();
                c = new contact(id, nameNew, phoneN, imgNew, emaill);
                boolean x = database.updateContact(c, name);
                System.out.println(x);
                if (x) {
                    Intent i = new Intent(update_contact.this, Show_Contact.class);
                    startActivity(i);
                }
            }
//                name = et_userName.getText().toString();
//                phoneN = et_phoneNumber.getText().toString();
//                emaill = et_email.getText().toString();
//                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneN) || imgNew.length == 0) {
//                    Toast.makeText(update_contact.this, "The Data is Empty", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    System.out.println("Done");
//                   // database.updateContact(new contact(id,name, phoneN, imgNew,emaill));
////                    if(x){
////                        Intent i = new Intent(update_contact.this, Show_Contact.class);
////                        startActivity(i);
////                    }
//
//                }
//            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDetailsContact();
    }

    private void showDetailsContact() {
        contactList = database.getContact();
        contact contacts = contactList.get(id);
        name = contacts.getName();
        et_phoneNumber.setText(contacts.getPhone());
        et_userName.setText(contacts.getName());
        et_email.setText(contacts.getEmail());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri imageuri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageuri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                image_contact.setImageBitmap(bitmap);
                imgNew = getBytes(bitmap);
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