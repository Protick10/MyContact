package com.example.mycontacts;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ContactFormActivity extends AppCompatActivity {

    EditText name, email, phoneHome, phoneOffice;
    ImageView profileImg;
    Button cancel,save;
    String encodedImage;
    int camera_req_code=100, gallary_req_code=10;

    private ContactForm contactToUpdate;

    long getid=0;
    ContactFormAdapter adapter;
    private ArrayList<ContactForm> contacts;


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

        contacts = new ArrayList<>();
        adapter = new ContactFormAdapter(this, contacts);



        //when click on image..

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(ContactFormActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED){
//                   //WHEN PERMISSION IS NOT GRANTED..
//                    //requesting the permission..
//                    ActivityCompat.requestPermissions(ContactFormActivity.this, new String[]{
//                            Manifest.permission.READ_EXTERNAL_STORAGE
//                    }, 100);
//                }else {
                    //when permission is granted
                    selectImage();
//                    //decode base64 string
//                    byte[] bytes = Base64.decode(encodedImage,Base64.DEFAULT);
//                    //initializing bitmap
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//
//                    profileImg.setImageBitmap(bitmap);
//                }
            }
        });

//        byte[] bytes = Base64.decode(encodedImage,Base64.DEFAULT);
//        //initializing bitmap
//        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//
//        profileImg.setImageBitmap(bitmap);


        //adding code for update contact..

        contactToUpdate = getIntent().getParcelableExtra("contact");

        if (contactToUpdate != null) {
            // Update UI elements with existing contact details
            name.setText(contactToUpdate.name);
            email.setText(contactToUpdate.email);
            phoneHome.setText(contactToUpdate.phone_home);
            phoneOffice.setText(contactToUpdate.phone_offfice);

            // Decode and display the existing profile image
            if (contactToUpdate.image != null) {
                try {
                    byte[] byteCam = Base64.decode(contactToUpdate.image, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteCam, 0, byteCam.length);
                    profileImg.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


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
//                    ContactFormDB db = new ContactFormDB(ContactFormActivity.this);
//                    db.insertContacts(nameStr, emailStr, phoneHomeStr, phoneOfficeStr, encodedImage);
//                    Intent intent = new Intent(ContactFormActivity.this, ContactListActivity.class);
//                    startActivity(intent);

                    saveOrUpdateContact();
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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }

    private void selectImage() {

        // clear previous data.
        profileImg.setImageBitmap(null);

        //initializes intent..

//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//
//        startActivityForResult(Intent.createChooser(intent,"Select image"),100);

//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////        intent.setType("image/*");
//        startActivityForResult(intent, gallary_req_code);
//        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraintent, camera_req_code);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Gallery", "Camera"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // Gallery option
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                        galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, gallary_req_code);
                        break;
                    case 1:
                        // Camera option
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, camera_req_code);
                        break;
                }
            }
        });
        builder.show();



    }

    private void saveOrUpdateContact() {
        // Get the user-inputted data from UI elements
        String updatedName = name.getText().toString().trim();
        String updatedEmail = email.getText().toString().trim();
        String updatedPhoneHome = phoneHome.getText().toString().trim();
        String updatedPhoneOffice = phoneOffice.getText().toString().trim();
        // Encode the image to Base64
        String updatedEncodedImage = encodedImage; // Implement this method


        // If contactToUpdate is null, it means we're creating a new contact
        if (contactToUpdate == null) {
            // Save the new contact
            ContactFormDB db = new ContactFormDB(ContactFormActivity.this);
//            getid=db.insertContacts(updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage);
//            Log.d(TAG, "saveOrUpdateContact: " + getid);

            long newContactId = db.insertContacts(updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage);
            // Add the new contact to the ArrayList
            contacts.add(new ContactForm(newContactId, updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage));




        } else {
            // Update the existing contact
            ContactFormDB db = new ContactFormDB(ContactFormActivity.this);
//            ContactForm contactForm = new ContactForm(contactToUpdate.id, updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage);
//            getid = db.insertContacts(updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage);
//            db.updateContacts(contactToUpdate.id, updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage);
//
//            db.updateContacts(contactToUpdate.id, updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage);
//            adapter = new ContactFormAdapter(this,contacts);

            db.updateContacts(contactToUpdate.id, updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage);
            // Update the corresponding ContactForm in the ArrayList
            int position = contacts.indexOf(contactToUpdate);
            if (position != -1) {
                contacts.set(position, new ContactForm(contactToUpdate.id, updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage));
            }

            adapter.notifyDataSetChanged();
//            int position = contacts.indexOf(contactToUpdate);
//            if (position != -1) {
//                contacts.set(position, new ContactForm(contactToUpdate.id, updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage));
//            }
        }

        // Navigate back to ContactListActivity
        Intent intent = new Intent(ContactFormActivity.this, ContactListActivity.class);
        intent.putExtra("id", getid);
        startActivity(intent);
    }



//    private void saveOrUpdateContact() {
//        // Get the user-inputted data from UI elements
//        String updatedName = name.getText().toString().trim();
//        String updatedEmail = email.getText().toString().trim();
//        String updatedPhoneHome = phoneHome.getText().toString().trim();
//        String updatedPhoneOffice = phoneOffice.getText().toString().trim();
//        // Encode the image to Base64
//        String updatedEncodedImage = encodedImage; // Implement this method
//
//        // If contactToUpdate is null, it means we're creating a new contact
//        if (contactToUpdate == null) {
//            // Save the new contact
//            ContactFormDB db = new ContactFormDB(ContactFormActivity.this);
//            long newContactId = db.insertContacts(updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage);
//            // Initialize the contacts ArrayList if it's null
//            if (contacts == null) {
//                contacts = new ArrayList<>();
//            }
//            // Add the new contact to the ArrayList
//            contacts.add(new ContactForm(newContactId, updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage));
//        } else {
//            // Update the existing contact
//            ContactFormDB db = new ContactFormDB(ContactFormActivity.this);
//            db.updateContacts(contactToUpdate.id, updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage);
//            // Initialize the contacts ArrayList if it's null
//            if (contacts == null) {
//                contacts = new ArrayList<>();
//            }
//            // Update the corresponding ContactForm in the ArrayList
//            int position = contacts.indexOf(contactToUpdate);
//            if (position != -1) {
//                contacts.set(position, new ContactForm(contactToUpdate.id, updatedName, updatedEmail, updatedPhoneHome, updatedPhoneOffice, updatedEncodedImage));
//            }
//        }
//        adapter.notifyDataSetChanged();
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        //CHECK CONDITION
//        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            selectImage();
//        }else {
//            // when permission is denied..
//
//            Toast.makeText(getApplicationContext(),"Permission Denied", Toast.LENGTH_SHORT).show();
//
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && data!=null){

            if (requestCode==gallary_req_code){

                // when result is ok initializing URI

                Uri uri = data.getData();

                //initializeing bitmap

                try {
                    // Get the original image from the gallery or camera
                    Bitmap originalImage = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

// Resize the image
                    int width = 115;
                    int height = 119;
                    Bitmap resizedImage = Bitmap.createScaledBitmap(originalImage, width, height, false);

// Convert the resized image to a byte array
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    resizedImage.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                    byte[] bytes = stream.toByteArray();

// Encode the byte array to Base64
                    encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
//
//                    //Initialize bite stream
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//                    //compress bitmap
//                    bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
//                    //Initialize byte array
//                    byte[] bytes = stream.toByteArray();
//                    //will get encoded string here
//
//                    encodedImage = Base64.encodeToString(bytes,Base64.DEFAULT);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //decode base64 string
                byte[] bytes = Base64.decode(encodedImage,Base64.DEFAULT);
                //initializing bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                profileImg.setImageBitmap(bitmap);

            } else if (requestCode == camera_req_code) {

                Bitmap img = (Bitmap) (data.getExtras().get("data"));

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                //compress bitmap
                img.compress(Bitmap.CompressFormat.PNG,100,stream);
                //Initialize byte array
                byte[] bytes = stream.toByteArray();
                //will get encoded string here

                encodedImage = Base64.encodeToString(bytes,Base64.DEFAULT);


                //get the image..

                byte[] bytecam = Base64.decode(encodedImage,Base64.DEFAULT);
                //initializing bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytecam,0,bytes.length);

                profileImg.setImageBitmap(bitmap);


            }

        }
    }
}