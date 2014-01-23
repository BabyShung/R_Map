import ArrayMap.ArrayMap;
import Interfaces.Entry;
import Interfaces.Map;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> grades = new ArrayMap<>();

		// Add a few entries into our grade map
		grades.put("Andy", 76);
		grades.put("John Doe", 95);
		grades.put("Jane Doe", 98);

		// Get the value of a few entries
		System.out.println(grades.get("Andy"));
		System.out.println(grades.get("John Doe"));
		System.out.println(grades.get("Nobody"));

		// Iterate over the keys
		for (String s : grades.keySet()) {
			System.out.println(s);
		}

		// Iterate over the values
		for (Integer i : grades.values()) {
			System.out.println(i);
		}

		// Remove "Andy", insert "Andy Berns", and update his score.
		System.out.println(grades.remove("Andy"));
		grades.put("Andy Berns", 83);
		System.out.println(grades.remove("Andy"));
		grades.put("Andy Berns", 84);

		// Iterate over the entries
		for (Entry<String, Integer> e : grades.entrySet()) {
			System.out.println(e);
		}
	}

}
