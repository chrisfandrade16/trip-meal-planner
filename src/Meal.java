public class Meal //this class represents a meal from the menu.csv file
{
	private String restaurantName; //name of restaurant the meal belongs to
	private String mealName; //name of the meal itself
	private double priceTag; //price of the meal
	
	public Meal(String restaurant, String meal, double price)
	{
		restaurantName = restaurant;
		mealName = meal;
		priceTag = price;
	}
	
	public void setRestaurantName(String restaruant) //rewrite the restaurant name
	{
		restaurantName = restaruant;
	}
	
	public void setMealName(String meal) //rewrite the meal name
	{
		mealName = meal;
	}
	
	public void setPriceTag(double price) //rewrite the price tag
	{
		priceTag = price;
	}
	
	public String getRestaurantName() //get the restaurant name field
	{
		return restaurantName;
	}
	
	public String getMealName() //get the meal name field
	{
		return mealName;
	}
	
	public double getPriceTag() //get the price tag field
	{
		return priceTag;
	}
	
	public boolean equals(Meal meal) //check if the name, price, and restaurant name are all the same for the instance and the given meal object
	{
		if(mealName.equals(meal.getMealName()) && priceTag == meal.getPriceTag() && restaurantName.equals(meal.getRestaurantName()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String toString() //return string representation of meal object, which is its name
	{
		return mealName;
	}
	
	public int compareTo(Meal meal) //compare method, returns -1 if instance price is less than given object's price, 1 if greater, and 0 if equal
	{
		if(priceTag < meal.getPriceTag())
		{
			return -1;
		}
		else if (priceTag > meal.getPriceTag())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
