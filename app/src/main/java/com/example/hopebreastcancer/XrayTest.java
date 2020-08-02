package com.example.hopebreastcancer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class XrayTest extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 100;
    Button btReturnXrayTest;
    FirebaseAuth mAuth;
    DatabaseReference databaseXrays;
    Calendar calendar ;
    SimpleDateFormat simpleDateFormat ;
    String dateTime;
    ImageView imageView;
    EditText editXrayResult;
    Uri uriProfileImage;
    String profileImageUrl;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xray_test);
        imageView=findViewById(R.id.xrayImageTest);
        editXrayResult=findViewById(R.id.xrayResultTest);
        btReturnXrayTest = findViewById(R.id.btReturnXrayTest);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();

        databaseXrays = FirebaseDatabase.getInstance().getReference("Xray").child(id);
        btReturnXrayTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(XrayTest.this,NewExamination.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

    }
    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"), CHOOSE_IMAGE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE  && resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            uriProfileImage =  data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                imageView.setImageBitmap(bitmap);
                uploadImageToFireBaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadImageToFireBaseStorage(){
        final StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("Xraypics/"+System.currentTimeMillis()+".jpg");
        //StorageReference deleteImage = FirebaseStorage.getInstance().getReferenceFromUrl(profileImageUrl);
        // deleteImage.delete();
        result =editXrayResult.getText().toString().trim();
        if (uriProfileImage!=null){
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            profileImageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    profileImageUrl = task.getResult().toString();
                                    dateTime= simpleDateFormat.format(calendar.getTime());
                                    Xray xray = new Xray(dateTime,profileImageUrl,result);
                                    databaseXrays.child(dateTime).setValue(xray).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(XrayTest.this,"Success",Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(XrayTest.this,"Failed",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    loadUserInformation();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(XrayTest.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }}
             private void loadUserInformation() {
                Glide.with(this).load(profileImageUrl.toString()).into(imageView);
            }



    }

