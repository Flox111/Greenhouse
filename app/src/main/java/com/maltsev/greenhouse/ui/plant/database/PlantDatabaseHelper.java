package com.maltsev.greenhouse.ui.plant.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.maltsev.greenhouse.ui.plant.util.PlantConfig;

public class PlantDatabaseHelper extends SQLiteOpenHelper {

    private static PlantDatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = PlantConfig.DATABASE_NAME;

    private PlantDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static PlantDatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            synchronized (PlantDatabaseHelper.class) {
                if (databaseHelper == null)
                    databaseHelper = new PlantDatabaseHelper(context);
            }
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_STUDENT_TABLE = "CREATE TABLE " + PlantConfig.TABLE_PLANT + "("
                + PlantConfig.COLUMN_PLANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PlantConfig.COLUMN_PLANT_NAME + " TEXT NOT NULL, "
                + PlantConfig.COLUMN_PLANT_DESCRIPTION + " INTEGER NOT NULL, "
                + PlantConfig.COLUMN_PLANT_IMAGE_URL + " TEXT NOT NULL "
                + ")";

        String CREATE_SUBJECT_TABLE = "CREATE TABLE " + PlantConfig.TABLE_NOTE + "("
                + PlantConfig.COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PlantConfig.COLUMN_NOTE_TEXT + " TEXT NOT NULL, "
                + PlantConfig.COLUMN_NOTE_DATE + " DATE NOT NULL, "
                + PlantConfig.COLUMN_FK_PLANT_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + PlantConfig.COLUMN_FK_PLANT_ID + ") REFERENCES " + PlantConfig.TABLE_PLANT + "(" + PlantConfig.COLUMN_PLANT_ID + ") ON UPDATE CASCADE ON DELETE CASCADE "
                + ")";

        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_SUBJECT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + PlantConfig.TABLE_PLANT);
        db.execSQL("DROP TABLE IF EXISTS " + PlantConfig.TABLE_NOTE);

        // Create tables again
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }
}
