package Processing;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CriaArquivoExcel {

	public static void criarArquivo(String nomeArquivo, ArrayList<Double> close, ArrayList<double[]> calculo) {

		System.out.println("Gerando o arquivo {}" + nomeArquivo);

		try (var workbook = new XSSFWorkbook(); var outputStream = new FileOutputStream(nomeArquivo)) {
			var planilha = workbook.createSheet("Calculos");
			int numeroDaLinha = 0;

			adicionarCabecalho(planilha, numeroDaLinha++);

			for (int i = 0; i < close.size(); i++) {

				var linha = planilha.createRow(numeroDaLinha++);
				adicionarCelula(linha, 0, close.get(i));
				adicionarCelula(linha, 1, calculo.get(0)[i]);
				adicionarCelula(linha, 2, calculo.get(1)[i]);
				adicionarCelula(linha, 3, calculo.get(2)[i]);
				adicionarCelula(linha, 4, calculo.get(3)[i]);
				adicionarCelula(linha, 5, calculo.get(4)[i]);
			}

			workbook.write(outputStream);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado: {}" + nomeArquivo);
		} catch (IOException e) {
			System.out.println("Erro ao processar o arquivo: {} " + nomeArquivo);
		}
		System.out.println("Arquivo gerado com sucesso!");
	}

	private static void adicionarCabecalho(XSSFSheet planilha, int numeroLinha) {
		var linha = planilha.createRow(numeroLinha);
		adicionarCelula(linha, 0, "Close");
		adicionarCelula(linha, 1, "MM curta");
		adicionarCelula(linha, 2, "MM inter");
		adicionarCelula(linha, 3, "MM longa");
		adicionarCelula(linha, 4, "MME");
		adicionarCelula(linha, 5, "DP");
	}

	private static void adicionarCelula(Row linha, int coluna, Double close) {
		Cell cell = linha.createCell(coluna);
		cell.setCellValue(close);
	}

	private static void adicionarCelula(Row linha, int coluna, double valor) {
		Cell cell = linha.createCell(coluna);
		cell.setCellValue(valor);
	}

	private static void adicionarCelula(Row linha, int coluna, String valor) {
		Cell cell = linha.createCell(coluna);
		cell.setCellValue(valor);
	}
}