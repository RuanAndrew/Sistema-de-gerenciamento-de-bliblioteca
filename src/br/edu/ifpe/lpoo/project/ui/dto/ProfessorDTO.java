package br.edu.ifpe.lpoo.project.ui.dto;

import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;

public class ProfessorDTO {
    private int id;
    private String nome;
    private String email;
    private String cpf;
    private String matricula;
    private TipoMembro tipomembro = TipoMembro.ALUNO;
    private String debitomultas;
    private StatusMembro statusmembro = StatusMembro.ATIVO;
    private String areaAtuacao;
    private String departamento;

    public ProfessorDTO(int id, String email, String nome, String cpf, String matricula, TipoMembro tipomembro, String debitomultas, StatusMembro statusmembro, String areaAtuacao, String departamento) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.cpf = cpf;
        this.matricula = matricula;
        this.tipomembro = tipomembro;
        this.debitomultas = debitomultas;
        this.statusmembro = statusmembro;
        this.areaAtuacao = areaAtuacao;
        this.departamento = departamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
