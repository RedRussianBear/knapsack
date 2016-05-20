package knap;

public class Thing {
	private int value;
	private int weight;
	
	public Thing(){
		value = 0;
		weight = 0;
	}
	public Thing(int w, int v){
		value = v;
		weight = w;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
