package nl.zoetermeer.onszoetermeer.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import nl.zoetermeer.onszoetermeer.R;

import static android.content.Context.MODE_PRIVATE;

public class MapHelper
{
    private Context mContext;
    private String mUserName;

    public MapHelper(Context context)
    {

        mContext = context;

    }

    public void setLocationData(Location location)
    {
        Geocoder geocoder = new Geocoder(mContext,
                Locale.getDefault());

        List<Address> addresses;
        try
        {
            addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0)
            {
                String address = addresses.get(0).getAddressLine(0);
                String postalCode = addresses.get(0).getPostalCode();
                String city = addresses.get(0).getLocality();

                if (address != null)
                {
                    String locationString = address + ", " + postalCode + ", " + city;
                    Toast.makeText(mContext, locationString, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(mContext, "De huidige locatiegegevens zijn niet bekend", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(mContext, "De huidige locatiegegevens konden niet worden opgehaald", Toast.LENGTH_SHORT).show();
        }
    }

    public void setUserMarker(Location location, GoogleMap mMap)
    {
        // Get position on the map
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // add marker and current position
        final LatLng ZOETERMEER = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(ZOETERMEER)
                .title(mUserName)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_contact_pointer)));

        // move camera to current position
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ZOETERMEER));
    }

    public Marker setSocialWorkerMarker(GoogleMap mMap)
    {
        final LatLng USERJOLANDA = new LatLng(52.060463, 4.495392);
        return mMap.addMarker(new MarkerOptions()
                .position(USERJOLANDA)
                .title("Jolanda Koetsier")
                .snippet("Vrijwilliger")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_contact_jolanda)));
    }

    public void getUserPreferences()
    {
        SharedPreferences mUserPreferences = mContext.getSharedPreferences("user_details",
                MODE_PRIVATE);
        mUserName = mUserPreferences.getString("first_name", "0");
    }

}
