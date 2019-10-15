package formation.serli.com.beer;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class BeerActivity extends AppCompatActivity {

    private ImageView mBeerIcon;
    private TextView mBeerName;
    private TextView mBeerDescription;
    private TextView mBeerTagLine;
    private TextView mBeerBrewerTips;
    private Boolean mEnableTouchEvents = true;
    private Bitmap mBeerBitMap;
    private PopupWindow mPopupWindow;
    private View mLinearLayout;
    private Slide slide = new Slide(Gravity.LEFT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(slide);
        }

        setContentView(R.layout.activity_beer);

        if (savedInstanceState != null) {
        } else {
        }

        mBeerName = findViewById(R.id.activity_beer_name);
        mBeerDescription = findViewById(R.id.activity_beer_desc);
        mBeerTagLine = findViewById(R.id.activity_beer_tagline);
        mBeerBrewerTips = findViewById(R.id.activity_beer_brewer_tips);
        mBeerIcon = findViewById(R.id.activity_beer_icon);

        mBeerIcon.setOnClickListener(v -> onButtonShowPopupWindowClick(v));
        Bundle b = getIntent().getExtras();
        try {
            JSONObject jsonObject = new JSONObject(b.getString("CURRENT_BEER"));
            mBeerName.setText(jsonObject.getString("name"));
            mBeerDescription.setText(jsonObject.getString("description"));
            mBeerTagLine.setText(jsonObject.getString("tagline"));
            mBeerBrewerTips.setText(jsonObject.getString("brewers_tips"));
            String image_url = jsonObject.getString("image_url");
            if (image_url == null || image_url == "null") {
                image_url = "https://images.punkapi.com/v2/keg.png";
            }

                Picasso.with(mBeerIcon.getContext()).load(image_url).into(mBeerIcon);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.beer_picture, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        mPopupWindow = new PopupWindow(popupView, width, height, focusable);
        mPopupWindow.setBackgroundDrawable(getDrawable(R.drawable.border_radius));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


            slide.addTarget(R.id.activity_beer_icon);

            mPopupWindow.setEnterTransition(slide);
            mPopupWindow.setExitTransition(slide);
        }
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 500);

// Set an elevation value for popup window
        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(20.0f);
        }

        final ImageView mBeerPopupedIcon = popupView.findViewById(R.id.beer_picture_popuped);
        Bundle b = getIntent().getExtras();
        try {
            JSONObject jsonObject = new JSONObject(b.getString("CURRENT_BEER"));
            String image_url = jsonObject.getString("image_url");
            if (image_url == null || image_url == "null") {
                image_url = "https://images.punkapi.com/v2/keg.png";
            }

            Picasso.with(mBeerPopupedIcon.getContext()).load(image_url).into(mBeerPopupedIcon);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // dismiss the popup window when touched
        popupView.setOnTouchListener((v, event) -> {
            mPopupWindow.dismiss();
            return true;
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }


}
