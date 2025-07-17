package br.edu.ifpe.lpoo.project.entities.funcionarios;

public abstract class Funcionario extends Pessoa {

    private String matricula;
    private double salario;
    private String cargo;
    private int idCredencialAcesso;
    private StatusFuncionario statusFuncionario;

    public Funcionario(String nome, String email, String cpf, String matricula, double salario,
                       String cargo, int idCredencialAcesso, StatusFuncionario statusFuncionario) {
        super(nome, email, cpf);
        this.matricula = matricula;
        this.salario = salario;
        this.cargo = cargo;
        this.idCredencialAcesso = idCredencialAcesso;
        this.statusFuncionario = statusFuncionario;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getIdCredencialAcesso() {
        return idCredencialAcesso;
    }

    public void setIdCredencialAcesso(int idCredencialAcesso) {
        this.idCredencialAcesso = idCredencialAcesso;
    }

    public StatusFuncionario getStatusFuncionario() {
        return statusFuncionario;
    }

    public void setStatusFuncionario(StatusFuncionario statusFuncionario) {
        this.statusFuncionario = statusFuncionario;
    }
}
