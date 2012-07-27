package pacote.android.Service;

import java.util.ArrayList;

import pacote.android.Beans.TipoEvento;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TipoEventoService {

	private SQLiteDatabase db;	
	private final static String TABELA_TIPOEVENTO = "tipoEvento";
	private final static String ATRIBUTO_DB_ID = "id";
	private final static String ATRIBUTO_DB_DESCRICAO = "descricao";

	
	public TipoEventoService(SQLiteDatabase d){

		this.db  = d;
		
		StringBuilder sb2 = new StringBuilder();
		sb2.append(" CREATE TABLE IF NOT EXISTS "+TABELA_TIPOEVENTO);
		sb2.append(" ( ");
		sb2.append( ATRIBUTO_DB_ID + " integer primary key autoincrement NOT NULL,");	
		sb2.append( ATRIBUTO_DB_DESCRICAO + " varchar(50) NOT NULL");
		sb2.append(" ); ");
		//Log.d("SQL GERADA construtor TipoEventoService:  ", ""+sb2.toString() );
		db.execSQL(sb2.toString());
	}

	public boolean inserirTipoEvento(TipoEvento tipoEvento){
		
		ContentValues cv = new ContentValues();		
		cv.put(ATRIBUTO_DB_DESCRICAO, tipoEvento.getDescricao() );
		
	
		long res = db.insert(TABELA_TIPOEVENTO, ATRIBUTO_DB_ID, cv);
		
		
		//Log.d("RETORNO INSERÇÃO TipoEvento: ", ""+res);
		
		if(res > 0){
			
			//Log.d("INSERÇÃO DE DADOS ", "Dados inseridos com sucesso!");
			return true;
		}else{
			
			//Log.d("ERRO INSERÇÃO DE DADOS ", "Erro, a inserção não pode ser efetuada.!");
			return false;
		}
	}

	
	
	public TipoEvento getTipoEvento(int id){
		
		Cursor c = db.query(TABELA_TIPOEVENTO, new String[]{ATRIBUTO_DB_ID, ATRIBUTO_DB_DESCRICAO}, ATRIBUTO_DB_ID+" = "+id, null, null, null, null);
				
		while(c.moveToNext()){
		
/*			Log.d("Debug getTipoEvento(int id): ", 
					"TipoEventoId: "+c.getString(c.getColumnIndex("id"))
					+"| TipoEventoDescrição: "+c.getString(c.getColumnIndex("descricao")) 
			);
*/			
			TipoEvento tipoEventoRetorno = new TipoEvento( 
					c.getInt(c.getColumnIndex(ATRIBUTO_DB_ID)), 
					c.getString(c.getColumnIndex(ATRIBUTO_DB_DESCRICAO))  
					);
			
			//c.close();
			
			return tipoEventoRetorno;
		}
		return null;
	}
	
	public TipoEvento getTipoEvento(String descricao){
		
		Cursor c = db.query("tipoEvento", new String[]{ATRIBUTO_DB_ID, ATRIBUTO_DB_DESCRICAO}, ATRIBUTO_DB_DESCRICAO+" = '"+descricao+"'", null, null, null, null);
		
		while(c.moveToNext()){
		
/*			Log.d("Debug getTipoEvento(String descricao): ", 
					"TipoEventoId: "+c.getString(c.getColumnIndex("id"))
					+"| TipoEventoDescrição: "+c.getString(c.getColumnIndex("descricao")) 
			);
*/			
			TipoEvento tipoEventoRetorno = new TipoEvento( 
					c.getInt(c.getColumnIndex(ATRIBUTO_DB_ID)), 
					c.getString(c.getColumnIndex(ATRIBUTO_DB_DESCRICAO))  
					);
			
			//c.close();
			
			return tipoEventoRetorno;
		}
		return null;
	}

	public void removeTabelaTipoEvento(){
		
		StringBuilder sb = new StringBuilder();
		sb.append(" DROP TABLE "+TABELA_TIPOEVENTO);
				
		//Log.d("SQL GERADA removeTabelaTipoEvento():  ", ""+sb.toString() );
				
		db.execSQL(sb.toString());
				
	}

	public void deletarTodosTiposDeEvento(){
		
		Cursor c = db.query(TABELA_TIPOEVENTO, new String[]{ATRIBUTO_DB_ID, ATRIBUTO_DB_DESCRICAO},
				null, null, null, null, null);
		
		while(c.moveToNext()){
			
			if(deletarTipoEvento( c.getInt(c.getColumnIndex("id"))  ) ){
				
				Log.d("Remoção TipoEvento ", "Remoção do Tipoevento id ["+c.getInt(c.getColumnIndex("id"))+"] efetuada com sucesso!" );
			}
				
		}
		//c.close();		
		
	}

	public ArrayList<TipoEvento> getTiposEvento(){				
		
		ArrayList<TipoEvento> list = new ArrayList<TipoEvento>();
		
		Cursor c = db.query(TABELA_TIPOEVENTO, new String[]{ATRIBUTO_DB_ID, ATRIBUTO_DB_DESCRICAO},
				null, null, null, null, null);					
		
		int contador = 0;
		
		while(c.moveToNext()){
			
			contador++;
			
			list.add( new TipoEvento( 
					Integer.parseInt( c.getString(c.getColumnIndex(ATRIBUTO_DB_ID)) ),
					c.getString(c.getColumnIndex(ATRIBUTO_DB_DESCRICAO))
					));
		}
		//c.close();		
		
/*		Log.d("Debug TipoEventos ", "QUANTIDADE TipoEventos: "+contador);
		
		for(int i = 0; i < list.size(); i++){
			
			Log.d("Debug TiposEvento: ["+i+"]", 
					"TipoEventoId: "+list.get(i).getId()
					+" TipoEventoDescrição: "+list.get(i).getDescricao() 
			);
		}		
*/		
		return list;
	}

	
	public boolean deletarTipoEvento(int id){
		
		return db.delete(TABELA_TIPOEVENTO, ATRIBUTO_DB_ID+" = "+ id, null) > 0;	
	}

	
	
}
