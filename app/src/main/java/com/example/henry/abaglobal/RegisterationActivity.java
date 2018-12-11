package com.example.henry.abaglobal;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.henry.abaglobal.utils.JsonParser;
import com.example.henry.abaglobal.utils.Utils;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

import static android.widget.Toast.LENGTH_SHORT;

public class RegisterationActivity extends AppCompatActivity {


    final Utils util = new Utils();
    KProgressHUD hud;

    private EditText etUsername, etName, etEmail, etPhone, etPassword, etConfirmpass;
    private Button btnregister;
    private TextView tv_reg;
    private String username, name, email, phone, password, confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

            initializeFields();

            btnregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validation();
                }
            });
            tv_reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(RegisterationActivity.this, LoginActivity.class));
                }
            });

        }


    private void initializeFields() {
        etUsername = findViewById(R.id.et_username);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        etConfirmpass = findViewById(R.id.et_confirmpass);
        btnregister = findViewById(R.id.btn_reg);
        tv_reg= findViewById(R.id.tv_red_login);
    }

    private void validation() {

        username = etUsername.getText().toString().trim();
        name = etName.getText().toString();
        email = etEmail.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        password  = etPassword.getText().toString().trim();
        confirmpass = etConfirmpass.getText().toString().trim();


        if (!util.isNetworkAvailable(RegisterationActivity.this)){
            Toasty.error(this, "Please turn on network Connection", LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(username)){
            etUsername.setError("pls choose a username");
            return;
        }
        if (TextUtils.isEmpty(name)){
            etName.setError("pls input your name");
            return;
        }
        if (TextUtils.isEmpty(email) || !util.isValidEmail(getApplicationContext(), email)){
            etEmail.setError("pls input a valid email");
            return;
        }
        if (TextUtils.isEmpty(phone) || !util.isValidPhoneNumber(getApplicationContext(), phone)){
            etPhone.setError("pls input a valid email");
            return;
        }
        if (password.length() < 7 || TextUtils.isDigitsOnly(password)){
            etPassword.setError("password must be more than 7 and has alpha and numbers");
            return;
        }
        if (!password.matches(confirmpass)){
            etConfirmpass.setError("password does not match");
            return;
        }
        if(util.isNetworkAvailable(getApplicationContext())){
            try{
                new sendPostRequest().execute();
            }catch(Exception e){
                String m = e.getStackTrace().toString();
                Toasty.error(getApplicationContext(),"m");
                e.printStackTrace();
            }
        }else{
            String message = "check your network connection";
            Toasty.error(getApplicationContext(), message, LENGTH_SHORT, true ).show();
        }
    }

    private class sendPostRequest extends AsyncTask<String, Void, JSONObject> {
        JsonParser jsonParser = new JsonParser();
        final Utils util = new Utils();
        KProgressHUD hud;

        private  static final  String REGISTRATION_URL = "http://192.168.42.247:3000/api/register";


        String username = etUsername.getText().toString().trim();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password  = etPassword.getText().toString().trim();
        String confirmpass = etConfirmpass.getText().toString().trim();
//        String gender = spGender.getItemAtPosition(spGender.getSelectedItemPosition()).toString();

        @Override
        protected void onPreExecute() {
            hud = KProgressHUD.create(getApplicationContext())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("please wait...")
                    .setDetailsLabel("Registering..." + name)
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
                params.put("name", name);
                params.put("phone", phone);
                Log.d("jsonpush", "pushing");
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
            Log.d("jsonguy", "okay");
            try{
                if (json != null && json.getString("status").equals(200)){
                  startActivity(new Intent(RegisterationActivity.this, SplashScreen.class));
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

