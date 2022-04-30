package com.maltsev.greenhouse.ui.plant.util;

public class PlantConfig {
    public static final String DATABASE_NAME = "plant-db";

    //column names of plant table
    public static final String TABLE_PLANT = "plant";
    public static final String COLUMN_PLANT_ID = "_id";
    public static final String COLUMN_PLANT_NAME = "name";
    public static final String COLUMN_PLANT_DESCRIPTION = "description";
    public static final String COLUMN_PLANT_IMAGE_URL = "image_url";

    //column names of notes table
    public static final String TABLE_NOTE = "note";
    public static final String COLUMN_NOTE_ID = "_id";
    public static final String COLUMN_NOTE_TEXT = "text";
    public static final String COLUMN_NOTE_DATE = "date";
    public static final String COLUMN_FK_PLANT_ID = "fk_plant_id";
    public static final String PLANT_SUB_CONSTRAINT = "plant_sub_unique";

}
