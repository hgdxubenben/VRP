Solving the pickup and delivery problem using reactive tabu search

As you can see, my method is heavily based on the algorithm proposed by the paper named “Solving the pickup and delivery problem with time windows using reactive tabu search”.

1.	The feasible initial solution
Just use one car and deliver one client order at one time. This is the original solution provide by the project.

2.	Move neighborhood
This part is a little different from the original algorithm, as we didn’t have time window with every order. 
2.1 Single paired insertion(SPI)
The first mobe neighborhood attempts to move a ps-pair from its current car route to another car in the solution. See, Fig.1 below.
 
	And this move is performed by all the order pair.

2.2 Within route insertion(WRI)
This part is a little different from the original one. The order pair can be inserted into any where within the route, the only constrain is that is predecessor must be in front of the successor.

3.	Tabu Search design
 


References 
1.	Nanry, William P., and J. Wesley Barnes. "Solving the pickup and delivery problem with time windows using reactive tabu search." Transportation Research Part B: Methodological 34.2 (2000): 107-121.
