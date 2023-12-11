package com.example.mycontacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//public class ContactFormAdapter extends ArrayAdapter<ContactForm> {
//
//    private final Context context;
//    private final ArrayList<ContactForm> values;
//    public ContactFormAdapter(@NonNull Context context,ArrayList<ContactForm> values) {
//        super(context, -1, values);
//        this.context = context;
//        this.values = values;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View rowView = inflater.inflate(R.layout.layout_contact_list_row, parent, false);
//
//        CircleImageView personimg = rowView.findViewById(R.id.profile_image);
//        TextView name = rowView.findViewById(R.id.tvName);
//        TextView phonenum = rowView.findViewById(R.id.tvPhone);
//        TextView email = rowView.findViewById(R.id.tvEmail);
//        //TextView eventType = rowView.findViewById(R.id.tvEventType);
//
//        ContactForm contactForm = values.get(position);
//        ContactFormDB contactFormDB = new ContactFormDB(context);
//
//        // Decode Base64 string to byte array
////        byte[] bytecam = Base64.decode(contactForm.image, Base64.DEFAULT);
////
////        // Initialize bitmap
////        Bitmap bitmap = BitmapFactory.decodeByteArray(bytecam, 0, bytecam.length);
//        //initializing bitmap
////        Bitmap bitmap = BitmapFactory.decodeByteArray(bytecam,0,bytes.length);
//
//        if (contactForm.image != null) {
//            byte[] bytecam = Base64.decode(contactForm.image, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bytecam, 0, bytecam.length);
//            personimg.setImageBitmap(bitmap);
//        } else {
//            // Handle the case when the image is null
//            personimg.setImageDrawable(context.getDrawable(R.drawable.man));
//        }
//
////        personimg.setImageBitmap(bitmap);
//        name.setText(contactForm.name);
//        phonenum.setText(contactForm.phone_home);
//        email.setText(contactForm.email);
////        topLeftTitle.setText(e.course + ":" + e.type + "-" + e.lecture);
////        dateTime.setText(e.date);
////        topic.setText(e.topic);
//
//        return rowView;
//    }
//}
public class ContactFormAdapter extends ArrayAdapter<ContactForm> {

    private final Context context;
    private final ArrayList<ContactForm> values;

    public ContactFormAdapter(@NonNull Context context, ArrayList<ContactForm> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.layout_contact_list_row, parent, false);

        CircleImageView personImg = rowView.findViewById(R.id.profile_image);
        TextView name = rowView.findViewById(R.id.tvName);
        TextView phoneNum = rowView.findViewById(R.id.tvPhone);
        TextView email = rowView.findViewById(R.id.tvEmail);

        ContactForm contactForm = values.get(position);

        if (contactForm.image != null) {
            try {
                byte[] byteCam = Base64.decode(contactForm.image, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteCam, 0, byteCam.length);
                personImg.setImageBitmap(bitmap);
            } catch (Exception e) {
                // Handle decoding exceptions
                e.printStackTrace();
                // Provide a default image in case of an exception
                personImg.setImageDrawable(context.getDrawable(R.drawable.man));
            }
        } else {
            // Handle the case when the image is null
            personImg.setImageDrawable(context.getDrawable(R.drawable.man));
        }

        name.setText(contactForm.name);
        phoneNum.setText(contactForm.phone_home);
        email.setText(contactForm.email);

        return rowView;
    }
}
