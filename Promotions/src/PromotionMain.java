
/**
 * Author: Nicholas A. Hays
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PromotionMain {

	static int lowerInterval, higherInterval, numOfNodes, numOfRules;
	static Map<String, Node> myHashMap = new HashMap<String, Node>();
	static ArrayList<Integer> myLevels = new ArrayList<Integer>();
	static List<Node> mySortedNodes;

	public static void main(String[] args) {
		Scanner scanIn = new Scanner(System.in);
		lowerInterval = Integer.parseInt(scanIn.next());
		higherInterval = Integer.parseInt(scanIn.next());
		numOfNodes = Integer.parseInt(scanIn.next());
		numOfRules = Integer.parseInt(scanIn.next());
		createNodes();
		for (int i = 0; i < numOfRules; i++) {
			Node parentNode = myHashMap.get(scanIn.next());
			Node childNode = myHashMap.get(scanIn.next());
			childNode.myParentNodes.add(parentNode);
			parentNode.myChildNodes.add(childNode);
			update(childNode);
		}
		scanIn.close();
		mySortedNodes = new ArrayList<Node>(myHashMap.values());
		Collections.sort(mySortedNodes);
		createLevelCount();
		printResults();
	}

	static void createNodes() {
		for (int i = 0; i < numOfNodes; i++) {
			Node node = new Node(String.valueOf(i));
			myHashMap.put(node.myId, node);
		}
	}

	static void createLevelCount() {
		if (mySortedNodes.size() > 0) {
			int numOfEmp = 0;
			int theEnd = mySortedNodes.size() - 1;
			for (int i = 1; i < mySortedNodes.size(); i++) {
				if (mySortedNodes.get(i).myLevel == mySortedNodes.get(i - 1).myLevel) {
					numOfEmp++;
					if (i == theEnd) {
						myLevels.add(new Integer(++numOfEmp));
					}
				} else {
					myLevels.add(new Integer(++numOfEmp));
					numOfEmp = 0;
					if (i == theEnd) {
						myLevels.add(new Integer(++numOfEmp));
					}
				}
			}
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

	static int determinePromotions(int thePromotions) {
		int maxPromoted = 0;
		int numOfPromotions = thePromotions;
		for (int i = 0; i < myLevels.size(); i++) {
			if (numOfPromotions - ((int) myLevels.get(i)) > 0) {
				maxPromoted += ((int) myLevels.get(i));
				numOfPromotions -= ((int) myLevels.get(i));
			} else if ((numOfPromotions - ((int) myLevels.get(i))) == 0) {
				return maxPromoted + ((int) myLevels.get(i));
			} else {
				return maxPromoted;
			}
		}
		return maxPromoted;
	}

	static void printResults() {
		// how many are getting promoted at lower interval
		System.out.println(determinePromotions(lowerInterval));

		int maxInterval = determinePromotions(higherInterval);
		// how many are getting promoted at higher interval
		System.out.println(maxInterval);
		// how many are not being promoted
		System.out.println(myHashMap.size() - maxInterval);
	}

	public static class Node implements Comparable<Node> {

		String myId;
		List<Node> myParentNodes;
		List<Node> myChildNodes;
		int myLevel;

		public Node(String theId) {
			myChildNodes = new ArrayList<Node>();
			myParentNodes = new ArrayList<Node>();
			myId = theId;
		}

		@Override
		public int compareTo(Node node) {
			if (this.myLevel < node.myLevel) {
				return -1;
			} else if (this.myLevel == node.myLevel) {
				return 0;
			} else {
				return 1;
			}
		}
	}
}
