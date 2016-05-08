package io.github.z3ntu.schaukuchl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class Schaukuchl extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private MenuCardParser menuCardParser;

    public static void log(LogLevel logLevel, String text) {
        System.out.println("[Schaukuchl] " + logLevel + ": " + text);
    }

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
        getMenuInflater().inflate(R.menu.menu_schaukuchl, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public enum LogLevel {
        ASSERT, ERROR, WARN, INFO, DEBUG, VERBOSE
    }
}
