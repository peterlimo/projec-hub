package com.example.projecthub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class studentPage extends AppCompatActivity {
    private EditText full_name,Reg_no,proj_tittle;
    private Button finish_account;
    private ProgressDialog pd;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);
        full_name = findViewById(R.id.full_name);
        Reg_no = findViewById(R.id.Reg_no);
        proj_tittle = findViewById(R.id.proj_tittle);
        finish_account =findViewById(R.id.finish_account);
        pd = new ProgressDialog(this);
        pd.setTitle("Completing Profile Setup");
        pd.setMessage("Please Wait...");

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        database =FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Student_Details ");



        finish_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                String Full_Name = full_name.getText().toString().trim();
                String Reg_No = Reg_no.getText().toString().trim();
                String Project_Tittle = proj_tittle.getText().toString().trim();

                if (!TextUtils.isEmpty(Full_Name)&& !TextUtils.isEmpty(Reg_No)
                        && !TextUtils.isEmpty(Project_Tittle))
                {
                    upData(Full_Name,Reg_No,Project_Tittle);
                }else {
                    pd.dismiss();
                    Toast.makeText(studentPage.this, "You left Some Blanks", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void upData(String full_name, String reg_no, String project_tittle) {
        DatabaseReference newStudent = databaseReference.child(userId);

        HashMap<String,Object>
        myMap = new HashMap<>();
        myMap.put("Full Name", full_name);
        myMap.put("Reg No",reg_no);
        myMap.put("Project Tittle",project_tittle);
        newStudent.updateChildren(myMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    pd.dismiss();
                    startActivity(new Intent(studentPage.this,Student_Home.class));
                    finish();
                }else {
                    pd.dismiss();
                    toast(task.getException().getMessage());
                }
            }
        });

    }

    private void toast(String  s) {
        Toast.makeText(this, "Message" +s, Toast.LENGTH_SHORT).show();
    }


}