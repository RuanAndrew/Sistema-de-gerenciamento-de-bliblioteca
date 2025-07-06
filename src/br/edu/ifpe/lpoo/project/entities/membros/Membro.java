package br.edu.ifpe.lpoo.project.entities.membros;

import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;

public abstract class Membro extends Pessoa {

    private String matricula;
    private TipoMembro tipomembro;
    private int debitomultas;
    private StatusMembro statusmembro;

    public Membro(String nome, String email, String cpf, String matricula, TipoMembro tipomembro, int debitomultas, StatusMembro statusmembro) {
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

    public TipoMembro getTipomembro() {
        return tipomembro;
    }

    public void setTipomembro(TipoMembro tipomembro) {
        this.tipomembro = tipomembro;
    }

    public int getDebitomultas() {
        return debitomultas;
    }

    public void setDebitomultas(int debitomultas) {
        this.debitomultas = debitomultas;
    }

    public StatusMembro getStatusmembro() {
        return statusmembro;
    }

    public void setStatusmembro(StatusMembro statusmembro) {
        this.statusmembro = statusmembro;
    }
}
