package Principal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import entidades.DatosPersona;
import entidades.NIE;
import entidades.NIF;
import utils.ConexBD;

public class Principal_GabrielFernandezMelendez {
	public static void Main(String[] args) {
		
		System.out.println("iniciando!");
		//prueba del metodo del ejercicio 1
		DatosPersona.exportarPersonasAlfabetico();
		//prueb del metodo del ejercicio 2
		DatosPersona.insertarPersonas();
		
		
		//  nota post examen, este ejercicio  no me dio  tiempo de hacerlo pero tomar en cuenta
		//que una funcion esttica en un metodo main  no necesita ser declarado como un metodo
		
		// explicado por partes este metodo!!
		//primero realizamos la declaraccion de las clases relacionadas con sql necesarias 

		Connection conex= null;
		Statement consult= null;
		ResultSet resultado=null;
		//el fichero donde se van a exportar los datos leidos de la base de datos
		//y los correspondientes escritores para esribir sobre el fichero
		File fichero=new File("Atletas_Alfabetico2.txt");
		FileWriter escritor=null;
		PrintWriter buffer=null;
		
		try {
			escritor=new FileWriter(fichero);
			buffer=new PrintWriter(escritor);
			conex=ConexBD.establecerConexion();
			String consultaString="select * from personas order by nombre asc";
			//aqui  hay  otro metodo para conectarse a la base de datos en caso de que falle el primero
			if(conex==null) 
				conex=ConexBD.getCon();
			//aqui  ya metemos la consulta en la variable de conex que esta vinculada a la base de datos
			consult=conex.createStatement();
			resultado=consult.executeQuery(consultaString);
			//una vez se ejecuta la consulta hacemos un  bucle while cn una condicion que nos mantendra recibiendo informacion
			//hasta que finalicemos de recibir la informacion
			while(resultado.next()) {
				//aqui  hay que declarar los tipos adecuador para almacenar los datos recibidos
				int id = resultado.getInt(1);
				String nombre=resultado.getString(2);
				String telefono=resultado.getString(3);
			    //importarte!! este casteo para las fechas
				java.sql.Date fecha=resultado.getDate(4);
				java.time.LocalDate fechadate=fecha.toLocalDate();
				String documentacion=resultado.getString(5);
				//LO METO TODO EN UN  OBJETO PARA PODER IMPORTARLO A UN  FICHERO
				DatosPersona Dp=new DatosPersona(id,nombre,telefono,fechadate);
				if (Character.isLetter(documentacion.charAt(0))) /// si comienza por una letra es un NIE
					Dp.setNifnie(new NIE(documentacion));
				else
					Dp.setNifnie(new NIF(documentacion));
				buffer.write(Dp.data() + "\n");
				buffer.flush();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				//en la clausula finally se cierra todo lo relacionado a la conexion con laq base de datos!
				System.out.println("Cerrando recursos...");
				if (resultado != null)
					resultado.close();
				if (consult != null)
					consult.close();
				if (conex != null)
					conex.close();
			} catch (SQLException e) {
				System.out.println("Se ha producido una Excepcion:" + e.getMessage());
				e.printStackTrace();
			}
		}
		System.out.println("final!");
		
		
		
		
        
        
        
    }
}
