package br.edu.ifpe.lpoo.project.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private TelaPrincipalPanel telaPrincipalPanel;
    private JCadastroLivro jCadastroLivroPanel;

    public MainFrame() {
        setTitle("Sistema de Gerenciamento de Livros");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        telaPrincipalPanel = new TelaPrincipalPanel(this);
        jCadastroLivroPanel = new JCadastroLivro(this);

        cardPanel.add(telaPrincipalPanel, "TelaPrincipal");
        cardPanel.add(jCadastroLivroPanel, "CadastroLivro");


        add(cardPanel);

        showPanel("TelaPrincipal");
    }


    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }
}