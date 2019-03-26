package com.example.anton.todoornot;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewArchivedActivity extends AppCompatActivity {


    ListView listView;
    SharedPreferences prefs;

    String pass;
    String userName;

    ArrayList<HashMap<String, String>> archivedList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_archived);


        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        userName = prefs.getString("login_name", null);
        pass = prefs.getString("login_password", null);


        listView = (ListView) findViewById(R.id.list);

        String[] values = new String[]{"Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

    }

    private void populateList() {
        if (userName.equals(null) || pass.equals(null)) {
            toastMessage("Please fill up user name and pass in preferences");
        } else {
            BufferedReader in = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI("http://www.youcode.ca/Lab02Get.jsp?ALIAS=" + userName + "&PASSWORD=" + pass));
                HttpResponse response = client.execute(request);
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String field = "";
                while ((field = in.readLine()) != null) {
//                Log.d(TAG, "reading from server - vslue =" + Value );
                    HashMap<String, String> temp = new HashMap<String, String>();
                    temp.put("LIST_TITLE", field);
                    field = in.readLine();
                    temp.put("CONTENT", field);
                    field = in.readLine();
                    temp.put("CREATED_DATE", field);
                    archivedList.add(temp);

                }
                in.close();
            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
            }
        }

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}
