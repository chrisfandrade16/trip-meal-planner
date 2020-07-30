import java.io.*;
import java.util.*; 

public class ReadCSV //reads all CSV files, method for each type
{
	public static ArrayList<Meal> readMenu() //reads menu.csv, creates a meal object for every meal on the menu, adds all of them to an ArrayList and returns it
	{
		ArrayList<Meal> meals = new ArrayList<Meal>();
		
		try
		{
			BufferedReader menuFile = new BufferedReader(new FileReader("data/menu.csv"));
			String line = menuFile.readLine();
			line = menuFile.readLine();
			
			while(line != null)
			{
				String[] row = line.split(","); //since CSV columns are separated by commas, we split a whole line read from the file by the commas
				row[0] = row[0].replace("’", "'").replace("�", "'").trim(); //this row represents the restaurant name, we replace the unorthodox type of apostrophes with regular ones
				row[1] = row[1].trim(); //this row represents the restaurant name, trims any whitespace at start or end of the 
				row[2] = row[2].replace("$", "").trim(); //this row represents the price but we remove the dollar sign so we can parse the string into a double
				
				meals.add(new Meal(row[0], row[1], Double.parseDouble(row[2]))); //creates a meal object based on the rows above extracted from the file, then adds it to a meal ArrayList
				
				line = menuFile.readLine(); //read the next line in the file
			}
			
			menuFile.close();
		} 
		catch(FileNotFoundException error)
		{
			error.printStackTrace();
		}
		catch(IOException error)
		{
			error.printStackTrace();
		}
		
		return meals;
	}
	
	public static ArrayList<Restaurant> readRestaurants(ArrayList<Meal> meals) //read all 3 restaurant CSV files and take in the ArrayList of meals from the previous method to make restaurant objects
	{
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		ArrayList<Meal> burgerkingMeals = new ArrayList<Meal>();
		ArrayList<Meal> mcdonaldsMeals = new ArrayList<Meal>();
		ArrayList<Meal> wendysMeals = new ArrayList<Meal>();
		
		for(Meal meal : meals) //go through each meal in the ArrayList of meals and add meals to their designated array
		{
			if(meal.getRestaurantName().equals("Burger King"))
			{
				burgerkingMeals.add(meal);
			}
			else if(meal.getRestaurantName().equals("McDonald's"))
			{
				mcdonaldsMeals.add(meal);
			}
			else if(meal.getRestaurantName().equals("Wendy's"))
			{
				wendysMeals.add(meal);
			}
		}
		
		try
		{
			BufferedReader burgerkingFile = new BufferedReader(new FileReader("data/burgerking.csv"));
			BufferedReader mcdonaldsFile = new BufferedReader(new FileReader("data/mcdonalds.csv"));
			BufferedReader wendysFile = new BufferedReader(new FileReader("data/wendys.csv"));
			
			String line1 = burgerkingFile.readLine(); //read through the headings which aren't included when making the restaurant objects
			line1 = burgerkingFile.readLine();
			String line2 = mcdonaldsFile.readLine();
			line2 = mcdonaldsFile.readLine();
			String line3 = wendysFile.readLine();
			line3 = wendysFile.readLine();
			
			while(line1 != null)
			{
				String[] row = line1.split(",");
				row[2] = processRestaurantName(row[2]); //get the restaurant name and process it so it's consistent with other restaurant names from other files
				
				restaurants.add(new Restaurant(Double.parseDouble(row[0]), Double.parseDouble(row[1]), row[2], burgerkingMeals)); //parse the longituides and latitudes to be doubles and create restaurant objects
				
				line1 = burgerkingFile.readLine();
			}
			while(line2 != null)
			{
				String[] row = line2.split(",");
				row[2] = processRestaurantName(row[2]);
				
				restaurants.add(new Restaurant(Double.parseDouble(row[0]), Double.parseDouble(row[1]), row[2], mcdonaldsMeals));
				
				line2 = mcdonaldsFile.readLine();
			}
			while(line3 != null)
			{
				String[] row = line3.split(",");
				row[2] = processRestaurantName(row[2]);
				
				restaurants.add(new Restaurant(Double.parseDouble(row[0]), Double.parseDouble(row[1]), row[2], wendysMeals));
				
				line3 = wendysFile.readLine();
			}
			
			burgerkingFile.close();
			mcdonaldsFile.close();
			wendysFile.close();
		} 
		catch(FileNotFoundException error)
		{
			error.printStackTrace();
		}
		catch(IOException error)
		{
			error.printStackTrace();
		}
		
		return restaurants; //return an ArrayList of all the possible restaurants
	}
	
	public static ArrayList<City> readCities(ArrayList<Restaurant> restaurants) //takes in the ArrayList of restaurants of forms an ArrayList of all the cities and returns it
	{
		ArrayList<City> cities = new ArrayList<City>();
		
		try
		{
			BufferedReader uscitiesFile = new BufferedReader(new FileReader("data/USCities.csv"));
			String line = uscitiesFile.readLine();
			line = uscitiesFile.readLine();
			
			while(line != null)
			{
				String[] row = line.split(",");
				row[3] = processCityName(row[3]); //process the city name so it is consistent with the formatting for other strings we've used this far
				
				cities.add(new City(row[3], Double.parseDouble(row[5]), Double.parseDouble(row[4]), restaurants)); //create a city object with this information and add it to our list
				
				line = uscitiesFile.readLine();
			}
			
			uscitiesFile.close();
		} 
		catch(FileNotFoundException error)
		{
			error.printStackTrace();
		}
		catch(IOException error)
		{
			error.printStackTrace();
		}
		
		return cities;
	}
	
	private static String processRestaurantName(String restaurantName) //since formatting is very inconsistent between all the files, we will have to manually check the first letter and assign it from there
	{
		if(restaurantName.charAt(1) == 'B')
		{
			return "Burger King";
		}
		else if(restaurantName.charAt(1) == 'M')
		{
			return "McDonald's";
		}
		else
		{
			return "Wendy's";
		}
	}
	
	private static String processCityName(String cityName) //some files use all capitals for the cities, others don't, so we make sure any letter without a space before it, and not the first letter, becomes lowercase
	{
		String properCityName = "" + cityName.charAt(0);
		
		for(int i = 1; i < cityName.length(); i++)
		{ 
			if(cityName.charAt(i - 1) != ' ') //if space before is not a space, then make it lower case
			{
				properCityName += Character.toLowerCase(cityName.charAt(i));
			}
			else
			{
				properCityName += cityName.charAt(i);
			}
		}
		
		return properCityName.trim(); //trim white space, then return the final proper city name
	}
}
