package formation.serli.com.beer;

import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;


//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//
//    private static final int MAIN_ACTIVITY_REQUEST_CODE = 42;
//
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private FloatingActionButton mPrevButton;
//    private FloatingActionButton mNextButton;
//    private String mBeers;
//    private int mCurrentPage;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//            getWindow().setEnterTransition(new Slide(Gravity.LEFT));
//        }
//
//        setContentView(R.layout.activity_main);
//        recyclerView = findViewById(R.id.my_recycler_view);
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
//
//        this.recyclerView.setLayoutAnimation(controller);
//        //this.projectAdapter.update(projectResponse.getProjects());
//        this.recyclerView.scheduleLayoutAnimation();
//
//        //this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//
//        // specify an adapter (see also next example)
//
//        mPrevButton = findViewById(R.id.activity_main_prev_btn);
//        mPrevButton.setTag(-1);
//        mPrevButton.setEnabled(false);
//        mPrevButton.setOnClickListener(this);
//
//        mNextButton = findViewById(R.id.activity_main_next_btn);
//        mNextButton.setTag(1);
//        mNextButton.setOnClickListener(this);
//
//        if (savedInstanceState != null) {
//            mBeers = savedInstanceState.getString("beers");
//            JSONArray myDataset = null;
//            mCurrentPage = savedInstanceState.getInt("current_page");
//            try {
//                myDataset = new JSONArray(mBeers);
//                mAdapter = new BeerAdapter(myDataset, recyclerView);
//                recyclerView.setAdapter(mAdapter);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
//            mCurrentPage = 1;
//            updateData();
//
//        }
//
//
//    }
//
//
//    private void updateData() {
//        final RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://api.punkapi.com/v2/beers?page=" + mCurrentPage + "&per_page=40";
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                response -> {
//                    // Display the first 500 characters of the response string.
//                    mBeers = response;
//                    JSONArray myDataset = null;
//                    try {
//                        myDataset = new JSONArray(response);
//                        if (myDataset.length() > 0) {
//                            mAdapter = new BeerAdapter(myDataset, recyclerView);
//                            recyclerView.setAdapter(mAdapter);
//                            mNextButton.setEnabled(true);
//                        } else {
//                            mCurrentPage--;
//                            mNextButton.setEnabled(false);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                },
//                error -> {
//                });
//
//        queue.add(stringRequest);
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//
//        outState.putString("beers", mBeers);
//        outState.putInt("current_page", mCurrentPage);
//
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        //Snackbar.make(v, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//        //      .setAction("Action", null).show();
//        int responseIndex = (int) v.getTag();
//
//        mCurrentPage += responseIndex;
//        if (mCurrentPage == 0 || mCurrentPage == 1) {
//            mCurrentPage = 1;
//            mPrevButton.setEnabled(false);
//        } else {
//            mPrevButton.setEnabled(true);
//        }
//        updateData();
//    }
//}


public class MainActivity extends AppCompatActivity {

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureAndShowMainFragment();
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    private void configureAndShowMainFragment(){

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_frame_layout, mainFragment)
                    .commit();
        }
    }
}