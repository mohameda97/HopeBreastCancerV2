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

public class DiagnoseTest extends AppCompatActivity {
    Button btDiagnoseTest,btReturnDiagnoseTest;
    FirebaseAuth mAuth;
    DatabaseReference databaseDiagnoses;
    Calendar calendar ;
    SimpleDateFormat simpleDateFormat ;
    String dateTime;
    EditText editTexture_worst,editRadius_se,editRadius_worst,editArea_se,editArea_worst,editConcave_points_mean,editConcave_points_worst,editDiagnoseTestResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_test);
        btDiagnoseTest = findViewById(R.id.btDiagnoseTest);
        btReturnDiagnoseTest = findViewById(R.id.btReturnDiagnoseTest);
        editTexture_worst = findViewById(R.id.texture_worst);
        editRadius_se = findViewById(R.id.radius_se);
        editRadius_worst = findViewById(R.id.radius_worst);
        editArea_se = findViewById(R.id.area_se);
        editArea_worst = findViewById(R.id.area_worst);
        editConcave_points_mean = findViewById(R.id.concave_points_mean);
        editConcave_points_worst = findViewById(R.id.concave_points_worst);
        editDiagnoseTestResult = findViewById(R.id.diagnoseTestResult);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");

        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        databaseDiagnoses = FirebaseDatabase.getInstance().getReference("Diagnose").child(id);
        btDiagnoseTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDiagnose();
            }
        });
        btReturnDiagnoseTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DiagnoseTest.this,NewExamination.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void newDiagnose() {

        float texture_worst = Float.parseFloat(editTexture_worst.getText().toString().trim());
        float radius_se = Float.parseFloat(editRadius_se.getText().toString().trim());
        float radius_worst = Float.parseFloat(editRadius_worst.getText().toString().trim());
        float area_se = Float.parseFloat(editArea_se.getText().toString().trim());
        float area_worst = Float.parseFloat(editArea_worst.getText().toString().trim());
        float concave_points_mean = Float.parseFloat(editConcave_points_mean.getText().toString().trim());
        float concave_points_worst = Float.parseFloat(editConcave_points_worst.getText().toString().trim());
        String diagnoseTestResult = editDiagnoseTestResult.getText().toString().trim();
        if (editTexture_worst.getText().toString().trim().isEmpty()){
            editTexture_worst.setError("required field");
            editTexture_worst.requestFocus();
            return;
        }
        if (editRadius_se.getText().toString().trim().isEmpty()){
            editRadius_se.setError("required field");
            editRadius_se.requestFocus();
            return;
        }
        if (editRadius_worst.getText().toString().trim().isEmpty()){
            editRadius_worst.setError("required field");
            editRadius_worst.requestFocus();
            return;
        }
        if (editArea_se.getText().toString().trim().isEmpty()){
            editArea_se.setError("required field");
            editArea_se.requestFocus();
            return;
        }
        if (editArea_worst.getText().toString().trim().isEmpty()){
            editArea_worst.setError("required field");
            editArea_worst.requestFocus();
            return;
        }
        if (editConcave_points_mean.getText().toString().trim().isEmpty()){
            editConcave_points_mean.setError("required field");
            editConcave_points_mean.requestFocus();
            return;
        }
        if (editConcave_points_worst.getText().toString().trim().isEmpty()){
            editConcave_points_worst.setError("required field");
            editConcave_points_worst.requestFocus();
            return;
        }
        if (editDiagnoseTestResult.getText().toString().trim().isEmpty()){
            editDiagnoseTestResult.setError("required field");
            editDiagnoseTestResult.requestFocus();
            return;
        }
            dateTime= simpleDateFormat.format(calendar.getTime());

            Diagnose diagnose = new Diagnose(dateTime,texture_worst,radius_se,radius_worst,area_se,area_worst,concave_points_mean,concave_points_worst,diagnoseTestResult);
            databaseDiagnoses.child(dateTime).setValue(diagnose).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                                Toast.makeText(DiagnoseTest.this,"Success",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(DiagnoseTest.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }
}
