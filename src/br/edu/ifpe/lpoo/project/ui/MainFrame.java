package br.edu.ifpe.lpoo.project.ui;

import br.edu.ifpe.lpoo.project.ui.acervo.*;
import br.edu.ifpe.lpoo.project.ui.emprestimo.JRealizarEmprestimoPanel;
import br.edu.ifpe.lpoo.project.ui.membros.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private TelaPrincipalPanel telaPrincipalPanel;
    private JCadastroLivro jCadastroLivroPanel;
    private JCadastroEbook jCadastroEbookPanel;
    private JCadastroPeriodico jCadastroPeriodicoPanel;
    private JListarLivrosPanel jListarLivrosPanel;
    private JListarEbooksPanel jListarEbooksPanel;
    private JListarPeriodicosPanel jListarPeriodicosPanel;
    private JCadastroAluno jCadastroAluno;
    private JCadastroPesquisador jCadastroPesquisador;
    private JCadastroProfessor jCadastroProfessor;
    private JVisualizarMembros jVisualizarMembros;
    private JRealizarEmprestimoPanel jRealizarEmprestimoPanel;
    private GerenciarAcervoPanel gerenciarAcervoPanel;
    private GerenciarMembrosPanel gerenciarMembrosPanel;

    public MainFrame() {
        setTitle("Sistema de Gerenciamento de Livros");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        telaPrincipalPanel = new TelaPrincipalPanel(this);
        jCadastroLivroPanel = new JCadastroLivro(this);
        jCadastroEbookPanel = new JCadastroEbook(this);
        jCadastroPeriodicoPanel = new JCadastroPeriodico(this);
        jListarLivrosPanel = new JListarLivrosPanel(this);
        jListarEbooksPanel = new JListarEbooksPanel(this);
        jListarPeriodicosPanel = new JListarPeriodicosPanel(this);
        jCadastroAluno = new JCadastroAluno(this);
        jCadastroPesquisador = new JCadastroPesquisador(this);
        jCadastroProfessor = new JCadastroProfessor(this);
        jVisualizarMembros = new JVisualizarMembros(this);
        jRealizarEmprestimoPanel = new JRealizarEmprestimoPanel(this);
        gerenciarAcervoPanel = new GerenciarAcervoPanel(this);
        gerenciarMembrosPanel = new GerenciarMembrosPanel(this);

        cardPanel.add(telaPrincipalPanel, "TelaPrincipal");
        cardPanel.add(jCadastroLivroPanel, "CadastroLivro");
        cardPanel.add(jCadastroEbookPanel, "CadastroEbook");
        cardPanel.add(jCadastroPeriodicoPanel, "CadastroPeriodico");
        cardPanel.add(jListarLivrosPanel, "ListarLivros");
        cardPanel.add(jListarEbooksPanel, "ListarEbooks");
        cardPanel.add(jListarPeriodicosPanel, "ListarPeriodicos");
        cardPanel.add(jCadastroAluno, "CadastroAluno");
        cardPanel.add(jCadastroPesquisador,"CadastroPesquisador");
        cardPanel.add(jCadastroProfessor,"CadastroProfessor");
        cardPanel.add(jVisualizarMembros,"VisualizarMembros");
        cardPanel.add(jRealizarEmprestimoPanel, "RealizarEmprestimo");
        cardPanel.add(gerenciarAcervoPanel,"GerenciarAcervoPanel");
        cardPanel.add(gerenciarMembrosPanel,"GerenciarMembrosPanel");


        add(cardPanel);

        showPanel("TelaPrincipal");
    }


    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }
}