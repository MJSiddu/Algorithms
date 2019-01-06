import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//read data
		Scanner in = new Scanner(System.in); 
		Graph graph = new Graph();
		  
        String s = in.nextLine(); 
        
        String[] inputs = s.split(" ");
        int nodesCount = Integer.parseInt(inputs[0]);
        int edgesCount = Integer.parseInt(inputs[1]);
        for(int i = 0; i < edgesCount; i++) {
        	String edge = in.nextLine();
        	String[] edgeData = edge.split(" ");
        	int startNode = Integer.parseInt(edgeData[0]);
        	int endNode = Integer.parseInt(edgeData[1]);
        	int edgeWeight = Integer.parseInt(edgeData[2]);
        	if(!graph.getNodesMap().containsKey(startNode)) {
        		Node newNode = new Node(startNode);
        		graph.addNode(startNode, newNode);
        	}
        	if(!graph.getNodesMap().containsKey(endNode)) {
        		Node newEndNode = new Node(endNode);
        		graph.addNode(endNode, newEndNode);
        	}
        	addEdges(graph, startNode, endNode, edgeWeight);	
        }
        String points = in.nextLine();
        String[] executionData = points.split(" ");
        int source = Integer.parseInt(executionData[0]);
        int destination = Integer.parseInt(executionData[1]);
        runDijkstra(graph, source, destination);
        printPath(graph, source, destination);
	}
	
	private static void runDijkstra(Graph g, int source, int dest) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new NodeComparator());
		HashSet<Integer> settledNodes = new HashSet<Integer>();
		Node sourceNode = g.getNodesMap().get(source);
		sourceNode.setDistance(0);
		sourceNode.setEdgesCount(0);
		for (Entry<Integer,Node> pair : g.getNodesMap().entrySet()){
	        //iterate over the pairs and add to priority queue
	        pq.add(pair.getValue());
	    }
		while (!pq.isEmpty()) { 
			Node current = pq.poll();
			settledNodes.add(current.getIndex());
			for (Entry<Integer,Integer> entry : current.getAdjacentNodesList().entrySet()){
				if(!settledNodes.contains(entry.getKey())) {
					Node adjacentNode = g.getNodesMap().get(entry.getKey());
					if(adjacentNode.getDistance() > (current.getDistance() + entry.getValue())) {
						pq.remove(adjacentNode);
						adjacentNode.setDistance(current.getDistance() + entry.getValue());
						//set parent
						adjacentNode.setParent(current);
						adjacentNode.setEdgesCount(current.getEdgesCount() + 1);
						pq.add(adjacentNode);
					}
					if(adjacentNode.getDistance() == (current.getDistance() + entry.getValue())) {
						if(adjacentNode.getEdgesCount() > (current.getEdgesCount() + 1)) {
							pq.remove(adjacentNode);
							adjacentNode.setParent(current);
							adjacentNode.setEdgesCount(current.getEdgesCount() + 1);
							pq.add(adjacentNode);
						} else if(adjacentNode.getEdgesCount() == (current.getEdgesCount() + 1)) {
							if(isBetter(adjacentNode.getParent(), current)) {
								pq.remove(adjacentNode);
								adjacentNode.setParent(current);
								pq.add(adjacentNode);
							}
						} else {
							continue;
						}
					}
				}	
		    }
		}
	}
	
	private static void printPath(Graph g, int source, int dest) {
		Node destination = g.getNodesMap().get(dest);
		System.out.println(destination.getDistance());
		List<Integer> path = new ArrayList<Integer>();
		path.add(destination.getIndex());
		while(destination.getParent() != null) {
			path.add(destination.getParent().getIndex());
			destination = destination.getParent();
		}
		Collections.reverse(path); 
		for (int i = 0; i < path.size() - 1; i++) { 
            System.out.print(path.get(i) + " "); 
        } 
		System.out.println(path.get(path.size() - 1));
	}

	private static boolean isBetter(Node parent, Node current) {
		
		List<Integer> path1 = new ArrayList<Integer>();
		path1.add(parent.getIndex());
		while(parent.getParent() != null) {
			path1.add(parent.getParent().getIndex());
			parent = parent.getParent();
		}
		Collections.reverse(path1);
		List<Integer> path2 = new ArrayList<Integer>();
		path2.add(current.getIndex());
		while(current.getParent() != null) {
			path2.add(current.getParent().getIndex());
			current = current.getParent();
		}
		Collections.reverse(path2);
		int j = 0;
		// returns true if current is better than existing
		for(j = 0; j < path1.size(); j++) {
			if(path1.get(j) == path2.get(j)) {
				continue;
			} else {
				return path1.get(j) > path2.get(j);
			}
		}
		return false;		
	}

	private static void addEdges(Graph graph, int startNode, int endNode, int weight) {
		
		Node nodeOne = graph.getNodesMap().get(startNode);
		Node nodeTwo = graph.getNodesMap().get(endNode);
		nodeOne.addEdge(endNode, weight);
		nodeTwo.addEdge(startNode, weight);	
		
	}
	
}

class Graph {
	private Map<Integer, Node> nodesMap;
	
	public Graph() {
		nodesMap = new HashMap<Integer, Node>();
	}
	public void addNode(int index, Node node) {
		nodesMap.put(index, node);
	}
	public Map<Integer, Node> getNodesMap() {
		return nodesMap;
	}
}


class NodeComparator implements Comparator<Node>{ 
    public int compare(Node n1, Node n2) { 
        if (n1.distance > n2.distance) 
            return 1; 
        else if (n1.distance < n2.distance) 
            return -1;
        else
        	return 0; 
        } 
} 

class Node {
	
	int index;
	int distance;
	int edgesCount;
	Node parent;
	Map<Integer, Integer> adjacentNodes;
	
	public Node(int ind) {
		
		index = ind;
		adjacentNodes = new HashMap<Integer, Integer>();
		parent = null;
		edgesCount = Integer.MAX_VALUE;
		distance = Integer.MAX_VALUE;
		
	}
	
	public void addEdge(int vertexIndex, int weight) {
		this.getAdjacentNodesList().put(vertexIndex, weight);
	}
	
	public Map<Integer, Integer> getAdjacentNodesList(){
		return this.adjacentNodes;
	}
	
	public void setEdgesCount(int count) {
		this.edgesCount = count;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Node getParent() {
		return this.parent;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getDistance() {
		return this.distance;
	}
	public Integer getIndex() {
		return this.index;
	}
	public int getEdgesCount() {
		return this.edgesCount;
	}
	
}
