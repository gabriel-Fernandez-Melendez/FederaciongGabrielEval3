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
import java.util.LinkedList;
import java.util.List;
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
	//nota despues del exameen:es mas correcto el uso e this! y con este llamar al setter del atributo , antes que llamar al 
	//atributo directamente
	public String data() {
		return "" + this.getId() + "|" + this.getNombre() + "|" + this.getTelefono() + "|"
				+ this.getFechaNac().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")) + "|"
				+ this.getNifnie().mostrar();	
	}
	
	
	//apartado c del primer ejercicio de exportar personas
	public static void exportarPersonasAlfabetico() {
		ArrayList<DatosPersona> personas=new ArrayList<DatosPersona>();
		for(DatosPersona Dp: Datos.PERSONAS) {
			personas.add(Dp);
		}
		//a este metodo sort le pasas la coleccion y el metodo de orden alfabetico  para que se ordene
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
				//nota post examen:cuidado! habia puesto que imprimiera solo el nombre, deme de imprimir el metodo data!
				buffer.println(Dp.data()+"\n");
				//muy importante ademas hacer el flush
				buffer.flush();
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
	//nota post examen: tuve problemas con este ejercicio , pero tomar en cuenta que en los comparadores pueden venir bien los if else
	@Override
	public int compareTo(DatosPersona o2) {
		// si la fecha_nac de this es posterior a la de o2, entonces this es menor (en
		// edad) que o2
		if (this.getFechaNac().isAfter(o2.getFechaNac()))
			return -1;
		else
		// si la fecha_nac de this es posterior a la de o2, entonces this es menor (en
		// edad) que o2
		if (this.getFechaNac().isBefore(o2.getFechaNac()))
			return 1;
		else {
			// si la fecha_nac de this la misma de o2, entonces se desempata en funcion del
			// campo Documentacion
			//nota post examen: ESTO ES LO IMPORTANTE!, ver como atraves de elses se puede declarar
			//otro comparador dentro del propio comparador con el metodo compare to 
			return this.getNifnie().compareTo(o2.getNifnie());
		}
		/// Otra forma más sencilla sería esta:
		//return this.getFechaNac().compareTo(o2.getFechaNac());
		
	}
	
	//apartado b ejercicio 2
	//nota post examen: la conexion obiamente no  puede ir dentro del bucle, eso haria que tenga que 
	//conectarse despues de cada vuelta,consejo declarar la conecion lo primero!
	public static boolean insertarPersonas() {
		//cambie el metodo de void a boolean para que devuelva true si sale bien!
		boolean ret = false;
		String consultaInsertStr1 = "insert into personas(id, nombre, telefono, fechanac, nifnie) values (?,?,?,?,?)";
		try {
			Connection conex = ConexBD.establecerConexion();
			PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr1);

			List<DatosPersona> personas = new LinkedList<>();
			for (DatosPersona dp : Datos.PERSONAS) {
				personas.add(dp);
			}
			Collections.sort(personas);
			Iterator<DatosPersona> it = personas.iterator();
			while (it.hasNext()) {
				DatosPersona dp = (DatosPersona) it.next();
				pstmt.setLong(1, dp.getId());
				pstmt.setString(2, dp.getNombre());
				pstmt.setString(3, dp.getTelefono());
				//én el examen  nos faltaba meter la fecha , aqui lo convierte primero en un  valor valido y luego ya lo incluye
				//pstmt.setdate!
				java.sql.Date fechaSQL = java.sql.Date.valueOf(dp.getFechaNac());
				pstmt.setDate(4, fechaSQL);
				pstmt.setString(5, dp.getNifnie().mostrar());
				int resultadoInsercion = pstmt.executeUpdate();
				ret = (resultadoInsercion != 0);
			}
		} catch (SQLException e) {
			System.out.println("Se ha producido una SQLException:" + e.getMessage());
			e.printStackTrace();
			ret = false;
		}

		return ret;
	}
	

}
