package cortez.pdsofttrab2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //field
    Button newUserButton;
    TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newUserButton = (Button) findViewById(R.id.newUserButton);
        usernameTextView = (TextView) findViewById(R.id.userEditText);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.newUserButton:
                Intent createNewuserIntent = new Intent(this, NewUser.class);
                startActivity(createNewuserIntent);
                break;

            case R.id.loginButton:
                Intent loginIntent = new Intent(this,UserScreen.class);
                loginIntent.putExtra("username",usernameTextView.getText().toString());
                startActivity(loginIntent);
                break;
        }
    }
}
