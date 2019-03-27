package com.example.anton.todoornot;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
    String[] values ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_archived);


        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        userName = prefs.getString("login_name", null);
        pass = prefs.getString("login_password", null);

        displayArchive();

    }


    private void displayArchive(){
        String[] keys = new String[]{"LIST_TITLE","CONTENT","COMPLETED_FLAG","CREATED_DATE"};
        int[] ids = new int[]{R.id.custom_row_date,R.id.custom_row_title,R.id.custom_row_content,R.id.custom_row_flag};
        SimpleAdapter adapter = new SimpleAdapter(this,archivedList,R.layout.custom_row_archive,keys,ids);
        listView = (ListView) findViewById(R.id.listview_custom);
        populateList();
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
                    HashMap<String, String> temp = new HashMap<String, String>();
                    temp.put("LIST_TITLE", field);
                    field = in.readLine();
                    temp.put("CONTENT", field);
                    field = in.readLine();
                    temp.put("COMPLETED_FLAG", field);
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
