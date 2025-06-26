package br.edu.ifpe.lpoo.project.entities;

public class Membro extends Pessoa {

    private String matricula;
    private String tipomembro;
    private String debitomultas;
    private String statusmembro;

    public Membro(String nome, String email, String cpf, String matricula, String tipomembro, String debitomultas, String statusmembro) {
        super(nome, email, cpf); 
        this.matricula = matricula;
        this.tipomembro = tipomembro;
        this.debitomultas = debitomultas;
        this.statusmembro = statusmembro;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipomembro() {
        return tipomembro;
    }

    public void setTipomembro(String tipomembro) {
        this.tipomembro = tipomembro;
    }

    public String getDebitomultas() {
        return debitomultas;
    }

    public void setDebitomultas(String debitomultas) {
        this.debitomultas = debitomultas;
    }

    public String getStatusmembro() {
        return statusmembro;
    }

    public void setStatusmembro(String statusmembro) {
        this.statusmembro = statusmembro;
    }
}
