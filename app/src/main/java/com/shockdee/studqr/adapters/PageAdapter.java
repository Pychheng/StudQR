package com.shockdee.studqr.adapters;

import android.app.AlertDialog;
import android.view.View;

import com.shockdee.studqr.R;
import com.shockdee.studqr.utilities.TabGrade;
import com.shockdee.studqr.utilities.TabOption;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int nbOfTabs;

    public PageAdapter(@NonNull FragmentManager fm, int nbOfTabs) {
        super(fm);
        this.nbOfTabs = nbOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TabGrade tabGrade = new TabGrade();
                return tabGrade;
            case 1:
                TabOption tabOption = new TabOption();
                return tabOption;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return nbOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }


}
