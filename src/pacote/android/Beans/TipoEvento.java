package pacote.android.Beans;

public class TipoEvento {
	
	private int id;
	private String descricao;
	
	public TipoEvento(){	}
	
	public TipoEvento(int id, String descricao){
		
		setId(id);
		setDescricao(descricao);
	}
	
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
	
		this.id = id;
	}
	
	public String getDescricao() {
		
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		
		this.descricao = descricao;
	}

	

}
