package pacote.android.Beans;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;



public class DataHora {

	private static GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT-3"),new Locale("pt_BR")); 

	public DataHora(){ }
	
	
	public static String getDataAtual(){

		SimpleDateFormat formatador = new SimpleDateFormat("dd'/'MM'/'yyyy",new Locale("pt_BR")); 
		formatador.setTimeZone( TimeZone.getTimeZone("GMT-03:00"));  
		String dataAtual =formatador.format(calendar.getTime()).toString();
		return dataAtual;
	}
	
	public static String getHoraAtual(){
		
		SimpleDateFormat formatadorHora = new SimpleDateFormat("HH':'mm",new Locale("pt_BR")); 
		formatadorHora.setTimeZone( TimeZone.getTimeZone("GMT-03:00"));  
		String horaAtual = formatadorHora.format(calendar.getTime()).toString();
		return horaAtual;
	}
	
}
