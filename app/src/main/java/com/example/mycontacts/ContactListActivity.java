package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

        contlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected contact
                ContactForm selectedContact = contacts.get(position);

                // Open ContactDetailActivity for viewing and updating contact details
                Intent intent = new Intent(ContactListActivity.this, ContactDetailActivity.class);
                intent.putExtra("contact", selectedContact);
                startActivity(intent);
            }
        });


//        contlv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
//                ContactForm selectedContact = contacts.get(position);
//                Intent intent = new Intent(ContactListActivity.this, ContactFormActivity.class);
//                intent.putExtra("contact", selectedContact);
//                startActivity(intent);
//                return true;
//            }
//        });

        contlv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected contact
                ContactForm selectedContact = contacts.get(position);

                // Show a confirmation dialog or directly delete the contact
                showDeleteConfirmationDialog(selectedContact);

                return true; // Consume the long click event
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
//        Intent intent = getIntent();
//        long id = intent.getLongExtra("id",0);
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                long id = cursor.getLong(0);
//                Intent intent = getIntent();
//                long id = intent.getLongExtra("id",0);
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String phone_home = cursor.getString(3);
                String phone_office = cursor.getString(4);
                String image = cursor.getString(5);

                contacts.add(new ContactForm(id,name,email,phone_home,phone_office,image));
                Log.d("id", String.valueOf(id));
            }
        }
        contactFormDB.close();
        adapter = new ContactFormAdapter(this,contacts);
        contlv.setAdapter(adapter);
    }



    // Add a method to show a confirmation dialog
    private void showDeleteConfirmationDialog(final ContactForm contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked the "Delete" button
                        deleteContact(contact);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked the "Cancel" button, do nothing
                        dialog.dismiss();
                    }
                });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // Add a method to delete the contact from the database and refresh the UI
    private void deleteContact(ContactForm contact) {
        ContactFormDB contactFormDB = new ContactFormDB(this);
        contactFormDB.deleteContact(contact.id);
        contactFormDB.close();

        // Remove the contact from the list and notify the adapter
        contacts.remove(contact);
        adapter.notifyDataSetChanged();
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