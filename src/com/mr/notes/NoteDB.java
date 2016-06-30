package com.mr.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDB extends SQLiteOpenHelper {
	public static final String TABLE_NAME = "NOTE";
	public static final String ID = "_ID";
	public static final String CONTENT = "CONTENT";
	public static final String PATH = "PATH";
	public static final String VIDEO = "VIDEO";
	public static final String TIME = "TIME";

	public NoteDB(Context context) {
		super(context, "notes", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID
//				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTENT
//				+ " TEXT NOT NULL," + PATH + " TEXT NOT NULL," + VIDEO
//				+ " TEXT NOT NULL," + TIME + " TEXT NOT NULL)");
		db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTENT
				+ " TEXT NOT NULL," + PATH + " TEXT," + VIDEO
				+ " TEXT," + TIME + " TEXT NOT NULL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
