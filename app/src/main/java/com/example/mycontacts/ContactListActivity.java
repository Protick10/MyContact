package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    ImageView backbtn, addbtn;
    ListView contlv;

    private ArrayList<ContactForm> contacts;
    private ContactFormAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        contacts = new ArrayList<>();


        backbtn = findViewById(R.id.contback);
        addbtn = findViewById(R.id.contadd);
        contlv = findViewById(R.id.contlistview);

//        loadContacts();

        // when add button will be clicked
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactListActivity.this, ContactFormActivity.class);
                startActivity(intent);

            }
        });

        //when clicked on back button..
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ContactListActivity.this, SignupLoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                intent.putExtra("isloginview", true);
//                startActivity(intent);
                finishAffinity();


//                finish();
            }
        });
    }
    public void onStart(){
        super.onStart();
        loadData();
    }

    private void loadData(){
        contacts.clear();
        ContactFormDB contactFormDB = new ContactFormDB(this);
        Cursor cursor = contactFormDB.selectContacts("SELECT * FROM contacts");
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String phone_home = cursor.getString(3);
                String phone_office = cursor.getString(4);
                String image = cursor.getString(5);

                contacts.add(new ContactForm(name,email,phone_home,phone_office,image));
            }
        }
        contactFormDB.close();
        adapter = new ContactFormAdapter(this,contacts);
        contlv.setAdapter(adapter);
    }

//    private void loadContacts() {
//
//        String query = "SELECT * FROM contacts";
//        ContactFormDB contactFormDB = new ContactFormDB(this);
//        Cursor cursor = contactFormDB.selectContacts(query);
//
//        if (cursor != null){
//            if (cursor.getCount()> 0){
//                while (cursor.moveToNext()){
//                    String name = cursor.getString(1);
//                    String email = cursor.getString(2);
//                    String phone_home = cursor.getString(3);
//                    String phone_office = cursor.getString(4);
////                    String image = cursor.getString(5);
//                    String image = "Image Preview";
//
//                    System.out.println(name + " " + email + " " + phone_home + " " + phone_office + " " + image);
//                }
//            }
//        }
//    }
}