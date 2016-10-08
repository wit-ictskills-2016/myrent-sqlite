package sqlite.myrentsqlite.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sqlite.myrentsqlite.models.Residence;

public class DbHelper extends SQLiteOpenHelper
{
  static final String TAG = "DbHelper";
  static final String DATABASE_NAME = "residences.db";
  static final int DATABASE_VERSION = 1;
  static final String TABLE_RESIDENCES = "tableResidences";

  static final String PRIMARY_KEY = "id";
  static final String GEOLOCATION = "geolocation";

  Context context;

  public DbHelper(Context context)
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
  }

  @Override
  public void onCreate(SQLiteDatabase db)
  {
    String createTable =
        "CREATE TABLE tableResidences " +
            "(id text primary key, " +
            "geolocation text)";

    db.execSQL(createTable);
    Log.d(TAG, "DbHelper.onCreated: " + createTable);
  }

  /**
   * @param residence Reference to Residence object to be added to database
   */
  public void addResidence(Residence residence)
  {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(PRIMARY_KEY, residence.id.toString());
    values.put(GEOLOCATION, residence.geolocation);
    // Insert record
    db.insert(TABLE_RESIDENCES, null, values);
    db.close();
  }

  public Residence selectResidence(UUID resId) {
    Residence residence;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = null;

    try {
      residence = new Residence();

      cursor = db.rawQuery("SELECT * FROM tableResidences WHERE id = ?", new String[]{resId.toString() + ""});

      if (cursor.getCount() > 0) {
        int columnIndex = 0;
        cursor.moveToFirst();
        residence.id = UUID.fromString(cursor.getString(columnIndex++));
        residence.geolocation = cursor.getString(columnIndex++);
      }
    } finally {
      cursor.close();
    }
    return residence;
  }

  public void deleteResidence(Residence residence) {
    SQLiteDatabase db = this.getWritableDatabase();
    try {
      db.delete("tableResidences", "id" + "=?", new String[]{residence.id.toString() + ""});
    } catch (Exception e) {
      Log.d(TAG, "delete residence failure: " + e.getMessage());
    }
  }

  /**
   * Query database and select entire tableResidences.
   *
   * @return A list of Residence object records
   */
  public List<Residence> selectResidences() {
    List<Residence> residences = new ArrayList<Residence>();
    String query = "SELECT * FROM " + "tableResidences";
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      int columnIndex = 0;
      do {
        Residence residence = new Residence();
        residence.id = UUID.fromString(cursor.getString(columnIndex++));
        residence.geolocation = cursor.getString(columnIndex++);

        columnIndex = 0;

        residences.add(residence);
      } while (cursor.moveToNext());
    }
    cursor.close();
    return residences;
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
    db.execSQL("drop table if exists " + TABLE_RESIDENCES);
    Log.d(TAG, "onUpdated");
    onCreate(db);
  }
}