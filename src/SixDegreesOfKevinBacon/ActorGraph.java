package SixDegreesOfKevinBacon;

/**
 * Six degree of Kevin Bacon
 * Actually, the bacon number is the shortest path length from A to B, in an undirected graph.
 * 
 * Use BFS to implement, with important user-defined ActorGraphNode data structure
 * 
 * 
 * @author haozheng
 */

import java.util.HashMap;
import java.util.Map;

public class ActorGraph {

	private Map<String, ActorGraphNode> actors;

	public ActorGraph() {
		actors = new HashMap<>();
	}

	public ActorGraphNode addAnActor(String name) {
		ActorGraphNode newActor = new ActorGraphNode(name);
		actors.put(name, newActor);
		return newActor;
	}

	public ActorGraphNode addAnActor(String name, String existName) {
		if (!actors.containsKey(existName))
			throw new Error("linking actor doesn't exist!");
		else if (actors.containsKey(name))
			throw new Error("adding actor already exist!");

		ActorGraphNode newActor = this.addAnActor(name);
		newActor.linkCoStar(actors.get(existName));
		return newActor;
	}

	public void buildLink(String name1, String name2) {
		if (!actors.containsKey(name1) || !actors.containsKey(name2))
			throw new Error("linking actor doesn't exist!");

		actors.get(name1).linkCoStar(actors.get(name2));
	}

	public int getShorestPathLength(String from, String to) {
		if (!actors.containsKey(from) || !actors.containsKey(to))
			throw new Error("actor doesn't exist!");

		ActorGraphNode fromActor = actors.get(from);
		fromActor.setBaconNumbers(to);// BFS method
		return actors.get(to).getBaconNumber();

	}

	public void resetAll() {
		// reset bacon number
		for (ActorGraphNode tmp : actors.values()) {
			tmp.setBaconNumber(-1);
		}
	}

}
