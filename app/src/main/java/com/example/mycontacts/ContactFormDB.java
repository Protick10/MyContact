package com.example.mycontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;

import androidx.annotation.Nullable;

//import java.sql.Blob;
//import java.sql.SQLException;

//public class ContactFormDB extends SQLiteOpenHelper {
//    public ContactFormDB(@Nullable Context context) {
//        super(context, "contactsDB.db", null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//        System.out.println("DB@OnCreate");
//        String sql = "CREATE TABLE contacts  ("
//                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + "name TEXT,"
//                + "email TEXT,"
//                + "phone_home TEXT,"
//                + "phone_offfice TEXT,"
//                + "image TEXT"
//                + ")";
//        db.execSQL(sql);
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//            System.out.println("Write code to modify database schema here");
//
//    }
//
//    public void insertContacts(String name, String email, String phone_home, String phone_offfice, String image) {
//        SQLiteDatabase db = this.getWritableDatabase();
////        db.execSQL("INSERT INTO contacts (name, email, phone_home, phone_offfice, image) VALUES ('" + name + "', '" + email + "', '" + phone_home + "', '" + phone_offfice + "', '" + image + "')");
//        ContentValues cols = new ContentValues();
//        cols.put("name", name);
//        cols.put("email", email);
//        cols.put("phone_home", phone_home);
//        cols.put("phone_offfice", phone_offfice);
//        cols.put("image", image);
//        db.insert("contacts", null, cols);
//        db.close();
//    }
//    public void updateContacts(String name, String email, String phone_home, String phone_offfice, String image, int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cols = new ContentValues();
//        cols.put("name", name);
//        cols.put("email", email);
//        cols.put("phone_home", phone_home);
//        cols.put("phone_offfice", phone_offfice);
//        cols.put("image", image);
//        db.update("contacts", cols, "ID = " + id, null);
//        db.close();
//    }
//    public void deleteContacts(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete("contacts", "ID = " + id, null);
//        db.close();
//    }
//
//    public Cursor selectContacts(String query) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cur = db.rawQuery(query, null);
//        return cur;
//    }
//}


public class ContactFormDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CONTACTS = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE_HOME = "phone_home";
    private static final String COLUMN_PHONE_OFFICE = "phone_office";
    private static final String COLUMN_IMAGE = "image";

    private static final String CREATE_TABLE_CONTACTS =
            "CREATE TABLE " + TABLE_CONTACTS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PHONE_HOME + " TEXT, " +
                    COLUMN_PHONE_OFFICE + " TEXT, " +
                    COLUMN_IMAGE + " TEXT" +
                    ")";

    public ContactFormDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_CONTACTS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    // Method to insert contact with Base64-encoded image
    public long insertContacts(String name, String email, String phoneHome, String phoneOffice, String encodedImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE_HOME, phoneHome);
        values.put(COLUMN_PHONE_OFFICE, phoneOffice);
        values.put(COLUMN_IMAGE, encodedImage);

        long id = db.insert(TABLE_CONTACTS, null, values);
        db.close();
        return id;
    }

    // Method to retrieve contacts
    public Cursor selectContacts(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    // Other methods for updating, deleting, querying, etc. can be added based on requirements

    public int updateContact(long id, String name, String email, String phoneHome, String phoneOffice, String encodedImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE_HOME, phoneHome);
        values.put(COLUMN_PHONE_OFFICE, phoneOffice);
        values.put(COLUMN_IMAGE, encodedImage);

        return db.update(TABLE_CONTACTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Method to delete contact
    public void deleteContact(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}