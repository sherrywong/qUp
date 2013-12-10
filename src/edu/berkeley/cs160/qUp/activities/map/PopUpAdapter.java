package edu.berkeley.cs160.qUp.activities.map;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import edu.berkeley.cs160.qUp.R;

/**
 * Purpose of Class:
 * <p/>
 * qUp ==> edu.berkeley.cs160.qUp.activities.map
 * Date: 12/9/13
 * Time: 11:58 PM
 * Version: 1.0
 */
public class PopUpAdapter implements GoogleMap.InfoWindowAdapter {
    LayoutInflater inflater=null;

    public PopUpAdapter(LayoutInflater inflater) {
        this.inflater=inflater;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return(null);
    }

    @Override
    public View getInfoContents(Marker marker) {
        View popup=inflater.inflate(R.layout.popup, null);

        TextView tv=(TextView)popup.findViewById(R.id.popup_title);

        tv.setText(marker.getTitle());
        tv=(TextView)popup.findViewById(R.id.snippet);
        tv.setText(marker.getSnippet());

        return(popup);
    }
}
