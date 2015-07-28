package io.github.z3ntu.schaukuchl;

/**
 * Created by luca on 23.07.15.
 */
public class SaveHelper {

    public static String getStringFromFood(Food food) {
        return food.name+"/~"+food.price+"/~"+food.aus;
    }

    public static Food getFoodFromString(String food) {
        String[] foodSplit = food.split("/~");
        return new Food(foodSplit[0], foodSplit[1], Boolean.parseBoolean(foodSplit[2]));
    }

}
