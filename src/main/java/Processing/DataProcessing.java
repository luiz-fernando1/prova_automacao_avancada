package Processing;
/**
*	Classe responsavel por instanciar um ArrayList do tipo
*	Reader para os 4 ativos para retornar um getAssets
*	
*@author Luiz Fernando
*/
import java.util.ArrayList;

public class DataProcessing {

	private ArrayList<Reader> assets;
	private Reader Asset_1;
	private Reader Asset_2;
	private Reader Asset_3;
	private Reader Asset_4;

	public DataProcessing() {

		assets = new ArrayList<Reader>();

		String path_1 = "C:\\Projetos_java\\prova1\\Dados\\AUDUSD_M10.csv";
		String path_2 = "C:\\Projetos_java\\prova1\\Dados\\EURUSD_M10.csv";
		String path_3 = "C:\\Projetos_java\\prova1\\Dados\\NZDUSD_M10.csv";
		String path_4 = "C:\\Projetos_java\\prova1\\Dados\\USDCAD_M10.csv";

		Asset_1 = new Reader("Asset1", path_1);
		Asset_2 = new Reader("Asset2", path_2);
		Asset_3 = new Reader("Asset3", path_3);
		Asset_4 = new Reader("Asset4", path_4);

		assets.add(Asset_1);
		assets.add(Asset_2);
		assets.add(Asset_3);
		assets.add(Asset_4);
		
	}

	public ArrayList<Reader> getAssets() {
		return assets;
	}

}
