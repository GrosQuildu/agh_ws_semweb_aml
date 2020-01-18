package pl.edu.agh.eis.wsswaml;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.edu.agh.eis.wsswaml.localization.LocalizerService;
import pl.edu.agh.eis.wsswaml.localization.LocalizerServiceConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LocalizerServiceTest {
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Test
    public void testLocalizer() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Location testLocation = new Location("Nowa Huta");
        testLocation.setLatitude(50.06999972);
        testLocation.setLongitude(20.03583319);

        try {
            String mocLocationProvider = LocationManager.GPS_PROVIDER;
            LocationManager mLocationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
            mLocationManager.addTestProvider(mocLocationProvider, false, false, false, false, true, true, true, 0, 5);
            mLocationManager.setTestProviderLocation(mocLocationProvider, testLocation);
            mLocationManager.setTestProviderEnabled(mocLocationProvider, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LocalizerServiceConnection localizer = LocalizerServiceConnection.getInstance();
        localizer.enable(appContext);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {}

        Location location = localizer.getLocation();
        localizer.disable(appContext);

        if (location != null)
            System.out.println(location.toString());
        else
            System.out.println("Null location");
    }
}
