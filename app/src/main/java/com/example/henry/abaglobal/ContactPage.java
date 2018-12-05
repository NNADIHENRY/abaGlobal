package com.example.henry.abaglobal;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.henry.abaglobal.utils.Utils;

import java.util.regex.Pattern;

public class ContactPage extends AppCompatActivity {

    private EditText etemail,etyourname,etsubject,etmessage;
    private Button btnsendmessage;
    private String email,yourname,subject,message;
    private Utils utils;
    private ImageView imgfacebook,imgtwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);
        utils = new Utils();

        etemail = findViewById(R.id.etemail);
        etyourname = findViewById(R.id.etname);
        etsubject = findViewById(R.id.etsubject);
        etmessage = findViewById(R.id.etmessage);
        btnsendmessage = findViewById(R.id.btnsendmessage);
        imgfacebook = findViewById(R.id.imgfacebook);
        imgtwitter = findViewById(R.id.imgtwitter);


        btnsendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        imgfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.facebook.com/AbaGlobal-1077754245730814/"));
                startActivity(browserIntent);

            }
        });

        imgtwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/AbaGlobal/"));
                startActivity(browserIntent);

            }
        });
    }

    private void validate() {

      email  = etemail.getText().toString().trim();
      yourname = etyourname.getText().toString().trim();
      subject = etsubject.getText().toString().trim();
      message = etmessage.getText().toString().trim();

      if (utils.isNetworkAvailable(getApplicationContext())){

          if (!utils.isValidEmail(getApplicationContext(),email)) {
              etemail.setError("Please enter a correct email");
              return;
          }if (TextUtils.isEmpty(yourname)) {
              etyourname.setError("enter your name");
              return;

          }if (TextUtils.isEmpty(subject)){
              etsubject.setError("pls enter your subject");
              return;

          }else {
              String to = "Abaglobal2@gmail.com";
              Intent mail = new Intent(Intent.ACTION_SEND);
              mail.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
              mail.putExtra(Intent.EXTRA_SUBJECT, subject);
              mail.putExtra(Intent.EXTRA_TEXT, message);
              mail.setType("message/rfc822");
              startActivity(Intent.createChooser(mail, "Send email via:"));
          }


      }else{
          Toast.makeText(this, "pls turn on network", Toast.LENGTH_SHORT).show();
      }

    }
}
