package algorithms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.moeaframework.problem.tsplib.TSP2OptHeuristic;
import org.moeaframework.problem.tsplib.Tour;

public class MemeticAlgorithm extends TSP_Algorithm {
	int sizeOfCandidateSolutionPool;
//	int numberOf2OptApplications;
	Tour[] candidateSolutions;
	double[] fitnessOfCandidateSolutions;
	Tour bestSolutionFound;
	double fitnessOfBestSolution;
	LinkedList<Double> bestFitnessOfSolutions;
	LinkedList<Double> averageFitnessOfSolutions;
	TSP2OptHeuristic heuristic = new TSP2OptHeuristic(instance);

	public MemeticAlgorithm(String s, int sizeOfCandidateSolutionPool) throws IOException {
		super(s);
		this.sizeOfCandidateSolutionPool = sizeOfCandidateSolutionPool;
//		this.numberOf2OptApplications = numberOf2OptApplications;
		candidateSolutions = new Tour[sizeOfCandidateSolutionPool];
		fitnessOfCandidateSolutions = new double[sizeOfCandidateSolutionPool];
		bestFitnessOfSolutions = new LinkedList<Double>();
		averageFitnessOfSolutions = new LinkedList<Double>();
	}

	public double executeAlgorithm(int timespan) {
		long time = System.currentTimeMillis();
		// Tour t = Tour.createRandomTour(instance.getDimension())
		initializePopulation();
		bestSolutionFound = getTourWithBestFitness();
		fitnessOfBestSolution = bestSolutionFound.distance(instance);
		bestFitnessOfSolutions.add(relativeDistance(bestSolutionFound));
		averageFitnessOfSolutions
				.add(relativeDistance(getAverageFitness()));
		while (System.currentTimeMillis() - time <= timespan) {
//			List<Double> tmp = new ArrayList<Double>(sizeOfCandidateSolutionPool);
			recombinePopulation();
			mutatePopulation();
			improvePopulation();
			double bestFitnessOfCurrentIteration = getBestFitness();
			bestFitnessOfSolutions.add(relativeDistance(bestFitnessOfCurrentIteration));
			//track best found solution
			if (bestFitnessOfCurrentIteration < fitnessOfBestSolution)
			{
				bestSolutionFound = getTourWithBestFitness();
				fitnessOfBestSolution = bestSolutionFound.distance(instance);
			}
			averageFitnessOfSolutions
					.add(relativeDistance(getAverageFitness()));
			//draw diagram after each iteration (for debug)
//			for(int i = 0; i < sizeOfCandidateSolutionPool; i++)
//				tmp.add(relativeDistance(fitnessOfCandidateSolutions[i]));
//			drawDiagram("Fitness Of Solutions", tmp);
		}
		System.out.println("Number of iterations: "
				+ bestFitnessOfSolutions.size());
		drawDiagram("Best Fitness", bestFitnessOfSolutions);
//		CategoryPlot plot = chart.getCategoryPlot();
		drawDiagram("Average Fitness", averageFitnessOfSolutions);
		return fitnessOfBestSolution;
	}

	private Tour getTourWithBestFitness() {
		Tour t = candidateSolutions[0];
		double bestFitness = fitnessOfCandidateSolutions[0];
		for (int i = 1; i < sizeOfCandidateSolutionPool; i++) {
			if (fitnessOfCandidateSolutions[i] < bestFitness) {
				t = candidateSolutions[i];
				bestFitness = fitnessOfCandidateSolutions[i];
			}
		}
		return t;
	}

	private Double getAverageFitness() {
		double avg = 0;
		for (double d : fitnessOfCandidateSolutions)
			avg += d;
		avg = avg / sizeOfCandidateSolutionPool;
		return avg;
	}

	private Double getBestFitness() {
		double min = Double.MAX_VALUE;
		for (double d : fitnessOfCandidateSolutions)
			if (d < min)
				min = d;
		return min;
	}

	private void mutatePopulation() {
		for (int i = 0; i < sizeOfCandidateSolutionPool; i++) {
			//perform a randomly selected 4-change with probability 0.1, assumes size of TSP > 8
			if (Math.random() < 0.1)
			{
				candidateSolutions[i].fourChange();
				fitnessOfCandidateSolutions[i] = candidateSolutions[i].distance(instance);
			}
		}
	}

	private void recombinePopulation() {
		// stores indices of the Tours to be replaced/participate in
		// Recombination
		LinkedList<Integer> toReplace = new LinkedList<Integer>();
		LinkedList<Integer> toRecombine = new LinkedList<Integer>();
		double best = getBestFitness();
		double avg = getAverageFitness();
		for (int i = 0; i < sizeOfCandidateSolutionPool; i++) {
			double d = fitnessOfCandidateSolutions[i];
			double p_survive = 0.5 * (avg - d) / (avg - best) + 0.5; // probability
																		// for
																		// Solution
																		// i to
																		// survive
			double p_recombine = (avg - d) / (avg - best); // probability for
															// Solution i to
															// participate in
															// recombination
			if (Math.random() > p_survive)
				toReplace.add(i);
			if (Math.random() < p_recombine)
				toRecombine.add(i);
		}
		// Treatment of rare case where almost all solutions are chosen to be
		// replaced
		if (toReplace.size() > 0.9 * sizeOfCandidateSolutionPool) {
			for (int i = 0; i < 0.4 * sizeOfCandidateSolutionPool; i++)
				toReplace.remove();// simply removes some of the indices
		}
		// Treatment of rare case where almost no solutions are chosen to be
		// recombined
		if (toRecombine.size() < 0.2 * sizeOfCandidateSolutionPool) {
			for (int i = 0; i < 0.2 * sizeOfCandidateSolutionPool; i++)
			{
				//simply add random indices that are not contained
				int k = rnd.nextInt(sizeOfCandidateSolutionPool);
				if (!toRecombine.contains(k))
					toRecombine.add(k);
			}
		}
		int n = toRecombine.size();
		for (int i: toReplace)
		{
			int indexOfParent1 = toRecombine.get(rnd.nextInt(n));
			int indexOfParent2 = toRecombine.get(rnd.nextInt(n));
			while (indexOfParent1 == indexOfParent2)
				indexOfParent2 = toRecombine.get(rnd.nextInt(n));
			Tour parent1 = candidateSolutions[indexOfParent1];
			Tour parent2 = candidateSolutions[indexOfParent2];
			Tour offspring = dpx(parent1,parent2);
			//replaces the old solution with the offspring
			candidateSolutions[i] = offspring; 
			fitnessOfCandidateSolutions[i] = offspring.distance(instance);
		}
	}

	private void improvePopulation() {
		for (int i = 0; i < sizeOfCandidateSolutionPool; i++) {
			heuristic.apply(candidateSolutions[i]);
//			heuristic.apply_kTimes(candidateSolutions[i],
//					numberOf2OptApplications);
			fitnessOfCandidateSolutions[i] = candidateSolutions[i]
					.distance(instance);
		}
	}

	private void initializePopulation() {
//		//creates random Tours
//		for (int i = 0; i < sizeOfCandidateSolutionPool; i++) {
//			candidateSolutions[i] = Tour.createRandomTour(instance.getDimension());
//			fitnessOfCandidateSolutions[i] = candidateSolutions[i].distance(instance);
//		}
		//creates initial solutions with Greedy heuristic
		for (int i = 0; i < sizeOfCandidateSolutionPool; i++) {
			int[] newTour = createGreedyRandomizedTour(instance.getDimension());
			candidateSolutions[i] = Tour.createTour(newTour);
			fitnessOfCandidateSolutions[i] = candidateSolutions[i].distance(instance);
		}
	}

	

	public Tour dpx(Tour t1, Tour t2) {
		int[] parent1 = t1.toArray();
		int[] parent2 = t2.toArray();
		int n = parent1.length;
		int[][] neighbours1 = new int[n][2];
		int[][] neighbours2 = new int[n][2];
		for (int i = 0; i < n; i++) {
			neighbours1[parent1[i] - 1][0] = parent1[normalize(i - 1)];
			neighbours1[parent1[i] - 1][1] = parent1[normalize(i + 1)];
			neighbours2[parent2[i] - 1][0] = parent2[normalize(i - 1)];
			neighbours2[parent2[i] - 1][1] = parent2[normalize(i + 1)];
		}
		// divide into fragments (the list contains the indices of the end of a
		// fragment, endpoints)
		List<ArrayList<Integer>> fragments = new ArrayList<ArrayList<Integer>>();
		int[] fragmentNumberOfCity = new int[n];
		Set<Integer> endpoints = new HashSet<Integer>();
		int fragmentIndex = -1;
		int k = 0;
		while (k < n) {
			fragmentIndex++;
			ArrayList<Integer> fragment = new ArrayList<Integer>();
			fragment.add(parent1[k]);
			fragmentNumberOfCity[parent1[k] - 1] = fragmentIndex;
			endpoints.add(parent1[k]);
			k++;
			while ((k < n)
					&& (parent1[(k) % n] == neighbours2[parent1[k - 1] - 1][0] || parent1[(k)
							% n] == neighbours2[parent1[k - 1] - 1][1])) {
				fragment.add(parent1[k]);
				fragmentNumberOfCity[parent1[k]-1] = fragmentIndex;
				k++;
			}
			fragments.add(fragment);
			endpoints.add(parent1[k - 1]);
		}
		// recombine(creates child tour)
		ArrayList<Integer> child = new ArrayList<Integer>();
		fragmentIndex = 0;
		for (int i = 0; i < fragments.size(); i++) {
			ArrayList<Integer> fragment = fragments.get(fragmentIndex);
			child.addAll(fragment);
			endpoints.remove(fragment.get(0));
			int end = fragment.get(fragment.size() - 1);
			endpoints.remove(end);
			// reconnect to the nearest city (by determining the new
			// fragmentIndex)
			if (endpoints.isEmpty())
				break;
			fragmentIndex = fragmentNumberOfCity[getNearestCity(end, endpoints)-1];
		}
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = child.get(i);
		}
		return Tour.createTour(arr);
	}

	private int normalize(int i) {
		int size = candidateSolutions[0].size();
		while (i < 0)
			i += size;
		while (i >= size)
			i -= size;
		return i;
	}
}
