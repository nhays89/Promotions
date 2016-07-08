
/**
 * Author: Nicholas A. Hays
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class PromotionMain {

	static int lowerInterval, higherInterval, numOfNodes, numOfRules;
	static Map<String, Node> myTree = new TreeMap<String, Node>();
	static ArrayList<Integer> myLevels = new ArrayList<Integer>();

	public static void main(String[] args) {
		Scanner scanIn = new Scanner(System.in);
		lowerInterval = Integer.parseInt(scanIn.next());
		higherInterval = Integer.parseInt(scanIn.next());
		numOfNodes = Integer.parseInt(scanIn.next());
		numOfRules = Integer.parseInt(scanIn.next());
		createNodes();
		for (int i = 0; i < numOfRules; i++) {
			Node parentNode = myTree.get(scanIn.next());
			Node childNode = myTree.get(scanIn.next());
			childNode.myParentNodes.add(parentNode);
			parentNode.myChildNodes.add(childNode);
			update(childNode);
		}
		scanIn.close();
		for(Node n : myTree.values()) {
			System.out.println("id " + n.myId + " level " + n.myLevel);
		}
	
	}

	static void createNodes() {
		for (int i = 0; i < numOfNodes; i++) {
			Node node = new Node(String.valueOf(i));
			myTree.put(node.myId, node);
		}
	}

	static void update(Node theChildNode) {
		for (Node parentNode : theChildNode.myParentNodes) {
			if (parentNode.myLevel + 1 > theChildNode.myLevel) {
				theChildNode.myLevel = parentNode.myLevel + 1;
				for (Node child : theChildNode.myChildNodes) {
					update(child);
				}
				break;
			}
		}
	}

	static int determinePromotions(int promotions) {
		int maxPromoted = 0;
		for (int i = 0; i < myLevels.size(); i++) {
			if (promotions - ((int) myLevels.get(i)) > 0) {
				maxPromoted += ((int) myLevels.get(i));
			} else if ((promotions - ((int) myLevels.get(i))) == 0) {
				return maxPromoted + ((int) myLevels.get(i));
			} else {
				return maxPromoted;
			}
		}
		return maxPromoted;
	}

	public static class Node implements Comparable<Node> {

		String myId;
		List<Node> myParentNodes = new ArrayList<Node>();
		List<Node> myChildNodes = new ArrayList<Node>();
		int myLevel;

		public Node(String theId) {
			myId = theId;
		}

		@Override
		public int compareTo(Node node) {
			if (this.myLevel > node.myLevel) {
				return -1;
			} else if (this.myLevel == node.myLevel) {
				return 0;
			} else {
				return 1;
			}
		}
	}

}
