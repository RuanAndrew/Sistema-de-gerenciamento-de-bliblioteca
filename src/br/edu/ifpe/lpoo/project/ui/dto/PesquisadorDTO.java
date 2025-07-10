package br.edu.ifpe.lpoo.project.ui.dto;

import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;

public class PesquisadorDTO {
    private int id;
    private String nome;
    private String email;
    private String cpf;
    private String matricula;
    private TipoMembro tipomembro = TipoMembro.ALUNO;
    private String debitomultas;
    private StatusMembro statusmembro = StatusMembro.ATIVO;
    private String instituicao;


    public PesquisadorDTO(int id, String email, String nome, String cpf, String matricula, TipoMembro tipomembro, String debitomultas, StatusMembro statusmembro, String instituicao) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.cpf = cpf;
        this.matricula = matricula;
        this.tipomembro = tipomembro;
        this.debitomultas = debitomultas;
        this.statusmembro = statusmembro;
        this.instituicao = instituicao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getDebitomultas() {
        return debitomultas;
    }

    public void setDebitomultas(String debitomultas) {
        this.debitomultas = debitomultas;
    }

    public StatusMembro getStatusmembro() {
        return statusmembro;
    }

    public void setStatusmembro(StatusMembro statusmembro) {
        this.statusmembro = statusmembro;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }
}
