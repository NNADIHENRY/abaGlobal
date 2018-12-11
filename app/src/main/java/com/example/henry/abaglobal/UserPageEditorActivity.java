package com.example.henry.abaglobal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserPageEditorActivity extends AppCompatActivity {
    private EditText etbusinessName, etBusinessLocation, etWebsite, etEmail, etTwitter, etFacebook
            ,etInstagram , etStatus, etKeyword, etProduct;
    private TextView tvDisplayName;
    private CircleImageView cvDisplayPix;
    private Button btnPublish;
    private CheckBox chkWouldYouLikeTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page_editor);

        initializeFields();

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }


    private void initializeFields() {

        etBusinessLocation = findViewById(R.id.et_business_location);
        etbusinessName = findViewById(R.id.et_business_name);
        etWebsite = findViewById(R.id.et_website);
        etEmail = findViewById(R.id.et_user_email_setup);
        etFacebook = findViewById(R.id.et_facebook);
        etInstagram = findViewById(R.id.et_instagram);
        etTwitter = findViewById(R.id.et_twitter);
        etStatus = findViewById(R.id.et_status);
        etKeyword = findViewById(R.id.et_keywordforsearch);
        etProduct = findViewById(R.id.et_product_type);
        tvDisplayName = findViewById(R.id.display_name);
        cvDisplayPix = findViewById(R.id.user_profile_image_setup);
        btnPublish = findViewById(R.id.btn_publish);
        chkWouldYouLikeTo = findViewById(R.id.like_to_optimize_check);

    }


    private void validate() {


    }
}
