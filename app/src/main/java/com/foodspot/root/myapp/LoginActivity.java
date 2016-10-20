package com.foodspot.root.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foodspot.root.myapp.HttpService.Login.UserRequest;
import com.foodspot.root.myapp.HttpService.Login.UserRequestArray;
import com.foodspot.root.myapp.HttpService.ServiceHandler;
import com.foodspot.root.myapp.dao.Logindao;
import com.firebase.client.Firebase;
import com.google.gson.GsonBuilder;

import java.net.URLEncoder;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Firebase mRef;
    EditText etUsername, etPassword;
    public static String USERNAME, PASSWORD;
    Button btnLogin, btnSignUp;
    Logindao logindao;
    Intent i;
    ProgressDialog progressDialog;
    Bundle b = new Bundle();
    ArrayList<UserRequest> userRequests = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponent();

        sharedPreferences = getApplicationContext().getSharedPreferences("data", 0);
        editor = sharedPreferences.edit();
        Firebase.setAndroidContext(getApplicationContext());
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Please wait ...");
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!sharedPreferences.getString("id_user","kosong").equals("kosong")){
            i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }else{
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USERNAME = etUsername.getText().toString();
                PASSWORD = etPassword.getText().toString();
                progressDialog.show();
                new backgroundTask().execute();
            }
        });

        }


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });


    }
    void initComponent(){
        etUsername = (EditText) findViewById(R.id.Login_etUsename);
        etPassword = (EditText) findViewById(R.id.Login_etPassword);
        btnLogin = (Button) findViewById(R.id.Login_btnLogin);
        btnSignUp = (Button) findViewById(R.id.Login_btnSignUp);
    }

    public class backgroundTask extends AsyncTask<Void,Void,Void>{
        Boolean b = false;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (b = true){
                if (userRequests.size() > 0) {
                    if (USERNAME.equals(userRequests.get(0).getUsername())) {
                        i = new Intent(LoginActivity.this, MainActivity.class);
                        //progressDialog.dismiss();

                        editor.putString("id_user", userRequests.get(0).getId_user());
                        editor.putString("nama", userRequests.get(0).getNama_lengkap());
                        if(userRequests.get(0).getType_user().equals("1")){
                        editor.putString("status", "Restaurant");
                        }else{
                        editor.putString("status", "Food Lovers");
                        }
                        editor.putString("noTelp", userRequests.get(0).getNo_telepon());
                        editor.putString("email", userRequests.get(0).getEmail());
                        editor.putString("pic_profile", userRequests.get(0).getPicture_profile());
                        editor.putString("pic_bg", userRequests.get(0).getPicture_backgournd());
                        editor.putString("lat", userRequests.get(0).getLatitude());
                        editor.putString("long", userRequests.get(0).getLongitude());
                        editor.putString("type_user", userRequests.get(0).getType_user());

                        editor.commit();
                        progressDialog.dismiss();
                        startActivity(i);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
        }

        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            System.out.println(USERNAME);
            System.out.println(PASSWORD);
            String url = "http://morefoods.hol.es/User.php?api_key=restoran&action=getLogin&username="+ URLEncoder.encode(String.valueOf(LoginActivity.USERNAME)) +"&password="+URLEncoder.encode(LoginActivity.PASSWORD);
            String response = serviceHandler.makeServiceCall(url, ServiceHandler.GET);

            response.replace("<pre>","");
            response.replace("</pre>","");

            Log.d("response baru ",response);

            if(!response.contains("Username atau password salah")){
                b = true;
                UserRequestArray userRequestArray = new GsonBuilder().create().fromJson(response, UserRequestArray.class);
                if(userRequestArray.resultUser != null) {
                    for(UserRequest uq : userRequestArray.resultUser){
                        Log.d("Hasil",uq.getEmail());
                        userRequests.add(uq);
                    }
                } else {
                    Log.d("info"," Uq is null");
                }
            }else{
                b=false;

            }
            return null;
        }
    }
}
