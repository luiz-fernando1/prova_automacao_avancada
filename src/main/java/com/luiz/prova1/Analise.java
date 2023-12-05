package com.luiz.prova1;

import java.util.ArrayList;
import Processing.CalculoDinamico;

public class Analise extends Thread {

	public static ArrayList<Double> mediaCurta;
	public static ArrayList<Double> mediaInter;
	public static ArrayList<Double> mediaLonga;

	// Ativo a ser analisado
	public ArrayList<Double> AssetList = new ArrayList<>();

	//Variaveis para calculo do Drawdown
	private Double calculo;
	private Double max;
	private Double min;

	// CONSTRUTOR
	public Analise(ArrayList<Double> Asset) {
		this.AssetList = Asset;
		mediaCurta = new ArrayList<Double>();
		mediaInter = new ArrayList<Double>();
		mediaLonga = new ArrayList<Double>();

		calculo = 0.0;
		max = 0.0;
		min = 0.0;
	}

	public void run() {

		while (Corretora.adicionando()) {
			try {
				CalculoDinamico.MMcurta(AssetList, mediaCurta);
				CalculoDinamico.MMinter(AssetList, mediaInter);
				CalculoDinamico.MMlonga(AssetList, mediaLonga);

				Thread.sleep(2000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public boolean tendenciaGeral() {

		if (mediaLonga.size() >= 2) {
			if (mediaCurta.get(mediaCurta.size() - 1) > mediaLonga.get(mediaLonga.size() - 1)) {
				return true;
			}
		}
		return false;
	}

	public boolean drawDown() {
		if (AssetList.get(AssetList.size() - 1) > max) {
			max = AssetList.get(AssetList.size() - 1);
			min = AssetList.get(AssetList.size() - 1);
		} else if (AssetList.get(AssetList.size() - 1) < min) {
			min = AssetList.get(AssetList.size() - 1);
		}
		calculo = ((max - min) / max) * 100.00;
		if (calculo > 15.0) { // Se a queda for maior q 15% vende tudo
			return true;
		}
		return false;
	}
}
