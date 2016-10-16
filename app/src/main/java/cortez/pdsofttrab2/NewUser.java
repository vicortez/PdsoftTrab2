package cortez.pdsofttrab2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.List;

public class NewUser extends AppCompatActivity {

    public static final int REQUEST_CODE_IMAGE_PICK = 10;
    //fields
    TextView userEditText;
    TextView senhaEdittext;
    TextView listaTextView;
    User user;
    DBAdapter db = new DBAdapter(this);
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        userEditText = (TextView) findViewById(R.id.userEditText);
        senhaEdittext = (TextView) findViewById(R.id.senhaEditText);
        listaTextView = (TextView) findViewById(R.id.listaTextView);
        imageView = (ImageView) findViewById(R.id.imageViewCreate);
    }

    public void onClick(View v){
        switch (v.getId()){

            case(R.id.addPictureButton):
                //invoke the image gallery using an implicit intent
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

                //where do we want to find the data?
                //this method gets the public directory where a certain media type is stored, in this case, pictures
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                //finally, get a uri representation
                Uri data = Uri.parse(pictureDirectoryPath);

                //set the data and type
                photoPickerIntent.setDataAndType(data, "image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CODE_IMAGE_PICK);

                break;

            case (R.id.createNewUserButton):

                //criamos um user com as informacoes das views
                user = new User();
                user.setSenha(Security.encrypt(senhaEdittext.getText().toString()));
                user.setUser(userEditText.getText().toString());
                Bitmap foto = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                //precisamos salvar o bitmap da foto em forma de bytearray
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                foto.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] fotoByteArray = byteArrayOutputStream.toByteArray();
                user.setFoto(fotoByteArray);

                //checamos se ja existe um user com esse username, se nao, adicionamos.
                if(db.searchFor("usuario",user.getUser(),"tabela").size() == 0) {
                    db.addUser(user);
                    Toast t = Toast.makeText(this, "user adicionado!", Toast.LENGTH_LONG);
                    t.show();
                }
                else{
                    Toast t = Toast.makeText(this, "Nome de usuario já existe.",Toast.LENGTH_LONG);
                    t.show();
                }
                break;

            case(R.id.showTableButton):
                List<User> users = db.getAllUsers();
                for (int i=0;i<users.size();i++) {
                    System.out.println(users.get(i).getUser());
                }
                Toast t = Toast.makeText(this, "mostrando tabela", Toast.LENGTH_LONG);
                t.show();
                listaTextView.setText("");
                for (int i=0;i<users.size();i++) {
                    listaTextView.setText(listaTextView.getText().toString() + "\n"
                            + "usuario: " + users.get(i).getUser() + "\n" + "senha: " + users.get(i).getSenha() + "\n");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            //if we are here, everything processed successfully
            if(requestCode == REQUEST_CODE_IMAGE_PICK){
                //if we are here, we are hearing back from the image gallery
                Uri imageUri = data.getData();
                //declare a string to read image data from the sd card
                InputStream inputStream;

                //we are getting an inputstream based on the uri of the image
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    //get a bitmap from the stram
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imageView.setImageBitmap(bitmap);
//                    byte[] image = new byte[inputStream.available()];
//                    inputStream.read(image);

                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "falha ao abrir imagem",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
