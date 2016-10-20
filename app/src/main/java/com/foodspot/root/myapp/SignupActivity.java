package com.foodspot.root.myapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.foodspot.root.myapp.HttpService.ServiceHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    EditText etName, etUsername, etPassword, etPassword1, etNoHp, etEMail;
    String username, name, password, retype, nohp, email,choose;
    Button btnSubmit;
    Spinner user;
    ArrayAdapter<String> adapter;
    static String response;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        InitComponent();
        ArrayList<String> jenis_user = new ArrayList<>();
        jenis_user.add("Restaurant Owner");
        jenis_user.add("Food Lovers");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Please wait ...");

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_jenis_user, jenis_user);
        user.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etName.getText().toString().equals("") ||
                        etUsername.getText().toString().equals("")||
                        etPassword.getText().toString().equals("")||
                        etPassword1.getText().toString().equals("")||
                        etNoHp.getText().toString().equals("")||
                        etEMail.getText().toString().equals("")||
                        user.getSelectedItem().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Isi semua Form!", Toast.LENGTH_LONG).show();
                }else{


                progressDialog.show();

                btnSubmit.setEnabled(false);
                username = etUsername.getText().toString();
                name = etName.getText().toString();
                password = etPassword.getText().toString();
                retype = etPassword1.getText().toString();
                nohp = etNoHp.getText().toString();
                email = etEMail.getText().toString();
                choose = user.getSelectedItem().toString();


                new backgroundTask().execute();

                }
            }
        });

    }



    class backgroundTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(response.contains("success")){
                Toast.makeText(getApplicationContext(),"Berhasil Register",Toast.LENGTH_LONG).show();
                finish();
            }else if(response.contains("Username already exists")){
                Toast.makeText(getApplicationContext(),"Username or Email already exists",Toast.LENGTH_LONG).show();
            }else if(response.contains("Username or Email already exists")){
                Toast.makeText(getApplicationContext(),"Username or Email already exists",Toast.LENGTH_LONG).show();
            }
            btnSubmit.setEnabled(true);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String url = null;
            try {
                url = "http://morefoods.hol.es/User.php?api_key=restoran&action=register" +
                        "&username=" + URLEncoder.encode(username,"utf-8") +
                        "&password=" + URLEncoder.encode((password.equals(retype) ? password : ""),"utf-8") +
                        "&nama_lengkap=" +URLEncoder.encode(name,"utf-8")+
                        "&email=" + URLEncoder.encode(email,"utf-8")+
                        "&no_telepon=" +URLEncoder.encode(nohp,"utf-8")+
                        "&type_user=" + URLEncoder.encode((choose.equals("Food Lovers") ? "0" : "1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d("URLnya ", url);

            response = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
            Log.d("hasil signUp ",response);
            return null;

        }
    }
    void InitComponent(){
        user = (Spinner) findViewById(R.id.spinnerJenisUser);
        btnSubmit = (Button) findViewById(R.id.signUp_btnRegister);
        etName = (EditText) findViewById(R.id.signup_etName);
        etUsername = (EditText) findViewById(R.id.signup_username);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword1 = (EditText) findViewById(R.id.etRetypePassword);
        etNoHp = (EditText) findViewById(R.id.etNohp);
        etEMail = (EditText) findViewById(R.id.etEmail);
    }
}
