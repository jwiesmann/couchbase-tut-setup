package de.i_netsource.couchbasetutorial.db;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;

import de.i_netsource.couchbasetutorial.activity.MasterActivity;

/**
 * Created by joerg wiesmann on 2/8/16.
 */
public class DBOperations extends MasterActivity {


    public static final String MY_DB = "mydatabase";
    private static final String TAG = "de.i-netsource";


    public Database getDB() {
        if (DBHolder.getInstance().getDatabase() == null) {
            try {
                initDB();
            } catch (Exception e) {
                Log.e(TAG, "Error getting database", e);
            }
        } else {
            Log.i(TAG, "Request for DB .. nothing created, got it from Singleton !");
        }
        return DBHolder.getInstance().getDatabase();
    }

    /**
     * drops the database (only for debug purpose)
     */
    public void dropDB() {
        try {
            getDB().delete();
            // we need to init it again, since we still have it in our Singleton
            initDB();
        } catch (Exception e) {
            Log.e(TAG, "Error dropping database", e);
        }

    }

    /**
     * initializes the database
     * @throws IOException
     * @throws CouchbaseLiteException
     */
    private void initDB() throws IOException, CouchbaseLiteException {
        Manager manager = new Manager(new AndroidContext(this), Manager.DEFAULT_OPTIONS);
        Database database = manager.getDatabase(MY_DB);
        DBHolder.getInstance().setDatabase(database);
        DBHolder.getInstance().setManager(manager);
        initViews();
    }

    /**
     * Add all your views here
     */
    private void initViews() {
        // we will need that later
    }

    /**
     * this function will get the first result of a view or returns null
     *
     * @param viewName
     * @return
     */
    public Document getSingleResultFromView(String viewName) {
        try {
            QueryEnumerator queryRows = DBHolder.getInstance().getDatabase().getView(viewName).createQuery().run();
            if (queryRows.getCount() >= 1 && queryRows.hasNext()) {
                Document d = queryRows.next().getDocument();
                if (d == null) {
                    String docId = queryRows.getRow(0).getDocumentId();
                    if (docId != null) {
                        return DBHolder.getInstance().getDatabase().getDocument(docId);
                    }
                } else {
                    return d;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error executing getSingleResultFromView ", e);
        }
        return null;
    }
}
