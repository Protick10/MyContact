package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ContactFormActivity extends AppCompatActivity {

    EditText name, email, phoneHome, phoneOffice;
    ImageView profileImg;
    Button cancel,save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        name = findViewById(R.id.formName);
        email = findViewById(R.id.formEmail);
        phoneHome = findViewById(R.id.formphoneHome);
        phoneOffice = findViewById(R.id.formPhoneOffice);
        profileImg = findViewById(R.id.formImageView);
        cancel = findViewById(R.id.formCancelbutton);
        save = findViewById(R.id.formSaveButton);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;

                // Name validation
                String nameStr = name.getText().toString().trim();
                if (TextUtils.isEmpty(nameStr)) {
                    name.setError("Name is required");
                    isValid = false;
                }

                // Email validation
                String emailStr = email.getText().toString().trim();
                if (TextUtils.isEmpty(emailStr)) {
                    email.setError("Email is required");
                    isValid = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                    email.setError("Invalid email format");
                    isValid = false;
                }

                // Phone Home validation with default value
                String phoneHomeStr = phoneHome.getText().toString().trim();
                if (phoneHomeStr.isEmpty()) {
                    phoneHome.setError("Phone number is required");
                    isValid = false;
                } else if (!isValidBangladeshiNumber(phoneHomeStr)) {
                    phoneHome.setError("Invalid Bangladeshi phone number format");
                    isValid = false;
                }


                // Phone Office validation
                String phoneOfficeStr = phoneOffice.getText().toString().trim();

                if (phoneOfficeStr.isEmpty()) {
                    phoneOfficeStr = null; // Set default value to null if not entered
                } else if (!phoneOfficeStr.isEmpty() && !isValidBangladeshiNumber(phoneOfficeStr)) {
                    phoneOffice.setError("Invalid Bangladeshi phone number format");
                    isValid = false;
                }

                // Image validation
                Drawable profileImage = profileImg.getDrawable();
                if (profileImage == null) {
                    isValid = false;
                    Toast.makeText(ContactFormActivity.this, "Please select a profile image", Toast.LENGTH_SHORT).show();
                }

                // If all fields are valid, proceed with saving the contact information.
                if (isValid) {
                    // Get the image data
                    // ...
                    // Your code to save contact information with image data
                    // ...
                    // Clear the errors
                    name.setError(null);
                    email.setError(null);
                    phoneHome.setError(null);
                    phoneOffice.setError(null);
                }
            }

            private boolean isValidBangladeshiNumber(String phoneNumber) {
                // Regular expression to match Bangladeshi phone numbers
                String regex = "^(?:\\+88|88)?(01[3-9]\\d{8})$";

                return phoneNumber.matches(regex);
            }
        });
    }
}