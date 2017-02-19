package justynafirkowska.datacollector;

import android.app.Application;

public class App extends Application {
    public DbHelper db;

    @Override
    public void onCreate() {
        super.onCreate();
        this.db = new DbHelper(this);
    }
}
