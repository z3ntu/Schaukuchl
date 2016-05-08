package io.github.z3ntu.schaukuchl;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by luca on 22.07.15.
 */
public class MenuCardParser implements Response.ErrorListener, Response.Listener<String> {

    public static final String URL = "http://www.schaukuchl.at/tk/tk.htm";

    RequestQueue requestQueue;
    RecyclerViewAdapter recyclerViewAdapter;
    ActionBar actionBar;
    SharedPreferences sharedPreferences;
    Context context;

    public MenuCardParser(Context context, RecyclerViewAdapter recyclerViewAdapter, ActionBar actionBar, SharedPreferences sharedPreferences) {
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.actionBar = actionBar;
        this.sharedPreferences = sharedPreferences;
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void getFood() {
        StringRequest request = new StringRequest(Request.Method.GET, URL, this, this);
        requestQueue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        for (int i = 0; i < 20; i++) {
            String food = sharedPreferences.getString("food_" + i, "DEFAULT_VALUE");
            Schaukuchl.log(Schaukuchl.LogLevel.INFO, "food_" + i);
            Schaukuchl.log(Schaukuchl.LogLevel.INFO, food);
            if (food.equals("DEFAULT_VALUE"))
                break;
            recyclerViewAdapter.add(SaveHelper.getFoodFromString(food));
            Schaukuchl.log(Schaukuchl.LogLevel.DEBUG, food);
            Schaukuchl.log(Schaukuchl.LogLevel.DEBUG, SaveHelper.getFoodFromString(food).toString());
        }
        Toast.makeText(context, R.string.offlineData, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        Document doc = Jsoup.parse(response);
        Elements rows = doc.select("body > div > div > table > tbody > tr > td:nth-child(1) > table > tbody> tr");

        int counter = -1; // -1 because of the headline, that the first food is 0
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        for (Element row : rows) {
            /* Headline */
            if (row.attr("style").contains("mso-yfti-firstrow:yes")) {
                Element span = row.child(0).child(0).child(0);
                String title = span.ownText().replace("Tageskarte", "Tageskarte ");

                Schaukuchl.log(Schaukuchl.LogLevel.VERBOSE, "First Row!");
                Schaukuchl.log(Schaukuchl.LogLevel.DEBUG, title);
                actionBar.setTitle(title);
            }
            /* not headline */
            else {
                /* NAME */
                Element pName = row.child(1).child(0);
                String name = pName.child(0).child(0).ownText();
                if (name.equals(" "))
                    continue;
                name += " " + pName.child(1).ownText();

                /* PRICE */
                Element spanPrice = row.child(2).child(0).child(0).child(0);
                String price = spanPrice.ownText().replace(",", ".");

                /* AUS */
                boolean aus = price.contains("AUS");

//                Food food = new Food(name, price, aus);
//                Schaukuchl.log(Schaukuchl.LogLevel.DEBUG, food.toString());

                Food newFood = new Food(name, price, aus);
                recyclerViewAdapter.add(newFood);

                editor.putString("food_" + counter, SaveHelper.getStringFromFood(newFood));
                Schaukuchl.log(Schaukuchl.LogLevel.INFO, "food_" + counter);
            }
            counter++;
        }
        editor.apply();
    }
}
