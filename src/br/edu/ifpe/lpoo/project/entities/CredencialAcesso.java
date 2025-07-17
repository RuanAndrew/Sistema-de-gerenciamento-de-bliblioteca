package br.edu.ifpe.lpoo.project.entities;

public class CredencialAcesso {
    private String usuario;
    private String senha;

    public CredencialAcesso( String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }
    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
