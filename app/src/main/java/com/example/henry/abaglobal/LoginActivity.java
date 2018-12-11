package com.example.henry.abaglobal;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.henry.abaglobal.utils.JsonParser;
import com.example.henry.abaglobal.utils.Utils;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    final Utils util = new Utils();
    KProgressHUD hud;

    private View login;
    private EditText etUsername, etEmail, etPassword;
    private TextView tvForgotPass, tvLogin;
    private CircleImageView facebook, twitter, linkedIn;
    private ImageView signUp;
    private String username, email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       initializeFields();

       signUp.setOnClickListener(this);
       tvLogin.setOnClickListener(this);
       tvForgotPass.setOnClickListener(this);
       facebook.setOnClickListener(this);
       twitter.setOnClickListener(this);
       linkedIn.setOnClickListener(this);
    }

    private void initializeFields() {

        etEmail = (EditText)findViewById(R.id.et_Email);
        etPassword = (EditText) findViewById(R.id.et_Password);
        tvLogin = (TextView) findViewById(R.id.tv_Login_btn);
        tvForgotPass = (TextView) findViewById(R.id.tv_ForgotPass);
        facebook = (CircleImageView) findViewById(R.id.fb_link);
        twitter = (CircleImageView) findViewById(R.id.twitter_link);
        linkedIn = (CircleImageView) findViewById(R.id.linkedin_link);
        signUp = (ImageView) findViewById(R.id.img_signUp_red);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.img_signUp_red:
                startActivity(new Intent(LoginActivity.this, RegisterationActivity.class));
                break;
            case R.id.tv_ForgotPass:
                break;
            case R.id.fb_link:
                break;
            case R.id.twitter_link:
                break;
            case R.id.linkedin_link:
                break;
            case R.id.tv_Login_btn:

                validation();
                break;
        }

    }

    private void validation() {

        username = etUsername.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password  = etPassword.getText().toString().trim();

        if (!util.isNetworkAvailable(LoginActivity.this)){
            Toasty.error(this, "Please turn on network Connection", LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)){
            etEmail.setError("please input email");
            return;
        }
        if (!util.isValidEmail(getApplicationContext(), email)){
            etEmail.setError("Not a valid EMAIL");
            return;
        }
        if (password.isEmpty()){
            etPassword.setError("Please input password");
        }else {
            new sendPostRequest().execute();
        }
    }

    private class sendPostRequest extends AsyncTask<String, Void, JSONObject> {
        JsonParser jsonParser = new JsonParser();
        final Utils util = new Utils();
        KProgressHUD hud;

        private  static final  String REGISTRATION_URL = "http://localhost:8000/api/login";


        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password  = etPassword.getText().toString().trim();
//        String gender = spGender.getItemAtPosition(spGender.getSelectedItemPosition()).toString();

        @Override
        protected void onPreExecute() {
            hud = KProgressHUD.create(getApplicationContext())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("please wait...")
                    .setDetailsLabel("Loging you in..." + username)
                    .setCancellable(true)
                    .setBackgroundColor(Color.BLUE)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
            super.onPreExecute();

        }
        @Override
        protected JSONObject doInBackground(String... strings) {
            try{
                HashMap<String, String > params = new HashMap<>();
                params.put("password", password);
                params.put("username", username);
                params.put("email", email);

                JSONObject json = jsonParser.makeHttpRequest(REGISTRATION_URL, "POST", params);

                if (json != null){
                    return json;
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hud.dismiss();
                            String message = "Network Error Pls Try Again Later";
                            util.toastMessage(getApplicationContext(), message);

                        }
                    });
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(JSONObject json) {
            hud.dismiss();
            try{
                if (json != null && json.getString("status").equals(200)){
                    startActivity(new Intent(LoginActivity.this, SplashScreen.class));
                    Log.d("jsonerror", ""+json);
                }else if (json != null && json.getString("status").equals(503)){
                    String message = json.getString("message").toString();
                    Toasty.error(getApplicationContext(), message, LENGTH_SHORT, true).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }catch(NullPointerException e){
                e.printStackTrace();
            }
            super.onPostExecute(json);
        }


    }

}

