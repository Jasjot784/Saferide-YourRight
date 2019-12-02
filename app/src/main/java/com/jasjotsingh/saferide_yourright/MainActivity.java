package com.jasjotsingh.saferide_yourright;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GridView grid;
    Button button;
    TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       button = findViewById(R.id.button);
       user = findViewById(R.id.user);

        // Array of strings storing country names
        final String[] properties = new String[] {
                "Plot",
                "Speed",
                "Location",
                "Contact Us"
        };

        // Array of integers points to images stored in /res/drawable-ldpi/
        int[] images = new int[]{
                R.drawable.heartbeat,
                R.drawable.speedometer,
                R.drawable.pin,
                R.drawable.contact
        };
        CustomGrid adapter = new CustomGrid(MainActivity.this, properties, images);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter((ListAdapter) adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(MainActivity.this, "You Clicked at " +properties[+ position], Toast.LENGTH_SHORT).show();

                if(position==0){
                    Intent graphIntent = new Intent(MainActivity.this,GraphActivity.class);
                    startActivity(graphIntent);
                }
                if(position==1){
                    Intent tableIntent = new Intent(MainActivity.this,SpeedActivity.class);
                    startActivity(tableIntent);
             }
                if(position==2){
                    Intent locationIntent = new Intent(MainActivity.this, LocationActivity.class);
                    startActivity(locationIntent);
                }
                if(position==3){
                    Intent locationIntent = new Intent(MainActivity.this, ContactUsActivity.class);
                    startActivity(locationIntent);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(signupIntent);
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null){

            user.setText(" Welcome " + bundle.get("email"));
        }


    }
}
