package com.shockdee.studqr.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shockdee.studqr.R;
import com.shockdee.studqr.database.DatabaseHelper;
import com.shockdee.studqr.models.Student;
import com.shockdee.studqr.utilities.Utilities;

public class ModifActivity extends AppCompatActivity {

    private int width, height;
    private EditText edt_first_Name, edt_last_name, edt_email, edt_grade, edt_promotion;
    private String fName, lName, email, grade;
    private int promotion = 0;
    private Button btn_modif;
    private TextView empty_name, wrong_date;
    private Student student;
    private int studentID;
    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif);

        display();

        myDB = new DatabaseHelper(this);

        edt_first_Name = findViewById(R.id.edt_first_name_modif);
        edt_last_name = findViewById(R.id.edt_last_name_modif);
        edt_email = findViewById(R.id.edt_email_modif);
        edt_grade = findViewById(R.id.edt_grade_modif);
        edt_promotion = findViewById(R.id.edt_promotion_modif);
        btn_modif = findViewById(R.id.btn_modif);
        empty_name = findViewById(R.id.tv_empty_name_modif);
        wrong_date = findViewById(R.id.tv_wrong_date_modif);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            studentID = bundle.getInt("modif");
            student = myDB.searchStudent(studentID);
            edt_first_Name.setText(student.first_name);
            edt_last_name.setText(student.last_name);
            edt_email.setText(student.email);
            edt_grade.setText(student.grade);
            edt_promotion.setText(String.valueOf(student.promotion));
        }
        btn_modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modif();
            }
        });

    }

    public void modif() {
        fName = edt_first_Name.getText().toString().trim();
        lName = edt_last_name.getText().toString().trim();
        email = edt_email.getText().toString().trim();
        grade = edt_grade.getText().toString().trim();
        try {
            promotion = Integer.parseInt(edt_promotion.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (fName.length()>0 && lName.length()>0){
            if (promotion>1900 && promotion<2100){
                try {
                    myDB.updateStudent(studentID, fName, lName, email, grade, promotion);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                }
            }else {
                empty_name.setVisibility(View.INVISIBLE);
                wrong_date.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }else {
            empty_name.setVisibility(View.VISIBLE);
            wrong_date.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }


    }

    public void display() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.7));

    }

}
