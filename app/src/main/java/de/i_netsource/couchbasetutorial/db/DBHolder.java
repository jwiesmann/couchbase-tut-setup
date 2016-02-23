package de.i_netsource.couchbasetutorial.db;

import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;

/**
 * Created by geekdivers on 2/12/16.
 */
public class DBHolder {

    private static DBHolder instance = null;
    private Database database;
    private Manager manager;

    public static DBHolder getInstance() {
        if (instance == null) {
            instance = new DBHolder();
        }
        return instance;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
