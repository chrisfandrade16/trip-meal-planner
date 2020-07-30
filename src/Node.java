import java.util.*;

public class Node //Node class which will represent the nodes for the graph used in running dijkstra's
{
	private Meal meal; //meal object, which is what the node is focused around
	private City city; //city object so we know which meal this city belongs to
	private ArrayList<Edge> connections; //ArrayList of outgoing edges to to connected meals; meals from same city or same meal will not be connected
	
	public Node(Meal meal, City city)
	{
		this.meal = meal;
		this.city = city;
		this.connections = new ArrayList<Edge>(); //Initialize the connections array to be added to later
	}
	
	public void setMeal(Meal newMeal) //set new meal field
	{
		meal = newMeal;
	}
	
	public void setCity(City newCity) //set new city field
	{
		city = newCity;
	}
	
	public void setConnections(ArrayList<Edge> newConnections) //set new connections field
	{
		connections = newConnections;
	}
	
	public Meal getMeal() //get meal field object
	{
		return meal;
	}
	
	public City getCity() //get city field object
	{
		return city;
	}
	
	public ArrayList<Edge> getConnections() //get connections field ArrayList
	{
		return connections;
	}
	
	public void addToConnections(Edge edge) //add to the connections ArrayList field
	{
		connections.add(edge);
	}
	
	public boolean equals(Node node) //an equals method which checks if both meal and city fields are equal using their respective equals methods
	{
		if(meal.equals(node.getMeal()) && city.equals(node.getCity()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String toString() //toString method that returns the city; although meals are technically nodes, it's easier to read the nodes by city, regardless each city has at most 2 nodes, one for cheapest meal and one for second cheapest meal
	{
		return city.getCityName();
	}
	
	public int compareTo(Node node) //compareTo method that compares the two objects' meals using the Meal class compareTo method
	{
		if(meal.compareTo(node.getMeal()) == -1)
		{
			return -1;
		}
		else if(meal.compareTo(node.getMeal()) == 1)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
