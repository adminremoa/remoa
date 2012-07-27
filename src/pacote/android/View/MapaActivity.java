package pacote.android.View;



import java.util.ArrayList;
import java.util.List;

import pacote.android.R;
import pacote.android.Beans.DataHora;
import pacote.android.Beans.Evento;
import pacote.android.Beans.TipoEvento;
import pacote.android.Service.ConectaBanco;
import pacote.android.Service.VotoService;

import pacote.android.Service.EventoService;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @author Ricardo Burg Machado
 * @version 5.6
 * @since 2012
*/


public class MapaActivity extends MapActivity  implements LocationListener  {

	
	private MapController controleMapa;
    private LocationManager locationManager;
    private LocationListener locationLinstener;
    private LocalizacaoGPS localizacaoGPS;
	private List mapOverlays;
	private MapView mapView;
	private MyLocationOverlay myLocationOverlay;
	private EventoService eventoService;
	private ConectaBanco conectaBanco;
	public static SQLiteDatabase sQLiteDatabase;
	
	public MapaActivity(){ }
	
	/**
	 * Este método executa as seguintes ações:
	 * - inicialização do mapa;
	 * - carregamento do overlay na posição (GPS) atual do usuário;
	 * - conexão com base de dados
	 * - inicialização de todos os eventos contidos na base de dados
	 */
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mapView = (MapView) findViewById(R.id.mapa);
        controleMapa = mapView.getController();
        mapView.setBuiltInZoomControls(true);
		mapOverlays = mapView.getOverlays();   

	    insereOverlayLocalizacaoGPS();
	    
	    localizacaoGPS = new LocalizacaoGPS(this, mapView);
	    
	    sQLiteDatabase = openOrCreateDatabase("bancoremoa", MODE_WORLD_WRITEABLE, null);
	    conectaBanco = new ConectaBanco(sQLiteDatabase);
	    
        eventoService = new EventoService( conectaBanco.getBanco() );       
        
        inicializaEventos();

    }
    
    /**
     * Este método executa as seguintes ações:
     * - preenche um array de objetos do tipo Evento com todos os eventos contido no banco de dados;
     * - percorre este array executando ações para cada objeto:
     *   - verifica qual o tipo/classificação do evento;
     *   - cria um icone do tipo Drawble que corresponde ao tipo obtido anteriormente;
     *   - cria um objeto do tipo OverlayItemized que será utilizado para representar o evento no mapa que tem como um de seus parâmetros o ícone criado acima;
     *   - cria um objeto do tipo GeoPoint que contém como parâmetro a latitude e longitude que são obtidas do objeto do tipo Evento
     *   - cria um objeto do tipo OverlayItem que tem como um de seus parâmetros o GeoPoint criado anteriormente
     *   - executa o método addOverlay que tem como um de seus parâmetros o OverlayItem criado anteriormente  
     *   - adiciona o OverlayItemized ao List mapOverlays que representa os eventos contido no mapa, atualiza o mapa com este novo evento e 
     *   centraliza o mapa na localização deste evento.
     * 
     * @return void
     */
    public void inicializaEventos(){	
    	
		ArrayList<Evento> eventosBanco = eventoService.getEventos();
		
		for(int i = 0; i < eventosBanco.size(); i++){
		
			Drawable icone;
			if(eventoService.getEventos().get(i).getTipo().getDescricao().equalsIgnoreCase("SAÚDE")){
				icone = getResources().getDrawable(R.drawable.saude);									
			}else if(eventoService.getEventos().get(i).getTipo().getDescricao().equalsIgnoreCase("TRÂNSITO")){
				icone = getResources().getDrawable(R.drawable.transito);
			}else if(eventoService.getEventos().get(i).getTipo().getDescricao().equalsIgnoreCase("SEGURANÇA")){
				icone = getResources().getDrawable(R.drawable.seguranca);
			}else if(eventoService.getEventos().get(i).getTipo().getDescricao().equalsIgnoreCase("ENTRETENIMENTO")){
				icone = getResources().getDrawable(R.drawable.entretenimento);
			}else{
				icone = getResources().getDrawable(R.drawable.app_icon);
			}
			
			OverlayItemized itemizedoverlay = new OverlayItemized( icone , this, conectaBanco.getBanco());
			GeoPoint gp = new GeoPoint( eventoService.getEventos().get(i).getLatitude() , eventoService.getEventos().get(i).getLongitude() );		
			OverlayItem overlayitem1 = new OverlayItem(gp, null, null);
			
			itemizedoverlay.addOverlay(overlayitem1, eventoService.getEventos().get(i) , this);
			mapOverlays.add(itemizedoverlay);
			MapController mc = mapView.getController();
			mc.animateTo(gp);
			mc.setZoom(14);
			mc.setCenter( gp );
	        mapView.invalidate();
		}    
	}
    
    
    /**
     * Método reponsável de inserir um (ícone padrão) do tipo MyLocationOverlay que representa a localização atual do usuário (obtida pelo GPS)
     * a execução deste método é automática, ou seja, a cada mudança de localização do usuário o ícone é atualizado no mapa
     * @return void
     */
    public void insereOverlayLocalizacaoGPS(){
    	
    	myLocationOverlay = new MyLocationOverlay(this, mapView);
 	    mapView.getOverlays().add(myLocationOverlay);
 	    
 		retornaLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
    

    /**
     * Cria o menu de opções(Cadastrar novo evento) que é ativado ao clicar no botão menu do dispositivo/emulador
     * @param Menu menu
     * @return boolean true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	
    	MenuInflater menuInflater = getMenuInflater();
    	menuInflater.inflate(R.menu.itensmenu, menu);
    	
    	return true;
    }


    /**
     * Faz o gerenciamento das ações do menu com a opção: Cadastrar novo evento
     * @param MenuItem menuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem ){
	   	 
	   	 switch(menuItem.getItemId()){
	   	 		   
	   	 case R.id.cadastroEvento: DialogCadEventoActivity dialogCadEventoActivity = new DialogCadEventoActivity(this, mapOverlays, mapView, localizacaoGPS.getGeoPointGPS(), conectaBanco.getBanco());
	   	 	return true;	   	 
	   	 }
	   	 
	   	 return false;	   	 
    }
    
    
    
    @Override
    protected void onResume(){
    	super.onResume();
    	myLocationOverlay.enableMyLocation();
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	myLocationOverlay.disableMyLocation();
    }
    
    private LocationManager retornaLocationManager() {
    	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	return locationManager;
    }


    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	retornaLocationManager().removeUpdates(this);
    }
    
    public static GeoPoint posicao(double lat, double lng){
    	
        GeoPoint geoPoint = new GeoPoint( (int) (lat * 1E6),(int) (lng * 1E6) );        
        return geoPoint;   	
    }
	
	public void onLocationChanged(Location location) {

		if (location != null){
			
			GeoPoint gp = posicao(location.getLatitude(),location.getLongitude());
			controleMapa.setCenter(gp);

		}
		
	}
	
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}