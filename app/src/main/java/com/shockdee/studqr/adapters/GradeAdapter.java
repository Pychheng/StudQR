package com.shockdee.studqr.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.shockdee.studqr.R;
import com.shockdee.studqr.database.DatabaseHelper;
import com.shockdee.studqr.models.Grade;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {

    public Context context;
    public ArrayList<Grade> gradeArrayList;

    public GradeAdapter(Context context, ArrayList<Grade> gradeArrayList) {
        this.context = context;
        this.gradeArrayList = gradeArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_setttings;
        public int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnLongClickListener(onLongClickListener);
            itemView.setTag(this);
            txt_setttings = itemView.findViewById(R.id.settings_row);
        }
    }

    @NonNull
    @Override
    public GradeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.settings_row, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull GradeAdapter.ViewHolder holder, int position) {
        Grade grade = gradeArrayList.get(position);
        holder.txt_setttings.setText(grade.grade_name);
        holder.position = position;
        Log.d("TROLL", "onBindViewHolder Grade : "+grade.grade_ID+grade.grade_name);
    }

    @Override
    public int getItemCount() {
        return gradeArrayList.size();
    }

    public void setGradeFilter(ArrayList<Grade> newList) {
        gradeArrayList = new ArrayList<>();
        gradeArrayList.addAll(newList);
        notifyDataSetChanged();
    }

    public View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            ViewHolder viewHolder = (ViewHolder) v.getTag();
            View view = LayoutInflater.from(context).inflate(R.layout.add_settings, null);
            final EditText edt_settings = view.findViewById(R.id.edt_add_settings);
            final DatabaseHelper mySettingDB = new DatabaseHelper(context);
            final int position = viewHolder.position;
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final Grade grade = gradeArrayList.get(position);
            builder.setMessage("What do you want to do?");
            edt_settings.setText(grade.grade_name);
            builder.setView(view);
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    mySettingDB.delGrade(grade.grade_ID);
                    gradeArrayList.remove(position);
                    notifyDataSetChanged();
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, gradeArrayList.size());
                }
            });
            builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    final String update_grade = edt_settings.getText().toString();
                    if (update_grade.length() > 0 && update_grade != null) {
                        try {
                            mySettingDB.updateGrade(grade);
                            notifyDataSetChanged();
                            notifyItemChanged(position);
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
            return false;

        }
    };
}
