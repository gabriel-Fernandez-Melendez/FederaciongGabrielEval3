package entidades;

import java.util.Comparator;

public class ComparadorAlfabetico implements Comparator<DatosPersona> {

	
	//ejercicio 1 apartado b: el metodo ha sido implementado con la clase comparator para que se ordene de forma alfabetica atraves del nombre de la persona
	//nota despues del examen: este metodo es igual al dado en la respuesta , si unica difencia se encuentra en que el metodo usado para la comparacion es 
	//comparetoignorecase y en el de la respuesta es compareto, no creo que afecte a su funcionamiento
	@Override
	public int compare(DatosPersona o1, DatosPersona o2) {
		return o1.getNombre().compareToIgnoreCase(o2.getNombre());
	}

}
