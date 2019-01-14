package ca.nait.adrantiev1.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ReciveActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recive_activty);

        Bundle bundle = this.getIntent().getExtras();
        String prefix = bundle.getString("PREFIX");
        String data = bundle.getString("DATA");

        EditText editText = (EditText) findViewById(R.id.edittext_recive_data);
        editText.setText(prefix + data);
    }
}
