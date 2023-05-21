package com.example.basma.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.basma.model.contact;



import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATA_BASE_NAME = "DB_Contact";
    public static final int VERSION_DB = 4;
    public static final String TABLE_NAME = "contact";
    public static final String C_COLUMN_ID = "id";
    public static final String C_COLUMN_NAME = "name";
    public static final String C_COLUMN_IMAGE_URL = "image";
    public static final String C_COLUMN_PHONE = "phone";
    public static final String C_COLUMN_EMAIL = "email";

    public static DataBaseHelper Instance;

    //***************************************************************
    public DataBaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, VERSION_DB);

    }

    //*******************************************************************
    public static DataBaseHelper getInstance(Context context) {
        if (Instance == null) {
            Instance = new DataBaseHelper(context);
        }
        return Instance;
    }

    //*******************************************************************
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ( " +
                C_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + C_COLUMN_NAME + "  TEXT ,"
                + C_COLUMN_EMAIL + "  TEXT ,"
                + C_COLUMN_IMAGE_URL + " BLOB , " +
                C_COLUMN_PHONE + " TEXT)");
    }

    //******************************************************************
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "");
        onCreate(db);
    }

    //*****************************************************************
    public boolean insertContact(contact c) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_COLUMN_NAME, c.getName());
        contentValues.put(C_COLUMN_IMAGE_URL, c.getImageUrl());
        contentValues.put(C_COLUMN_PHONE, c.getPhone());
        contentValues.put(C_COLUMN_EMAIL, c.getEmail());
        long result = database.insert(TABLE_NAME, null, contentValues);

        return result != -1;

    }

    //****************************************************************
    public ArrayList<contact> getContact() {
        ArrayList<contact> contacts = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM contact", null);
        if (cursor.moveToFirst()) {
            int indexID = cursor.getColumnIndex(C_COLUMN_ID);
            int indexName = cursor.getColumnIndex(C_COLUMN_NAME);
            int indexPhone = cursor.getColumnIndex(C_COLUMN_PHONE);
            int indexEmail = cursor.getColumnIndex(C_COLUMN_EMAIL);
            int indexImgUrl = cursor.getColumnIndex(C_COLUMN_IMAGE_URL);


            do {
                int Id = cursor.getInt(indexID);
                String name = cursor.getString(indexName);
                String phone = cursor.getString(indexPhone);
                byte[] imgUrl = cursor.getBlob(indexImgUrl);
                String email = cursor.getString(indexEmail);
                contact c = new contact(Id, name, phone, imgUrl, email);

                contacts.add(c);
            } while (cursor.moveToNext());
        }
        return contacts;
    }

    //****************************************************************
    public boolean deleteCourse(String courseName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, "name=?", new String[]{courseName});
        db.close();
        return result > 0;
    }

    //**************************************************************
    public boolean updateContact(contact c , String nameUpdate) {
        System.out.println(c.getId());
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_COLUMN_NAME, c.getName());
        contentValues.put(C_COLUMN_PHONE, c.getPhone());
        contentValues.put(C_COLUMN_EMAIL,c.getEmail());
        contentValues.put(C_COLUMN_IMAGE_URL, c.getImageUrl());
        int result = database.update(TABLE_NAME,contentValues, "name=?",new String[]{nameUpdate});
        database.close();
        return result >0;
    }
}
