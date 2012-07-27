package pacote.android.Service;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import pacote.android.R;
import pacote.android.Beans.Evento;
import pacote.android.Beans.TipoEvento;
import pacote.android.Beans.Voto;
import pacote.android.View.OverlayItemized;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;
/**
 * @author Ricardo Burg Machado
 * @version 5.6
 * @since 2012
 */


public class EventoService {


	private SQLiteDatabase db;
	private final static String TABELA_EVENTO = "eventos";
	private final static String ATRIBUTO_DB_ID = "_id";
	private final static String ATRIBUTO_DB_NOME = "nome";
	private final static String ATRIBUTO_DB_INFORMACAO = "informacao";
	private final static String ATRIBUTO_DB_LATITUDE = "latitude";
	private final static String ATRIBUTO_DB_LONGITUDE = "longitude";
	private final static String ATRIBUTO_DB_ID_TIPO_EVENTO = "id_tipo_evento";
	private final static String ATRIBUTO_DB_DATA = "data";
	private final static String ATRIBUTO_DB_HORA = "hora";
	private TipoEventoService tipoEvService;
	
	public EventoService(SQLiteDatabase d) {				
		
		this.db = d;		
		
		//cria a tabela EVENTOS
		StringBuilder sb = new StringBuilder();
		sb.append(" CREATE TABLE IF NOT EXISTS "+TABELA_EVENTO);
		sb.append(" ( ");
		sb.append(""+ATRIBUTO_DB_ID+" integer primary key autoincrement NOT NULL,");	
		sb.append(""+ATRIBUTO_DB_NOME+" varchar(50) NOT NULL,");
		sb.append(""+ATRIBUTO_DB_INFORMACAO+" varchar(500) NOT NULL,");
		sb.append(""+ATRIBUTO_DB_LATITUDE+" varchar(20) NOT NULL,");
		sb.append(""+ATRIBUTO_DB_LONGITUDE+" varchar(20) NOT NULL,");
		sb.append(""+ATRIBUTO_DB_ID_TIPO_EVENTO+" integer NOT NULL,");
		
		sb.append(""+ATRIBUTO_DB_DATA+" varchar(20) NOT NULL,");
		sb.append(""+ATRIBUTO_DB_HORA+" varchar(20) NOT NULL, ");
		sb.append("FOREIGN KEY ("+ATRIBUTO_DB_ID_TIPO_EVENTO+") REFERENCES tipoEvento (id)"); 
		sb.append(" ); ");
		//Log.d("SQL GERADA construtor EventoService:  ", ""+sb.toString() );		
		db.execSQL(sb.toString());
	
	}
	
	
	public void votar(Voto voto){		
				
		VotoService votoService = new VotoService(this.db);
		
		votoService.inserirVoto(voto);
		
	}

	public int getQtdVotosConcordo(int idEvento){
		
		int retorno = 0;
		
		Cursor c = db.rawQuery("SELECT COUNT(voto) FROM votos WHERE idEvento = "+idEvento+" AND voto = 'Concordo' " , null );					
		
		c.moveToFirst();
		
		retorno = c.getInt(0);
		
		return retorno;
		
	}
	
	public int getQtdVotosDiscordo(int idEvento){
	
		int retorno = 0;
		
		Cursor c = db.rawQuery("SELECT COUNT(voto) FROM votos WHERE idEvento = "+idEvento+" AND voto = 'Discordo' " , null );					
		
		c.moveToFirst();
		
		retorno = c.getInt(0);
		
		return retorno;
		
	}
	
	
/*	
	public boolean inserir(Evento evento){
		
		ContentValues cv = new ContentValues();		
		cv.put(ATRIBUTO_DB_NOME, 		   ""+evento.getNome()+"");
		cv.put(ATRIBUTO_DB_INFORMACAO,     ""+evento.getInformacao()+"");
		cv.put(ATRIBUTO_DB_LATITUDE,       ""+evento.getLatitude()+"");		
		cv.put(ATRIBUTO_DB_LONGITUDE,      ""+evento.getLongitude()+"");			
		cv.put(ATRIBUTO_DB_ID_TIPO_EVENTO, ""+evento.getTipo().getId()+"");
		cv.put(ATRIBUTO_DB_DATA,           ""+evento.getData()+"");
		cv.put(ATRIBUTO_DB_HORA,           ""+evento.getHora()+"");
		
	
		long res = db.insert(TABELA_EVENTO, "_id", cv);
		
		
		Log.d("RETORNO INSERÇÃO: ", ""+res);
		
		if(res > 0){
			
			Log.d("INSERÇÃO DE DADOS ", "Dados inseridos com sucesso!");
			return true;
		}else{
			
			Log.d("ERRO INSERÇÃO DE DADOS ", "Erro, a inserção não pode ser efetuada.!");
			return false;
		}
	}
*/
	
	public long inserir(Evento evento){
		
		ContentValues cv = new ContentValues();		
		cv.put(ATRIBUTO_DB_NOME, 		   ""+evento.getNome()+"");
		cv.put(ATRIBUTO_DB_INFORMACAO,     ""+evento.getInformacao()+"");
		cv.put(ATRIBUTO_DB_LATITUDE,       ""+evento.getLatitude()+"");		
		cv.put(ATRIBUTO_DB_LONGITUDE,      ""+evento.getLongitude()+"");			
		cv.put(ATRIBUTO_DB_ID_TIPO_EVENTO, ""+evento.getTipo().getId()+"");
		cv.put(ATRIBUTO_DB_DATA,           ""+evento.getData()+"");
		cv.put(ATRIBUTO_DB_HORA,           ""+evento.getHora()+"");
		
	
		long res = db.insert(TABELA_EVENTO, "_id", cv);
		
		
		Log.d("RETORNO INSERÇÃO: ", ""+res);
		
		if(res > 0){
			
			Log.d("INSERÇÃO DE DADOS ", "Dados inseridos com sucesso!");
			return res;
		}else{
			
			Log.d("ERRO INSERÇÃO DE DADOS ", "Erro, a inserção não pode ser efetuada.!");
			return res;
		}
	}

	
	public boolean atualizar(Evento evento, int idParamtro){
		
		ContentValues cv = new ContentValues();		
		cv.put(ATRIBUTO_DB_NOME, 		   ""+evento.getNome()+"");
		cv.put(ATRIBUTO_DB_INFORMACAO,     ""+evento.getInformacao()+"");
		cv.put(ATRIBUTO_DB_LATITUDE,       ""+evento.getLatitude()+"");		
		cv.put(ATRIBUTO_DB_LONGITUDE,      ""+evento.getLongitude()+"");			
		cv.put(ATRIBUTO_DB_ID_TIPO_EVENTO, ""+evento.getTipo().getId()+"");
		cv.put(ATRIBUTO_DB_DATA,           ""+evento.getData()+"");
		cv.put(ATRIBUTO_DB_HORA,           ""+evento.getHora()+"");
		
		long res = db.update(TABELA_EVENTO, cv, ATRIBUTO_DB_ID +" = "+idParamtro+" ", null);
		
		Log.d("RETORNO ATUALIZAÇÃO: ", ""+res);
		
		if(res > 0){
			
			Log.d("ATUALIZAÇÃO DE DADOS ", "Dados atualizados com sucesso!");
			return true;
		}else{
			
			Log.d("ERRO ATUALIZAÇÃO DE DADOS ", "Erro, a atualização não pode ser efetuada.!");
			return false;
		}
	}
	
	
	public void removeTabelaEventos(){
		
		StringBuilder sb = new StringBuilder();
		sb.append(" DROP TABLE "+TABELA_EVENTO);
				
		Log.d("SQL GERADA removeTabelaEventoS():  ", ""+sb.toString() );
				
		db.execSQL(sb.toString());
				
	}
	
	
	public void deletarTodosEventos(){
		
		Cursor c = db.query(TABELA_EVENTO, new String[]{ATRIBUTO_DB_ID, ATRIBUTO_DB_LATITUDE, ATRIBUTO_DB_LONGITUDE, ATRIBUTO_DB_NOME, ATRIBUTO_DB_INFORMACAO, ATRIBUTO_DB_ID_TIPO_EVENTO, ATRIBUTO_DB_DATA, ATRIBUTO_DB_HORA},
				null, null, null, null, null);
		
		while(c.moveToNext()){
			
			if(deletar( c.getInt(c.getColumnIndex(ATRIBUTO_DB_ID))  ) ){
				
				Log.d("Remoção Evento ", "Remoção do evento id ["+c.getInt(c.getColumnIndex(ATRIBUTO_DB_ID))+"] efetuada com sucesso!" );
			}
				
		}
		//c.close();		
		
	}

	
	public ArrayList<Evento> getEventos(){				
		
		ArrayList<Evento> list = new ArrayList<Evento>();
		tipoEvService = new TipoEventoService(this.db);
		
		Cursor c = db.query(TABELA_EVENTO, new String[]{ATRIBUTO_DB_ID, ATRIBUTO_DB_LATITUDE, ATRIBUTO_DB_LONGITUDE, ATRIBUTO_DB_NOME, ATRIBUTO_DB_INFORMACAO, ATRIBUTO_DB_ID_TIPO_EVENTO, ATRIBUTO_DB_DATA, ATRIBUTO_DB_HORA},
				null, null, null, null, null);					
		
		int contador = 0;		
		
		while(c.moveToNext()){
			
			contador++;
			
			list.add(new Evento( 
					c.getInt(c.getColumnIndex(ATRIBUTO_DB_ID)), 
					Integer.parseInt(  c.getString(c.getColumnIndex(ATRIBUTO_DB_LATITUDE)) ), 
					Integer.parseInt(  c.getString(c.getColumnIndex(ATRIBUTO_DB_LONGITUDE)) ),
					c.getString(c.getColumnIndex(ATRIBUTO_DB_NOME)),
					c.getString(c.getColumnIndex(ATRIBUTO_DB_INFORMACAO)),
					tipoEvService.getTipoEvento( c.getInt(c.getColumnIndex(ATRIBUTO_DB_ID_TIPO_EVENTO) ) ),
					c.getString(c.getColumnIndex(ATRIBUTO_DB_DATA)),
					c.getString(c.getColumnIndex(ATRIBUTO_DB_HORA))
					));
					
		}
		//c.close();		

		
		Log.d("Debug Eventos ", "QUANTIDADE EVENTOS: "+contador);
		
		for(int i = 0; i < list.size(); i++){
			
			Log.d("Debug Eventos: ["+i+"]", 
					"EventoId: "+list.get(i).getIdEvento()
					+" EventoNome: "+list.get(i).getNome() 
					+" EventoInformação: "+list.get(i).getInformacao()
					+" EventoLatitude: "+list.get(i).getLatitude()
					+" EventoLongitude: "+list.get(i).getLongitude()
					+" EventoTipoId: "+list.get(i).getTipo().getId()
					+" EventoTipoDescrição: "+list.get(i).getTipo().getDescricao()
					+" EventoData: "+list.get(i).getData()
					+" EventoHora: "+list.get(i).getHora()
			);
		}
				
		
		return list;
	}

	public ArrayList<Voto> getVotos(){				
		
		ArrayList<Voto> list = new ArrayList<Voto>();
		
		Cursor c = db.query("votos", new String[]{"id", "voto", "idEvento"},
				null, null, null, null, null);					
		
		int contador = 0;
		
		while(c.moveToNext()){
			
			contador++;
			
			list.add( new Voto( 
					c.getInt(c.getColumnIndex("id")),
					c.getString(c.getColumnIndex("voto")),
					c.getInt(c.getColumnIndex("idEvento"))
					));
			

			Log.d("Debug Votos: ", 
					"Id: "+c.getInt(c.getColumnIndex("id"))
					+"Voto: "+c.getString(c.getColumnIndex("voto"))
					+" IdEvento: "+c.getInt(c.getColumnIndex("idEvento"))
				);

			
		}
		//c.close();		

		
		Log.d("Debug Votos ", "QUANTIDADE Votos: "+contador);
/*		
		Evento evento = new Evento();
		for(int i = 0; i < list.size(); i++){
			
			Log.d("Debug Votos: ["+i+"]", 
					"Id: "+list.get(i).getId()
					+"Voto: "+list.get(i).getVoto()
					+" IdEvento: "+list.get(i).getIdEvento()
					+" Evento: "+ getEvento( list.get(i).getIdEvento() ).getNome()
			);
		}		
*/		
		return list;
	}

	
	
	public Cursor getEventosCursor(){								
		
		return db.query(TABELA_EVENTO, new String[]{ATRIBUTO_DB_ID+" as _id",ATRIBUTO_DB_LATITUDE, ATRIBUTO_DB_LONGITUDE, ATRIBUTO_DB_NOME, ATRIBUTO_DB_INFORMACAO, ATRIBUTO_DB_ID_TIPO_EVENTO, ATRIBUTO_DB_DATA, ATRIBUTO_DB_HORA},
				null, null, null, null, null);		
	}
	
	public boolean deletar(int id){
		
		return db.delete(TABELA_EVENTO, ATRIBUTO_DB_ID +" = "+ id, null) > 0;	
	}

	
	public Evento getEvento(String informacao){
		
		tipoEvService = new TipoEventoService(this.db);
		Cursor c = db.query(TABELA_EVENTO, new String[]{ATRIBUTO_DB_ID, ATRIBUTO_DB_LATITUDE, ATRIBUTO_DB_LONGITUDE, ATRIBUTO_DB_NOME, ATRIBUTO_DB_INFORMACAO, ATRIBUTO_DB_ID_TIPO_EVENTO, ATRIBUTO_DB_DATA, ATRIBUTO_DB_HORA},
				ATRIBUTO_DB_INFORMACAO +" = '"+informacao+"'", null, null, null, null);

		while(c.moveToNext()){
			
			Log.d("Debug getEvento(String informacao): ", 
					"EventoId: "+c.getString(c.getColumnIndex(ATRIBUTO_DB_ID))
					+"| EventoNome: "+c.getString(c.getColumnIndex(ATRIBUTO_DB_NOME)) 
					+"| EventoInformação: "+c.getString(c.getColumnIndex(ATRIBUTO_DB_INFORMACAO))
					+"| EventoLatitude: "+c.getString(c.getColumnIndex(ATRIBUTO_DB_LATITUDE))
					+"| EventoLongitude: "+c.getString(c.getColumnIndex(ATRIBUTO_DB_LONGITUDE))
			        );
			
			Evento eventoRetorno = new Evento( 
					c.getInt(c.getColumnIndex(ATRIBUTO_DB_ID)), 
					Integer.parseInt(  c.getString(c.getColumnIndex(ATRIBUTO_DB_LATITUDE)) ), 
					Integer.parseInt(  c.getString(c.getColumnIndex(ATRIBUTO_DB_LONGITUDE)) ),
					c.getString(c.getColumnIndex(ATRIBUTO_DB_NOME)),
					c.getString(c.getColumnIndex(ATRIBUTO_DB_INFORMACAO)),
					tipoEvService.getTipoEvento( c.getInt(c.getColumnIndex(ATRIBUTO_DB_ID_TIPO_EVENTO) ) ),
					c.getString(c.getColumnIndex(ATRIBUTO_DB_DATA)),
					c.getString(c.getColumnIndex(ATRIBUTO_DB_HORA))
					);
			
			//c.close();
			return eventoRetorno;				
		}
		
		return null;		
	}
	
	public Evento getEvento(int id){
		
		tipoEvService = new TipoEventoService(this.db);
		Cursor c = db.query(TABELA_EVENTO, new String[]{ATRIBUTO_DB_ID, ATRIBUTO_DB_LATITUDE, ATRIBUTO_DB_LONGITUDE, ATRIBUTO_DB_NOME, ATRIBUTO_DB_INFORMACAO, ATRIBUTO_DB_ID_TIPO_EVENTO, ATRIBUTO_DB_DATA, ATRIBUTO_DB_HORA},
				ATRIBUTO_DB_ID +" = "+id, null, null, null, null);
		
		while(c.moveToNext()){
			
			Log.d("Debug getEvento(String informacao): ", 
					"EventoId: "+c.getString(c.getColumnIndex(ATRIBUTO_DB_ID))
					+"| EventoNome: "+c.getString(c.getColumnIndex(ATRIBUTO_DB_NOME)) 
					+"| EventoInformação: "+c.getString(c.getColumnIndex(ATRIBUTO_DB_INFORMACAO))
					+"| EventoLatitude: "+c.getString(c.getColumnIndex(ATRIBUTO_DB_LATITUDE))
					+"| EventoLongitude: "+c.getString(c.getColumnIndex(ATRIBUTO_DB_LONGITUDE))
			        );
			
			Evento eventoRetorno = new Evento( 
					c.getInt(c.getColumnIndex(ATRIBUTO_DB_ID)), 
					Integer.parseInt(  c.getString(c.getColumnIndex(ATRIBUTO_DB_LATITUDE)) ), 
					Integer.parseInt(  c.getString(c.getColumnIndex(ATRIBUTO_DB_LONGITUDE)) ),
					c.getString(c.getColumnIndex(ATRIBUTO_DB_NOME)),
					c.getString(c.getColumnIndex(ATRIBUTO_DB_INFORMACAO)),
					tipoEvService.getTipoEvento( c.getInt(c.getColumnIndex(ATRIBUTO_DB_ID_TIPO_EVENTO) ) ),
					c.getString(c.getColumnIndex(ATRIBUTO_DB_DATA)),
					c.getString(c.getColumnIndex(ATRIBUTO_DB_HORA))
					);
			
			//c.close();
			return eventoRetorno;				
		}
		
		return null;
	}
	

	


	
	
	
}
