package Processing;

import java.util.ArrayList;

public class testeCalculo {

	public static void main(String[] args) {

		CalculoEstatico c = new CalculoEstatico();

		// Media Movel exponencial
		ArrayList<double[]> MME = new ArrayList<>();

		// Media Movel Simples
		ArrayList<double[]> MMcurta = new ArrayList<>();
		ArrayList<double[]> MMinter = new ArrayList<>();
		ArrayList<double[]> MMlonga = new ArrayList<>();
		ArrayList<double[]> DP = new ArrayList<>();
		
		// Estrutura q envia preço de fechamento de todos ativos
		ArrayList<ArrayList<Double>> assets = new ArrayList<ArrayList<Double>>();

		for (int i = 0; i < 4; i++) {
			assets.add(c.assets.get(i).getClose());
			MME.add(c.mediaExpo(5, i));
			MMcurta.add(c.MMcurta(i));
			MMinter.add(c.MMinter(i));
			MMlonga.add(c.MMlonga(i));
			DP.add(c.desvioPadrao(5, i));
		}

		ArrayList<double[]> calculo0 = new ArrayList<>();
		calculo0.add(MMcurta.get(0));
		calculo0.add(MMinter.get(0));
		calculo0.add(MMlonga.get(0));
		calculo0.add(MME.get(0));
		calculo0.add(DP.get(0));
		CriaArquivoExcel.criarArquivo("Ativo0.xlsx", assets.get(0), calculo0);

		ArrayList<double[]> calculo1 = new ArrayList<>();
		calculo1.add(MMcurta.get(1));
		calculo1.add(MMinter.get(1));
		calculo1.add(MMlonga.get(1));
		calculo1.add(MME.get(1));
		calculo1.add(DP.get(1));
		CriaArquivoExcel.criarArquivo("Ativo1.xlsx", assets.get(1), calculo1);

		ArrayList<double[]> calculo2 = new ArrayList<>();
		calculo2.add(MMcurta.get(2));
		calculo2.add(MMinter.get(2));
		calculo2.add(MMlonga.get(2));
		calculo2.add(MME.get(2));
		calculo2.add(DP.get(2));
		CriaArquivoExcel.criarArquivo("Ativo2.xlsx", assets.get(2), calculo2);

		ArrayList<double[]> calculo3 = new ArrayList<>();
		calculo3.add(MMcurta.get(3));
		calculo3.add(MMinter.get(3));
		calculo3.add(MMlonga.get(3));
		calculo3.add(MME.get(3));
		calculo3.add(DP.get(3));
		CriaArquivoExcel.criarArquivo("Ativo3.xlsx", assets.get(3), calculo3);

	}

}
