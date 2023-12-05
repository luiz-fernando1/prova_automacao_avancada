package Processing;

/**
*	Classe responsavel pela leitura dos arquivos csv
*	retorna ArrayList de cada coluna
*
*@author Luiz Fernando
*/

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class Reader {
	private String name;
	private ArrayList<String> data;
	private ArrayList<String> time;
	private ArrayList<Double> open;
	private ArrayList<Double> high;
	private ArrayList<Double> low;
	private ArrayList<Double> close;
	private ArrayList<Double> tick;
  
	public Reader(String name, String path) {
		this.name = name;
		data = new ArrayList<String>();
		time = new ArrayList<String>();
		open = new ArrayList<Double>();
		high = new ArrayList<Double>();
		low = new ArrayList<Double>();
		close = new ArrayList<Double>();
		tick = new ArrayList<Double>();
		
		try {
			FileReader filereader = new FileReader(path);
			CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
			CSVReader csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();

			// Read all data at once
			List<List<String>> rows = new ArrayList<List<String>>();
			String[] column = null;
			csvReader.readNext();
			
			while ((column = csvReader.readNext()) != null) {
				rows.add(Arrays.asList(column));
			}

			rows.forEach(cols -> {
				data.add(cols.get(0));
				time.add(cols.get(1));
				open.add(Double.parseDouble(cols.get(2)));
				high.add(Double.parseDouble(cols.get(3)));
				low.add(Double.parseDouble(cols.get(4)));
				close.add(Double.parseDouble(cols.get(5)));
				tick.add(Double.parseDouble(cols.get(6)));
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getData() {
		return data;
	}

	public ArrayList<String> getTime() {
		return time;
	}

	public ArrayList<Double> getOpen() {
		return open;
	}

	public ArrayList<Double> getHigh() {
		return high;
	}

	public ArrayList<Double> getLow() {
		return low;
	}

	public ArrayList<Double> getClose() {
		return close;
	}

	public ArrayList<Double> getTick() {
		return tick;
	}

}