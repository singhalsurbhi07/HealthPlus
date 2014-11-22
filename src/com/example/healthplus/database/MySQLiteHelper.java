package com.example.healthplus.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "HealthDB.db";
	public static final String TABLE_NAME = "WATER";
	public static String COLUMN_ID = "DATE";
	public static final String COLUMN_AMOUNT = "AMOUNT";
	//public static final String column_date = dateToSqliteDateString(Calendar.DATE); 


	private static final int amount = 0;
	private static final String TABLE_INSERT = "INSERT INTO " + TABLE_NAME + "(Date, Amount) Values( " 
			+ Calendar.DATE + amount + ");" ;

	private static final String TBL_FOOD = "FOOD";
	private static final String TBL_SLEEP = "SLEEP";
	private static final String TBL_ACTIVITIES = "ACTIVITIES";

	private static final String TABLE_WATER_CREATE = "CREATE TABLE  IF NOT EXISTS " + TABLE_NAME 
			+ " ( " + COLUMN_ID + " TEXT PRIMARY KEY NOT NULL, " 
			+  " AMOUNT INTEGER DEFAULT 0 );"	;

	private static final String TABLE_FOOD_CREATE = "CREATE TABLE IF NOT EXISTS " + TBL_FOOD 
			+ " (" + COLUMN_ID + " TEXT PRIMARY KEY NOT NULL,  "
			+ " CALORIES REAL DEFAULT 0.0, "
			+ " CARBS REAL DEFAULT 0.0, "
			+ " FATS REAL DEFAULT 0.0, "
			+ " FIBER REAL DEFAULT 0.0, "
			+ " PROTEIN REAL DEFAULT 0.0, "
			+ " SODIUM REAL DEFAULT 0.0 );";

	private static final String TABLE_SLEEP_CREATE = "CREATE TABLE  IF NOT EXISTS " + TBL_SLEEP
			+ " (" + COLUMN_ID + " TEXT PRIMARY KEY NOT NULL, "
			+ " TOTAL_SLEEP_RECORD REAL DEFAULT 0.0, "
			+ " TOTAL_MINUTES_ASLEEP REAL DEFAULT 0.0, "
			+ " MINUTES_AWAKE REAL DEFAULT 0.0 ); ";

	private static final String TABLE_ACTIVITIES_CREATE = "CREATE TABLE  IF NOT EXISTS " + TBL_ACTIVITIES
			+ " ( " + COLUMN_ID + " TEXT PRIMARY KEY NOT NULL , "
			+ " DISTANCES REAL DEFAULT 0.0," 
			+ " ACTIVITY_CALORIES REAL DEFAULT 0.0 ); ";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_WATER_CREATE);
		db.execSQL(TABLE_FOOD_CREATE);
		db.execSQL(TABLE_SLEEP_CREATE);
		db.execSQL(TABLE_ACTIVITIES_CREATE);
		db.execSQL(TABLE_INSERT);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATER_CREATE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_CREATE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLEEP_CREATE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES_CREATE);

		onCreate(db);
	}

	// FITBIT DATE FORMAT = 2010-02-25.json.
	public static String dateToSqliteDateString(String date) {
		// The format is the same as CURRENT_TIMESTAMP: "YYYY-MM-DD"
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		DateFormat.getDateInstance();
		return sdf.format(date);
	}

}
