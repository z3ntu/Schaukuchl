package io.github.z3ntu.schaukuchl;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class Schaukuchl extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String[] myDataset = {"Text1", "Text2", "Text3", "Text4", "Text5", "Text6", "Text7", "Text8"};

    private MenuCardParser menuCardParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schaukuchl);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

        menuCardParser = new MenuCardParser(getApplicationContext(), mAdapter, getSupportActionBar(), getSharedPreferences("FoodStorage", MODE_PRIVATE));
        menuCardParser.getFood();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schaukuchl, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void log(LogLevel logLevel, String text) {
        System.out.println("[Schaukuchl] " + logLevel + ": " + text);
    }

    public static enum LogLevel {
        ASSERT, ERROR, WARN, INFO, DEBUG, VERBOSE;
    }
}
