package com.shockdee.studqr.utilities;

import android.content.Context;
import android.util.Log;

import com.shockdee.studqr.database.DatabaseHelper;
import com.shockdee.studqr.models.Area;
import com.shockdee.studqr.models.Grade;
import com.shockdee.studqr.models.Student;

import java.util.ArrayList;

public class Utilities {

    public static ArrayList<Student> initStudentFromDB(Context context){
        DatabaseHelper myDB = new DatabaseHelper(context);
        return myDB.selectAllStudent();
    }

    public static void delStudent(Context context, int id){
        DatabaseHelper myDB = new DatabaseHelper(context);
        myDB.delStudent(id);
    }

    /**
     * =============================================================================================
     */

    public static ArrayList<Grade> initGradeFromDB(Context context){
        DatabaseHelper myGradeFromDB = new DatabaseHelper(context);
        return myGradeFromDB.selectAllGrade();
    }

    public static ArrayList<Area> initAreaFromDB(Context context){
        DatabaseHelper myAreaFromDB = new DatabaseHelper(context);
        return myAreaFromDB.selectAllArea();
    }


    public static boolean delateArea(Context context, int areaID){
        DatabaseHelper myAreaDB = new DatabaseHelper(context);
        boolean result = myAreaDB.delAreaFromDB(areaID);
        Log.d("TROLL", "delateArea: "+result);

        return true;
    }
}
