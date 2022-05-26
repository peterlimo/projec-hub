package com.example.projecthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginStudent extends AppCompatActivity {
    TextInputEditText LoginEmail;
    TextInputEditText LoginPassword;
    TextView RegisterHere;
    Button btnLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);
        LoginEmail= findViewById(R.id.LoginEmail);
        LoginPassword = findViewById(R.id.LoginPassword);
        RegisterHere = findViewById(R.id.RegisterHere);
        btnLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            LoginUser();
        });
        RegisterHere.setOnClickListener(view -> {
            startActivity(new Intent(LoginStudent.this,student.class));
        });
    }

    private void LoginUser() {
        String email = LoginEmail.getText().toString();
        String password = LoginPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            LoginEmail.setError("Email cannot be empty");
            LoginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)){
            LoginPassword.setError("Password cannot be empty");
            LoginPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginStudent.this, "User logged in successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginStudent.this,Student_Home.class));
                        //finish();
                    } else {
                        Toast.makeText(LoginStudent.this, "Log in Error: " +task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}