import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

/**
 *	Montoya Montes Pedro
 *	31219536-2
 *
 *	Clase AproxSubsetSum
 *	Aplicamos el algoritmo de aproximación descrito en las páginas 
 *	1128-1133 del libro "Introduction to Algorithms" de Thomas H. Cormen.
 */
public class AproxSubsetSum{

	/**
	 * Método main
	 * Aquí obtenemos de consola los valores con los que vamos a correr el algoritmo.
	 * Obtenemos y creamos el conjunto en una lista y los valores t y epsilón.
	 */
	public static void main(String []args){
		try {

			//Inicializamos el conjunto donde aplicar el algoritmo.
			ArrayList<Integer> s = new ArrayList<Integer>();
			
			System.out.println("Cantidad de valores del conjunto (ejemplo: 4):");
			Scanner sc = new Scanner(System.in);
			int n = sc.nextInt();		

			System.out.println("Ingresa los valores del conjunto (ejemplo: 104 102 201 101):");

			sc = new Scanner(System.in);
			for(int i = 0; i < n; i++){
				s.add(sc.nextInt());
			}

	        Scanner reader = new Scanner(System.in);
			System.out.println("Ingresa un valor epsilon (ejemplo: .4):");
	        double epsi = reader.nextDouble();

	        System.out.println("Ingrese el valor de t(ejemplo: 308):");
	        int t = reader.nextInt();

			System.out.println("****************************\nEl algoritmo se aplicará para:\n"+
								"     t="+t+"\n     e="+epsi + "\nEn el conjunto:\n "+ "    S="+s.toString());

			System.out.println("El resultado es:" + aproxSubsetSum(s,t,epsi)); 

		}catch(Exception e) {
			System.out.println("Los valores que has introducido son incorrectos:\n-> n es un entero positivo \n-> Los elementos de s son enteros positivos \n-> e es un real.");	
			System.out.println("Error en:" + e);
		}
	}

	/**
	 * Método trim que dada una lista L escanea sus elementos y se agrega un valor a la lista L' 
	 * si es el primer elemento de L y regresa L'.
	 *
	 * @param list, la lista a revisar.
	 * @param phi el valor de trimming.
	 * @return listTwo que es la nueva lista modificada.
	 */
	public static ArrayList<Integer> trim(ArrayList<Integer> list, double phi){
		int listLenght = list.size();

		//Obtenemos y creamos la nueva lista con el elemento del primer valor de list.
		int last = list.get(0);
		ArrayList<Integer> listTwo = new ArrayList<Integer>() {{add(list.get(0));}};

		//Ciclo que revisa cada elemento y agregando si cumple con el parámetro 1+epsilón.
		for (int i = 2; i<=listLenght; i++) {
			if (list.get(i-1) >= (last*(1+phi))){
				listTwo.add(list.get(i-1));
				last = list.get(i-1);//Actualizamos el último.
			}
		}
		return listTwo;
	}

	/**
	 * Dadas dos listas, las ordena y mezcla en una nueva lista lFinal.
	 * @param l1 la primer lista
	 * @param l1 la segunda lista
	 * @return lFinal que es la lista ya mezclada y ordenada de l1 y l2.
	 */
	public static ArrayList<Integer> mergeList(ArrayList<Integer> l1, ArrayList<Integer> l2){
		Collections.sort(l1);
		Collections.sort(l2);

		ArrayList<Integer> lFinal=l1;
		lFinal.removeAll(l2);
		l1.addAll(l2);
		Collections.sort(lFinal);
		
		return lFinal;
	}

	/**
	 * Método removeGreater que dada una lista y un entero t, elimina todos los valores mayores a t de la lista.
	 * @param list, la lista a eliminar elementos.
	 * @param t, el valor a comparar.
	 */
	public static void removeGreater(ArrayList<Integer> list, Integer t){
		int n =  list.size();

		Iterator<Integer> it = list.iterator();                        
		while (it.hasNext()) {
			Integer integer = it.next();
			if (t < integer) {
				it.remove();
			}
		}
	}

	/**
	 * Método sumList que dada una lista, le suma un entero entero a cada elemento.
	 */
	public static ArrayList<Integer> sumList(ArrayList<Integer> list, int a){
		ArrayList<Integer> listAdd = new ArrayList<Integer>();
		
		for (Integer i :list ) {
			listAdd.add(i+a);
		}
		return listAdd;
	}

	/**
	 * Método aproxSubsetSum, que dado un conjunto S, un entero t y un valor epsilón
	 * aplica el algoritmo sobre sublistas y aplicando trim sobre las mismas para regresar el máximo de la última sublista.
	 * @param s una lista de enteros
	 * @param t un entero
	 * @param epsi un double que es el factor
	 * @return z* que es el máximo de la última sublista creada.
	 */
	public static int aproxSubsetSum(ArrayList<Integer> s, Integer t, Double epsi){
		Integer n = s.size();

		//Una lista de listas para manejar mejor las sublistas que se crearán y modificarán.
		ArrayList<ArrayList<Integer>> listas = new ArrayList<ArrayList<Integer>>();

		//Una lista inicializada con un entero 0.
		ArrayList<Integer> lZero = new ArrayList<Integer> () {{add(0);}}; 

		listas.add(lZero);

		//Ciclo donde aplicamos trim, merge y eliminación de valores mayores a t.
		for (int i = 1; i<=n ;i++) {
			//Agregamos una lista que es la unión de una lista l1 y l2, donde l1 es la anterior lista y l2 es la anterior lista sumado al valor xi.
			listas.add(mergeList(listas.get(i-1), sumList(listas.get(i-1), s.get(i-1))));
			//A la lista anterior, eliminamos los valores mayores que t.
			removeGreater(listas.get(i-1),t);
			//Aplicamos set para tener el i-ésimo elemento la lista con trim.
			listas.set(i,trim(listas.get(i), epsi/(2*n)));
		}
		//Regresamos con max, el valor más alto de la última lista.
		return Collections.max(listas.get(listas.size()-1));
	}
} 