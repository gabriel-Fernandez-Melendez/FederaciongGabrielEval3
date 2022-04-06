package Principal;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

import entidades.Patrocinador;
import entidades.Responsable;
import utils.Datos;

public class PrincipalExam10 {

	public static void main(String[] args) {
		
		ArrayList<Patrocinador>patro=new ArrayList<Patrocinador>();
		Responsable r1 = new Responsable (1, "902422202", LocalTime.of(00, 00), LocalTime.of(23, 59), Datos.buscarPersonaPorId(1011));
		Responsable r2 = new Responsable (2, "985181105", LocalTime.of(9, 00), LocalTime.of(18, 00), Datos.buscarPersonaPorId(1012));
		Responsable r3 = new Responsable (3, "985103000", LocalTime.of(8, 30), LocalTime.of(20, 00), Datos.buscarPersonaPorId(1013));
		Responsable r4 = new Responsable (4, "985185503", LocalTime.of(8, 30), LocalTime.of(18, 00), Datos.buscarPersonaPorId(1014));

		Patrocinador [] PATROCINADORES = {
		new Patrocinador (1, "ALSA", "www.alsa.es", 500.00F, r1),
		new Patrocinador (2, "Ayto.Gijón", "www.gijon.es", 250.00F, r2),
		new Patrocinador (3, "Universidad de Oviedo", "www.uniovi.es", 350.50F, r3),
		new Patrocinador (4, "CIFP La Laboral", "www.cifplalaboral.es", 255.99F, r4)
		};
	
	}
	//funcion fuera del main  que muestro  lo que hay que realizar para recorrer un array
    public static void recorrerPatrocinadores(ArrayList<Patrocinador> par ) {
    	String path="patrocinadores.dat";
    	try {
			FileOutputStream fichero = new FileOutputStream(path, false);
			ObjectOutputStream escritor = new ObjectOutputStream(fichero);
			for(Patrocinador p:par) {
				Collections.sort(par);
    	    escritor.writeObject((Patrocinador) p);
    	    escritor.flush();
    	}
			escritor.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	
    }
}
