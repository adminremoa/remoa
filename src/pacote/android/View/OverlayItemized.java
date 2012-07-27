package pacote.android.View;

/**
 * @author Ricardo Burg Machado
 * @version 5.6
 * @since 2012
 */

import java.util.ArrayList;

import pacote.android.Beans.Evento;


import android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
 
public class OverlayItemized extends ItemizedOverlay{
	
	
	ArrayList mOverlays = new ArrayList();

	
	public static SQLiteDatabase sQLiteDatabase;
	private Evento evento;
	private Context context;
	
	
	public OverlayItemized(Drawable defaultMarker, Context context, SQLiteDatabase sQLiteDatabase) {
		
		super(boundCenterBottom(defaultMarker));
		this.context = context;
		this.sQLiteDatabase = sQLiteDatabase;
	}
	
	
	
	@Override
	protected OverlayItem createItem(int i) {
	  return (OverlayItem) mOverlays.get(i);
	}
	
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
 
	
	public void addOverlay(OverlayItem overlay, Evento evento, Context context) {
		
		mOverlays.add(overlay);
	    populate();
	    this.evento = evento;
	    this.context = context;	    
	}	
	
	
	@Override
	public int size() {
	  return mOverlays.size();
	}
 
	@Override
	protected boolean onTap(int index) {
		
		
	  OverlayItem item = (OverlayItem) mOverlays.get(index);
	
	  
	  DialogVotoEventoActivity dialogVotoEventoActivity = new DialogVotoEventoActivity(context, evento, sQLiteDatabase);
	
	  return true;
	
	}
	
	
}