package cortez.pdsofttrab2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //field
    Button newUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newUserButton = (Button) findViewById(R.id.newUserButton);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.newUserButton:
                Intent createNewuserIntent = new Intent(this, NewUser.class);
                startActivity(createNewuserIntent);
        }
    }
}
