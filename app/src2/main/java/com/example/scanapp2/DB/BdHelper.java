package com.example.scanapp2.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BdHelper extends SQLiteOpenHelper {
    private static final String DB_NAME= "qr";
    private static final int DB_SCHEMA_VERSION= 1;

    public BdHelper(Context context)
    {

        super(context, DB_NAME, null, DB_SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DbManager.TABLA_PRODUCTOS_CREATE);
        sqLiteDatabase.execSQL(DbManager.TABLA_PROMOCIONES_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS productos");
        db.execSQL("DROP TABLE IF EXISTS promociones");
        onCreate(db);
    }
}
