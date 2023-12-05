package com.luiz.prova1;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import Processing.CalculoDinamico;

/**
 * A demonstration application showing a time series chart where you can
 * dynamically add (random) data by clicking on a button.
 *
 */
public class DynamicData extends ApplicationFrame {

	private static final long serialVersionUID = 1L;

	private TimeSeries series;

	private double lastValue = 100;
	
	private ArrayList<Double> listaRetorno;

	@SuppressWarnings("deprecation")
	public DynamicData(final String title, double p, double s) {

		
		super(title);
		
		listaRetorno = new ArrayList<>();
		this.series = new TimeSeries("Time ", Millisecond.class);
		final TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
		final JFreeChart chart = createChart(dataset,  p,  s, title);

		final ChartPanel chartPanel = new ChartPanel(chart);
		final JPanel content = new JPanel(new BorderLayout());
		content.add(chartPanel);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(content);

	}


	private JFreeChart createChart(final XYDataset dataset, double p, double s, final String title) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart(title, "Media Movel", "Valor", dataset,
				true, true, false);
		final XYPlot plot = result.getXYPlot();
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis.setFixedAutoRange(60000.0); // 60 seconds
		axis = plot.getRangeAxis();
		axis.setRange(p, s);
		return result;
	}

	public static ArrayList<Double> dados;
	
	public void incrementValue(int ativo) {
		
		dados = Corretora.publicPRICE.get(ativo);
		
		if ( dados.size() > 1) {
			
			CalculoDinamico.MMcurta(dados, listaRetorno);
			
			if(listaRetorno.size()>1) {
				
				this.lastValue = listaRetorno.get(listaRetorno.size()-1);
				final Millisecond now = new Millisecond();
				System.out.println("Now = " + now.toString());
				this.series.add(new Millisecond(), this.lastValue);
				
			}

		}
	}

}