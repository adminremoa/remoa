package pacote.android.Service;

import java.io.Serializable;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

public class ConectaBanco {

	private static SQLiteDatabase sQLiteDatabase;
	 
	
	public SQLiteDatabase getBanco() {
		return sQLiteDatabase;
	}

	public void setBanco(SQLiteDatabase banco) {
		this.sQLiteDatabase = banco;
	}

	public ConectaBanco(SQLiteDatabase b) {
		this.sQLiteDatabase = b;
	}
	
}
