package ca.nait.adrantiev1.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendButton = (Button) findViewById(R.id.button_main_submit);
        sendButton.setOnClickListener(this);

        Button buttonLaunch = (Button) findViewById(R.id.button_launch_activity);
        buttonLaunch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        EditText editText = (EditText) findViewById(R.id.edittext_main);
        String data = editText.getText().toString();
        switch (view.getId())
        {
            case R.id.button_main_submit:
            {
                editText.setText("");
                Toast.makeText(this, "You typed: " + data, Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.button_launch_activity:
            {
                Intent intent = new Intent(this,ReciveActivty.class);

                Bundle bundle = new Bundle();
                bundle.putString("PREFIX","You typed: ");
                bundle.putString("DATA",data);

                intent.putExtras(bundle);
                this.startActivity(intent);
                break;
            }
        }

    }
}
