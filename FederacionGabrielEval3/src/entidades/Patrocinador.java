package entidades;

public class Patrocinador implements Comparable<Patrocinador> {
	private long id;
	private String nombre;
	private String web;
	private double dotacion;
	
	Responsable resp;
	
	//constructor por defecto
	public Patrocinador() {
		
	}
	//constructor con todos los atributos como argumento
	public Patrocinador(long id, String nombre, String web, double dotacion,Responsable resp) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.web = web;
		this.dotacion = dotacion;
		this.resp=resp;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public double getDotacion() {
		return dotacion;
	}
	public void setDotacion(double dotacion) {
		this.dotacion = dotacion;
	}
	@Override
	public int compareTo(Patrocinador o) {
	 if(this.getDotacion()>o.getDotacion()) {
		 return 1;
	 }
	 if(this.getDotacion()<o.getDotacion()) {
		 return -1;
	 }
	 //aqui deberia de haber una  con fechas pero  no conozco sus metodos pero seria misma estructira de if 
	 if(this.getId()>o.getId()) {
		 return 1;
	 }
	 if(this.getId()<o.getId()) {
		 return -1;
	 }
		return 0;
	}
	
	//ejercicio 8 del examen 
	public String data() {
		return ""+this.getId()+"|"+resp.getId()+"|"+this.getNombre()+"|"+this.getDotacion()+"|"+this.getWeb();
	}
	
	
	
	

}
