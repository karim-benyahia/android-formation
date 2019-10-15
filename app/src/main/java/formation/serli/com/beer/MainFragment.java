package formation.serli.com.beer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import formation.serli.com.beer.utils.ItemClickSupport;
import formation.serli.com.beer.views.BeerAdapter;

public class MainFragment extends Fragment implements View.OnClickListener {


    public static final String CURRENT_BEER = "CURRENT_BEER";
    private RecyclerView recyclerView;
    private StringRequest disposable;
    private BeerAdapter adapter;
    private List<JSONObject> beers;
    private int mCurrentPage = 1;
    private String mBeers;
    private FloatingActionButton mScrollToTop;
    private boolean mReachEnd;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        this.recyclerView = view.findViewById(R.id.fragment_main_recycler_view);
        this.configureRecyclerView(); // - 4 Call during UI creation
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit(); // 5 - Execute stream after UI creation
        mScrollToTop = view.findViewById(R.id.activity_main_prev_btn);
        mScrollToTop.setOnClickListener(this);
        mScrollToTop.hide();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(-1)){
                    mScrollToTop.hide();
                }else {
                    mScrollToTop.show();
                }

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE  && !mReachEnd) {
                    executeHttpRequestWithRetrofit();
                }
            }
        });
        return view;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {
        // 3.1 - Reset list
        this.beers = new ArrayList<>();
        // 3.2 - Create adapter passing the list of users
        this.adapter = new BeerAdapter(this.beers);
        // 3.3 - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // 3.4 - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.beer_widget)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Intent intent = new Intent(recyclerView.getContext(), BeerActivity.class);
                    JSONObject jsonObject = beers.get(position);
                    intent.putExtra(CURRENT_BEER, jsonObject.toString());
                    startActivity(intent);
                });
    }


    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit() {
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://api.punkapi.com/v2/beers?page=" + mCurrentPage + "&per_page=40";

        // Request a string response from the provided URL.
        disposable = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Display the first 500 characters of the response string.
                    mBeers = response;
                    JSONArray myDataset = null;
                    try {
                        myDataset = new JSONArray(response);
                        if (myDataset.length() > 0) {
                            mCurrentPage ++;
                            updateUI(myDataset);
                        } else {
                            mReachEnd = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    System.out.println(error);
                });

        queue.add(disposable);
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isCanceled()) this.disposable.cancel();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(JSONArray users) {
        //beers.clear();
        for (int i = 0; i < users.length(); i++) {
            try {
                JSONObject obj = users.getJSONObject(i);

                beers.add(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            protected int calculateTimeForScrolling(int dx) {
                return 50;
            }
        };

        smoothScroller.setTargetPosition(0);
        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
    }
}

