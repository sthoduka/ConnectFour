/**
 * Class used to count number of objects created
 * @author Santosh Thoduka
 *
 */
public class ObjectCounter {
	int n;
	public ObjectCounter() {
		n = 0;
	}
	public void increment() {
		n++;
	}
	
	public int get() {
		return n;
	}
}
