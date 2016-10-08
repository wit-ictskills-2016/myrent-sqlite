package sqlite.myrentsqlite.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
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


  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
    db.execSQL("drop table if exists " + TABLE_RESIDENCES);
    Log.d(TAG, "onUpdated");
    onCreate(db);
  }
}