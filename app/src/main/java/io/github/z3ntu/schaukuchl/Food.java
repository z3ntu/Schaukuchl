package io.github.z3ntu.schaukuchl;

/**
 * Created by luca on 22.07.15.
 */
public class Food {
    public String name;
    public String price;
    public boolean aus;

    public Food(String name, String price, boolean aus) {
        this.name = name;
        this.price = price;
        this.aus = aus;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", price=" + price + '\'' +
                ", aus=" + aus +
                '}';
    }
}
