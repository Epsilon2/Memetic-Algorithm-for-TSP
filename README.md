Memetic-Algorithm-for-TSP
=========================
In this project, a memetic algorithm for solving the TSP is implemented. The performance of this memetic algorithm on certain TSPLib instances is compared with an iterated local search algorithm.

Iterated local search:

This algorithm starts with a certain solution (created by using a randomized greedy heuristic or entirely random) and performs the 2-opt heuristic on it until it is no longer possible. This is iterated until the time limit is reached (memorizing the best found solution).

Memetic algorithm:

This algorithm uses a candidate solution pool (default size: 100) initialized by using a randomized greedy heuristic or entirely random. In each step, some candidate solutions are selected to be replaced or to participate in recombination based on their fitness(randomized).

The TSP_main class executes both algorithms for a certain amount of time (default: 5 s) and then prints the length of the best found solution for each algorithm as well as the length of the optimal solution and additional information on the console. 

Additionally, diagrams of the results are drawn (using the JFreeChart library): 
- The fitness of the result of iterated local search for each restart
- The average fitness of the candidate solution pool against the number of the iteration.
- The best fitness of the candidate solution pool against the number of the iteration.
