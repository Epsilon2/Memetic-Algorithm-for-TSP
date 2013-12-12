package algorithms;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.moeaframework.problem.tsplib.DistanceTable;
import org.moeaframework.problem.tsplib.TSPInstance;
import org.moeaframework.problem.tsplib.Tour;

public abstract class TSP_Algorithm {
	static TSPInstance instance;
	DistanceTable distanceTable;
	LinkedList<Double> results = new LinkedList<Double>();
	Random rnd = new Random();

	public TSP_Algorithm(String s) throws IOException {
		instance = new TSPInstance(new File("./data/tsp/" + s + ".tsp"));
		instance.addTour(new File("./data/tsp/" + s + ".opt.tour"));
		distanceTable = instance.getDistanceTable();
	}

	public double distance(Tour t) {
		return t.distance(instance);
	}

	public double getOptimalDistance() {
		return instance.getTours().get(0).distance(instance);
	}

	protected JFreeChart drawDiagram(String s, List<Double> values) {
		XYSeries graph = new XYSeries(s);
	    XYDataset xyDataset = new XYSeriesCollection(graph);
	    int i = 0;
	    for (double d:values) {
	    	i++;
	    	graph.add(i, d);
	    }
	    JFreeChart chart = ChartFactory.createXYLineChart(
	            "Quality of Solutions", "Number of Iteration", "deviation from optimal solution in %",
	            xyDataset, PlotOrientation.VERTICAL, true, true, false);
	    ChartFrame graphFrame = new ChartFrame("Quality of Solutions vs. Number of Iterations", chart);
	    graphFrame.setVisible(true);
	    graphFrame.setSize(300, 300);
		return chart;
	}
	
	protected double relativeDistance(Tour t){
		return 100*(t.distance(instance) - getOptimalDistance())/getOptimalDistance();
	}
	
	protected double relativeDistance(double d){
		return 100*(d - getOptimalDistance())/getOptimalDistance();
	}
	
	protected int[] createGreedyRandomizedTour(int dimension) {
		int[] newTour = new int[dimension];
		Set<Integer> unvisitedCities = new HashSet<>();
		for(int i = 1; i <= dimension; i++)
			unvisitedCities.add(i);
		newTour[0] = rnd.nextInt(dimension)+1;
		unvisitedCities.remove(newTour[0]);
		for(int i = 1; i < dimension; i++)
		{
			int nearestCity = getNearestCity(newTour[i-1], unvisitedCities);
			if (Math.random() < 0.6 || unvisitedCities.size() <= 1)
			{
				newTour[i] = nearestCity;
				unvisitedCities.remove(nearestCity);
			}
			else
			{
				unvisitedCities.remove(nearestCity);
				int secondNearestCity = getNearestCity(newTour[i-1], unvisitedCities);
				unvisitedCities.add(nearestCity);
				newTour[i] = secondNearestCity;
				unvisitedCities.remove(secondNearestCity);
			}
		}
		return newTour;
	}

	protected int getNearestCity(int city, Set<Integer> endpoints) {
			Iterator<Integer> it = endpoints.iterator();
			int nearestCity = 0;
			double minDistance = Double.MAX_VALUE;
			while (it.hasNext()) {
				int i = it.next();
	//			boolean containedInParents = (neighbours1[city - 1][0] == i
	//					|| neighbours1[city - 1][1] == i
	//					|| neighbours2[city - 1][0] == i || neighbours2[city - 1][1] == i);
				if (distanceTable.getDistanceBetween(city, i) < minDistance
						) {
					minDistance = distanceTable.getDistanceBetween(city, i);
					nearestCity = i;
				}
			}
			if (nearestCity != 0)
				return nearestCity;
			else //treats the case that all possible edges are already contained in the parents
			{
				it = endpoints.iterator();
				while (it.hasNext()) {
					int i = it.next();
					if (distanceTable.getDistanceBetween(city, i) < minDistance) {
						minDistance = distanceTable.getDistanceBetween(city, i);
						nearestCity = i;
					}
				}
				return nearestCity;
			}
						
		}
}
