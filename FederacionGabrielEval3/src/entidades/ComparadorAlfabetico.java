package entidades;

import java.util.Comparator;

public class ComparadorAlfabetico implements Comparator<DatosPersona> {

	
	//ejercicio 1 apartado b: el metodo ha sido implementado con la clase comparator para que se ordene de forma alfabetica atraves del nombre de la persona 
	@Override
	public int compare(DatosPersona o1, DatosPersona o2) {
		return o1.getNombre().compareToIgnoreCase(o2.getNombre());
	}

}
