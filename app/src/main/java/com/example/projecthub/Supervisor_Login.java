package com.example.projecthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Supervisor_Login extends AppCompatActivity {
    TextInputEditText Admin_Id;
    TextInputEditText LoginPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_login);
        Admin_Id= findViewById(R.id.Admin_Id);
        LoginPassword = findViewById(R.id.LoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String admin = Admin_Id.getText().toString();
                String password = LoginPassword.getText().toString();

                if (TextUtils.isEmpty(admin)){
                    Admin_Id.setError("UserName is required");
                    Admin_Id.requestFocus();

                }
                else if (TextUtils.isEmpty(password)){
                    LoginPassword.setError("Password is required");
                    LoginPassword.requestFocus();

                }else if (password.equals("admin")&& admin.equals("admin"))
                {
                    startActivity(new Intent(Supervisor_Login.this,Supervisor_Home.class));
                    Toast.makeText(Supervisor_Login.this, "Supervisor Login Successfully", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(Supervisor_Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}