package com.luiz.prova1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.ui.RefineryUtilities;

//import java.util.concurrent.Semaphore;
//import java.util.concurrent.atomic.AtomicInteger;
import Processing.DataProcessing;
import Processing.Reader;

public class Corretora extends Thread {

	// leitura dos ativos
	private DataProcessing Process;
	private static ArrayList<Reader> Assets = new ArrayList<>();

	// parte divulgada pela corretora
	public static ArrayList<Double> Asset1 = new ArrayList<>();
	public static ArrayList<Double> Asset2 = new ArrayList<>();
	public static ArrayList<Double> Asset3 = new ArrayList<>();
	public static ArrayList<Double> Asset4 = new ArrayList<>();

	// Lista de 4 ativos que serao divulgados publicamente pela corretora
	public static ArrayList<ArrayList<Double>> publicPRICE = new ArrayList<ArrayList<Double>>();

	public static int amostra; // amostra em que percorre o vetor de ativos
	private static AtomicInteger cont; // numero de operações
	private Semaphore semaforo = new Semaphore(2);// semaforo para os caixas 1 e 2
	
	//Atributo para o grafico
	final DynamicData chart1;
	final DynamicData chart2;
	final DynamicData chart3;
	final DynamicData chart4;
	
	//Atributo para salvar os dados em excel
	private static ArrayList<Hashtable<String, String>> caixaGeral;

	public Corretora() {

		// Faz leitura dos dados e retorna uma lista de ativos (Assets)
		Process = new DataProcessing();
		Assets = Process.getAssets();
		amostra = 0;
		cont = new AtomicInteger(1);

		//Instancia dos graficos
		chart1 = new DynamicData("Media Móvel Ativo 1", 0.70, 0.71);
		chart1.pack();
		RefineryUtilities.centerFrameOnScreen(chart1);
		chart1.setVisible(true);

		chart2 = new DynamicData("Media Móvel Ativo 2", 105, 106);
		chart2.pack();
		RefineryUtilities.centerFrameOnScreen(chart2);
		chart2.setVisible(true);
		
		chart3 = new DynamicData("Media Móvel Ativo 3", 0.635, 0.648);
		chart3.pack();
		RefineryUtilities.centerFrameOnScreen(chart3);
		chart3.setVisible(true);
		
		chart4 = new DynamicData("Media Móvel Ativo 4", 127.5, 128.4);
		chart4.pack();
		RefineryUtilities.centerFrameOnScreen(chart4);
		chart4.setVisible(true);

		caixaGeral = new ArrayList<Hashtable<String, String>>();
	}

	//Metodo para laço de repetição dentro da excecução das Threads
	public static boolean adicionando() {
		if ((Asset1.size() != Assets.get(0).getClose().size())) {
			return true;
		}
		return false;
	}

	private void addPrice() {
		Asset1.add(Assets.get(0).getClose().get(amostra));
		Asset2.add(Assets.get(1).getClose().get(amostra));
		Asset3.add(Assets.get(2).getClose().get(amostra));
		Asset4.add(Assets.get(3).getClose().get(amostra));

		publicPRICE.add(0, Asset1);
		publicPRICE.add(1, Asset2);
		publicPRICE.add(2, Asset3);
		publicPRICE.add(3, Asset4);

		chart1.incrementValue(0);
		chart2.incrementValue(1);
		chart3.incrementValue(2);
		chart4.incrementValue(3);
	}

	@Override
	public void run() {
		while (adicionando()) {
			try {
				addPrice();
				criarArquivo();
				amostra++;
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("fim");

	}

	public boolean registrarOperacao(String operacao, String cliente, int ativo, double valorAtual, int posicao,
			double saldo) {
		try {
			if (cont.get() > 1000) {
				System.out.println("NUMERO DE OPERACOES ESGOTADO");
				return false;
			} else {
				// Semaforo para CAIXA 1 e CAIXA 2; permitido somente dois ciientes
				semaforo.acquire();
				// System.out.println("-- ENTROU CLIENTE: " + cliente + "--");

				String data = Assets.get(ativo).getData().get(posicao);
				String hora = Assets.get(ativo).getTime().get(posicao);
				
				//--------Estrutura para salvar os parametros no excel----------
				Hashtable<String, String> registro = new Hashtable<String, String>();

				registro.put("Cliente", String.valueOf(cliente));
				registro.put("Data", data);
				registro.put("Hora", hora);
				registro.put("Operacao", operacao);
				registro.put("Ativo", String.valueOf(ativo));
				registro.put("Valor", String.valueOf(valorAtual));
				registro.put("Saldo", String.valueOf(saldo));

				caixaGeral.add(registro); //ArrayList com os dados à ser salvo
				cont.incrementAndGet();
				
				System.out.println("Op: " + cont + " -> Cliente: " + cliente + ", efetuou " + operacao
						+ " do ativo: " + ativo + " Saldo Atual: " + saldo);
				
				// System.out.println("-- SAIU CLIENTE: " + cliente + "--");
				return true;
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaforo.release();
		}
		return false;
	}
	
	//Metodo para criar o arquivo e salvar os dados
	public synchronized static void criarArquivo() {

		try (var workbook = new XSSFWorkbook(); var outputStream = new FileOutputStream("RegistroCaixaGeral.xlsx")) {
			var planilha = workbook.createSheet("CaixaGeral");
			int numeroDaLinha = 0;

			adicionarCabecalho(planilha, numeroDaLinha++);

			for (int i = 0; i < caixaGeral.size(); i++) {
				var linha = planilha.createRow(numeroDaLinha++);
				adicionarCelula(linha, 0, caixaGeral.get(i).get("Cliente"));
				adicionarCelula(linha, 1, caixaGeral.get(i).get("Data"));
				adicionarCelula(linha, 2, caixaGeral.get(i).get("Hora"));
				adicionarCelula(linha, 3, caixaGeral.get(i).get("Operacao"));
				adicionarCelula(linha, 4, caixaGeral.get(i).get("Ativo"));
				adicionarCelula(linha, 5, caixaGeral.get(i).get("Valor"));
				adicionarCelula(linha, 6, caixaGeral.get(i).get("Saldo"));
			}
			workbook.write(outputStream);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado: RegistroCaixaGeral");
		} catch (IOException e) {
			System.out.println("Erro ao processar o arquivo: RegistroCaixaGeral ");
		}
		System.out.println("Arquivo gerado com sucesso!");
	}

	private static void adicionarCabecalho(XSSFSheet planilha, int numeroLinha) {
		var linha = planilha.createRow(numeroLinha);
		adicionarCelula(linha, 0, "Cliente");
		adicionarCelula(linha, 1, "Data");
		adicionarCelula(linha, 2, "Hora");
		adicionarCelula(linha, 3, "Operação");
		adicionarCelula(linha, 4, "Ativo");
		adicionarCelula(linha, 5, "Valor");
		adicionarCelula(linha, 6, "Saldo atual");
	}

	private static void adicionarCelula(Row linha, int coluna, String valor) {
		Cell cell = linha.createCell(coluna);
		cell.setCellValue(valor);
	}

}
