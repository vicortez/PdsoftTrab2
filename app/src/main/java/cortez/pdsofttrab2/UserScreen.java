package cortez.pdsofttrab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class UserScreen extends AppCompatActivity {

    DBAdapter db = new DBAdapter(this);
    User user;
    ImageView imageView;
    TextView ruaTextView;
    String username;
    List<User> users = new LinkedList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);

        imageView = (ImageView) findViewById(R.id.imageViewShow);
        ruaTextView = (TextView) findViewById(R.id.ruaEditText);

        //pega o nome de usuario que logou
        Bundle extra = getIntent().getExtras();
        username = extra.getString("username");



        users = db.searchFor("usuario",username,"tabela");
        user = users.get(0);

        // exemplo para rua pegando o nome de usuario e mostrando(errado, consertar)
        ruaTextView.setText(user.getUser());

        imageView.setImageBitmap(StringToBitMap(user.getFoto()));


    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
