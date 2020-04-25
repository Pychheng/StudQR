package com.shockdee.studqr.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shockdee.studqr.models.Area;
import com.shockdee.studqr.models.Grade;
import com.shockdee.studqr.models.Student;

import java.util.ArrayList;

import androidx.annotation.Nullable;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Student.db";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "Student";

    public static final String COL_ID = "ID";
    public static final String COL_FIRST_NAME = "First_Name";
    public static final String COL_LAST_NAME = "Last_Name";
    public static final String COL_EMAIL = "Email";
    public static final String COL_GRADE = "Grade";
    public static final String COL_PROMOTION = "Promotion";

    public static final String CREATE_TABLE_STUDENT = "CREATE TABLE "
            + TABLE_NAME
            + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_FIRST_NAME + " TEXT, "
            + COL_LAST_NAME + " TEXT, "
            + COL_EMAIL + " TEXT, "
            + COL_GRADE + " TEXT, "
            + COL_PROMOTION + " INTEGER"
            + ")";

    public static final String TABLE_GRADE = "Grade";
    public static final String COL_GRADE_ID = "Grade_ID";
    public static final String COL_GRADE_NAME = "Grade";
    public static final String CREATE_TABLE_GRADE = "CREATE TABLE "
            + TABLE_GRADE
            + "("
            + COL_GRADE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_GRADE_NAME + " TEXT"
            + ")";

    public static final String TABLE_AREA = "Area";
    public static final String COL_AREA_ID = "Area_ID";
    public static final String COL_AREA_NAME = "Area";
    public static final String CREATE_TABLE_AREA = "CREATE TABLE "
            + TABLE_AREA
            + "("
            + COL_AREA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_AREA_NAME + " TEXT"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_GRADE);
        db.execSQL(CREATE_TABLE_AREA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AREA);
        onCreate(db);
    }

    public boolean insertStudent(Student student) {

        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FIRST_NAME, student.first_name);
        contentValues.put(COL_LAST_NAME, student.last_name);
        contentValues.put(COL_EMAIL, student.email);
        contentValues.put(COL_GRADE, student.grade);
        contentValues.put(COL_PROMOTION, student.promotion);

        long result = myDB.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            myDB.close();
            return false;
        } else {
            myDB.close();
            return true;
        }
    }

    public Student searchStudent(int id) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.query(TABLE_NAME, new String[]{COL_ID, COL_FIRST_NAME, COL_LAST_NAME, COL_EMAIL, COL_GRADE, COL_PROMOTION},
                COL_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Student student = null;
                if (cursor.moveToFirst()){
                    do {
                        student = new Student();
                        student.studentID = cursor.getInt(cursor.getColumnIndex(COL_ID));
                        student.first_name = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME));
                        student.last_name = cursor.getString(cursor.getColumnIndex(COL_LAST_NAME));
                        student.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL));
                        student.grade = cursor.getString(cursor.getColumnIndex(COL_GRADE));
                        student.promotion = cursor.getInt(cursor.getColumnIndex(COL_PROMOTION));
                    }while (cursor.moveToNext());
                }
                myDB.close();
                return student;

    }

    public boolean delStudent(int idStudent) {

        SQLiteDatabase myDB = this.getWritableDatabase();

        int result = myDB.delete(TABLE_NAME, COL_ID + " =?", new String[]{String.valueOf(idStudent)});
        if (result > 0) {
            myDB.close();
            return true;
        } else {
            myDB.close();
            return false;
        }

    }

    public void updateStudent(int upId, String upFirstName, String upLastName, String upEmail, String upGrade, int upPromotion) {
        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FIRST_NAME, upFirstName);
        contentValues.put(COL_LAST_NAME, upLastName);
        contentValues.put(COL_EMAIL, upEmail);
        contentValues.put(COL_GRADE, upGrade);
        contentValues.put(COL_PROMOTION, upPromotion);

        myDB.update(TABLE_NAME, contentValues, COL_ID + " = ?", new String[]{String.valueOf(upId)});
        myDB.close();
    }

    public ArrayList<Student> selectAllStudent() {
        ArrayList<Student> studentArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.studentID = cursor.getInt(cursor.getColumnIndex(COL_ID));
                student.first_name = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME));
                student.last_name = cursor.getString(cursor.getColumnIndex(COL_LAST_NAME));
                student.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL));
                student.grade = cursor.getString(cursor.getColumnIndex(COL_GRADE));
                student.promotion = cursor.getInt(cursor.getColumnIndex(COL_PROMOTION));

                studentArrayList.add(student);
            }
            while (cursor.moveToNext());
        }
        myDB.close();
        return studentArrayList;
    }

    /**
     * #########################################Add method##########################################
     */
    public boolean addGrade(Grade grade) {
        SQLiteDatabase myGradeDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_GRADE_NAME, grade.grade_name);

        long result = myGradeDB.insert(TABLE_GRADE, null, contentValues);

        if (result == -1) {
            myGradeDB.close();
            return false;
        } else {
            myGradeDB.close();
            return true;
        }
    }

    public boolean addArea(Area area) {
        SQLiteDatabase myAreaDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_AREA_NAME, area.area_name);

        long result = myAreaDB.insert(TABLE_AREA, null, contentValues);
        if (result == -1) {
            myAreaDB.close();
            return false;
        } else {
            myAreaDB.close();
            return true;
        }
    }

    /**
     * #######################################Retrieve method#######################################
     */
    public ArrayList<Grade> selectAllGrade() {
        SQLiteDatabase myGradeDB = this.getReadableDatabase();

        ArrayList<Grade> gradeArrayList = new ArrayList<>();
        String selectGradeFromeDB = "SELECT * FROM " + TABLE_GRADE;
        Cursor cursor = myGradeDB.rawQuery(selectGradeFromeDB, null);
        if (cursor.moveToFirst()) {
            do {
                Grade grade = new Grade();
                //grade.grade_ID = cursor.getInt(cursor.getColumnIndex(COL_GRADE_ID));
                grade.grade_name = cursor.getString(cursor.getColumnIndex(COL_GRADE_NAME));

                gradeArrayList.add(grade);
            } while (cursor.moveToNext());

        }
        cursor.close();
        myGradeDB.close();
        return gradeArrayList;
    }

    ;

    public ArrayList<Area> selectAllArea() {
        SQLiteDatabase myAreaDB = this.getReadableDatabase();
        ArrayList<Area> areaArrayList = new ArrayList<>();
        String selectAreaFromDB = "SELECT * FROM " + TABLE_AREA;
        Cursor cursor = myAreaDB.rawQuery(selectAreaFromDB, null);
        if (cursor.moveToFirst()) {
            do {
                Area area = new Area();
                area.area_name = cursor.getString(cursor.getColumnIndex(COL_AREA_NAME));

                areaArrayList.add(area);
            } while (cursor.moveToNext());
        }
        cursor.close();
        myAreaDB.close();
        return areaArrayList;
    }

    ;

    /**
     * ########################################Update method########################################
     */
    public void updateGrade(Grade grade) {
        SQLiteDatabase myGradeDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_GRADE_ID, grade.grade_ID);
        contentValues.put(COL_GRADE_NAME, grade.grade_name);

        myGradeDB.update(TABLE_GRADE, contentValues, COL_GRADE_ID + " = ?", new String[]{String.valueOf(grade.grade_ID)});
        myGradeDB.close();
    }

    public void updateArea(int areaID, String areaName) {
        SQLiteDatabase myAreaDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_AREA_ID, areaID);
        contentValues.put(COL_AREA_NAME, areaName);

        myAreaDB.update(TABLE_AREA, contentValues, COL_AREA_ID + " = ?", new String[]{String.valueOf(areaID)});
        myAreaDB.close();
    }

    /**
     * #######################################Search method#########################################
     */
    public Grade searchGrade(int id){
        SQLiteDatabase myGradeDB = this.getReadableDatabase();
        Cursor cursor = myGradeDB.query(TABLE_GRADE, new String[]{COL_GRADE_ID, COL_GRADE_NAME},
                COL_GRADE_ID+" = ?",
                new String[]{String.valueOf(id)}, null, null,null, null);
        Grade grade = new Grade();
        if (cursor.moveToFirst()){
            do {
                grade.grade_ID = cursor.getInt(cursor.getColumnIndex(COL_GRADE_ID));
                grade.grade_name = cursor.getString(cursor.getColumnIndex(COL_GRADE_NAME));
            }while (cursor.moveToNext());
        }
        myGradeDB.close();
        cursor.close();
        return grade;
    }
    public Area searchArea(int id) {
        SQLiteDatabase myAreaDB = this.getWritableDatabase();
        Cursor cursor = myAreaDB.query(TABLE_AREA, new String[]{COL_AREA_ID, COL_AREA_NAME},
                COL_AREA_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Area area = new Area();
        if (cursor.moveToFirst()) {

            do {
                area.area_ID = cursor.getInt(cursor.getColumnIndex(COL_AREA_ID));
                area.area_name = cursor.getString(cursor.getColumnIndex(COL_AREA_NAME));
            } while (cursor.moveToNext());

        }
        myAreaDB.close();
        cursor.close();
        return area;

    }

    /**
     * #######################################Delete method#########################################
     */

    public boolean delGrade(int gradeID) {

        SQLiteDatabase myGradeDB = this.getWritableDatabase();
        int result = myGradeDB.delete(TABLE_GRADE, COL_GRADE_ID + " = ?", new String[]{String.valueOf(gradeID)});
        if (result > 0) {
            myGradeDB.close();
            return true;
        } else {
            myGradeDB.close();
            return false;
        }
    }

    public boolean delAreaFromDB(int areaIdFromDB) {
        SQLiteDatabase myAreaDB = this.getWritableDatabase();
        int result = myAreaDB.delete(TABLE_AREA, COL_AREA_ID + " =?", new String[]{String.valueOf(areaIdFromDB)});
        Log.d("TROLL", "delAreaFromDB: "+TABLE_AREA+COL_AREA_ID+areaIdFromDB);
        if (result >0) {
            myAreaDB.close();
            return true;
        } else {
            myAreaDB.close();
            return false;
        }
    }
}
