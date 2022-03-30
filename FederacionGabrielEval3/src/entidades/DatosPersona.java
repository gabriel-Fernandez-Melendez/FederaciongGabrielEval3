package entidades;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

import utils.ConexBD;
import utils.Datos;
import utils.Utilidades;
import validaciones.Validaciones;

public class DatosPersona implements Comparable<DatosPersona> {
	private long id;
	private String nombre;
	private String telefono;
	private LocalDate fechaNac;

	private Documentacion nifnie; //Examen 2 Ejercicio 3.2

	public DatosPersona(long id, String nombre, String telefono, LocalDate fechaNac) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.fechaNac = fechaNac;
	}
	
	//Examen 2 Ejercicio 3.2
	public DatosPersona(long id, String nombre, String telefono, LocalDate fechaNac, Documentacion nifnie) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.fechaNac = fechaNac;
		this.nifnie = nifnie;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public Documentacion getNifnie() {
		return nifnie;
	}

	public void setNifnie(Documentacion nifnie) {
		this.nifnie = nifnie;
	}

	@Override
	public String toString() {
		return nombre + " NIF/NIE: " + nifnie.mostrar() + " Tfn:" + telefono + " ("
				+ fechaNac.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")";
	}

	// Examen 2 Ejercicio 3.3
	// Examen 5 Ejercicio 3
	public static DatosPersona nuevaPersona() {
		DatosPersona ret = null;
		Scanner in;
		long id = -1;
		String nombre = "";
		String tfn = "";
		boolean valido = false;
		do {
			System.out.println("Introduzca el id de la nueva persona:");
			in = new Scanner(System.in);
			id = in.nextInt();
			valido = Validaciones.validarId(id);
			if (!valido)
				System.out.println("ERROR: Valor incorrecto para el identificador.");
			else
				valido = true;
		} while (!valido);
		valido = false;
		do {
			System.out.println("Introduzca el nombre de la nueva persona:");
			in = new Scanner(System.in);
			nombre = in.nextLine();
			valido = Validaciones.validarNombre(nombre);
			if (!valido)
				System.out.println("ERROR: El valor introducido para el nombre no es válido. ");
		} while (!valido);
		do {
			System.out.println("Introduzca el teléfono de la nueva persona:");
			in = new Scanner(System.in);
			tfn = in.nextLine();
			valido = Validaciones.validarTelefono(tfn);
			if (!valido)
				System.out.println("ERROR: El valor introducido para el teléfono no es válido. ");
		} while (!valido);
		System.out.println("Introduzca la fecha de nacimiento de la nueva persona");
		LocalDate fecha = Utilidades.leerFecha();
		System.out.println("¿Va a introducir un NIF? (pulse 'S' par SÍ o 'N' para NIE)");
		boolean esnif = Utilidades.leerBoolean();
		Documentacion doc;
		valido = false;
		do {
			if (esnif)
				doc = NIF.nuevoNIF();
			else
				doc = NIE.nuevoNIE();
			valido = doc.validar();
			if (!valido)
				System.out.println("ERROR: El documento introducido no es válido.");
		} while (!valido);
		ret = new DatosPersona(id, nombre, tfn, fecha, doc);
		return ret;
	}
	
	
	//ejercicio 1 apartado A del metodo data de DatosPersona
	public String data() {
		return "" + id+"|"+nombre+"|" +telefono+"|"+fechaNac.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"|"+nifnie.mostrar();
	}
	
	
	//apartado c del primer ejercicio de exportar personas
	public static void exportarPersonas() {
		ArrayList<DatosPersona> personas=new ArrayList<DatosPersona>();
		for(DatosPersona Dp: Datos.PERSONAS) {
			personas.add(Dp);
		}
		Collections.sort(personas,new ComparadorAlfabetico());
		String path ="atletas_alfabetico.txt";
		File fich= new File(path);
		FileWriter escribir=null;
	    PrintWriter buffer =null;
	    try {
	    try {
	    	//duda de como llega al fichero los datos
			escribir= new FileWriter(fich);
			buffer= new PrintWriter(escribir);
			for(DatosPersona Dp:personas) {
				buffer.println(Dp.nombre);
			}
		}
	    finally{
			if(buffer != null) {
				buffer.close();
			}
			if(escribir != null) {
				escribir.close();
			}
	    }
	    }
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//aqui salen  los datos de errores (con el .err )
			System.err.println("error dato por  ioException");
		}
	}

	//metodo sobreescrito de del compareto del ejercicio 2 apartado a 
	//no  logre meter con un if que si dos fechas son iguales se haga con el nifnie
	@Override
	public int compareTo(DatosPersona o) {
		boolean vale;
		return this.fechaNac.compareTo(o.fechaNac);
		
	}
	
	//apartado b ejercicio 2
	public static void insertarPersonas() {
		ArrayList<DatosPersona> personas = new ArrayList<DatosPersona>();
		for(DatosPersona Dp:Datos.PERSONAS) {
			personas.add(Dp);
		}
		Collections.sort(personas);
		System.out.println("lista ordenadas de personas segun su nombre:");
		Iterator<DatosPersona> it= personas.iterator();
		while(it.hasNext()) {
			DatosPersona per =(DatosPersona)it.next();
			Connection conex =ConexBD.establecerConexion();
			String insert=("insert into Personas(id,nombre,telefono,nifnie)values(?,?,?)");
			
			try {
				PreparedStatement ps = conex.prepareStatement(insert);
				for(DatosPersona Dp:personas) {
					ps.setLong(1,Dp.getId());
					ps.setString(2,Dp.getNombre());
					ps.setString(3,Dp.getTelefono());
					ps.setString(4,Dp.getNifnie().mostrar());	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			
		}
	}

}
