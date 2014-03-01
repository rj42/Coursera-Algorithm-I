import java.lang.reflect.Array;
import java.util.*;
import java.util.Map;

public class Test {
	public static void main(String[] args) {

		ArrayList<Integer> l = new ArrayList<Integer>();
		Integer[] a = new Integer[10];
		for (int i = 0; i < 10; i++)
			a[i] = i;
		l.add(a[5]);
		a[5] = 7;
		for (Integer i : l)
			StdOut.print(i+" ");
	}
}