package Processing;

import java.util.ArrayList;

public class CalculoEstatico {

	private DataProcessing Process;
	protected ArrayList<Reader> assets;

	private double[] mediaAritmetica;
	private double[] mediaExponencial;
	private double[] desvioPadrao;
	private int n;

	public CalculoEstatico() {
		Process = new DataProcessing();
		assets = Process.getAssets();
	}

	public double[] mediaMovel(double intervalo, int nAsset) {
		n = assets.get(nAsset).getClose().size();
		mediaAritmetica = new double[n];

		for (int i = 0; i < n; i++) {
			if (i >= intervalo) {
				double sum = 0;
				for (int j = i; j > i - intervalo; j--) {
					sum += assets.get(nAsset).getClose().get(j - 1);
				}
				mediaAritmetica[i - 1] = (sum / intervalo);
			}
		}
		return mediaAritmetica;
	}

	public double[] mediaExpo(double intervalo, int nAsset) {
		double mult = 2 / (1 + intervalo);
		int cont = (int) intervalo;
		double close = 0;
		double[] Aux = mediaMovel(intervalo, nAsset);

		n = assets.get(nAsset).getClose().size();
		mediaExponencial = new double[n];
		mediaExponencial[cont - 1] = Aux[cont - 1];

		for (int j = cont; j < n; j++) {

			close = assets.get(nAsset).getClose().get(j);
			mediaExponencial[j] = (mult * (close - Aux[cont - 1])) + Aux[cont - 1];
			Aux[cont - 1] = mediaExponencial[j];

		}
		return mediaExponencial;
	}

	public double[] desvioPadrao(double intervalo, int nAsset) {
		n = assets.get(nAsset).getClose().size();
		double somediaAritmetica = 0;
		int aux = 0;

		desvioPadrao = new double[n];
		mediaAritmetica = mediaMovel(intervalo, nAsset);

		for (int i = (int) intervalo; i < mediaAritmetica.length; i++) {
			somediaAritmetica = 0;
			for (int j = aux; j < i; j++) {
				somediaAritmetica += (double) Math.pow(assets.get(nAsset).getClose().get(j) - mediaAritmetica[i], 2);
			}

			desvioPadrao[i] = Math.sqrt(somediaAritmetica / intervalo);
			aux++;
		}
		return desvioPadrao;
	}

	public double[] MMcurta(int nAsset) {
		return mediaMovel(5, nAsset);
	}

	public double[] MMinter(int nAsset) {
		return mediaMovel(10, nAsset);
	}

	public double[] MMlonga(int nAsset) {
		return mediaMovel(20, nAsset);
	}

}