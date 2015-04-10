package com.keonasoft.splitcharge;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.keonasoft.splitcharge.helpers.DatabaseOpenHelper;

import java.util.ArrayList;


public class PersonActivity extends Activity {
    private RecyclerView mRecyclerView;
    private ItemAdapter itemAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<DataItem> mDataset;
    private FloatingActionButton addPersonBtn;
    private int personId;
    private boolean isNewPerson = false;
    private int origNumItems;
    private int chargeId;
    private DatabaseOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        personId = getIntent().getIntExtra("index", -1);
        chargeId = getIntent().getIntExtra("chargeId", 1);
        if(personId == -1) {
            personId = getIntent().getIntExtra("newIndex", -1);
            isNewPerson = true;
        }
        dbHelper = new DatabaseOpenHelper(getApplicationContext());
        mDataset = new ArrayList<DataItem>();
        populateDataSet();
        mDataset.add(new DataItem(0, "item 1", 0.00));
        mRecyclerView = (RecyclerView) findViewById(R.id.chargeItemsList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        itemAdapter = new ItemAdapter(mDataset);
        mRecyclerView.setAdapter(itemAdapter);
        addPersonBtn = (FloatingActionButton) findViewById(R.id.add_charge_btn);
        addPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add item to database and take user to person edit screen
                mDataset.add(new DataItem(0, "New Item", 0.0));
                itemAdapter.refresh(mDataset);
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
        getMenuInflater().inflate(R.menu.menu_person, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if ( id == R.id.action_save) {
            return true;
        }
        else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateDataSet(){
        if (!isNewPerson) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor c = db.query(
                    DatabaseOpenHelper.ITEM_TABLE_NAME,
                    new String[]{DatabaseOpenHelper.ITEM_ID_COL, DatabaseOpenHelper.ITEM_NAME_COL, DatabaseOpenHelper.ITEM_COST_COL},
                    DatabaseOpenHelper.ITEM_CHARGEID_COL + " = ? AND " + DatabaseOpenHelper.ITEM_PERSONID_COL + " = ?",
                    new String[]{chargeId + "", personId + ""},
                    null,
                    null,
                    DatabaseOpenHelper.ITEM_ID_COL + " ASC"
            );
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndexOrThrow(DatabaseOpenHelper.ITEM_ID_COL));
                String name = c.getString(c.getColumnIndexOrThrow(DatabaseOpenHelper.ITEM_NAME_COL));
                int cost = c.getInt(c.getColumnIndexOrThrow(DatabaseOpenHelper.ITEM_COST_COL));

                mDataset.add(new DataItem(id, name, cost));
                c.moveToNext();
            }
        }
    }

    private class ItemAdapter extends MyAdapter {

        public class ViewHolder extends MyAdapter.ViewHolder {
            // each data item is just a string in this case
            public EditText textView;
            public EditText priceView;
            public ViewHolder(View v) {
                super(v);
                this.textView = (EditText) v.findViewById(R.id.edit_list_item_text);
                this.priceView = (EditText) v.findViewById(R.id.edit_list_item_price);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_list_item, parent, false);

            return new ViewHolder(v);
        }

        public ItemAdapter(ArrayList<DataItem> myDataset) {
            super(myDataset);
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int i) {
            ViewHolder vh = (ViewHolder)viewHolder;
            vh.textView.setText(data.get(i).getName());
            vh.priceView.setText(String.format( "%.2f", data.get(i).getCost()));
        }
    }
}
