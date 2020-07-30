import java.io.*;
import java.util.*; 

public class Graph //Graph class with 3 fields, one is the ArrayList of all the cities, and the other two are HashMaps representing adjacency lists
{
	HashMap<String, City> graph1; //used for BFS and DFS, maps a string name of a city to a city object, and each city object has a list of connected city objects
	HashMap<String, Node> graph2; //used for Dijkstra's, maps a string name of a city + 1 or + 2 (1 for cheapest meal in city, 2 for second cheapest meal in city) to a node, where each node is a meal, with a city associated with it, and a list of outgoing edges 
	ArrayList<City> allCities; //field of all the cities in a list, used later so we can reinitialize the graph after a Dijkstra is completed since we edit the graph temporarily
	
	public Graph(ArrayList<City> cities) //the constructor calls a private initialization method, this allows us to reset the graph by the calling the "constructor" (the GraphInit) so that our changes to the fields are reset
	{	
		GraphInit(cities);
	}
	
	private void GraphInit(ArrayList<City> cities)
	{
		HashMap<String, City> map1 = new HashMap<String, City>(); //this will become graph1 field
		HashMap<String, Node> map2 = new HashMap<String, Node>(); //this will become graph2 field
		
		for(City city : cities) //first put all the city objects into the HashMap
		{
			map1.put(city.getCityName(), city);
			
			//map2.put(city.getCityName() + " 1", new Node(city.findMostCheapestMeal(), city)); //for each city, put a Node object of that city name + 1 and it's cheapest meal, and also the city name + 2 and it's second cheapest meal
			//map2.put(city.getCityName() + " 2", new Node(city.findNextCheapestMeal(), city));
		}
		
		try
		{	
			BufferedReader connectedcitiesFile = new BufferedReader(new FileReader("data/connectedCities.txt"));
			String line = connectedcitiesFile.readLine();
			
			while(line != null)
			{
				String[] row = line.split(", ");
				City city1 = map1.get(row[0]); //each line in the connectedCities.txt file contains two cities
				City city2 = map1.get(row[1]);
				
				city1.addToConnectedCities(city2); //add connection from first city in row to second city in row
				
				line = connectedcitiesFile.readLine();
			}
			
			connectedcitiesFile.close();
		} 
		catch (FileNotFoundException error)
		{
			error.printStackTrace();
		}
		catch (IOException error)
		{
			error.printStackTrace();
		}
		
		for(City city: cities)
		{
			map2.put(city.getCityName() + " 1", new Node(city.findMostCheapestMeal(), city)); //for each city, put a Node object of that city name + 1 and it's cheapest meal, and also the city name + 2 and it's second cheapest meal
			map2.put(city.getCityName() + " 2", new Node(city.findNextCheapestMeal(), city));
		}
		
		for(Node node : map2.values()) //check over all the nodes we put into map2
		{
			for(City city : node.getCity().getConnectedCities()) //every node has a city, which has its connected cities now set, so we will check over these connected cities
			{
				if(!(city.findMostCheapestMeal().equals(node.getMeal()))) //if the city's cheapest meal and the node's meal are NOT equal, we can add an edge between them
				{
					node.addToConnections(new Edge(map2.get(city.getCityName() + " 1"), city.findMostCheapestMeal().getPriceTag())); //creates an edge by getting the corresponding node from map2
				}
				if(!(city.findNextCheapestMeal().equals(node.getMeal()))) //if the city's second cheapest meal and the node's meal are NOT equal, we can add an edge between them
				{
					node.addToConnections(new Edge(map2.get(city.getCityName() + " 2"), city.findNextCheapestMeal().getPriceTag()));
				}
			}
		}
		
		graph1 = map1;
		graph2 = map2;
		allCities = cities;
	}
	
	public String BFS(String startCityName, String finalCityName) //BFS method that prints to a2_out.txt and returns the String of what it printed
	{
		HashMap<City, City> parentMap = new HashMap<City, City>(); //this parentMap will allow us to keep track of each City node's parent so that we can retrace the path and print it
		HashMap<City, Boolean> visitedMap = new HashMap<City, Boolean>(); //will keep track of visited City nodes
		
		for(City city : graph1.values())
		{
		    visitedMap.put(city, false); //set all City nodes in visited map to false
		}
		
		Queue<City> queue = new LinkedList<City>(); //setting up queue for BFS
		queue.add(graph1.get(startCityName)); //add the City node represented by the starting city name input
		visitedMap.put(graph1.get(startCityName), true); //mark the starting city as visited
		
		while(queue.size() != 0)
		{
			City currentCity = queue.poll(); //dequeue item and return it
			
			for(City city : currentCity.getConnectedCities()) //go through all the neighbor cities from the city we dequeued
			{
				if(!(visitedMap.get(city))) //if the neighboring city is not visited already
				{
					parentMap.put(city, currentCity); //set the neighboring city's parent to be the dequeued city
					visitedMap.put(city, true); //mark the neighboring city as visited
					queue.add(city); //add this neighbor city to the queue
				}
			}
		}
		
		String output = findRouteBFSDFS(startCityName, finalCityName, parentMap, "BFS"); //call function with starting city, ending city, and the parent map to retrace path and print it
		return output; //return the printed path output string
	}
	
	public String DFS(String startCityName, String finalCityName) //DFS method, very similar to BFS method explained above
	{	
		HashMap<City, City> parentMap = new HashMap<City, City>(); //parentMap and visitedMap just like in BFS
		HashMap<City, Boolean> visitedMap = new HashMap<City, Boolean>();
		
		for(City city : graph1.values())
		{
		    visitedMap.put(city, false);
		}
		
		DFSRecursion(startCityName, finalCityName, parentMap, visitedMap); //begin recursive part of DFS so we don't have to use our own stack
		
		String output = findRouteBFSDFS(startCityName, finalCityName, parentMap, "DFS");
		return output; //return output string of the path from starting city to final city
	}
	
	private void DFSRecursion(String startCityName, String finalCityName, HashMap<City, City> parentMap, HashMap<City, Boolean> visitedMap)
	{
		City currentCity = graph1.get(startCityName);
		visitedMap.put(currentCity, true);
		
		for(City city : currentCity.getConnectedCities()) //go through all the current city's connections
		{
			if(!(visitedMap.get(city))) //if it's not visited
			{
				parentMap.put(city, currentCity); //mark its parent
				DFSRecursion(city.getCityName(), finalCityName, parentMap, visitedMap); //recall this method but this time with the starting city as the neighbor city
			}
		} //recursion will return when the city does not have any non visited connected cities
	}
	
	public String Dijkstra(String startCityName, String finalCityName) //Dijkstra method to find the shortest path tree
	{
		HashMap<Node, Boolean> sptMap = new HashMap<Node, Boolean>(); //sptMap will check to see if a given node is already in the shortest path tree
		HashMap<Node, Node> parentMap = new HashMap<Node, Node>();
		HashMap<Node, Double> distanceMap = new HashMap<Node, Double>(); //distance map which marks the distance of a node from the source
		
		Meal auxiliaryMeal = new Meal("A", "A", 0.0); //setting up auxiliary node fields
		City auxiliaryCity = new City("A", 0.0, 0.0, null);
		Node auxiliaryNode = new Node(auxiliaryMeal, auxiliaryCity); //the auxiliary node allows us to not have to choose to start at startCityName + " 1" or startCityName + " 2" since we don't know which will lead to the shortest path yet for startCity to finalCity
		
		Edge auxiliaryEdge1 = new Edge(graph2.get(startCityName + " 1"), 0.0); //add an edge of weight 0 to startCity 1
		Edge auxiliaryEdge2 = new Edge(graph2.get(startCityName + " 2"), 0.0); //add an edge of weight 0 to startCity 2
		
		auxiliaryNode.addToConnections(auxiliaryEdge1); //add the created edges to the auxiliary node's edge connections
		auxiliaryNode.addToConnections(auxiliaryEdge2);
		
		graph2.put("A", auxiliaryNode); //put auxiliary node into the graph2 field, will be removed later
		
		Node startNode1 = graph2.get(startCityName + " 1");
		Node startNode2 = graph2.get(startCityName + " 2");
		
		for(Edge edge1: startNode2.getConnections()) //since you don't have to eat a meal at the starting city, there should be an edge from the starting city meal nodes to all other connected city meal nodes
		{
			if(!(startNode1.getConnections().contains(edge1))) //only add the edge if it doesn't already exist
			{
				startNode1.addToConnections(edge1);
			}
		}
		for(Edge edge2: startNode1.getConnections())
		{
			if(!(startNode2.getConnections().contains(edge2)))
			{
				startNode2.addToConnections(edge2);
			}
		}
		
		for(Node node: graph2.values()) //set all distances to max value
		{
			distanceMap.put(node, Double.MAX_VALUE);
			sptMap.put(node, false);
		}
		
		distanceMap.put(graph2.get("A"), 0.0); //except our starting auxiliary node, we will set its distance to 0
		
		for(int i = 0; i < graph2.size() - 1; i++)
		{
			Node node = minimumDistance(sptMap, distanceMap); //get the meal Node that has the minimum distance and isn't already in the shortest path tree
			
			sptMap.put(node, true); //put it into the shortest path tree
			
			for(Edge edge: node.getConnections()) //for all the node's outgoing edges, we will update the distances if needed
			{
				Node node2 = edge.getDestination(); //get the edge's destination
				Double weight = edge.getWeight(); //get the edge's weight
				
				//if the destination node of the edge isn't already in the sptMap, its distance isn't the max value/infinity, and the weight of the edge plus the distance so far is less than the distance of the destination node
				if (sptMap.get(node2) == false && distanceMap.get(node) != Double.MAX_VALUE && distanceMap.get(node) + weight < distanceMap.get(node2))
				{
					parentMap.put(node2, node);
					distanceMap.put(node2, distanceMap.get(node) + weight); //update the destination node's distance, which will now be it's parent node's distance plus the edge weight
				}
			}
			
		}
		
		String finalNodeName = null;
		
		//the final meal node (so we know which meal to eat at the final city) depends on if the distance to the most expensive meal or second most expensive meal is shorter
		if(distanceMap.get(graph2.get(finalCityName + " 1")) < distanceMap.get(graph2.get(finalCityName + " 2")))
		{
			finalNodeName = finalCityName + " 1";
		}
		else
		{
			finalNodeName = finalCityName + " 2";
		}
		
		String output = findRouteDijkstra(startCityName, finalNodeName, parentMap); //call function to retrace and print route using the parent map
		return output;
	}
	
	private Node minimumDistance(HashMap<Node, Boolean> sptMap, HashMap<Node, Double> distanceMap) //method to find and return the node with the minimum distance while doing Dijkstra's
	{
		double minimum = Double.MAX_VALUE;
		Node minimumNode = null;
		
		for(Node node: graph2.values())
		{
			if(sptMap.get(node) == false && distanceMap.get(node) <= minimum) //if the node isn't in the sptMap and its distance is less than or equal to the minium so far
			{
				minimum = distanceMap.get(node); //keep track of that distance to see if there is any shorter
				minimumNode = node; //the minimum node is now that node
			}
		}
		
		return minimumNode; //return the minimum distance node
	}
	
	private String findRouteBFSDFS(String startCityName, String finalCityName, HashMap<City, City> parentMap, String BFSorDFS) //retrace path using parentMap and a stack
	{
		String route = BFSorDFS + ": "; //start with "BFS: " or "DFS: " depending on input parameter String BFSorDFS, this method works for both
		
		Stack<String> stack = new Stack<String>();
		stack.push(finalCityName); //first push the final city name onto the stack
		
		while(!(stack.peek().equals(startCityName))) //while the top element of the stack isn't the starting city
		{
			if(parentMap.get(graph1.get(stack.peek())) == null)
			{
				return null;
			}
			stack.push(parentMap.get(graph1.get(stack.peek())).getCityName()); //push the parent of the final city, then that city's parent, and so on, until we get to the starting city
		}
        while(stack.size() != 0) //now we pop from the stack to show the path from the starting city to the final city
        {
        	route += stack.pop() + ", ";
        }
        
        route = route.substring(0, route.length() - 2); //remove extra comma
        
        try
        {	
        	PrintWriter file = new PrintWriter(new FileOutputStream("a2_out.txt", true)); //true here means append and not overwrite
	        file.println(route); //append the route to the a2_out.txt file
	        file.close();
        }
        catch(FileNotFoundException error)
		{
			error.printStackTrace();
		}
        
        return route;
	}
	
	private String findRouteDijkstra(String startNodeName, String finalNodeName, HashMap<Node, Node> parentMap) //similar procedure as above findRouteBFSDFS method
	{
		String table = "City, Meal Choice, Cost of Meal\n"; //first set the headings
		
		Stack<Node> stack = new Stack<Node>(); //this time our stack will be of Nodes
		stack.push(graph2.get(finalNodeName)); //push final node
		
		while(!(stack.peek().equals(graph2.get("A")))) //while the top of stack isn't the auxiliary node (we must go to this since we wouldn't know to stop at starting city meal 1 or starting city meal 2
		{
			if(parentMap.get(stack.peek()) == null)
			{
				return null;
			}
			stack.push(parentMap.get(stack.peek()));
		}
        while(stack.size() != 0)
        {
        	if(stack.peek().getCity().getCityName().equals("A")) //if it's the auxiliary node, just pop it but don't add it to the string to be printed
        	{
        		stack.pop();
        	}
        	else if(stack.peek().getCity().getCityName().equals(startNodeName)) //if it's the starting city, we don't have to eat a meal there so the row is shown slightly differently
        	{
        		table += stack.peek().getCity().getCityName() + ", No Meal Required To Be Eaten In Starting City, 0.00\n";
        		stack.pop();
        	}
        	else //otherwise add the city name, the meal name, and its price that was chosen in the path
        	{
        		table += stack.peek().getCity().getCityName() + ", " + stack.peek().getMeal().getMealName() + ", " + stack.peek().getMeal().getPriceTag() + "\n";
        		stack.pop();
        	}
        }
        
        table = table.substring(0, table.length() - 1); //get rid of extra new line
        
        try
        {	
        	PrintWriter file = new PrintWriter(new FileOutputStream("a2_out.txt", true));
	        file.println(table);
	        file.close();
        }
        catch(FileNotFoundException error)
		{
			error.printStackTrace();
		}
        
        GraphInit(allCities); //recall the initializing method so our graph is reset and the auxiliary node plus the edges we made from the starting city to its neighbors are removed
        return table;
	}
}
