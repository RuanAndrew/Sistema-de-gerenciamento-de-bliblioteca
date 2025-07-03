package br.edu.ifpe.lpoo.project.ui;

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
    private JListarEbooksPanel jListarEbooksPanel;     // <-- ADICIONE ESTA LINHA
    private JListarPeriodicosPanel jListarPeriodicosPanel;

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

        cardPanel.add(telaPrincipalPanel, "TelaPrincipal");
        cardPanel.add(jCadastroLivroPanel, "CadastroLivro");
        cardPanel.add(jCadastroEbookPanel, "CadastroEbook");
        cardPanel.add(jCadastroPeriodicoPanel, "CadastroPeriodico");
        cardPanel.add(jListarLivrosPanel, "ListarLivros");
        cardPanel.add(jListarEbooksPanel, "ListarEbooks");
        cardPanel.add(jListarPeriodicosPanel, "ListarPeriodicos");


        add(cardPanel);

        showPanel("TelaPrincipal");
    }


    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }
}