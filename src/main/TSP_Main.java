package main;

import java.io.IOException;

import algorithms.IteratedLocalSearch;
import algorithms.MemeticAlgorithm;


public class TSP_Main {
	
	public static double[] main(String[] args) throws IOException {
		String nameOfInstance = args[0];
		int timeLimit = Integer.valueOf(args[1]); //in seconds
		int sizeOfCandidateSolutionPool = Integer.valueOf(args[2]);
		boolean drawDiagrams = true;
		if(args.length > 3 && args[3].equals("noDiagrams"))
			drawDiagrams = false;
		double[] results = new double[2];
		IteratedLocalSearch ils = new IteratedLocalSearch(nameOfInstance);
		System.out.println("Execute iterated local search algorithm");
		double ils_Solution = ils.executeAlgorithm(1000*timeLimit, drawDiagrams);
		System.out.println("deviation of best solution found by iterated local search from optimum:");
		System.out.println(ils_Solution);
		results[0] = ils_Solution;
		MemeticAlgorithm meme = new MemeticAlgorithm(nameOfInstance,sizeOfCandidateSolutionPool);
		System.out.println("Execute memetic algorithm");
		double meme_Solution = meme.executeAlgorithm(1000*timeLimit, drawDiagrams);
		System.out.println("deviation of best solution found by memetic algorithm from optimum:");
		System.out.println(meme_Solution);
//		System.out.println("optimal solution has length:");
//		System.out.println(ils.getOptimalDistance());
		results[1] = meme_Solution;
		return results;
	}
}
