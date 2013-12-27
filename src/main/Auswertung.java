package main;

import java.io.IOException;

public class Auswertung {
	
	public static void main(String[] args) {
		String[] nameOfTestInstances = {"att48"};
		int numberOfTestRuns = 2;
		int[] timeLimits = {};
		List<Double> results = new List<>();
		try {
			for(String s: nameOfTestInstances)
			{
				for(int i = 0; i<numberOfTestRuns; i++)
				{
					String[] arguments = {s, String.valueOf(5), String.valueOf(50)};
					results.add(TSP_Main.main(arguments));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
