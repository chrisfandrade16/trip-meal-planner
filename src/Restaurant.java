import java.util.*; 

public class Restaurant //restaurant class for each unique restaurant based on its longitude and latitude
{
	private double longitude;
	private double latitude;
	private String restaurantName;
	private Meal mostCheapestMeal; //Meal object representing the cheapest meal you can get from this restaurant
	private Meal nextCheapestMeal; //Meal object representing the second cheapest meal you can get from this restaurant
	
	public Restaurant(double longi, double lati, String restaurant, ArrayList<Meal> meals)
	{
		longitude = longi;
		latitude = lati;
		restaurantName = restaurant;
		
		Meal firstCheapestMeal = new Meal ("", "", Double.MAX_VALUE); //start off with the most expensive meal possible to then start the for loop to find the cheapest and second cheapest 
		Meal secondCheapestMeal = new Meal ("", "", Double.MAX_VALUE);
		
		for(Meal meal : meals) //simple algorithm to find cheapest and second cheapest items in a single for loop
		{
            if(meal.compareTo(firstCheapestMeal) == -1)
            { 
            	secondCheapestMeal.setMealName(firstCheapestMeal.getMealName()); //deep copy by not actually just setting the objects equal to each other
            	secondCheapestMeal.setPriceTag(firstCheapestMeal.getPriceTag());
                
            	firstCheapestMeal.setMealName(meal.getMealName());
            	firstCheapestMeal.setPriceTag(meal.getPriceTag());
            } 
  
            else if(meal.compareTo(secondCheapestMeal) == -1 && meal.compareTo(firstCheapestMeal) != 0)
            {
            	secondCheapestMeal.setMealName(meal.getMealName());
            	secondCheapestMeal.setPriceTag(meal.getPriceTag());
            }
		}
		
		mostCheapestMeal = firstCheapestMeal;
		nextCheapestMeal = secondCheapestMeal;
	}
	
	public void setLongitude(double longi) //get longitude
	{
		longitude = longi;
	}
	
	public void setLatitude(double lati) //get latitude
	{
		latitude = lati;
	}
	
	public void setRestaurantName(String restaurant) //get restaurant name 
	{
		restaurantName = restaurant;
	}
	
	public void setMostCheapestMeal(Meal firstCheapestMeal) //get the cheapest meal that was found in the constructor and was set to a field
	{
		mostCheapestMeal = firstCheapestMeal;
	}
	
	public void setNextCheapestMeal(Meal secondCheapestMeal) //get the second cheapest meal that was found in the constructor and was set to a field
	{
		nextCheapestMeal = secondCheapestMeal;
	}
	
	public double getLongitude() //get longitude
	{
		return longitude;
	}
	
	public double getLatitude() //get latitude
	{
		return latitude;
	}
	
	public String getRestaurantName() //get restaurant name string
	{
		return restaurantName;
	}
	
	public Meal getMostCheapestMeal() //get most cheapest meal as Meal object
	{
		return mostCheapestMeal;
	}
	
	public Meal getNextCheapestMeal() //get second cheapest meal as Meal object
	{
		return nextCheapestMeal;
	}
	
	public boolean equals(Restaurant restaurant) //if both longitude and latitude are the same, then the restaurants must be the same
	{
		if(longitude == restaurant.getLongitude() && latitude == restaurant.getLatitude())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String toString() //toString method to represent restaurant as its name for convenience purposes
	{
		return restaurantName;
	}
	
	public int compareTo(Restaurant restaurant) //compareTo method which returns -1 is the instance's price tag is less than the given object's price tag, 1 if greater, 0 if equal
	{
		if(mostCheapestMeal.getPriceTag() < restaurant.getMostCheapestMeal().getPriceTag())
		{
			return -1;
		}
		else if(mostCheapestMeal.getPriceTag() > restaurant.getMostCheapestMeal().getPriceTag())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
