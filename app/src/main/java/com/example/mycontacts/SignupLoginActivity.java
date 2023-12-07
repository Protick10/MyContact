package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupLoginActivity extends AppCompatActivity {

    private TextView signuphead, ahaa, login;
    EditText name,email,phone,password,reenterpass;
    Button signupbtn;
    CheckBox remme;
    private boolean isLoginPage = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);
        
        //stays logged in..

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if (checkbox.equals("true")){

            Intent intent = new Intent(SignupLoginActivity.this,ContactListActivity.class);
            startActivity(intent);
        }

        name = findViewById(R.id.signupetName);
        email= findViewById(R.id.signupetEmail);
        phone = findViewById(R.id.signupetPhone);
        password = findViewById(R.id.signupetPassword);
        reenterpass = findViewById(R.id.signupetReenterPassword);
        signuphead = findViewById(R.id.signupHeading);
        ahaa = findViewById(R.id.signupAHAA);
        login = findViewById(R.id.signupLogin);
        signupbtn = findViewById(R.id.signupbutton);
        remme = findViewById(R.id.signupcheckBox);

        this.changeView();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLoginPage = !isLoginPage;
                changeView();
            }
        });

        //convert variables into strings..



        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameStr = name.getText().toString().trim();
                String emailStr = email.getText().toString().trim();
                String phoneStr = phone.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();
                String reenterpassStr = reenterpass.getText().toString().trim();

                if (isLoginPage == false){
                    //here we will write the code for signup
                    if (TextUtils.isEmpty(nameStr)){
                        name.setError("username is must");
                    }



                    else if(TextUtils.isEmpty(emailStr)  ){
                        email.setError("email is not filled");
                    }
                    else if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()){
                        email.setError("email is not valid");
                    }

                    else if (TextUtils.isEmpty(phoneStr)){
                        phone.setError("phone number is must");
                    }
                    else if (phoneStr.length()< 11) {
                        phone.setError("phone number must be valid");

                    } else if (TextUtils.isEmpty(passwordStr)){
                        password.setError("password is must");
                    }
                    else if (passwordStr.length() < 8) {

                        password.setError("Password must be at least " + 8 + " characters long.\n");
                    }
                    else if (!passwordStr.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[\\w@$!%*?&]{8,20}$")) {

                        password.setError("Password must contain at least one lowercase letter, one uppercase letter, one number, and one special character.\n");
                    }

                    else if (!passwordStr.equals(reenterpassStr) ){
                        reenterpass.setError("Password does not match");
                    }

                    else {


                        SharedPreferences localPref = SignupLoginActivity.this.getSharedPreferences("localPref", MODE_PRIVATE) ;
                        SharedPreferences.Editor edit = localPref.edit();
                        edit.putString("email", emailStr);
                        edit.putString("name", nameStr);
                        edit.putString("phone", phoneStr);
                        edit.putString("password", passwordStr);
                        edit.putString("re_enterpassword", reenterpassStr);
//
                        edit.apply();

                        Log.d("SharedPreferences", "Saved email: " + email.getText().toString());
                        Log.d("SharedPreferences", "Saved name: " + name.getText().toString());

                        Intent intent = new Intent(SignupLoginActivity.this,ContactListActivity.class);
                        startActivity(intent);
                        finish();
                        
                        //stays logged in..

                        if (remme.isChecked()){
                            SharedPreferences preferences = SignupLoginActivity.this.getSharedPreferences("checkbox", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("remember", "true");
                            editor.apply();
                        } else if (!remme.isChecked()) {

                            SharedPreferences preferences = SignupLoginActivity.this.getSharedPreferences("checkbox", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("remember", "false");
                            editor.apply();
                        }

                    }
                }
                // here we will write the code for login
                else if (isLoginPage = !isLoginPage) {

                    if (TextUtils.isEmpty(phoneStr)){
                        phone.setError("phone number is must");
                    }
                    else if (phoneStr.length()< 11) {
                        phone.setError("phone number must be valid");
                    }
                    else {
                        SharedPreferences localPref = SignupLoginActivity.this.getSharedPreferences("localPref", MODE_PRIVATE) ;
                        String loginphone = localPref.getString("phone", "");
                        String loginpass = localPref.getString("password", "");

                        if ((phoneStr.equals(loginphone)) && (passwordStr.equals(loginpass)) ){
                            Intent i = new Intent(SignupLoginActivity.this, ContactListActivity.class);
                            startActivity(i);
                            finish();
                            // to stay logged in
                            if (remme.isChecked()){
                                SharedPreferences preferences = SignupLoginActivity.this.getSharedPreferences("checkbox", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("remember", "true");
                                editor.apply();
                            } else if (!remme.isChecked()) {

                                SharedPreferences preferences = SignupLoginActivity.this.getSharedPreferences("checkbox", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("remember", "false");
                                editor.apply();
                            }
                        } else {
                            Toast.makeText(SignupLoginActivity.this, "Invalid login Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }



                }
            }
        });


    }

    private void changeView(){
        if (isLoginPage) {

            name.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            reenterpass.setVisibility(View.GONE);
            signuphead.setText("Login");
            ahaa.setText("Don't have an Account?");
            signupbtn.setText("Login");
            login.setText("SignUp");
        } else {

            name.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            reenterpass.setVisibility(View.VISIBLE);
            signuphead.setText("Signup");
            ahaa.setText("Already have an Account?");
            signupbtn.setText("Sign Up");
            login.setText("Login");

        }
    }
}