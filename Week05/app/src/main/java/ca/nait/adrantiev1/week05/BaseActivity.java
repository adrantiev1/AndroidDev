package ca.nait.adrantiev1.week05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {

    static String category;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_toggle_service:
            {

                if (GetterService.bRun == false)
                {
                    startService(new Intent(this,GetterService.class));
                }else
                {
                    stopService(new Intent(this,GetterService.class));
                }
                break;
            }
            case R.id.menu_item_home:
            {
                startActivity(new Intent(this, Week05Activity.class));
                break;
            }
            case R.id.menu_item_list_chatter:
            {
                startActivity(new Intent(this, ChatterListActivity.class));
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        if (menu != null) {
            MenuItem toggleItem = menu.findItem(R.id.menu_item_toggle_service);
            if (GetterService.bRun == false) {
                toggleItem.setTitle("Go Online");
            } else {
                toggleItem.setTitle("Go ofline");
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflator = this.getMenuInflater();
        inflator.inflate(R.menu.main_menu,menu);
        return true;
    }
}
