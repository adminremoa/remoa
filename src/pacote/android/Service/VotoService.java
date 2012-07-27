package pacote.android.Service;
/**
 * @author Ricardo Burg Machado
 * @version 5.6
 * @since 2012
 */

import pacote.android.Beans.Voto;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class VotoService {

	private SQLiteDatabase db;
	private final static String TABELA_VOTOS= "votos";
	private final static String ATRIBUTO_DB_ID = "id";
	private final static String ATRIBUTO_DB_IDEVENTO = "idEvento";
	private final static String ATRIBUTO_DB_VOTO = "voto";	
	
	public VotoService (SQLiteDatabase d) {
		
		this.db = d;
		
		StringBuilder sb3 = new StringBuilder();
		sb3.append(" CREATE TABLE IF NOT EXISTS "+TABELA_VOTOS);
		sb3.append(" ( ");
		sb3.append(ATRIBUTO_DB_ID+" integer primary key autoincrement,");	
		sb3.append(ATRIBUTO_DB_IDEVENTO+" integer NOT NULL,");	
		sb3.append(ATRIBUTO_DB_VOTO+" varchar(50) NOT NULL, ");
		sb3.append("FOREIGN KEY ("+ATRIBUTO_DB_IDEVENTO+") REFERENCES eventos (_id)"); 
		sb3.append(" ); ");
		db.execSQL(sb3.toString());

	}

	
	public boolean inserirVoto(Voto voto){
		
		ContentValues cv = new ContentValues();		
		cv.put(ATRIBUTO_DB_IDEVENTO, 	  ""+voto.getIdEvento()+"");
		cv.put(ATRIBUTO_DB_VOTO,        ""+voto.getVoto()+"");		
	
		long res = db.insert(TABELA_VOTOS, ATRIBUTO_DB_ID, cv);
		
		
		Log.d("RETORNO INSERÇÃO: ", ""+res);
		
		if(res > 0){
			
			Log.d("INSERÇÃO DE DADOS ", "Dados inseridos com sucesso!");
			return true;
		}else{
			
			Log.d("ERRO INSERÇÃO DE DADOS ", "Erro, a inserção não pode ser efetuada.!");
			return false;
		}
	}


	
	public void removeTabelaVotos(){
		
		StringBuilder sb = new StringBuilder();
		sb.append(" DROP TABLE "+TABELA_VOTOS);
				
		Log.d("SQL GERADA removeTabelaVotos():  ", ""+sb.toString() );
				
		db.execSQL(sb.toString());
				
	}

	
	public void deletarTodosVotos(){
		
		Cursor c = db.query(TABELA_VOTOS, new String[]{ATRIBUTO_DB_ID, ATRIBUTO_DB_VOTO, ATRIBUTO_DB_IDEVENTO},
				null, null, null, null, null);
		
		while(c.moveToNext()){
			
			if(deletarVoto( c.getInt(c.getColumnIndex(ATRIBUTO_DB_ID))  ) ){
				
				Log.d("Remoção Voto ", "Remoção do Voto id ["+c.getInt(c.getColumnIndex("id"))+"] efetuada com sucesso!" );
			}
				
		}
		//c.close();		
		
	}

	public boolean deletarVoto(int id){
		
		return db.delete(TABELA_VOTOS, ATRIBUTO_DB_ID+" = "+ id, null) > 0;	
	}
	
	
	
}
