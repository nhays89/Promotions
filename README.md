# Promotions

2015 ACM ICPC Western and SouthWestern European Regionals programming problem. 

![alt](https://github.com/nhays89/Promotions/blob/master/Promotions/img/promotions.PNG)

## Problem 

The Fair Inc. administration decided to promote the best employees
and limited the number of promotions to a fixed interval [A, B].
The directors compared the employees’ performance and their evaluations
resulted in a consistent precedence relation among employees,
which has to be respected by promotions. This means that, for every
pair of employees x and y, if x outperformed y, then y may be
promoted only if x is promoted.
In order to understand whether the data collected so far is enough
for ensuring fairness, the executive chairman wants to know:

* How many employees will certainly be promoted in the interval
endpoints (i.e., if the number of promotions is A and if the
number of promotions is B)?

* How many employees have no possibility of being promoted
(even if the number of promotions is B)?

Consider the example depicted in the figure. There are seven
employees and eight precedence rules. An arrow from an employee
x to an employee y means that x outperformed y. The number of promotions is limited to the interval
[3, 4]. Therefore:

* If there are only three promotions, the promoted employees must be:
  *  either Anne, Bob and Greg,
  * or Anne, Eve and Greg.

In this case, two employees (Anne and Greg) will certainly be promoted. Notice that, with the
current information, Bob and Eve may or may not win a promotion.
* If there are four promotions, the promoted employees have to be:
  * Anne, Bob, Eve and Greg.

So, with four promotions, four employees (Anne, Bob, Eve and Greg) will certainly be promoted
and three employees (Cora, Dan and Fred) have no possibility of being promoted.
Write a program that, given the interval of the number of promotions, the set of employees and
the precedence relation among them, computes, for each of the interval endpoints, the number of
employees that will certainly be promoted, and the number of employees that have no possibility of
being promoted.

The precedence relation is consistent in the sense that, if an employee x outperformed an employee
y, y did not outperform (directly or indirectly) x.

### Input 
---
The input file contains several test cases, each of them as described below.
The first line of the input has four space separated integers: A, B, E, and P. A and B are the
interval endpoints, E is the number of employees and P is the number of precedence rules. Employees
are identified by integers, ranging from 0 to E − 1.

Each of the following P lines contains two distinct space separated integers, x and y, which indicate
that employee x outperformed employee y.

<b>Constraints:</b><br>
<samp>1 ≤ A < B < E</samp>  (Interval endpoints).<br>
<samp>2 ≤ E ≤ 5,000</samp>  (Number of employees).<br>
<samp>1 ≤ P ≤ 20,000</samp> (Number of precedence rules).<br>
### Output
---
For each test case, the output consists of three lines. The first line contains the number of employees
that will certainly be promoted if there are A promotions. The second line contains the number of
employees that will certainly be promoted if there are B promotions. The third line contains the
number of employees that have no possibility of being promoted (even if there are B promotions).

### Sample Input
---
3 4 7 8
<br>0 4
<br>1 2
<br>1 5
<br>5 2
<br>6 4
<br>0 1
<br>2 3
<br>4 5
### Sample Output
---
2
<br>4
<br>3

## Solution

Begin by creating <samp>E</samp> Node objects, and inserting them into a [HashMap](https://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html). In this implementation, 
a <code>Node</code> represents an Employee. Each node contains the following properties: id, list of parent nodes, list of child nodes, and level.

```
static void createNodes() {
		for (int i = 0; i < numOfNodes; i++) {
			Node node = new Node(String.valueOf(i));
			myHashMap.put(node.myId, node);
		}
	}
```

Next, establish each parent-child relationship according to the precedence rule. The parent node is always the first integer followed by the child node.
```
for (int i = 0; i < numOfRules; i++) {
			Node parentNode = myHashMap.get(scanIn.next());
			Node childNode = myHashMap.get(scanIn.next());
			childNode.myParentNodes.add(parentNode);
			parentNode.myChildNodes.add(childNode);
			update(childNode);
		}
```

Once the parent-child relationship has been established, recursively <b>update</b> the child's node and its children
<em>if and only if</em> the parent's level + 1 is greater than the childs current level. Once complete, the levels of all children nodes are now accurate, and the next precedence rule can be processed.

```
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
```

After all precendence rules have been processed, transfer the values of the <code>myHashMap</code> to an ArrayList, and sort on <em>level</em>. Once sorted, the nodes are grouped by their respective level. The grouping of nodes by level provides access
to the number of nodes per level. The number of nodes per level will determine how many employees are going be promoted for each interval. 

The process goes as follows: 

lowIntervalTotal := 0
<br>highIntervalTotal := 0

For each interval:

  For each level in list:
  
  1. Calculate the number of nodes in current level (start at 0). 
  2. Subtract the count from each Interval Endpoint (i.e A or B).
  3. If the value remaining in the Interval Endpoint is negative ? return interval total for that interval. break.
  4. Else if the value remaining in the Interval Endpoint is 0 ? return interval total + current level count. break.
  5. Else add current level count to interval total. continue. 









