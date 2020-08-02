package com.example.hopebreastcancer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RiskTest extends AppCompatActivity {
    Button btRiskTest,btReturnRiskTest;
    FirebaseAuth mAuth;
    DatabaseReference databaseRisks;
    Calendar calendar ;
    SimpleDateFormat simpleDateFormat ;
    String dateTime;
    EditText editQ1,editQ2,editQ3,editQ4,editQ5,editQ6,editQ7,editQ8,editQ9,editRiskResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_test);
        btRiskTest = findViewById(R.id.btRiskTest);
        btReturnRiskTest = findViewById(R.id.btReturnRiskTest);
        editQ1 = findViewById(R.id.q1);
        editQ2 = findViewById(R.id.q2);
        editQ3 = findViewById(R.id.q3);
        editQ4 = findViewById(R.id.q4);
        editQ5 = findViewById(R.id.q5);
        editQ6 = findViewById(R.id.q6);
        editQ7 = findViewById(R.id.q7);
        editQ8 = findViewById(R.id.q8);
        editQ9 = findViewById(R.id.q9);
        editRiskResult = findViewById(R.id.riskTestResult);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");

        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        databaseRisks = FirebaseDatabase.getInstance().getReference("Risk").child(id);
        btRiskTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRisk();
            }
        });
        btReturnRiskTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(RiskTest.this,NewExamination.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void newRisk() {
        String q1 = editQ1.getText().toString().trim();
        String q2 = editQ2.getText().toString().trim();
        String q3 = editQ3.getText().toString().trim();
        String q4 = editQ4.getText().toString().trim();
        String q5 = editQ5.getText().toString().trim();
        String q6 = editQ6.getText().toString().trim();
        String q7 = editQ7.getText().toString().trim();
        String q8 = editQ8.getText().toString().trim();
        String q9 = editQ9.getText().toString().trim();
        String result = editRiskResult.getText().toString().trim();
        if (q1.isEmpty()){
            editQ1.setError("required field");
            editQ1.requestFocus();
            return;
        }
        if (q2.isEmpty()){
            editQ2.setError("required field");
            editQ2.requestFocus();
            return;
        }
        if (q3.isEmpty()){
            editQ3.setError("required field");
            editQ3.requestFocus();
            return;
        }
        if (q4.isEmpty()){
            editQ4.setError("required field");
            editQ4.requestFocus();
            return;
        }
        if (q5.isEmpty()){
            editQ5.setError("required field");
            editQ5.requestFocus();
            return;
        }
        if (q6.isEmpty()){
            editQ6.setError("required field");
            editQ6.requestFocus();
            return;
        }
        if (q7.isEmpty()){
            editQ7.setError("required field");
            editQ7.requestFocus();
            return;
        }
        if (q8.isEmpty()){
            editQ8.setError("required field");
            editQ8.requestFocus();
            return;
        }
        if (q9.isEmpty()){
            editQ9.setError("required field");
            editQ9.requestFocus();
            return;
        }
        if (result.isEmpty()){
            editRiskResult.setError("required field");
            editRiskResult.requestFocus();
            return;
        }
        dateTime= simpleDateFormat.format(calendar.getTime());

        Risk risk = new Risk(dateTime,q1,q2,q3,q4,q5,q6,q7,q8,q9,result);
        databaseRisks.child(dateTime).setValue(risk).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RiskTest.this,"Success",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RiskTest.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });;
 }
}
