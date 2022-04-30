package com.maltsev.greenhouse.ui.plant.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.maltsev.greenhouse.ui.greenhouse.model.Note;
import com.maltsev.greenhouse.ui.plant.model.Plant;
import com.maltsev.greenhouse.ui.plant.util.PlantConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class PlantDatabaseQueryClass {
    private Context context;

    public PlantDatabaseQueryClass(Context context){
        this.context = context;
    }

    public long insertPlant(Plant plant){

        long id = -1;
        PlantDatabaseHelper databaseHelper = PlantDatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PlantConfig.COLUMN_PLANT_NAME, plant.getName());
        contentValues.put(PlantConfig.COLUMN_PLANT_DESCRIPTION, plant.getDescription());
        contentValues.put(PlantConfig.COLUMN_PLANT_IMAGE_URL, plant.getImageUrl());

        try {
            id = sqLiteDatabase.insertOrThrow(PlantConfig.TABLE_PLANT, null, contentValues);
        } catch (SQLiteException e){
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Plant> getAllPlant(){

        PlantDatabaseHelper databaseHelper = PlantDatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(PlantConfig.TABLE_PLANT, null, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Plant> plantList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow(PlantConfig.COLUMN_PLANT_ID));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(PlantConfig.COLUMN_PLANT_NAME));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow(PlantConfig.COLUMN_PLANT_DESCRIPTION));
                        String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(PlantConfig.COLUMN_PLANT_IMAGE_URL));

                        plantList.add(new Plant(id, name, description, imageUrl));
                    }   while (cursor.moveToNext());

                    return plantList;
                }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public long deletePlantById(long id) {
        long deletedRowCount = -1;
        PlantDatabaseHelper databaseHelper = PlantDatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(PlantConfig.TABLE_PLANT,
                    PlantConfig.COLUMN_PLANT_ID + " = ? ",
                    new String[]{ String.valueOf(id)});
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }

    public long insertNote(Note note, long plantId){
        long rowId = -1;
        PlantDatabaseHelper databaseHelper = PlantDatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PlantConfig.COLUMN_NOTE_TEXT, note.getText());
        contentValues.put(PlantConfig.COLUMN_NOTE_DATE, note.getDate());
        contentValues.put(PlantConfig.COLUMN_FK_PLANT_ID, plantId);

        try {
            rowId = sqLiteDatabase.insertOrThrow(PlantConfig.TABLE_NOTE, null, contentValues);
        } catch (SQLiteException e){
            Log.d("", e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowId;
    }

    public List<Note> getAllNoteByPlantId(long plantId){
        PlantDatabaseHelper databaseHelper = PlantDatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Note> noteList = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(PlantConfig.TABLE_NOTE,
                    new String[] {PlantConfig.COLUMN_NOTE_ID, PlantConfig.COLUMN_NOTE_DATE, PlantConfig.COLUMN_NOTE_TEXT},
                    PlantConfig.COLUMN_FK_PLANT_ID + " = ? ",
                    new String[] {String.valueOf(plantId)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                noteList = new ArrayList<>();
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(PlantConfig.COLUMN_NOTE_ID));
                    String noteText = cursor.getString(cursor.getColumnIndexOrThrow(PlantConfig.COLUMN_NOTE_TEXT));
                    String noteDate = cursor.getString(cursor.getColumnIndexOrThrow(PlantConfig.COLUMN_NOTE_DATE));

                    noteList.add(new Note(id, noteDate, noteText));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return noteList;
    }
}
