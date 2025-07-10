package br.edu.ifpe.lpoo.project.entities.funcionarios;
import java.time.LocalDate;

public class BibliotecarioChefe extends Bibliotecario {

    private boolean podeGerenciarContas;
    private LocalDate dataInicioChefia;

    public BibliotecarioChefe(String nome, String email, String cpf, String matricula,
                               double salario, String cargo, int idCredencialAcesso,
                               String setor, String numRegistroConselho,
                               boolean podeGerenciarContas, LocalDate dataInicioChefia) {
        super(nome, email, cpf, matricula, salario, cargo, idCredencialAcesso, setor, numRegistroConselho);
        this.podeGerenciarContas = podeGerenciarContas;
        this.dataInicioChefia = dataInicioChefia;
    }

    public boolean getpodeGerenciarContas() {
        return podeGerenciarContas;
    }

    public void podeGerenciarContas(boolean podeGerenciarContas) {
        this.podeGerenciarContas = podeGerenciarContas;
    }

    public LocalDate getdataInicioChefia() {
        return dataInicioChefia;
    }

    public void setdataInicioChefia(LocalDate dataInicioChefia) {
        this.dataInicioChefia = dataInicioChefia;
    }
}
