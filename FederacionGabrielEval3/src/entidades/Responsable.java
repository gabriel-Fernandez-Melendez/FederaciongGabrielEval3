package entidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Responsable {
	private long id;
	private String telefono;
	private LocalTime horarioIni;
	private LocalTime horarioFin;
	
	DatosPersona dp;

	// contructor por defecto
	public Responsable() {

	}

	// contructor con todos los metodos
	public Responsable(long id, String telefono, LocalTime horarioIni, LocalTime horarioFin,DatosPersona dp) {
		super();
		this.id = id;
		this.telefono = telefono;
		this.horarioIni = horarioIni;
		this.horarioFin = horarioFin;
		this.dp=dp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalTime getHorarioIni() {
		return horarioIni;
	}

	public void setHorarioIni(LocalTime horarioIni) {
		this.horarioIni = horarioIni;
	}

	public LocalTime getHorarioFin() {
		return horarioFin;
	}

	public void setHorarioFin(LocalTime horarioFin) {
		this.horarioFin = horarioFin;
	}
	
	
	//ejercicio 4 del examen 10,  no estoy seguro como incluir el campo  id persona preguntar luego de continuar con el examen 
	public String data() {
		return ""+this.id+"|"+this.telefono+"|"+this.horarioIni.format(DateTimeFormatter.ofPattern("dd/MM/YYYY"))+"|"+this.horarioFin.format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));
	}
	
	public void importarResponsables() {
		String path="responsables.txt";
		File fic=new File(path);
		FileReader lector=null;
		BufferedReader buffer=null;
	    
		try {
			lector=new FileReader(path);
			buffer=new BufferedReader(lector);
			String linea;
			while ((linea = buffer.readLine()) != null) {
				String[] campos = linea.split("\\|");
				String id=campos[0];
				String telefono=campos[1];
				String horarioini=campos[2];
				String horariofin=campos[3];
				//este ejercicio ha de continiar de alguna manera pero no entiendo por donde 
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
	}

}}
