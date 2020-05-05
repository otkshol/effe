package effe.builder;

import effe.builder.NyPizza.Size;
import effe.builder.Pizza.Topping;

public class Main {

	public static void main(String[] args) {

		NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
				.calories(100).sodium(35).carbohydrate(27).build();
		// 変数を呼び出してみる
		System.out.println("NutritionFactsの値");
		System.out.println("servingSize: " + cocaCola.getServingSize());
		System.out.println("servings: " + cocaCola.getServings());
		System.out.println("calories: " + cocaCola.getCalories());
		System.out.println("sodium: " + cocaCola.getSodium());
		System.out.println("carbohydrate: " + cocaCola.getCarbohydrate());
		System.out.println("----------");

		// Toppingのenumをstatic importするとTopping.を省略できる
		NyPizza pizza = new NyPizza.Builder(Size.SMALL)
				.addTopping(Topping.SAUSAGE).addTopping(Topping.ONION).build();

		System.out.println("NyPizzaの値");
		System.out.println("size: " + pizza.getSize());
		System.out.println("toppings: " + pizza.toppings);
		System.out.println("----------");

		// Toppingのenumをstatic importするとTopping.を省略できる
		Calzone calzone = new Calzone.Builder()
				.addTopping(Topping.HAM).sauceInside().build();

		System.out.println("Calzoneの値");
		System.out.println("toppings: " + calzone.toppings);
		System.out.println("sauceInside: " + calzone.isSauceInside());
	}
}
