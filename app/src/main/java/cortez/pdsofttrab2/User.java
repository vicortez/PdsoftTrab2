package cortez.pdsofttrab2;

/**
 * Created by Usuario on 10/10/2016.
 */

public class User {

    //fields
    String user;
    String senha;
    String nome;
    String rua;
    int id;

    User() {

    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
