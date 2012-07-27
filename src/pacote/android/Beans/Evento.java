package pacote.android.Beans;

import java.util.ArrayList;

public class Evento {
	
	private int idEvento;
	private int latitude;
	private int longitude;
	private String informacao;
	private TipoEvento tipo;
	private String nome;
	private String data;
	private String hora;
	
	private ArrayList<Voto> votos = new ArrayList<Voto>();
	
	
	public Evento(){  }
	
	public Evento(int idEvento, int latitude, int longitude, String nomeEvento, String informacao, TipoEvento tipo, String data, String hora){
		
		setIdEvento(idEvento);
		setLatitude(latitude);
		setLongitude(longitude);
		setInformacao(informacao);
		setTipo(tipo);
		setNome(nomeEvento);
		setData(data);
		setHora(hora);
	}
	
	
	//construtor sem o atributo idEvento, usado no cadastro de novo Evento 
	public Evento(int latitude, int longitude, String nomeEvento, String informacao, TipoEvento tipo, String data, String hora){
			
			setLatitude(latitude);
			setLongitude(longitude);
			setInformacao(informacao);
			setTipo(tipo);
			setNome(nomeEvento);
			setData(data);
			setHora(hora);
	}
	
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public void setVoto(Voto voto){
		
		this.votos.add(voto);		
	}
	
	public ArrayList<Voto> getVotos(){
		
		return this.votos;
	}
	

	public int getIdEvento() {
		
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		
		this.idEvento = idEvento;
	}

	public void setLatitude(int latitude){
		
		this.latitude = latitude;
	}
	
	public int getLatitude(){
		
		return this.latitude;
	}

	public void setLongitude(int longitude){
		
		this.longitude = longitude;
	}
	
	public int getLongitude(){
		
		return this.longitude;
	}

	public void setInformacao(String informacao){
		
		this.informacao = informacao;
	}
	
	public String getInformacao(){
		
		return this.informacao;
	}
	
	public void setTipo(TipoEvento tipo){
		
		this.tipo = tipo;
	}
	
	public TipoEvento getTipo(){
		
		return this.tipo;
	}
	
}
