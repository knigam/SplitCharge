package com.keonasoft.splitcharge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;
    private PersonAdapter personAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<PriceItem> mDataset;
    private FloatingActionButton addPersonBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataset = new ArrayList<PriceItem>();
        mDataset.add(new PriceItem(0, "Me", 0.00));
        mDataset.add(new PriceItem(1, "Name 2", 2.50));
        mRecyclerView = (RecyclerView) findViewById(R.id.chargesList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        personAdapter = new PersonAdapter(mDataset);
        mRecyclerView.setAdapter(personAdapter);
        addPersonBtn = (FloatingActionButton) findViewById(R.id.add_user_btn);
        addPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add item to database and take user to person edit screen
                mDataset.add(new PriceItem(0, "New User", 0.0));
                personAdapter.refresh(mDataset);
            }
        });
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean hidden = false;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Animation animation;

                super.onScrolled(recyclerView, dx, dy);
                if (dy >= 0 && !hidden) {
                    animation = new TranslateAnimation(0, 0,0, 350);
                    hidden = true;
                }
                else if (dy < 0 && hidden){
                    animation = new TranslateAnimation(0, 0,350, 0);
                    hidden = false;
                }
                else
                    return;

                animation.setDuration(300);
                animation.setFillAfter(true);
                addPersonBtn.startAnimation(animation);
                addPersonBtn.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class PersonAdapter extends MyAdapter {
        public PersonAdapter(ArrayList<PriceItem> data) {
            super(data);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, PersonActivity.class);
                    intent.putExtra("index", mRecyclerView.getChildPosition(v));
                    startActivity(intent);
                }
            });
            return new ViewHolder(v);
        }
    }
}
