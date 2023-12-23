package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactDetailActivity extends AppCompatActivity {

    private ContactForm contact;
    TextView contname, contemail,contphonhome, contphoneoffice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        // Get the selected contact from the intent
        contact = getIntent().getParcelableExtra("contact");

        contname = findViewById(R.id.textViewName);
        contemail = findViewById(R.id.textViewEmail);
        contphonhome = findViewById(R.id.textViewPhoneHome);
        contphoneoffice = findViewById(R.id.textViewPhoneOffice);
        ImageView personImg = findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent != null) {
            ContactForm contact = intent.getParcelableExtra("contact");
            if (contact != null) {
                // Access contact properties here
                String contactName = contact.name;
                String contactEmail = contact.email;
                String contactPhoneHome = contact.phone_home;
                String contactPhoneOffice = contact.phone_offfice;
                if (contact.image != null) {
                    try {
                        byte[] byteCam = Base64.decode(contact.image, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteCam, 0, byteCam.length);
                        personImg.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        // Handle decoding exceptions
                        e.printStackTrace();
                        // Provide a default image in case of an exception
                        personImg.setImageDrawable(getResources().getDrawable(R.drawable.man));
                    }
                } else {
                    // Handle the case when the image is null
                    personImg.setImageDrawable(getResources().getDrawable(R.drawable.man));
                }

                contname.setText(contactName);
                contemail.setText(contactEmail);
                contphonhome.setText(contactPhoneHome);
                contphoneoffice.setText(contactPhoneOffice);


            }
        }

//        contname.setText(contact.name);
//        contemail.setText(contact.email);
//        contphonhome.setText(contact.phone_home);
//        contphoneoffice.setText(contact.phone_offfice);


        // Set click listener for an "Edit" button or any UI element to initiate the update
        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open ContactFormActivity for updating contact details
                Intent intent = new Intent(ContactDetailActivity.this, ContactFormActivity.class);
                intent.putExtra("contact", contact);
                startActivity(intent);
            }
        });
    }
}