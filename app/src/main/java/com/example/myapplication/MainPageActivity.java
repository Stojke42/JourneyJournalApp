package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class MainPageActivity extends AppCompatActivity {


    private Integer itemLimit = 5;
    private FirebaseUser user;
    private FirebaseUser mAuth;

    private int gridNumber = 1;
    private static final String LOG_TAG=MainActivity.class.getName();

    private RecyclerView mRecyclerView;
    private ArrayList<TravelItem> mItemsData;
    private TravelItemAdapter mAdapter;

    private SharedPreferences preferences;

    private boolean viewRow = true;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Log.d(LOG_TAG,"Authentikalt uset "+user);
        }
        else{
            Log.d(LOG_TAG,"nembejelentkezet");
            finish();
        }


        setContentView(R.layout.activity_main_page);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                this, gridNumber));
        mItemsData = new ArrayList<>();

        mAdapter = new TravelItemAdapter(this, mItemsData);
        mRecyclerView.setAdapter(mAdapter);


        mFirestore = FirebaseFirestore.getInstance();
        mItems=mFirestore.collection("Items");


//        queryData();
        initializeData();
    }

    private void queryData(){
        mItemsData.clear();
        mItems.orderBy("name").limit(1).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                TravelItem item = document.toObject(TravelItem.class);
                mItemsData.add(item);
            }

            if (mItemsData.size() == 0) {
                initializeData();
                queryData();
            }

            // Notify the adapter of the change.
            mAdapter.notifyDataSetChanged();
        });

    }


    private void initializeData() {
        String[] itemsList = getResources()
                .getStringArray(R.array.travel_names);
        String[] itemsInfo = getResources()
                .getStringArray(R.array.travel_text);

        TypedArray itemsImageResources =
                getResources().obtainTypedArray(R.array.travel_image);
        TypedArray itemRate =
                getResources().obtainTypedArray(R.array.travel_rates);


        Log.d(LOG_TAG, "init start ilist "+itemsList.length);
        Log.d(LOG_TAG, "init start ilist "+ Arrays.toString(itemsList));
        Log.d(LOG_TAG, "init start ilist "+ Arrays.toString(itemsInfo));
        Log.d(LOG_TAG, "init start ilist "+itemsImageResources);
        Log.d(LOG_TAG, "init start ilist "+itemRate);
        for (int i = 0; i < itemsList.length; i++) {
            mItemsData.add(new TravelItem(itemsList[i], itemsInfo[i], itemRate.getFloat(i, 0),
                    itemsImageResources.getResourceId(i, 0)));
        }

        // Recycle the typed array.
        itemsImageResources.recycle();

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.nav_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.log_out_button);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out_button:
                Log.d(LOG_TAG, "Logout clicked!");
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
//            case R.id.contact_button:
//                Log.d(LOG_TAG, "Contact clicked!");
//                FirebaseAuth.getInstance().signOut();
//                finish();
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }







}