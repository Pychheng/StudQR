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

public class SaveActivity extends AppCompatActivity {

    public int height, width;
    public EditText edt_first_Name, edt_last_name, edt_email, edt_grade, edt_promotion;
    public String fName, lName, email, grade;
    public int promotion = 0;
    public Button btn_save;
    public TextView empty_name, wrong_date;
    public DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        display();
        myDB = new DatabaseHelper(this);

        edt_first_Name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_email = findViewById(R.id.edt_email);
        edt_grade = findViewById(R.id.edt_grade);
        edt_promotion = findViewById(R.id.edt_promotion);
        btn_save = findViewById(R.id.btn_save);
        empty_name = findViewById(R.id.tv_empty_name);
        wrong_date = findViewById(R.id.tv_wrong_date);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

    }

    public void addStudent() {
        fName = edt_first_Name.getText().toString().trim();
        lName = edt_last_name.getText().toString().trim();
        email = edt_email.getText().toString().trim();
        grade = edt_grade.getText().toString().trim();
        try {
            promotion = Integer.parseInt(edt_promotion.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        if (fName.length() > 0 && lName.length() > 0) {


            if ((promotion > 1900 && promotion < 2100)|| promotion == 0) {


                try {
                    Student student = new Student(fName, lName, email, grade, promotion);

                    boolean result = myDB.insertStudent(student);
                    if (result == true) {
                        Toast.makeText(SaveActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(SaveActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                empty_name.setVisibility(View.INVISIBLE);
                wrong_date.setVisibility(View.VISIBLE);
            }
        } else {
            empty_name.setVisibility(View.VISIBLE);
            wrong_date.setVisibility(View.INVISIBLE);
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
