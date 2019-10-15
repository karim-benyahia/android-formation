package formation.serli.com.beer.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import formation.serli.com.beer.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView firstLine;
    public TextView secondLine;
    public ImageView icon;

    public MyViewHolder(View i) {
        super(i);
        firstLine = i.findViewById(R.id.firstLine);
        secondLine = i.findViewById(R.id.secondLine);
        icon = i.findViewById(R.id.icon);
    }

    public void updateWithBeer(JSONObject jsonObject) {
        try {
            this.firstLine.setText(jsonObject.getString("name"));
            this.secondLine.setText(jsonObject.getString("tagline"));
            String image_url = jsonObject.getString("image_url");
            if (image_url == null || image_url == "null") {
                image_url = "https://images.punkapi.com/v2/keg.png";
            }
            Picasso.with(this.icon.getContext()).load(image_url).into(this.icon);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}