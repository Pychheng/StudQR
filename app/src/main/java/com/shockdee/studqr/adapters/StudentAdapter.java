package com.shockdee.studqr.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.shockdee.studqr.R;
import com.shockdee.studqr.activities.RecordActivity;
import com.shockdee.studqr.database.DatabaseHelper;
import com.shockdee.studqr.models.Student;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    public Context context;
    public ArrayList<Student> studentArrayList;

    public StudentAdapter(Context context, ArrayList<Student> studentArrayList) {
        this.context = context;
        this.studentArrayList = studentArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fName_row;
        public TextView lName_row;
        public ImageView img_row;
        public int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(onClickListener);
            itemView.setOnLongClickListener(onLongClickListener);
            itemView.setTag(this);

            fName_row = itemView.findViewById(R.id.tv_fName_row);
            lName_row = itemView.findViewById(R.id.tv_lName_row);
            img_row = itemView.findViewById(R.id.img_row);

        }
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.student_row, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        Student student = studentArrayList.get(position);
        String text = student.toString();
        holder.fName_row.setText(student.first_name);
        holder.lName_row.setText(student.last_name);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            holder.img_row.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        holder.position = position;

    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewHolder viewHolder = (ViewHolder) v.getTag();
            final int position = viewHolder.position;
            Intent intent = new Intent(context, RecordActivity.class);
            intent.putExtra("StudentID", studentArrayList.get(position).studentID);
            context.startActivity(intent);
        }
    };

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            ViewHolder viewHolder = (ViewHolder) v.getTag();
            final int position = viewHolder.position;
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final Student student = studentArrayList.get(position);
            builder.setMessage("Do you want to delete "+student.first_name+" "+student.last_name+" from the database ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseHelper myDB = new DatabaseHelper(context);
                    myDB.delStudent(student.studentID);

                    studentArrayList.remove(position);
                    notifyDataSetChanged();
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, studentArrayList.size());

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();

            return false;
        }
    };

    public void setFilter(ArrayList<Student> newList){
        studentArrayList = new ArrayList<>();
        studentArrayList.addAll(newList);
        notifyDataSetChanged();
    }
}
