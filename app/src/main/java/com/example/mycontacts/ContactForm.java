package com.example.mycontacts;

public class ContactForm {

//    int id;
    String name = "";
    String email = "";
    String phone_home = "" ;
    String phone_offfice = "";
    String image= "";

    public ContactForm(String name, String email, String phone_home, String phone_offfice, String image){
//        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_home = phone_home;
        this.phone_offfice = phone_offfice;
        this.image = image;
    }

}
