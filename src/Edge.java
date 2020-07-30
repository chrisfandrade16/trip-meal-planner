public class Edge //Edge class which has a destination node and a weight
{
	private Node destination; //destination field of type Node which represents what node the directed edge leads to
	private Double weight; //weight field of type Double which represents the weight of the edge
	
	public Edge(Node node, Double price)
	{
		this.destination = node;
		this.weight = price;
	}
	
	public void setDestination(Node newDestination) //set new destination field
	{
		destination = newDestination;
	}
	
	public void setWeight(Double newWeight) //set new weight field
	{
		weight = newWeight;
	}
	
	public Node getDestination() //get the destination node
	{
		return destination;
	}
	
	public Double getWeight() //get weight of edge
	{
		return weight;
	}
	
	public boolean equals(Edge edge) //equals method that returns true if the destination fields are equal and the weights of the edge are equal
	{
		if(destination.equals(edge.getDestination()) && weight.equals(edge.getWeight()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String toString() //toString method that returns the toString of the Node destination field
	{
		return destination.toString();
	}
	
	public int compareTo(Edge edge) //compareTo method that compares the instance and the given object
	{
		if(weight.compareTo(edge.getWeight()) == -1)
		{
			return -1;
		}
		else if(weight.compareTo(edge.getWeight()) == 1)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
