package Processing;

import java.util.ArrayList;

public class CalculoDinamico {

	private static ArrayList<Double> aux_Media;
	private static ArrayList<Double> aux_MediaDP;
	
	
	public CalculoDinamico() {
		aux_Media = new ArrayList<>();
		aux_Media = new ArrayList<>();
	}
	
	// Calculo da media simples
		public static void mediaMovel(ArrayList<Double> valores, double intervalo, ArrayList<Double> resultado) {
			//resultado.add(0.0);
			if(valores.size() > intervalo) {
				double sum = 0;
				for(int j =valores.size(); j > valores.size()-intervalo; j--) {
					sum += valores.get(j-1);
				}
				resultado.add(sum / intervalo);
			}
		}
		
		// Calculo da media exponencial
		public static ArrayList<Double> MediaExpo(ArrayList<Double> valores, double intervalo, ArrayList<Double> resultado){
			double mult = 2/(1+intervalo);
			mediaMovel(valores, intervalo, aux_Media);
			
			if(aux_Media.size() > 1) {
				resultado.add(((valores.get(valores.size()-1) - aux_Media.get(aux_Media.size()-2))*mult) + aux_Media.get(aux_Media.size()-2));
			}else {
				resultado.add(0.0);
			}
			return resultado;
		}
		
		
		public static ArrayList<Double> desvioPadrao(ArrayList<Double> valores, ArrayList<Double> resultado){
			int intervalo = 10;
			
			mediaMovel(valores, intervalo, aux_MediaDP);
			
			if(valores.size() > intervalo) {
				double soma = 0;
				
				for(int j = valores.size(); j > valores.size() - intervalo; j--) {
					soma += (double)Math.pow(valores.get(j-1) - aux_MediaDP.get(aux_MediaDP.size()-1), 2);
				}
				resultado.add(Math.sqrt(soma/intervalo));
			}
			return resultado;
		}
		
		public static void MMcurta(ArrayList<Double> valores, ArrayList<Double> resultado) {
			mediaMovel(valores, 5, resultado);
		}
		public static void MMinter(ArrayList<Double> valores, ArrayList<Double> resultado) {
			mediaMovel(valores, 10, resultado);
		}
		public static void MMlonga(ArrayList<Double> valores, ArrayList<Double> resultado) {
			mediaMovel(valores, 20, resultado);
		}


}
