package sqlite.myrentsqlite.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import sqlite.myrentsqlite.R;
import sqlite.myrentsqlite.services.RefreshResidenceService;;


public class MyRent extends AppCompatActivity implements View.OnClickListener
{

  private Button addResidence;
  private Button selectResidence;
  private Button deleteResidence;
  private Button selectResidences;
  private Button deleteResidences;
  private Button updateResidence;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_myrent);

    addResidence = (Button) findViewById(R.id.addResidence);
    addResidence.setOnClickListener(this);

    selectResidence = (Button) findViewById(R.id.selectResidence);
    selectResidence.setOnClickListener(this);

    deleteResidence = (Button) findViewById(R.id.deleteResidence);
    deleteResidence.setOnClickListener(this);

    selectResidences = (Button) findViewById(R.id.selectAllResidences);
    selectResidences.setOnClickListener(this);

    deleteResidences = (Button) findViewById(R.id.deleteAllResidences);
    deleteResidences.setOnClickListener(this);

    updateResidence = (Button) findViewById(R.id.updateResidence);
    updateResidence.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
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
        deleteResidences();
        break;

      case R.id.updateResidence:
        updateResidence();
        break;

    }
  }

  /**
   * Start a RefreshResidence service, passing the intent message ADD_RESIDENCE
   * This determines what functionality is invoked in the service.
   */
  private void addResidence() {
    Intent intent = new Intent(getBaseContext(), RefreshResidenceService.class);
    intent.putExtra(RefreshResidenceService.REFRESH, RefreshResidenceService.ADD_RESIDENCE);
    startService(intent);
  }

  /**
   * Select a single Residence record
   */
  public void selectResidence() {

  }

  /**
   * Select all Residence records
   */
  public void selectResidences() {

  }

  public void deleteResidence() {

  }

  /**
   * Delete all records.
   */
  public void deleteResidences() {

  }

  /**
   * Update a residence record.
   */
  public void updateResidence() {

  }

}