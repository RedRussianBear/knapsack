package knap;

public class Knapsack {
	private Thing[] contents;
	
	public Knapsack(Thing[] t, int maxValue){
		contents = t;
	}
	
	/**
	 * Solution given a specific number of elements and a specific capacity
	 * Basic idea:
	 * The max value for the first n terms either uses the last term or doesn't
	 * so it equals the max of 
	 * 1) not using the last term- the max value of the first n-1 terms with the same capacity
	 * 2) using the last term-     the max value of the first n-1 terms with the capacity - the nth term's value 
	 * 
	 * @param elements
	 * @param totalValue
	 * @return max value, with the total weight <= capacity
	 */
	public int recursiveSolution(int elements, int capacity){
		int solution1, solution2;
		//elements = 0 when it's solution(0, n)
		//capacity = 0 when it's t[0] + solution(n, 0) (n can be 0)
		if (elements == 0 || capacity == 0)
			return 0;
		
		
		//Can't use last term- too big
		if (contents[elements - 1].getWeight() > capacity){
			return recursiveSolution(elements - 1, capacity);
		}
		else{
			//Doesn't use last term
			solution1 = recursiveSolution(elements - 1, capacity);
			//Uses last term
			solution2 = contents[elements - 1].getValue() + recursiveSolution(elements - 1, capacity - contents[elements - 1].getWeight());
			
		}
		return Math.max(solution1, solution2);
	}
	
	/**
	 * Solves it given a capacity
	 * Easier to call
	 * @param maxCapacity
	 * @return
	 */
	public int solve(int maxCapacity){
		return recursiveSolution(contents.length, maxCapacity);
	}
	
	public static void main(String[] args){
		Thing[] t = new Thing[5];
		t[0] = new Thing(2, 3);
		t[1] = new Thing(4, 5);
		t[2] = new Thing(5, 6);
		t[3] = new Thing(7, 8);
		t[4] = new Thing(9, 10);
		Knapsack K = new Knapsack(t, 20);
		System.out.println(K.solve(20));
	}
}
