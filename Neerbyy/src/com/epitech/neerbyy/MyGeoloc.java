package com.epitech.neerbyy;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class MyGeoloc implements LocationListener {
    private LocationManager locationManager;
 
    
    public void onResume() {
        
        //Obtention de la r�f�rence du service
       // locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
 
        //Si le GPS est disponible, on s'y abonne
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            abonnementGPS();
        }
    }
    
    public void onPause() {
        //On appelle la m�thode pour se d�sabonner
        desabonnementGPS();
    }
 
    /**
     * M�thode permettant de s'abonner � la localisation par GPS.
     */
    public void abonnementGPS() {
        //On s'abonne
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }
 
    /**
     * M�thode permettant de se d�sabonner de la localisation par GPS.
     */
    public void desabonnementGPS() {
        //Si le GPS est disponible, on s'y abonne
        locationManager.removeUpdates(this);
    }
 
    @Override
    public void onLocationChanged(final Location location) {
        //On affiche dans un Toat la nouvelle Localisation
        final StringBuilder msg = new StringBuilder("lat : ");
        msg.append(location.getLatitude());
        msg.append( "; lng : ");
        msg.append(location.getLongitude());
 
        Log.w("GEOLOC", msg.toString());       
    }
 
    @Override
    public void onProviderDisabled(final String provider) {
        //Si le GPS est d�sactiv� on se d�sabonne
        if("gps".equals(provider)) {
            desabonnementGPS();
        }      
    }
 
    @Override
    public void onProviderEnabled(final String provider) {
        //Si le GPS est activ� on s'abonne
        if("gps".equals(provider)) {
            abonnementGPS();
        }
    }
 
    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) { }
}