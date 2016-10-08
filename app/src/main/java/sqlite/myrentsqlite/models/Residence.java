package sqlite.myrentsqlite.models;

import java.util.UUID;

public class Residence
{
  public UUID id;
  public String geolocation;


  public Residence()
  {
    id = UUID.randomUUID();
    geolocation = "52.253456,-7.187162";
  }

}