package sqlite.myrentsqlite.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by jfitzgerald on 18/07/2016.
 * Adapted from code in Learning Android Edition 2
 * Authors: Gargenta & Nakamura
 */
public class ResidenceProvider extends ContentProvider
{
  private static final String TAG = ResidenceProvider.class.getSimpleName();
  private DbHelper dbHelper;

  private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    uriMatcher.addURI(ResidenceContract.AUTHORITY, ResidenceContract.TABLE_RESIDENCES, ResidenceContract.RESIDENCE_DIR);
    uriMatcher.addURI(ResidenceContract.AUTHORITY, ResidenceContract.TABLE_RESIDENCES + "/#", ResidenceContract.RESIDENCE_ITEM);
  }

  @Override
  public boolean onCreate() {

    dbHelper = new DbHelper(getContext());
    Log.d(TAG, "onCreated");
    return true;
  }

  @Nullable
  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    return null;
  }

  @Nullable
  @Override
  public String getType(Uri uri) {
    return null;
  }

  @Nullable
  @Override
  public Uri insert(Uri uri, ContentValues values) {
    Uri ret = null;
    // Assert correct uri
    if (uriMatcher.match(uri) != ResidenceContract.RESIDENCE_DIR) {
      throw new IllegalArgumentException("Illegal uri: " + uri);
    }
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    long rowId = db.insertWithOnConflict(ResidenceContract.TABLE_RESIDENCES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    // Was insert successful?
    if (rowId != -1) {
      //Integer id = values.getAsInteger(ResidenceContract.Column.ID);
      //ret = ContentUris.withAppendedId(uri, id);
      ret = ContentUris.withAppendedId(uri, rowId);
      Log.d(TAG, "inserted uri: " + ret);
      // Notify that data for this uri has changed
      getContext().getContentResolver()
          .notifyChange(uri, null);
    }
    return ret;
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    return 0;
  }

}