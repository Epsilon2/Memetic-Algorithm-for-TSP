package main;

import java.io.IOException;

import org.moeaframework.problem.tsplib.Tour;

import algorithms.IteratedLocalSearch;
import algorithms.MemeticAlgorithm;


public class TSP_Main {
	
	public static void main(String[] args) throws IOException {
//		String nameOfInstance = readLine();
		String nameOfInstance = "brg180";
		IteratedLocalSearch ils = new IteratedLocalSearch(nameOfInstance,150);
		System.out.println("Execute iterated local search algorithm");
		Tour ils_Solution = ils.executeAlgorithm(5000);
		System.out.println("best solution found by iterated local search:");
		System.out.println(ils.distance(ils_Solution));
		MemeticAlgorithm meme = new MemeticAlgorithm(nameOfInstance,100,5);
		System.out.println("Execute memetic algorithm");
		double d = meme.executeAlgorithm(5000);
		System.out.println("length of best solution found by memetic algorithm:");
		System.out.println(d);
		System.out.println("optimal solution has length:");
		System.out.println(ils.getOptimalDistance());
	}

//	private static String readLine() throws IOException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		System.out.println("Name der TSP-Instanz eingeben:");
//		return br.readLine();
//	}
}
