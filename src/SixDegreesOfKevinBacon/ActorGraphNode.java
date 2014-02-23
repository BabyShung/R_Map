package SixDegreesOfKevinBacon;

/**
 * HashSet is a set, e.g. {1,2,3,4,5}
 * HashMap is a key -> value (key to value) map, e.g. {a -> 1, b -> 2, c -> 2, d -> 1}
 * 
 * HashMap doesn't have duplicate keys, but it may have duplicate values.
 * HashSet also doesn't have duplicate elements.
 * 
 * @author haozheng
 */

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class ActorGraphNode {

	private String name;
	private Set<ActorGraphNode> linkedActors;
	private int baconNumber = -1;

	public ActorGraphNode(String name) {
		this.name = name;
		linkedActors = new HashSet<ActorGraphNode>();
	}

	public void linkCoStar(ActorGraphNode coStar) {
		// both two involved actors update
		linkedActors.add(coStar);
		coStar.linkedActors.add(this);
	}

	public void setBaconNumbers(String toName) {
		this.baconNumber = 0;
		Queue<ActorGraphNode> q = new LinkedList<>();
		q.add(this);
		ActorGraphNode current;
		while (!q.isEmpty()) {
			current = q.poll();
			for (ActorGraphNode neighbor : current.linkedActors) {
				if (neighbor.name == toName) {
					neighbor.baconNumber = current.baconNumber + 1;
					return;
				}
				if (neighbor.baconNumber == -1) {
					neighbor.baconNumber = current.baconNumber + 1;
					q.add(neighbor);
				}
			}
		}
	}

	/**
	 * getter
	 */
	public int getBaconNumber() {// get the shortest path length
		return baconNumber;
	}

	public String getActorName() {
		return name;
	}

}
