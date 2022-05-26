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

public class student extends AppCompatActivity {
    TextInputEditText RegEmail;
    TextInputEditText RegPass;
    TextView LoginHere;
    Button btnRegister;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        RegEmail = findViewById(R.id.RegEmail);
        RegPass = findViewById(R.id.RegPass);
        LoginHere = findViewById(R.id.LoginHere);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view -> {
            CreateUser();
        });
        LoginHere.setOnClickListener(view -> {
            startActivity(new Intent(student.this,LoginStudent.class));
        });
    }
    private void CreateUser(){
        String email = RegEmail.getText().toString();
        String password = RegPass.getText().toString();

        if (TextUtils.isEmpty(email)){
            RegEmail.setError("Email cannot be empty");
            RegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)){
            RegPass.setError("Password cannot be empty");
            RegPass.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(student.this, "User registered successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(student.this,studentPage.class));
                    } else {
                        Toast.makeText(student.this, "Registration Error: " +task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}