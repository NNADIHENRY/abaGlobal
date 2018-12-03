package com.example.henry.abaglobal;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.henry.abaglobal.utils.JsonParser;
import com.example.henry.abaglobal.utils.Utils;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegFragment extends Fragment {

    final Utils util = new Utils();
    KProgressHUD hud;

    private View reg;
    private EditText etUsername, etName, etEmail, etPhone, etPassword, etConfirmpass;
    private Button btnregister, btnSignIn;
    private String username, name, email, phone, password, confirmpass;

    public RegFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        reg = inflater.inflate(R.layout.fragment_reg, container, false);

        initializeFields();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        return reg;
    }


    private void initializeFields() {
        etUsername = reg.findViewById(R.id.user_username);
        etName = reg.findViewById(R.id.user_name);
        etEmail = reg.findViewById(R.id.etemail);
        etPhone = reg.findViewById(R.id.etphone);
        etPassword = reg.findViewById(R.id.etpassword);
        etConfirmpass = reg.findViewById(R.id.etconfirmpass);
        btnregister = reg.findViewById(R.id.btnReg);
//        btnSignIn = reg.findViewById(R.id.btnSignin);
    }

    private void validation() {

        username = etUsername.getText().toString().trim();
        name = etName.getText().toString();
        email = etEmail.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        password  = etPassword.getText().toString().trim();
        confirmpass = etConfirmpass.getText().toString().trim();

        if (TextUtils.isEmpty(username)){
            etUsername.setError("pls choose a username");
        }
        if (TextUtils.isEmpty(name)){
            etName.setError("pls input your name");
        }
        if (TextUtils.isEmpty(email) || util.isValidEmail(getContext(), email)){
            etEmail.setError("pls input a valid email");
        }
        if (TextUtils.isEmpty(phone) || util.isValidPhoneNumber(getContext(), phone)){
            etPhone.setError("pls input a valid email");
        }
        if (password.length() < 7 || TextUtils.isDigitsOnly(password)){
            etPassword.setError("password must be more than 7 and has alpha and numbers");
        }
        if (!password.matches(confirmpass)){
            etConfirmpass.setError("password does not match");
        }
        if(util.isNetworkAvailable(getContext())){
            try{
                new sendPostRequest().execute();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            String message = "check your network connection";
            Toasty.error(getContext(), message, LENGTH_SHORT, true ).show();
        }
    }

    private class sendPostRequest extends AsyncTask<String, Void, JSONObject> {
        JsonParser jsonParser = new JsonParser();
        final Utils util = new Utils();
        KProgressHUD hud;

        private  static final  String REGISTRATION_URL = "10.10.11.142:8000/api/users";


        String username = etUsername.getText().toString().trim();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password  = etPassword.getText().toString().trim();
        String confirmpass = etConfirmpass.getText().toString().trim();
//        String gender = spGender.getItemAtPosition(spGender.getSelectedItemPosition()).toString();

        @Override
        protected void onPreExecute() {
            hud = KProgressHUD.create(getContext())
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
                params.put("password", password);
                params.put("username", username);
                params.put("email", email);

                JSONObject json = jsonParser.makeHttpRequest(REGISTRATION_URL, "POST", params);

                if (json != null){
                    return json;
                }else{
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            hud.dismiss();
//                            String message = "Network Error Pls Try Again Later";
//                            util.toastMessage(getContext(), message);
//
//                        }
//                    });
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
                    startActivity(new Intent(getContext(), SplashScreen.class));
                    Log.d("jsonerror", ""+json);
                }else if (json != null && json.getString("status").equals(503)){
                    String message = json.getString("message").toString();
                    Toasty.error(getContext(), message, LENGTH_SHORT, true).show();
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
