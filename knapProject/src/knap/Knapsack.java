package knap;

public class Knapsack {
	private Thing[] contents;
	private int maxCapacity;
	public Knapsack(Thing[] t, int maxValue){
		contents = t;
		maxCapacity = maxValue;
	}
	
	/**
	 * Recursive solution to the 0/1 Knapsack Problem, meaning you either take 1 copy (1) or don't at all (0). 
	 * Basic idea, there are two cases:
	 * 1) use the last term + max of the smaller knapsack
	 * 2) don't use the last term + same size knapsack but 1 less element
	 * @param elements = number of elements used
	 * @param capacity = capacity in knapsack
	 * @return max value given a capacity, with the total weight <= capacity
	 */
	public int recursiveSolution01(int elements, int capacity){
		int solution1, solution2;
		
		//if there are no elements or there's no space, you can fit 0 value in the knapsack
		if (elements == 0 || capacity == 0)
			return 0;
		
		
		//Can't use last term- too big
		if (contents[elements - 1].getWeight() > capacity){
			return recursiveSolution01(elements - 1, capacity);
		}
		//Two possible cases: use and don't use last term
		else{
			//Doesn't use last term
			solution1 = recursiveSolution01(elements - 1, capacity);
			//Uses last term
			solution2 = contents[elements - 1].getValue() + recursiveSolution01(elements - 1, capacity - contents[elements - 1].getWeight());
			
		}
		
		return Math.max(solution1, solution2);
	}
	public int dpSolution01(){
		
		//goes from (0,0) to (actual elements, actual size)
		int[][] solution = new int[contents.length + 1][maxCapacity + 1];
		
		//go row by row until you get to the end
		//row = elements, col = capacity
		for (int row = 1; row <= contents.length; row++){
			for (int col = 1; col <= maxCapacity; col++){
				
				//if the item's weight is greater than the capacity
				if (contents[row - 1].getWeight() > col)
					solution[row][col] = solution[row-1][col];//same weight, one less element
				
				//consider both cases, just like recursive
				else
					solution[row][col] = Math.max(solution[row-1][col], contents[row-1].getValue() + solution[row - 1][col - contents[row-1].getWeight()]);
			}
		}
		
		return solution[contents.length][maxCapacity];
	}
	
	public int recursiveSolutionUn(int capacity){
		int maxSolution = 0;
		
		for (int i = 0; i < contents.length; i++){
			//if item is too big, don't consider it
			if(contents[i].getWeight() > capacity)
				continue;
			
			int tempSolution = contents[i].getValue() + recursiveSolutionUn(capacity - contents[i].getWeight());
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
		return recursiveSolutionUn(maxCapacity);
	}
	
	public void setCapacity(int a){
		maxCapacity = a;
	}
	public static void main(String[] args){
		Thing[] t = new Thing[5];
		t[0] = new Thing(10, 80);
		t[1] = new Thing(50, 180);
		t[2] = new Thing(100, 1200);
		t[3] = new Thing(2, 2000);
		t[4] = new Thing(90, 5);
		Knapsack K = new Knapsack(t, 17);
		System.out.println(K.solve01());
		System.out.println(K.dpSolution01());
		System.out.println(K.solveUn());
		System.out.println(K.dpSolutionUn());
		
	}
}
