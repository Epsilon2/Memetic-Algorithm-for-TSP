Memetic-Algorithm-for-TSP
=========================
In this project, a memetic algorithm for solving the TSP is implemented. The performance of this memetic algorithm on certain TSPLib instances is compared with an iterated local search algorithm.

Usage:

The program needs 3 command line arguments. 
- The first specifies the filename (without ending!) of the TSPLib Instance, which has to be in the folder data/tsp (the instances "att48", "brg180" and "pr1002" are already there). 
- The second parameter specifies the time (in seconds) that each algorithm is performed. 
- The third parameter is the number of candidate solutions in the solution pool (only relevant for the memetic algorithm).

Output: 
Prints the length of the best found solution for each algorithm as well as the length of the optimal solution and additional information on the console. 

Iterated local search:

This algorithm starts with a certain solution (created by using a randomized greedy heuristic or entirely random) and performs the 2-opt heuristic on it until it is no longer possible. This is iterated until the time limit is reached (memorizing the best found solution).

Memetic algorithm:

This algorithm uses a candidate solution pool initialized by using a randomized greedy heuristic or entirely random. In each step, some candidate solutions are selected to be replaced or to participate in recombination based on their fitness (randomized). The solutions are recombined using the DPX (distance preserving crossover) recombination operator. With a probability of 0.1, each solution is subjected to the mutation operator (a nonsequential four-change). After these steps, the 2-opt heuristic is applied to each of the candidate solutions.

Additionally, three diagrams of the results are drawn (using the JFreeChart library): 
- The fitness of the result of iterated local search for each restart
- The average fitness of the candidate solution pool against the number of the iteration.
- The best fitness of the candidate solution pool against the number of the iteration.
