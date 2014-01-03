package algorithms;

import java.io.IOException;

import org.moeaframework.problem.tsplib.TSP2OptHeuristic;
import org.moeaframework.problem.tsplib.Tour;

public class IteratedLocalSearch extends TSP_Algorithm{
	
	public IteratedLocalSearch(String s) throws IOException {
		super(s);
	}
	
	public double executeAlgorithm(int timespan, boolean drawDiagrams) {
		long time = System.currentTimeMillis();		
		Tour t = Tour.createTour(createGreedyRandomizedTour(instance.getDimension()));
		Tour bestFoundSolution = t;
		int numberOfRestarts = -1;
		while (System.currentTimeMillis()-time <= timespan)
		{			
			TSP2OptHeuristic heuristic = new TSP2OptHeuristic(instance);
			heuristic.apply(t);
			results.add(relativeDistance(t));
			bestFoundSolution = bestFoundSolution.distance(instance) > t.distance(instance) ? t : bestFoundSolution;
			numberOfRestarts++;
			t = Tour.createTour(createGreedyRandomizedTour(instance.getDimension()));
		}
//		System.out.println(System.currentTimeMillis()-time);
		System.out.println("Number of restarts: " + numberOfRestarts);
		if(drawDiagrams)
			drawDiagram("IteratedLocalSearch", results);
		return relativeDistance(bestFoundSolution.distance(instance));
	}

}
