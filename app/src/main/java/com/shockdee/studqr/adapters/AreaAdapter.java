package com.shockdee.studqr.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shockdee.studqr.R;
import com.shockdee.studqr.database.DatabaseHelper;
import com.shockdee.studqr.models.Area;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {

    public Context context;
    public ArrayList<Area> areaArrayList;

    public AreaAdapter(Context context, ArrayList<Area> areaArrayList) {
        this.context = context;
        this.areaArrayList = areaArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_area;
        public ImageButton btn_del_area;
        public int position_area;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(onLongClickListener);
            itemView.setTag(this);

            txt_area = itemView.findViewById(R.id.txt_area_row);
            btn_del_area = itemView.findViewById(R.id.btn_del_area);

            btn_del_area.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Area area = areaArrayList.get(position_area);

                    Log.d("TROLL", "onClick: "+area.area_ID);
                    DatabaseHelper myAreaDB = new DatabaseHelper(context);
                    //Area option = myAreaDB.searchArea(area.area_ID);
                    //Log.d("TROLL", "onClick: Option "+option.area_name);
                    boolean isDelArea = myAreaDB.delAreaFromDB(area.area_ID);
                    Log.d("TROLL", "onClick: "+isDelArea+area.area_ID+ position_area);
                    if (isDelArea == true){
                        try {
                            Log.d("TROLL", "onClick: "+isDelArea);
                            //areaArrayList.clear();
                            //areaArrayList.addAll(Utilities.initAreaFromDB(context));
                            areaArrayList.remove(position_area);
                            notifyDataSetChanged();
                            notifyItemRemoved(position_area);
                            notifyItemRangeChanged(position_area, areaArrayList.size());

                            Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.area_row, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Area area = areaArrayList.get(position);
        holder.txt_area.setText(area.area_name);
        holder.position_area = position;
        Log.d("TROLL", "onBindViewHolder: "+position+area.area_ID+area.area_name);

    }

    @Override
    public int getItemCount() {
        return areaArrayList.size();
    }

    public void setAreaFilter(ArrayList<Area> newList) {
        areaArrayList = new ArrayList<>();
        areaArrayList.addAll(newList);
        notifyDataSetChanged();
    }

    public View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ViewHolder viewHolder = (ViewHolder) v.getTag();
            //View view = LayoutInflater.from(context).inflate(R.layout.add_settings, null);
            final int position = viewHolder.position_area;
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            //final EditText edt_area = view.findViewById(R.id.edt_add_settings);
            final Area area = areaArrayList.get(position);
            Log.d("TROLL", "onLongClick: "+area.area_ID);
            //edt_area.setText(area.area_name);
            //builder.setView(view);
            builder.setMessage("What do you whant to do?");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        DatabaseHelper myAreaDB = new DatabaseHelper(context);
                        //Area area = areaArrayList.get(position);
                        boolean isDelArea = myAreaDB.delAreaFromDB(area.area_ID);
                        Log.d("TROLL", "onClick: "+isDelArea+area.area_ID+position);
                        //myAreaDB.getWritableDatabase();
                        //myAreaDB.delAreaFromDB(area.area_ID);
                        if (isDelArea == true){
                            areaArrayList.remove(position);
                            notifyDataSetChanged();
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, areaArrayList.size());

                            Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Error isDelArea = "+isDelArea, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
            /*builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final int idArea = area.area_ID;
                    final String txt_area = edt_area.getText().toString();
                    if (txt_area.length() > 0 && txt_area != null) {
                        try {
                            SettingsDatabase myAreaDB = new SettingsDatabase(context);
                            myAreaDB.updateArea(idArea, txt_area);
                            notifyDataSetChanged();
                            notifyItemChanged(position_area);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            });*/
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
