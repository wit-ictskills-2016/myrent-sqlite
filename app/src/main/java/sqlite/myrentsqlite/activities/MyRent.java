package sqlite.myrentsqlite.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import sqlite.myrentsqlite.R;
import sqlite.myrentsqlite.app.MyRentApp;
import sqlite.myrentsqlite.models.Residence;

public class MyRent extends AppCompatActivity implements View.OnClickListener
{

  private Button addResidence;
  private Button selectResidence;
  private Button deleteResidence;
  private Button selectAllResidences;
  private Button deleteAllResidences;

  MyRentApp app;
  Residence residence;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_myrent);

    app = MyRentApp.getApp();

    addResidence = (Button) findViewById(R.id.addResidence);
    addResidence.setOnClickListener(this);

    selectResidence = (Button) findViewById(R.id.selectResidence);
    selectResidence.setOnClickListener(this);

    deleteResidence = (Button) findViewById(R.id.deleteResidence);
    deleteResidence.setOnClickListener(this);

    selectAllResidences = (Button) findViewById(R.id.selectAllResidences);
    selectAllResidences.setOnClickListener(this);

    deleteAllResidences = (Button) findViewById(R.id.deleteAllResidences);
    deleteAllResidences.setOnClickListener(this);

  }

  @Override
  public void onClick(View v)
  {
    switch (v.getId())
    {
      case R.id.addResidence:
        addResidence();
        break;

      case R.id.selectResidence:
        selectResidence();
        break;

      case R.id.deleteResidence:
        deleteResidence();
        break;

      case R.id.selectAllResidences:
        selectResidences();
        break;

      case R.id.deleteAllResidences:
        deleteAllResidences();
        break;
    }
  }

  private void addResidence()
  {
    residence = new Residence();

    app.dbHelper.addResidence(residence);

  }

  /**
   * This method demonstrates how to select a Residence record, identified by
   * its primary key, the UUID field.
   * Invoking addResidence() writes a Residence record to the database.
   * Additionally, it initializes this.residence field.
   * The id of this.residence is then used as a parameter in DbHelper.selectResidence.
   */
  public void selectResidence()
  {
    addResidence();
    UUID uuid = residence.id;
    Residence selectedResidence = app.dbHelper.selectResidence(uuid);
    if (residence != null && residence.id.toString().equals(selectedResidence.id.toString()))
    {
      Toast.makeText(this, "Residence record selected(id: " + residence.id, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(this, "Failed to select Residence record", Toast.LENGTH_LONG).show();
    }
  }

  public void deleteResidence()
  {
    if (residence == null)
    {
      addResidence();
    }
    else {
      Residence res = app.dbHelper.selectResidence(residence.id);
      app.dbHelper.deleteResidence(res);
    }
  }

  public void selectResidences() {
    List<Residence> residences = app.dbHelper.selectAllResidences();
    Toast.makeText(this, "Retrieved residence list containing  " + residences.size() + " records", Toast.LENGTH_LONG).show();
  }

  /**
   * Delete all records.
   * Count the number of rows in database following deletion -should be zero.
   * Provide user feed back in a toast.
   */
  public void deleteAllResidences() {
    app.dbHelper.deleteAllResidences();
    Toast.makeText(this, "Number of records in database " + app.dbHelper.getCount(), Toast.LENGTH_LONG).show();

  }
}