package com.shockdee.studqr.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.shockdee.studqr.R;
import com.shockdee.studqr.adapters.PageAdapter;
import com.shockdee.studqr.database.DatabaseHelper;
import com.shockdee.studqr.models.Area;

public class ParametersActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DatabaseHelper mySettingsDB;
    private PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);

        tabLayout = findViewById(R.id.parameters_tabs);
        viewPager = findViewById(R.id.viewPager_parameters);

        mySettingsDB = new DatabaseHelper(this);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pageAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    public void addSettingsTab(View view) {
        switch (view.getId()) {
            case R.id.btn_add_tab_area:
                addArea();
                break;
            case R.id.btn_add_tab_grade:
                finish();
                break;
        }
    }

    /**
     * *********************************************************************************************
     */
    public void addArea() {
        final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Adding area of study");
        View view = LayoutInflater.from(this).inflate(R.layout.add_settings, null);
        final EditText edt_add_settings = view.findViewById(R.id.edt_add_settings);
        builder.setView(view);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String areaTxt = edt_add_settings.getText().toString();

                if (areaTxt.length() > 0 && areaTxt != null) {
                    try {
                        Area area = new Area(areaTxt);
                        boolean isInsertedArea = mySettingsDB.addArea(area);
                        if (isInsertedArea == true) {
                            Toast.makeText(ParametersActivity.this, "Data inserted " + isInsertedArea, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ParametersActivity.this, "Data not inserted " + isInsertedArea, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
