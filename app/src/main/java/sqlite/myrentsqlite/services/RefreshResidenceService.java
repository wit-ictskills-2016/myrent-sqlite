package sqlite.myrentsqlite.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sqlite.myrentsqlite.cloud.ResidenceCloud;
import sqlite.myrentsqlite.models.Residence;
import sqlite.myrentsqlite.providers.ResidenceContract;

/**
 * Created by jfitzgerald on 18/07/2016.
 */
public class RefreshResidenceService extends IntentService
{
  public static final String TAG = "RefreshResidenceService";
  public static final String REFRESH = "refresh residence";
  public static final String ADD_RESIDENCE = "1";
  public static final String SELECT_RESIDENCE = "2";
  public static final String DELETE_RESIDENCE = "3";
  public static final String SELECT_RESIDENCES = "4";
  public static final String DELETE_RESIDENCES = "5";
  public static final String UPDATE_RESIDENCE = "6";

  ResidenceCloud cloud = new ResidenceCloud();

  public RefreshResidenceService() {
    super("RefreshResidenceService");
  }

  /**
   * @param name Used to name the worker thread, important only for debugging.
   */
  public RefreshResidenceService(String name) {
    super(name);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    String value = intent.getStringExtra(REFRESH);
    switch (value) {

      case ADD_RESIDENCE:
        addResidence(new Residence());
        break;
    }

    return START_STICKY;
  }

  private void addResidence(Residence residence) {
    ContentValues values = new ContentValues();

    values.put(ResidenceContract.Column.UUID, residence.uuid.toString());
    values.put(ResidenceContract.Column.GEOLOCATION, residence.geolocation);
    values.put(ResidenceContract.Column.DATE, String.valueOf(residence.date.getTime()));
    values.put(ResidenceContract.Column.RENTED, residence.rented == true ? "yes" : "no");
    values.put(ResidenceContract.Column.TENANT, residence.tenant);
    values.put(ResidenceContract.Column.ZOOM, Double.toString(residence.zoom));
    values.put(ResidenceContract.Column.PHOTO, residence.photo);

    Uri uri = getContentResolver().insert(
        ResidenceContract.CONTENT_URI, values);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Log.d(TAG, "onHandleIntent invoked");
  }

}