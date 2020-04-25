package com.shockdee.studqr.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shockdee.studqr.R;
import com.shockdee.studqr.adapters.StudentAdapter;
import com.shockdee.studqr.models.Student;
import com.shockdee.studqr.utilities.Utilities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public RecyclerView rvStudent;
    public RecyclerView.LayoutManager layoutManager;
    public StudentAdapter adapter;
    public ArrayList<Student> studentList;
    public Context context;
    public SearchView svStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        rvStudent = findViewById(R.id.rv_student);
        svStudent = findViewById(R.id.sv_student);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SaveActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        studentList = Utilities.initStudentFromDB(this);
        layoutManager = new LinearLayoutManager(this);
        rvStudent.setLayoutManager(layoutManager);
        adapter = new StudentAdapter(this, studentList);
        rvStudent.setAdapter(adapter);

        svStudent.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<Student> newList = new ArrayList<>();
                for (Student student : studentList){
                    String first_name = student.first_name.toLowerCase();
                    String last_name = student.last_name.toLowerCase();
                    if (first_name.contains(newText)||last_name.contains(newText)){
                        newList.add(student);
                    }
                }
                adapter.setFilter(newList);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, TestActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
