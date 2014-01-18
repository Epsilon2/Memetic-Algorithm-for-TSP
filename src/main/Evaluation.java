package main;

import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.Range;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

public class Evaluation extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1894881778985246405L;

	public static void main(String[] args) {
		final Evaluation eval = new Evaluation();
		eval.pack();
		eval.setVisible(true);
		
		
	}
	
	public Evaluation(){
		String[] nameOfTestInstances = {"att48","brg180","ch150","gr202"};
		int[] numberOfTestRuns = {100,100,50,20};
		int[] timeLimits = {1,2,15,120};
		LinkedList<Double> ilsResults = new LinkedList<Double>();
		LinkedList<Double> memeResults = new LinkedList<Double>();       

        final DefaultBoxAndWhiskerCategoryDataset dataset 
            = new DefaultBoxAndWhiskerCategoryDataset();
		try {
			for(int j = 0; j < nameOfTestInstances.length; j++)
			{
//				dataset.clear();
				for(int i = 0; i<numberOfTestRuns[j]; i++)
				{
					String[] arguments = {nameOfTestInstances[j], String.valueOf(timeLimits[j]), String.valueOf(50), "noDiagrams"};
					double[] results = TSP_Main.main(arguments);
					ilsResults.add(results[0]);
					memeResults.add(results[1]);					
				}
				dataset.add(ilsResults, "Iterated Local Search", nameOfTestInstances[j]);
				dataset.add(memeResults, "Memetic Algorithm", nameOfTestInstances[j]);
				ilsResults.clear();
				memeResults.clear();

			}
	        final CategoryAxis xAxis = new CategoryAxis("Name of TSPLIB instance");
	        final NumberAxis yAxis = new NumberAxis("deviation from optimum in %");
//	        yAxis.setAutoRangeIncludesZero(false);
	        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
//	        renderer.setFillBox(false);
	        renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
	        renderer.setMeanVisible(false);
	        renderer.setMaximumBarWidth(10);
	        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
	        final NumberAxis axis = (NumberAxis) plot.getRangeAxis();
	        axis.setRange(Range.expandToInclude(axis.getRange(), -0.2));
	        final JFreeChart chart = new JFreeChart(
	            "Comparison of ILS and Memetic Algorithm",
	            new Font("SansSerif", Font.BOLD, 16),
	            plot,
	            true
	        );
	        final ChartPanel chartPanel = new ChartPanel(chart);
//	        chartPanel.setPreferredSize(new java.awt.Dimension(900, 540));
	        ChartUtilities.saveChartAsJPEG(new File("evaluation"), chart, 500, 400);
	        setContentPane(chartPanel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
