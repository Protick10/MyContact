package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class ContactListActivity extends AppCompatActivity {

    ImageView backbtn, addbtn;
    ListView contlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);


        backbtn = findViewById(R.id.contback);
        addbtn = findViewById(R.id.contadd);
        contlv = findViewById(R.id.contlistview);

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
}