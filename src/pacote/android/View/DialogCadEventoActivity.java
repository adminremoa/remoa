
package pacote.android.View;

/**
 * @author Ricardo Burg Machado
 * @version 5.6
 * @since 2012
 */

import java.security.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import pacote.android.R;
import pacote.android.Beans.DataHora;
import pacote.android.Beans.Evento;
import pacote.android.Beans.TipoEvento;
import pacote.android.Beans.Voto;

import pacote.android.Service.EventoService;
import pacote.android.Service.TipoEventoService;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DialogCadEventoActivity extends Activity {


	private Evento evento;
	private TipoEventoService tipoEventoService;
	private Context context;
	private List mapOverlays;
	private MapView mapView;
	private Dialog dialog;
	private GeoPoint geoPointGPS;
	private static SQLiteDatabase sQLiteDatabase;
	private Spinner spinnerCategorias;
	
	
	public DialogCadEventoActivity(Context context , List mapOverlays , MapView mapView,  GeoPoint geoPointGPS, SQLiteDatabase sQLiteDatabase ){
	
		

		this.context = context;
		this.mapOverlays = mapOverlays;
		this.mapView = mapView;
		this.sQLiteDatabase = sQLiteDatabase;

		this.geoPointGPS = geoPointGPS;
		
		dialog = new Dialog(context);	  
	   
		dialog.setTitle( "Cadastrar Evento" );
		//dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
	    dialog.setContentView(R.layout.cadastroevento);
	    
		tipoEventoService = new TipoEventoService(sQLiteDatabase);

	    
	    String[] listaCategorias =  new String[ tipoEventoService.getTiposEvento().size() + 1 ];
	    
	    listaCategorias[0] = "<<Selecione>>";
	   
	    
	    for(int i = 0; i < tipoEventoService.getTiposEvento().size(); i++ ){
	    	
	    	listaCategorias[i + 1] = tipoEventoService.getTiposEvento().get(i).getDescricao();
	    }
	    
	    
	    //sQLiteDatabase.close();
	    		
		spinnerCategorias = (Spinner) dialog.findViewById(R.id.ClassificacaoEvento);
        ArrayAdapter<String> todasCategorias; 
        todasCategorias = new ArrayAdapter<String>(DialogCadEventoActivity.this.context, android.R.layout.simple_spinner_item, listaCategorias);
        todasCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//muda apenas a apresentação dos dados na tela
        spinnerCategorias.setAdapter(todasCategorias);

	    
		Button botaoSalvar = (Button) dialog.findViewById(R.id.botaoSalvar);		
		
		botaoSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Context context = DialogCadEventoActivity.this.context;
				List mapOverlays = DialogCadEventoActivity.this.mapOverlays;
				MapView mapView  = DialogCadEventoActivity.this.mapView;
				GeoPoint geoPointGPS = DialogCadEventoActivity.this.geoPointGPS;

				SQLiteDatabase sQLiteDatabase = context.openOrCreateDatabase("bancoremoa", context.MODE_WORLD_READABLE, null);
				
				EventoService eventoService = new EventoService(sQLiteDatabase);

				EditText editTextNomeEvento = (EditText) dialog.findViewById(R.id.nomeEvento);
				EditText editTextDescricaoEvento = (EditText) dialog.findViewById(R.id.descricaoEvento);

				String categoriaSelecionada = spinnerCategorias.getSelectedItem().toString();
				
				TipoEvento tipoEvento = new TipoEvento();
				
				
				Drawable icone;
				if(categoriaSelecionada.equalsIgnoreCase("SAÚDE")){
					icone = context.getResources().getDrawable(R.drawable.saude);		
					tipoEvento.setId( tipoEventoService.getTipoEvento(categoriaSelecionada).getId() );
					tipoEvento.setDescricao("SAÚDE");
				}else if(categoriaSelecionada.equalsIgnoreCase("TRÂNSITO")){
					icone = context.getResources().getDrawable(R.drawable.transito);
					tipoEvento.setId( tipoEventoService.getTipoEvento(categoriaSelecionada).getId() );
					tipoEvento.setDescricao("TRÂNSITO");
				}else if(categoriaSelecionada.equalsIgnoreCase("SEGURANÇA")){
					icone = context.getResources().getDrawable(R.drawable.seguranca);
					tipoEvento.setId( tipoEventoService.getTipoEvento(categoriaSelecionada).getId() );
					tipoEvento.setDescricao("SEGURANÇA");
				}else if(categoriaSelecionada.equalsIgnoreCase("ENTRETENIMENTO")){
					icone = context.getResources().getDrawable(R.drawable.entretenimento);
					tipoEvento.setId( tipoEventoService.getTipoEvento(categoriaSelecionada).getId() );
					tipoEvento.setDescricao("ENTRETENIMENTO");
				}else{
					icone = context.getResources().getDrawable(R.drawable.app_icon);
				}
				
				String nomeEvento = editTextNomeEvento.getText().toString();				
				String descricaoEvento = editTextDescricaoEvento.getText().toString();				
				
				Evento novoEvento = new Evento( geoPointGPS.getLatitudeE6(), geoPointGPS.getLongitudeE6(), nomeEvento, descricaoEvento, tipoEvento, DataHora.getDataAtual(), DataHora.getHoraAtual()  );
				
				long retornoCadastro = eventoService.inserir(novoEvento); 
				
				if( retornoCadastro > 0 ){
					
					novoEvento.setIdEvento( (int)retornoCadastro );
				
					OverlayItemized itemizedoverlay = new OverlayItemized( icone , context, sQLiteDatabase);
					GeoPoint gp = new GeoPoint(  geoPointGPS.getLatitudeE6() , geoPointGPS.getLongitudeE6() );		
					OverlayItem overlayitem1 = new OverlayItem(gp, null, null);
					itemizedoverlay.addOverlay(overlayitem1, novoEvento , context);
					mapOverlays.add(itemizedoverlay);
					MapController mc = mapView.getController();
					mc.animateTo(gp);
					mc.setZoom(14);
					mc.setCenter( gp );
			        mapView.invalidate();
						
			        //sQLiteDatabase.close();
					dialog.cancel();
								
				}				
				
				
			}
		});
	    
		Button botaoCancelar = (Button) dialog.findViewById(R.id.botaoCancelar);		
		
		botaoCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				dialog.cancel();
			}
		});

		
		
	    dialog.show();

	    
	}



	
}

