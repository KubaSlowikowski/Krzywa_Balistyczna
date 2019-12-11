package libapp;

import java.awt.Color;
import java.awt.Shape;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DrawPanel {
	
	private static JFreeChart createChart( XYDataset dataset) {
		
		JFreeChart chart = ChartFactory.createXYLineChart(
                "Graficzna reprezentacja rzutu balistycznego", "Zasieg [m]", 
                "Wysokosc [m]", dataset, PlotOrientation.VERTICAL,
                true, false, false);
        chart.setBackgroundPaint(Color.lightGray);
        
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        Shape sh = renderer.getSeriesShape(1);
        renderer.setSeriesShape(2, sh);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesLinesVisible(2, false);
        return chart;
	}
	
	private static XYDataset createDataset() {
		
		double y = 0;
		float x = 0;
		
		final double tan = Math.tan(Math.toRadians(Dane.kat));
		final double cos = Math.cos(Math.toRadians(Dane.kat));
		final double m = 1;
		final double b = 0.05;
		final double g = 9.81;
		final double v = Dane.predkosc;
		final float temp;
		
		if(Dane.kat>1)
			temp = 0.03f;
		else 
			temp=0.0005f;
		
        XYSeries s1 = new XYSeries("Trajektoria lotu");
        do
        {
        	y = (tan + m*g/(b*v*cos)) * x + g*(Math.pow(m, 2)/Math.pow(b, 2)) *	Math.log(1 - x*b/(m*v*cos));
        	s1.add(x,y);

        	x+=temp;
        }
        while(y>=0);
        
        Dane.zasieg = x;
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1);
		return dataset;
	}
	
	public static JPanel createWoPanel() {
		JFreeChart chart = createChart(createDataset());
		
        return new ChartPanel(chart);
	}
	
}