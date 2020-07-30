import static org.junit.Assert.*;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*; 

public class TestGraph
{
	private ArrayList<Meal> meals = ReadCSV.readMenu();
	private ArrayList<Restaurant> restaurants = ReadCSV.readRestaurants(meals);
	private ArrayList<City> cities = ReadCSV.readCities(restaurants);
	private Graph graph = new Graph(cities);
	
	@Test
	public void testBFS()
	{
		try
		{
			PrintWriter file = new PrintWriter(new FileOutputStream("a2_out.txt", false));
			file.close();
		}
		catch(FileNotFoundException error)
		{
			error.printStackTrace();
		}
		
		String expected1 = "BFS: Boston, New York City, Pittsburgh, Columbus, Chicago, Minneapolis";
		String expected2 = "BFS: Las Vegas, Albuquerque, Dallas, San Antonio";
		String expected3 = "BFS: Dallas";
		String expected4 = null;
		String expected5 = "BFS: Denver, Salt Lake City, Las Vegas, Los Angeles, San Francisco, Portland, Seattle";
		String result1 = graph.BFS("Boston", "Minneapolis");
		String result2 = graph.BFS("Las Vegas", "San Antonio");
		String result3 = graph.BFS("Dallas", "Dallas");
		String result4 = graph.BFS("Philadelphia", "Boston");
		String result5 = graph.BFS("Denver", "Seattle");
		assertTrue(expected1.equals(result1));
		assertTrue(expected2.equals(result2));
		assertTrue(expected3.equals(result3));
		assertTrue(expected4 == result4);
		assertTrue(expected5.equals(result5));
	}
	
	@Test
	public void testDFS()
	{
		try
		{
			PrintWriter file = new PrintWriter(new FileOutputStream("a2_out.txt", true));
			file.close();
		}
		catch(FileNotFoundException error)
		{
			error.printStackTrace();
		}
		
		String expected1 = "DFS: Baltimore, Washington, Charlotte, Atlanta, Nashville, Columbus, Indianapolis, St. Louis, Kansas City, Denver, Salt Lake City, Las Vegas, Los Angeles, San Francisco, Portland, Seattle, Minneapolis";
		String expected2 = "DFS: Houston, New Orleans, Memphis, Atlanta, Nashville, Columbus, Indianapolis, St. Louis, Kansas City, Denver, Salt Lake City, Las Vegas, Los Angeles, Phoenix, Albuquerque, Dallas, San Antonio";
		String expected3 = "DFS: New York City";
		String expected4 = null;
		String expected5 = "DFS: Oklahoma City, Dallas, Houston, New Orleans, Memphis, Atlanta, Nashville, Columbus, Indianapolis, St. Louis, Kansas City, Denver, Salt Lake City";
		String result1 = graph.DFS("Baltimore", "Minneapolis");
		String result2 = graph.DFS("Houston", "San Antonio");
		String result3 = graph.DFS("New York City", "New York City");
		String result4 = graph.DFS("Minneapolis", "Nashville");
		String result5 = graph.DFS("Oklahoma City", "Salt Lake City");
		assertTrue(expected1.equals(result1));
		assertTrue(expected2.equals(result2));
		assertTrue(expected3.equals(result3));
		assertTrue(expected4 == result4);
		assertTrue(expected5.equals(result5));
	}
}
