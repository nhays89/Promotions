
/**
 * Author: Nicholas A. Hays
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PromotionMain {

	static int lowerInterval, higherInterval, numOfNodes, numOfRules;
	static Map<String, Node> myTree = new HashMap<String, Node>();

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

	}
	static void createNodes() {
		for(int i =0; i < numOfNodes; i++) {
			Node node = new Node(String.valueOf(i));
			myTree.put(node.myId, node);
		}
	}
	
	static void update(Node theChildNode) {
		for(Node parentNode : theChildNode.myParentNodes) {
			if(parentNode.myLevel + 1 > theChildNode.myLevel) {
				theChildNode.myLevel = parentNode.myLevel + 1;
				for(Node ancestor : theChildNode.myChildNodes) {
					update(ancestor);
				}
			}
		}
	}

	public static class Node {

		String myId;
		List<Node> myParentNodes = new ArrayList<Node>();
		List<Node> myChildNodes = new ArrayList<Node>();
		int myLevel;

		public Node(String theId) {
			myLevel++;
			myId = theId;
		}
	}

}
