package pacote.android.View;

import pacote.android.R;
import pacote.android.Beans.Evento;
import pacote.android.Beans.Voto;

import pacote.android.Service.EventoService;
import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Ricardo Burg Machado
 * @version 5.6
 * @since 2012
 */

public class DialogVotoEventoActivity extends Activity {


	private Evento evento;
	private Dialog dialog;
	private Context context;
	private EventoService eventoService;
	private SQLiteDatabase sQLiteDatabase;
	
	public DialogVotoEventoActivity(Context context, Evento evento, SQLiteDatabase sQLiteDatabase){

		this.context = context;
		this.evento = evento;
		this.sQLiteDatabase = sQLiteDatabase;
		
		dialog = new Dialog(context);	  

		eventoService =  new EventoService(sQLiteDatabase);

		
		//dialog.setTitle( evento.getTipo().getDescricao() );
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//dialog.requestWindowFeature(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
		
	    dialog.setContentView(R.layout.votoevento);
	    

	    TextView categoriaEvento = (TextView) dialog.findViewById(R.id.categoriaEvento);	
	    categoriaEvento.setText( evento.getTipo().getDescricao() );	    
	    
	    TextView subTituloEvento = (TextView) dialog.findViewById(R.id.nomeEvento);	
	    subTituloEvento.setText( evento.getNome() );	    
	    
	    TextView descricaoEvento = (TextView) dialog.findViewById(R.id.descricaoEvento);	
	    descricaoEvento.setText( evento.getInformacao() );

	    TextView dataEvento = (TextView) dialog.findViewById(R.id.dataEvento);	
	    dataEvento.setText( evento.getData() );

	    TextView horaEvento = (TextView) dialog.findViewById(R.id.horaEvento);
	    horaEvento.setText( evento.getHora() );
	    
	    TextView qtdVotosConcordo = (TextView) dialog.findViewById(R.id.qtdVotosConcordo);
	    qtdVotosConcordo.setText( eventoService.getQtdVotosConcordo( evento.getIdEvento() )+ "" );
	    
	    TextView qtdVotosDiscordo = (TextView) dialog.findViewById(R.id.qtdVotosDiscordo);
	    qtdVotosDiscordo.setText( eventoService.getQtdVotosDiscordo( evento.getIdEvento() ) + "" );
	    
		Button botaoConcordar = (Button) dialog.findViewById(R.id.buttonCorcordar);		
		
		botaoConcordar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Voto voto = new Voto();
				voto.setVoto("Concordo");
				voto.setIdEvento( DialogVotoEventoActivity.this.evento.getIdEvento() );
				
			
				eventoService.votar( voto );
				
				//apenas Debug				
				eventoService.getVotos();
				
				//sQLiteDatabase.close();
				dialog.cancel();
			}
		});
	    
		Button botaoDiscordar = (Button) dialog.findViewById(R.id.buttonDiscordar);		
		
		botaoDiscordar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Voto voto = new Voto();
				voto.setVoto("Discordo");
				voto.setIdEvento( DialogVotoEventoActivity.this.evento.getIdEvento() );
			
				
				
				eventoService.votar( voto );
				//apenas Debug
				eventoService.getVotos();
				
				//sQLiteDatabase.close();
				dialog.cancel();
			}
		});

		
		Button botaoFechar = (Button) dialog.findViewById(R.id.buttonFecharDialog);		
		
		botaoFechar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				dialog.cancel();
			}
		});
		
		
	    dialog.show();
	    
	}




	
	
	
}
