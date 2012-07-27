package pacote.android.Beans;

public class Voto {
	
	private int id;
	private String voto;
	private int idEvento;
	
	
	public Voto(){}
	
	public Voto(int id, String voto, int idEvento){
		
		setId(id);
		setVoto(voto);
		setIdEvento(idEvento);
	}
	
	public String getVoto() {
		
		return voto;
	}

	public void setVoto(String voto) {
		
		this.voto = voto;
	}

	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		
		this.id = id;
	}
	
	public int getIdEvento() {
		
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		
		this.idEvento = idEvento;
	}

}
