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

        cardPanel.add(telaPrincipalPanel, "TelaPrincipal");
        cardPanel.add(jCadastroLivroPanel, "CadastroLivro");
        cardPanel.add(jCadastroEbookPanel, "CadastroEbook");
        cardPanel.add(jCadastroPeriodicoPanel, "CadastroPeriodico");


        add(cardPanel);

        showPanel("TelaPrincipal");
    }


    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }
}