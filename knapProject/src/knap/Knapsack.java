package knap;

public class Knapsack {
	private Thing[] contents;
	private int maxCapacity;
	public Knapsack(Thing[] t, int maxValue){
		contents = t;
		maxCapacity = maxValue;
	}
	
	/**
	 * Recursive solution to the 0/1 Knapsack Problem, meaning you either take it (1) or don't (0). You can't take more than 1 of something
	 * Basic idea:
	 * The max value for the first n terms either uses the last term or doesn't, so it equals the max of 
	 * 1) not using the last term- the max value of the first n-1 terms with the same capacity
	 * 2) using the last term-     the max value of the first n-1 terms with the capacity - the nth term's value 
	 * 
	 * @param elements = number of elements used
	 * @param capacity = capacity in knapsack
	 * @return max value, with the total weight <= capacity
	 */
	public int recursiveSolution01(int elements, int capacity){
		int solution1, solution2;
		//elements = 0 when it's solution(0, n)
		//capacity = 0 when it's t[0] + solution(n, 0) (n can be 0)
		if (elements == 0 || capacity == 0)
			return 0;
		
		
		//Can't use last term- too big
		if (contents[elements - 1].getWeight() > capacity){
			return recursiveSolution01(elements - 1, capacity);
		}
		else{
			//Doesn't use last term
			solution1 = recursiveSolution01(elements - 1, capacity);
			//Uses last term
			solution2 = contents[elements - 1].getValue() + recursiveSolution01(elements - 1, capacity - contents[elements - 1].getWeight());
			
		}
		
		return Math.max(solution1, solution2);
	}
	public int dpSolution01(){
		int[][] solution = new int[maxCapacity + 1][contents.length+1];
		
		//base cases:
		for (int i = 0; i <= contents.length; i++) solution[0][i] = 0;
		for (int i = 0; i <= maxCapacity; i++) solution[i][0] = 0;
		
		//go row by row until you get to the end
		//row = capacity, col = elements
		for (int row = 1; row <= maxCapacity; row++){
			for (int col = 1; col <= contents.length; col++){
				if (contents[col-1].getWeight() > row)
					solution[row][col] = solution[row][col - 1];//same weight, one less element
				else
					solution[row][col] = Math.max(solution[row][col - 1], contents[col-1].getValue() + solution[row-contents[col-1].getWeight()][col - 1]);
			}
		}
		
		return solution[maxCapacity][contents.length];
	}
	/**
	 * Recursive solution to the Unbounded Knapsack Problem, meaning you can take as many of an object as you want
	 * Very similar to 01 problem, there are just more cases to consider
	 * @param elements = number of elements used
	 * @param capacity = capacity in knapsack
	 * @return
	 */
	public int recursiveSolutionUn(int capacity){
		int maxSolution = 0;
		
		for (int i = 0; i < contents.length; i++){
			//if item is too big, don't consider it
			if(contents[i].getWeight() > capacity)
				continue;
			
			int tempSolution = contents[i].getValue() + recursiveSolutionUn(capacity - contents[i].getWeight);
			if(tempSolution > maxSolution)
				maxSolution = tempSolution;
		}

		//return the largest sub solution
		return maxSolution;
	}
	
	/**
	* 	Dynamic Programming solution
	*	Rather than calling the function repeatedly, store values in arrays, and build bottom up.
	*/
	public int dpSolutionUn(){
		int capacity = maxCapacity;

		int[] solution = new int[maxCapacity + 1];

		solution[0] = 0;
		
		//find solution for each integral capacity from 0 to max
		for(int c = 1; c < solution.length; c++) {
			int maxSol = 0;
			
			//consider all available items
			for(int i = 0; i < contents.length; i++){
				//if an item does not fit, don't consider it
				if(c - contents[i].getWeight() < 0)
					continue;
				
				int tempSol = solution[c - contents[i].getWeight()] + contents[i].getValue();
				if(tempSol > maxSol)
					maxSol = tempSol;
			}
			
			solution[c] = maxSol;
		}
		
		//return solution for the maximum capacity
		return solution[capacity];
	}
	/**
	 * Solves 0/1 problem given a capacity
	 * Easier to call
	 * @return solution to the 0/1 knapsack problem
	 */
	public int solve01(){
		return recursiveSolution01(contents.length, maxCapacity);
	}
	
	/**
	 * Solves unbounded problem given a capacity
	 * Easier to call
	 * @return solution to the unbounded knapsack problem 
	 */
	public int solveUn(){
		return recursiveSolutionUn(contents.length, maxCapacity);
	}
	
	public void setCapacity(int a){
		maxCapacity = a;
	}
	public static void main(String[] args){
		Thing[] t = new Thing[5];
		t[0] = new Thing(2, 3);
		t[1] = new Thing(4, 5);
		t[2] = new Thing(5, 6);
		t[3] = new Thing(7, 8);
		t[4] = new Thing(9, 10);
		Knapsack K = new Knapsack(t, 20);
		System.out.println(K.solve01());
		System.out.println(K.dpSolution01());
		System.out.println(K.solveUn());
		System.out.println(K.dpSolutionUn());
		K.setCapacity(19);
		System.out.println(K.solve01());
		System.out.println(K.dpSolution01());
		System.out.println(K.solveUn());
		System.out.println(K.dpSolutionUn());
		
	}
}
