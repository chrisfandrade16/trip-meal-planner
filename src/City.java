import java.util.*; 

public class City //City class which has a cityName, its nearby restaurants, and its connected cities as its fields, longitude and latitude aren't need to be stored after nearbyRestaurants is determined
{
	private String cityName; //String representation of the city name
	private ArrayList<Restaurant> nearbyRestaurants; //an ArrayList of restaurant objects representing all the restaurants within 0.5 degrees of the cities longitude and latitude
	private ArrayList<City> connectedCities; //an ArrayList of all the connected cities, thus if you have an array list of cities, this would be similar to an adjacency list
	
	public City(String city, double longitude, double latitude, ArrayList<Restaurant> restaurants)
	{
		cityName = city;
		
		ArrayList<Restaurant> stores = new ArrayList<Restaurant>();
		
		if(restaurants != null)
		{
			for(Restaurant restaurant : restaurants) //check for all restaurants that are within 0.5 degrees of the city
			{
				if(Math.abs(longitude - restaurant.getLongitude()) <= 0.5 && Math.abs(latitude - restaurant.getLatitude()) <= 0.5)
				{
					stores.add(restaurant);
				}
			}
		}
		
		nearbyRestaurants = stores;
		connectedCities = new ArrayList<City>(); //Initialize this array which can be set or added to later on
	}
	
	public void setCityName(String city) //change the city name field
	{
		cityName = city;
	}
	
	public void setNearbyRestaurants(ArrayList<Restaurant> restaurants) //change the nearbyRestaurants field
	{
		nearbyRestaurants = restaurants;
	}
	
	public void setConnectedCities(ArrayList<City> cities) //change the nearbyRestaurants field
	{
		connectedCities = cities;
	}
	
	public String getCityName() //get the city name field
	{
		return cityName;
	}

	public ArrayList<Restaurant> getNearbyRestaurants() //get the nearby restaurants as an array list
	{
		return nearbyRestaurants;
	}
	
	public ArrayList<City> getConnectedCities() //get the connected cities as an array list
	{
		return connectedCities;
	}
	
	public void addToNearbyRestaurants(Restaurant restaurant) //add a restaurant to the nearby restaurants array list
	{
		nearbyRestaurants.add(restaurant);
	}
	
	public void addToConnectedCities(City city) //add a city to the connected drinks to the connected cities array list
	{
		connectedCities.add(city);
	}
	
	public Meal findMostCheapestMeal() //find the cheapest meal by looping through the restaurants
	{
		Meal mostCheapestMeal = new Meal("", "", Double.MAX_VALUE);
		
		for(Restaurant restaurant : nearbyRestaurants)
		{
			if(restaurant.getMostCheapestMeal().compareTo(mostCheapestMeal) == -1)
			{
				mostCheapestMeal.setRestaurantName(restaurant.getMostCheapestMeal().getRestaurantName());
				mostCheapestMeal.setMealName(restaurant.getMostCheapestMeal().getMealName());
				mostCheapestMeal.setPriceTag(restaurant.getMostCheapestMeal().getPriceTag());
			}
		}
		
		return mostCheapestMeal;
	}
	
	public Meal findNextCheapestMeal() //find the next cheapest meal by looping through the restaurants, same as above method but don't include the most cheapest meal when looping
	{
		Meal mostCheapestMeal = findMostCheapestMeal();
		Meal nextCheapestMeal = new Meal("", "", Double.MAX_VALUE);
		
		for(Restaurant restaurant : nearbyRestaurants)
		{	
			if(restaurant.getMostCheapestMeal().compareTo(nextCheapestMeal) == -1 && !(restaurant.getMostCheapestMeal().equals(mostCheapestMeal)))
			{
				nextCheapestMeal.setRestaurantName(restaurant.getMostCheapestMeal().getRestaurantName()); //deep copy by setting each field, and not the whole object
				nextCheapestMeal.setMealName(restaurant.getMostCheapestMeal().getMealName());
				nextCheapestMeal.setPriceTag(restaurant.getMostCheapestMeal().getPriceTag());
			}
			else if(restaurant.getNextCheapestMeal().compareTo(nextCheapestMeal) == -1) //if next cheapest meal for the city could not be chosen because it's already the most cheapest meal for the city, check restaurant's next cheapest meal
			{
				nextCheapestMeal.setRestaurantName(restaurant.getNextCheapestMeal().getRestaurantName());
				nextCheapestMeal.setMealName(restaurant.getNextCheapestMeal().getMealName());
				nextCheapestMeal.setPriceTag(restaurant.getNextCheapestMeal().getPriceTag());
			}
		}
		
		return nextCheapestMeal;
	}
	
	public boolean equals(City city) //equals method that is true if the city name's are equal, since there only exists one city
	{
		if(cityName.equals(city.getCityName()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String toString() //toString method that returns the string representation of the city object, which is by its city name field
	{
		return cityName;
	}
	
	public int compareTo(City city) //compareTo method returns -1 if instance's cheapest meal's price is less than given object's cheapest meal's price, 1 if more, 0 if equal
	{
		if(findMostCheapestMeal().compareTo(city.findMostCheapestMeal()) == -1)
		{
			return -1;
		}
		else if(findMostCheapestMeal().compareTo(city.findMostCheapestMeal()) == 1)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
