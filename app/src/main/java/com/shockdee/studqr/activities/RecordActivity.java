package com.shockdee.studqr.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.shockdee.studqr.R;
import com.shockdee.studqr.database.DatabaseHelper;
import com.shockdee.studqr.models.Student;

public class RecordActivity extends AppCompatActivity {

    public TextView tv_fName, tv_lName, tv_email, tv_grade;
    public ImageView img_record;
    public int studentID;
    public DatabaseHelper myDB;
    public FloatingActionButton float_btn_modify;
    private Student student;
    private Bundle bundle;
    private ImageButton btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        myDB = new DatabaseHelper(this);

        tv_fName = findViewById(R.id.tv_fName_record);
        tv_lName = findViewById(R.id.tv_lName_record);
        tv_email = findViewById(R.id.tv_email_record);
        tv_grade = findViewById(R.id.tv_grade_record);
        img_record = findViewById(R.id.img_record);
        float_btn_modify = findViewById(R.id.float_btn_modify);
        btn_close = findViewById(R.id.btn_close_record);

        bundle = getIntent().getExtras();

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (bundle != null){
            studentID = bundle.getInt("StudentID");

            student = myDB.searchStudent(studentID);
            tv_fName.setText(student.first_name);
            tv_lName.setText(student.last_name);
            tv_email.setText(student.email);
            tv_grade.setText(student.grade+" "+student.promotion);

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(student.toString(), BarcodeFormat.QR_CODE, 500, 500);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                img_record.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        float_btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordActivity.this, ModifActivity.class);
                intent.putExtra("modif", studentID);
                startActivity(intent);
            }
        });
    }
}
