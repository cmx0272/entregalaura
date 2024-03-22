package fp.funciones;

import us.lsi.tools.Preconditions;  
import java.util.List;
import java.util.ArrayList;

public class Funciones {
	
	//EJERCICIO 1

	public static boolean esPrimo(Integer n) {
		Preconditions.checkArgument(n>=1, "El numero es nulo o menor a 2");
		
		boolean res = true;
		for(Integer i= 2;i <= Math.sqrt(n);i++) {
			if(n%i == 0) {
				res = false;
				break;
			}
		}
		return res;
	}
	
	//EJERCICIO 2
	
	public static Double factorial(Integer n) {
		if (n == 0 || n == 1) {
            return 1.;
      } else {
            Double factorial = 1.;
            for (int i = 2; i <= n; i++) {
                factorial *= i;
            }
            return factorial; 
        }
	}
	
	public static Double numeroCombinatorio(Integer n, Integer k) {
		Preconditions.checkArgument(n>=k, String.format("El valor n (%d) debe ser mayor que k (%d)", n,k));
		Double denominador = Funciones.factorial(n);
		Double divisor = Funciones.factorial(k)*(Funciones.factorial(n-k));
		return denominador/divisor;
	}
	
	
	//EJERCICIO 3
	
	public static Double calculoNumeroS(Integer n, Integer k) {
		Preconditions.checkArgument(n>=k,String.format("El valor n (%d) debe ser mayor que k (%d)",n,k));
		Double fueraSumatorio = 1/(Funciones.factorial(k));
		Double sumatorio =0. ;
		for(Integer i=0;i<=k;i++) {
			Double primero = Math.pow(-1, i);
			Double segundo = Funciones.numeroCombinatorio(k, i);
			Double tercero = Math.pow((k-i), n);
			sumatorio += primero * segundo * tercero;
			}
		return fueraSumatorio*sumatorio;
	}
	
	
	//EJERCICIO 4
	
	public static List<Integer> diferencias(List<Integer> numeros){
		List<Integer> diferencias= new ArrayList<>();
		
		if (numeros == null || numeros.size() <= 1) {
            return diferencias;
        }
		
		for(Integer i = 1;i < numeros.size();i++) {
			Integer diferencia = numeros.get(i) - numeros.get(i-1);
			diferencias.add(diferencia);
		}
		return diferencias;
	}
	
	
	//EJERCICIO 5
	
	public static String cadenaMasLarga(List<String> caracteres) {
		String masLarga = caracteres.get(0);
		for(String cadena:caracteres) {
			if(cadena.length() > masLarga.length()) {
				masLarga = cadena;
			}
		}
		return masLarga;
	}

	//EJERCICIO A DEFENSA
	
	 public static Double P2(Integer n, Integer k, Integer i) {
	        Preconditions.checkArgument(n >= k, String.format("El valor n (%d) debe ser mayor o igual que k (%d)", n, k));
	        Preconditions.checkArgument(i < k + 1, String.format("El valor i (%d) debe ser menor que k+1 (%d)", i, k + 1));

	        int start = k - 2;
	        double producto = 1.0;

	        for (int j = start; j >= 1; j--) {
	            producto *= (n - (i + j));
	        }

	        return producto;
	    }
	
	
	//EJERCICIO B DEFENSA
	 public static Double C2(Integer n, Integer k) {
	        Preconditions.checkArgument(n > k, String.format("El valor n (%d) debe ser mayor que k (%d)", n, k));

	        double numerador = factorial(n);
	        double denominador = factorial(k + 1) * factorial(n - k - 1);

	        return numerador / denominador;
	    }
	
	
	//EJERCICIO C DEFENSA
	 public static Double S2(Integer n, Integer k) {
	        Preconditions.checkArgument(n > k, String.format("El valor n (%d) debe ser mayor que k (%d)", n, k));

         double resultado = 0.0;
         double factorial_k = factorial(k);
         
         for (int i = 0; i <= k; i++) {
             double primero = Math.pow(-1, i);
             double segundo = numeroCombinatorio(k, i);
             double tercero = Math.pow((k - i), n);
             resultado += primero * segundo * tercero;
         }

         resultado *= (factorial_k / factorial(k + 2));

         return resultado;
     }
	 
}