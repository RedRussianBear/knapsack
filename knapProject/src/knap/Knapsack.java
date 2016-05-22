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
	
	/**
	 * Recursive solution to the Unbounded Knapsack Problem, meaning you can take as many of an object as you want
	 * Very similar to 01 problem, there are just more cases to consider
	 * @param elements = number of elements used
	 * @param capacity = capacity in knapsack
	 * @return
	 */
	public int recursiveSolutionUn(int elements, int capacity){
		//Base case: 0 terms or 0 space
		if (elements == 0 || capacity == 0)
			return 0;
		
		/*holds all of the solutions 
		*including last term 0 times -> including it capacity/weight times (max amount)*/
		int[] solutions = new int[capacity/contents[elements-1].getWeight() + 1];//0 ... capacity/last weight
		
		for (int i = 0; i < solutions.length; i++){
			//Uses i of the last term 
			solutions[i] = i * contents[elements-1].getValue() + recursiveSolutionUn(elements - 1, capacity - i * contents[elements - 1].getWeight());
		}
		
		//returns max solution
		int maxSolution = 0;
		for (int i: solutions){
			maxSolution = Math.max(maxSolution, i);
		}
		return maxSolution;
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
	
	public static void main(String[] args){
		Thing[] t = new Thing[4];
		t[0] = new Thing(6, 30);
		t[1] = new Thing(3, 14);
		t[2] = new Thing(4, 16);
		t[3] = new Thing(2, 9);
		Knapsack K = new Knapsack(t, 10);
		System.out.println(K.solveUn());
	}
}
