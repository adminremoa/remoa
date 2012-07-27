package pacote.android.View;


import java.util.List;

import pacote.android.R;
import pacote.android.Beans.Evento;
import pacote.android.Beans.TipoEvento;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

public class LocalizacaoGPS  {
	
	
	private LocationManager locationManager;
	private Context context;
	private MapView mapView;
	private LocationListener locationLinstener;
	private MapController controleMapa;
	private GeoPoint geoPointGPS;

	
	public LocalizacaoGPS (Context context, MapView mapView){
		
		this.context = context;
		this.mapView = mapView;
		
		controleMapa = this.mapView.getController();
	    locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
	    locationLinstener = new NovaLocalizacao();
	   
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationLinstener);
	}
	
	
	
	
	    
	    
	    
	    
	//utilizado para manipulação de geoPoint apenas na manipulação da localização por GPS
	public static GeoPoint posicao(double lat, double lng){
    	
        GeoPoint geoPoint = new GeoPoint(		
        		(int) (lat * 1E6),
        		(int) (lng * 1E6));

        return geoPoint;    	
    }
	
	
	
		
	
	
	class NovaLocalizacao extends MapActivity implements LocationListener {
    	
		
		
		
    	//método chamado toda vez que uma localização for alterada
		@Override
		public void onLocationChanged(Location location) {
			
			if(location != null){	
				
				setGeoPointGPS(location.getLatitude(), location.getLongitude());
	
				
				controleMapa.animateTo(posicao(location.getLatitude(), location.getLongitude()));
				
				GeoPoint gp = posicao(location.getLatitude(),location.getLongitude());
				controleMapa.setCenter(gp);
				
				//Toast.makeText(context, "CAIU: onLocationChanged ************************", Toast.LENGTH_LONG).show();
				
			}	
		}
		
		
		
		//chamado quando um provedor é DEsabilitado pelo usuário
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		//chamado quando um provedor é HAbilitado pelo usuário
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		//quando status do provedor for alterado
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}



		@Override
		protected boolean isRouteDisplayed() {
			// TODO Auto-generated method stub
			return false;
		}
		
		
		
    	
	    @Override
	    protected void onDestroy(){
	    	super.onDestroy();
	    	locationManager.removeUpdates(this);
	    }
	    
	    protected boolean enableMyLocation(){
	    	
	    	
	    	return true;
	    }
	    
	    
    }
	
	public GeoPoint getGeoPointGPS() {
		 
		return geoPointGPS;
	}


	public void setGeoPointGPS(double lat, double lng) {
			
		GeoPoint geoPoint = new GeoPoint((int) (lat * 1E6),(int) (lng * 1E6));
		this.geoPointGPS = geoPoint;	
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
