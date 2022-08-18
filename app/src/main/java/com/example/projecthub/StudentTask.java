package com.example.projecthub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentTask extends AppCompatActivity {
    EditText infoText;
    TextView imgName, docName;
    Button addImg, attach_doc, up_data;

    Uri ImgPathUri = null, DocPathUri = null;
    String StoragePath = "Students Task";

    String linkImg, linkDoc;

    UploadData uploadData;
//Initialize the database reference
    StorageReference storageReference;
    DatabaseReference databaseReference;

    int Image_Request_Code = 7;
    //Doc request code..
    final static int PICK_DOC_CODE = 2342;

//    Initialize the progress dialog

    ProgressDialog progressDialog;
    SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
    Date date = new Date();


//    Onactivity result for the selected image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getData() != null && requestCode == Image_Request_Code && resultCode == RESULT_OK) {
            ImgPathUri = data.getData();
            imgName.setText("IMG_SELECTED");
        }
        if ((data != null && data.getData() != null && requestCode == PICK_DOC_CODE && resultCode == RESULT_OK) ){
            DocPathUri = data.getData();
            docName.setText("DOC-SELECTED");
        }
    }
    public String getExtension(Uri uri){
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        //Handling the data from other apps without user interaction...
        ContentResolver contentResolver = getContentResolver();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_task);
        infoText = findViewById(R.id.infoText);
        imgName = findViewById(R.id.imgName);
        docName = findViewById(R.id.docName);
        addImg = findViewById(R.id.addImg);
        attach_doc = findViewById(R.id.attach_doc);
        up_data = findViewById(R.id.up_data);

        uploadData = new UploadData();
//Initialize the declared variables
        progressDialog = new ProgressDialog(StudentTask.this);
        storageReference = FirebaseStorage.getInstance().getReference("Student Task");
        databaseReference = FirebaseDatabase.getInstance().getReference("Students Task");

        attach_doc.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pick the doc"), PICK_DOC_CODE);
        });
        addImg.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pick the Image"), Image_Request_Code);
        });
        up_data.setOnClickListener(view -> {
            progressDialog.setTitle("Uploading your data");
            progressDialog.show();

            if (ImgPathUri == null && DocPathUri == null){
                String info = infoText.getText().toString();

                uploadData.setActual(formatter.format(date));
                uploadData.setInfo(info);
                uploadData.setDocURL(null);
                uploadData.setImgURL(null);

                databaseReference.child(formatter.format(date)).setValue(uploadData);

                progressDialog.dismiss();

            }

            if (ImgPathUri != null && DocPathUri == null){
                StorageReference str = storageReference.child(StoragePath + System.currentTimeMillis() + "." + getExtension(ImgPathUri));
                str.putFile(ImgPathUri).addOnSuccessListener(taskSnapshot -> taskSnapshot.getStorage().getDownloadUrl()
                        .addOnSuccessListener(uri -> {
                            String ImgLink=uri.toString();
                            linkImg=ImgLink;

                            String info=infoText.getText().toString();

                            uploadData.setInfo(info);
                            uploadData.setImgURL(linkImg);
                            uploadData.setDocURL(null);
                            uploadData.setActual(formatter.format(date));

                            databaseReference.child(formatter.format(date)).setValue(uploadData);

                            progressDialog.dismiss();

                        }))
                        .addOnFailureListener(e -> Toast.makeText(StudentTask.this, "Img Upload failed", Toast.LENGTH_SHORT).show());

            }
            if(DocPathUri!=null && ImgPathUri==null){

                StorageReference str=storageReference.child(StoragePath + System.currentTimeMillis() + "." + getExtension(DocPathUri));

                str.putFile(DocPathUri)
                        .addOnSuccessListener(taskSnapshot -> taskSnapshot.getStorage().getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    String DocLink=uri.toString();
                                    linkDoc=DocLink;

                                    String info=infoText.getText().toString();

                                    uploadData.setActual(formatter.format(date));
                                    uploadData.setInfo(info);
                                    uploadData.setDocURL(linkDoc);
                                    uploadData.setImgURL(null);

                                    databaseReference.child(formatter.format(date)).setValue(uploadData);

                                    progressDialog.dismiss();
                                }))
                        .addOnFailureListener(e -> Toast.makeText(StudentTask.this, "Doc upload failed", Toast.LENGTH_SHORT).show());
            }
            if(ImgPathUri!=null && DocPathUri!=null){
                StorageReference str=storageReference.child(StoragePath + System.currentTimeMillis() + "." + getExtension(ImgPathUri));

                str.putFile(ImgPathUri)
                        .addOnSuccessListener(taskSnapshot -> taskSnapshot.getStorage().getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    String ImgLink=uri.toString();
                                    linkImg=ImgLink;
                                    Toast.makeText(StudentTask.this, linkImg, Toast.LENGTH_SHORT).show();
                                    uploadData.setImgURL(linkImg);
                                    if(DocPathUri!=null){
                                        StorageReference storageReference1=storageReference.child(StoragePath + System.currentTimeMillis() + "." + getExtension(DocPathUri));

                                        storageReference1.putFile(DocPathUri)
                                                .addOnSuccessListener(taskSnapshot1 -> taskSnapshot1.getStorage().getDownloadUrl()
                                                        .addOnSuccessListener(uri1 -> {
                                                            String DocLink= uri1.toString();
                                                            linkDoc=DocLink;
                                                            uploadData.setDocURL(linkDoc);
                                                            String info=infoText.getText().toString();

                                                            uploadData.setInfo(info);
                                                            uploadData.setActual(formatter.format(date));

                                                            databaseReference.child(formatter.format(date)).setValue(uploadData);

                                                            progressDialog.dismiss();
                                                        }).addOnFailureListener(e -> Log.e("TAG_FOR_FAILURE LOG", "On Failure: The exception", e)))
                                                .addOnFailureListener(e -> Toast.makeText(StudentTask.this, "doc fucked", Toast.LENGTH_SHORT).show());
                                    }
                                }))
                        .addOnFailureListener(e -> Toast.makeText(StudentTask.this, "Failed!!", Toast.LENGTH_SHORT).show())
                        .addOnProgressListener(snapshot -> {

                        });

            }

        });


    }
}