package com.example.henry.abaglobal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by nnenna on 12/2/18.
 */

public class Abaglobal_help extends AppCompatActivity{

    private ImageView call, mail, chat;

    public Abaglobal_help() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abaglobals_help);

        chat = findViewById(R.id.chat);
        mail = findViewById(R.id.mail);
        call = findViewById(R.id.call);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = "+2349066130991";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View v) {
                startActivity(new Intent(Abaglobal_help.this,ContactPage.class));
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Abaglobal_help.this,  ChatActivity.class));

            }
        });
    }
}
