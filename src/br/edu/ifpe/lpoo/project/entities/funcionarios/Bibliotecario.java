package br.edu.ifpe.lpoo.project.entities.funcionarios;

public class Bibliotecario extends Funcionario {

    private String setor;
    private String numRegistroConselho;

    public Bibliotecario(String nome, String email, String cpf, String matricula, double salario, String cargo, int idCredencialAcesso, String setor, String numRegistroConselho) {
        super(nome, email, cpf, matricula, salario, cargo, idCredencialAcesso);
        this.setor = setor;
        this.numRegistroConselho = numRegistroConselho;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getNumRegistroConselho() {
        return numRegistroConselho;
    }

    public void setNumRegistroConselho(String numRegistroConselho) {
        this.numRegistroConselho = numRegistroConselho;
    }
}
