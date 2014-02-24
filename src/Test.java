import ArrayMap.ArrayMap;
import Interfaces.Entry;
import Interfaces.Map;
import SixDegreesOfKevinBacon.ActorGraph;

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

		ActorGraph ag = new ActorGraph();
		ag.addAnActor("Hao Zheng");
		ag.addAnActor("Yajie Yan", "Hao Zheng");
		ag.addAnActor("Dou Hang", "Yajie Yan");
		ag.addAnActor("Chao Yang", "Dou Hang");
		ag.addAnActor("Yixin Chen", "Chao Yang");
		ag.addAnActor("Baoluo Meng", "Yixin Chen");
		ag.addAnActor("Chutian Gao", "Chao Yang");
		ag.addAnActor("Tengyu Wang", "Chao Yang");
		ag.addAnActor("Yuan Pan", "Dou Hang");
		ag.addAnActor("Luan Rui", "Chutian Gao");
		ag.addAnActor("Xinmei Zhang", "Chutian Gao");
		ag.addAnActor("Ruoyu Zhang", "Xinmei Zhang");
		ag.addAnActor("Yuanyuan Jiang", "Ruoyu Zhang");

		ag.buildLink("Xinmei Zhang", "Yuan Pan");
		ag.buildLink("Hao Zheng", "Chao Yang");
		ag.buildLink("Baoluo Meng", "Hao Zheng");
		ag.buildLink("Dou Hang", "Yuanyuan Jiang");

		int spl = ag.getShorestPathLength("Hao Zheng", "Ruoyu Zhang");
		System.out.println("Shorest path length: " + spl);
		
		char[] test = {'a','b','c'};
		String tests = new String(test,0,3);
		System.out.println(tests);
		
		
	}

}
