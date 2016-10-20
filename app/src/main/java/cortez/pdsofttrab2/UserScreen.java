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
    TextView userEdittext;
    TextView senhaEdittext;
    TextView nomeEdittext;
    //contato
    TextView ruaEdittext;
    TextView bairroEdittext;
    TextView complementoEdittext;
    TextView numeroEdittext;
    TextView cepEdittext;
    TextView cidadeEdittext;
    TextView estadoEdittext;
    //contato
    TextView celularEdittext;
    TextView mailEdittext;
    //imagem
    ImageView imageView;


    String username;
    List<User> users = new LinkedList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);

        imageView = (ImageView) findViewById(R.id.imageViewShow);
        userEdittext = (TextView) findViewById(R.id.userEditText);
        nomeEdittext = (TextView) findViewById(R.id.nomeEditText);
        //endereço
        ruaEdittext = (TextView) findViewById(R.id.ruaEditText);
        bairroEdittext = (TextView) findViewById(R.id.bairroEditText);
        complementoEdittext = (TextView) findViewById(R.id.complementoEditText);
        numeroEdittext = (TextView) findViewById(R.id.numeroEditText);
        cepEdittext = (TextView) findViewById(R.id.cepEditText);
        cidadeEdittext = (TextView) findViewById(R.id.cidadeEditText);
        estadoEdittext = (TextView) findViewById(R.id.estadoEditText);
        //contato
        celularEdittext = (TextView) findViewById(R.id.celularEditText);
        mailEdittext = (TextView) findViewById(R.id.mailEditText);
        //pega o nome de usuario que logou
        Bundle extra = getIntent().getExtras();
        username = extra.getString("username");

        users = db.searchFor("usuario",username,"tabela");
        //usuario que estamos lidando foi selecionado
        user = users.get(0);

        // exemplo para rua pegando o nome de usuario e mostrando(errado, consertar isaac)
        userEdittext.setText(user.getUser());
        nomeEdittext.setText(user.getNome());
        //adress
        ruaEdittext.setText(user.getRua());
        bairroEdittext.setText(user.getBairro());
        complementoEdittext.setText(user.getComplemento());
        cepEdittext.setText(user.getCep());
        numeroEdittext.setText(user.getNumero());
        cidadeEdittext.setText(user.getCidade());
        estadoEdittext.setText(user.getEstado());
        //contato
        celularEdittext.setText(user.getCelular());
        mailEdittext.setText(user.getMail());

        //transformando bytearray em bitmap para poder exibir
        Bitmap bitmap = BitmapFactory.decodeByteArray(user.getFoto(),0,user.getFoto().length);
        imageView.setImageBitmap(bitmap);


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
