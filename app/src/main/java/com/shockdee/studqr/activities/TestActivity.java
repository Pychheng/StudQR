package com.shockdee.studqr.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shockdee.studqr.R;
import com.shockdee.studqr.adapters.AreaAdapter;
import com.shockdee.studqr.database.DatabaseHelper;
import com.shockdee.studqr.models.Area;
import com.shockdee.studqr.utilities.Utilities;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    public ArrayList<Area> areaTabList;
    public SearchView svOptionTab;
    public RecyclerView rvOptionTab;
    public RecyclerView.LayoutManager layoutManager;
    public EditText edt_save_test;
    public Button btn_save_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        edt_save_test = findViewById(R.id.edt_save_test);
        btn_save_test = findViewById(R.id.btn_save_test);
        rvOptionTab = findViewById(R.id.rv_test);
        areaTabList = Utilities.initAreaFromDB(this);
        layoutManager = new LinearLayoutManager(this);
        rvOptionTab.setLayoutManager(layoutManager);

        final AreaAdapter areaAdapter = new AreaAdapter(this, areaTabList);
        rvOptionTab.setAdapter(areaAdapter);

        btn_save_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper mySettingsDB = new DatabaseHelper(TestActivity.this);
                String areaTxt = edt_save_test.getText().toString();

                if (areaTxt.length() > 0 && areaTxt != null) {
                    try {
                        Area area = new Area(areaTxt);
                        boolean isInsertedArea = mySettingsDB.addArea(area);
                        if (isInsertedArea == true) {
                            Toast.makeText(TestActivity.this, "Data inserted " + isInsertedArea, Toast.LENGTH_SHORT).show();
                            areaTabList.add(area);
                            areaAdapter.notifyItemInserted(area.area_ID);
                            edt_save_test.setText("");
                        } else {
                            Toast.makeText(TestActivity.this, "Data not inserted " + isInsertedArea, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
