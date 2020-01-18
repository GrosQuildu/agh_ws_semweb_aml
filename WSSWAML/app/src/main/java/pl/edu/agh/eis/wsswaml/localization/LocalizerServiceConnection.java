package pl.edu.agh.eis.wsswaml.localization;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;

public class LocalizerServiceConnection {
    private static LocalizerServiceConnection INSTANCE = new LocalizerServiceConnection();

    private LocalizerServiceConnection() {}

    public static  LocalizerServiceConnection getInstance() {
        return INSTANCE;
    }

    private static LocalizerService mService;
    private static boolean mBound = false;

    private static ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            LocalizerService.LocalBinder binder = (LocalizerService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public Location getLocation() {
        return mService.getLocation();
    }

    public void enable(Context appContext) {
        appContext.bindService(new Intent(appContext, LocalizerService.class), connection, Context.BIND_AUTO_CREATE);
    }

    public void disable(Context appContext) {
        appContext.unbindService(connection);
    }

    public boolean isEnabled() {
        return mBound;
    }
}
