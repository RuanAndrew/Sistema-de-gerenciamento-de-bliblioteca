package br.edu.ifpe.lpoo.project.entities.funcionarios;

import br.edu.ifpe.lpoo.project.entities.membros.Pessoa;

public abstract class Funcionario extends Pessoa {

    private String matricula;
    private double salario;
    private String cargo;

    public Funcionario(String nome, String email, String cpf, String matricula, double salario, String cargo) {
        super(nome, email, cpf);
        this.matricula = matricula;
        this.salario = salario;
        this.cargo = cargo;
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

}
