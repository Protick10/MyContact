package com.example.mycontacts;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ContactForm implements Parcelable {

    long id;
    String name = "";
    String email = "";
    String phone_home = "" ;
    String phone_offfice = "";
    String image= "";

    public ContactForm(long id,String name, String email, String phone_home, String phone_offfice, String image){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_home = phone_home;
        this.phone_offfice = phone_offfice;
        this.image = image;
    }

//    public long getid(){
//        ContactFormDB contactFormDB = new ContactFormDB(ContactForm.this);
//    }


    public long getId() {
        return id;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phone_home);
        dest.writeString(phone_offfice);
        dest.writeString(image);
    }

    public static final Parcelable.Creator<ContactForm> CREATOR = new Parcelable.Creator<ContactForm>() {
        @Override
        public ContactForm createFromParcel(Parcel in) {
            return new ContactForm(in);
        }

        @Override
        public ContactForm[] newArray(int size) {
            return new ContactForm[size];
        }
    };

    protected ContactForm(Parcel in) {
        name = in.readString();
        email = in.readString();
        phone_home = in.readString();
        phone_offfice = in.readString();
        image = in.readString();
    }

}
