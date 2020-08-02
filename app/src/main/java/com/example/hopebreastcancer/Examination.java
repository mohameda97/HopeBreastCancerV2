package com.example.hopebreastcancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Examination extends AppCompatActivity {
    Button bNewExamination,bShowHistory,bReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);
        bNewExamination = findViewById(R.id.btNewExamination);
        bShowHistory = findViewById(R.id.btHistoryOfExaminations);
        bReturn= findViewById(R.id.btReturnExamination);
        bNewExamination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Examination.this,NewExamination.class);
                startActivity(intent);
            }
        });
        bShowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Examination.this,HistoryOfExamination.class);
                startActivity(intent);
            }
        });
        bReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Examination.this,Profile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
