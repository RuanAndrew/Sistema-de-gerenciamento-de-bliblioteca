package br.edu.ifpe.lpoo.project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipalPanel extends JPanel {

    private MainFrame mainFrame;

    public TelaPrincipalPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        // Título/Cabeçalho
        JLabel welcomeLabel = new JLabel("Bem-vindo ao Sistema!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        // Painel para os botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton btnCadastrarLivro = new JButton("Cadastrar Livro");
        btnCadastrarLivro.setPreferredSize(new Dimension(250, 60));
        btnCadastrarLivro.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCadastrarLivro.setBackground(new Color(100, 149, 237)); // Azul
        btnCadastrarLivro.setForeground(Color.WHITE);
        btnCadastrarLivro.setFocusPainted(false);
        btnCadastrarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("CadastroLivro");
            }
        });
        buttonPanel.add(btnCadastrarLivro);

        JButton btnCadastrarEbook = new JButton("Cadastrar E-book");
        btnCadastrarEbook.setPreferredSize(new Dimension(250, 60));
        btnCadastrarEbook.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCadastrarEbook.setBackground(new Color(147, 112, 219)); // Roxo
        btnCadastrarEbook.setForeground(Color.WHITE);
        btnCadastrarEbook.setFocusPainted(false);
        btnCadastrarEbook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("CadastroEbook");
            }
        });
        buttonPanel.add(btnCadastrarEbook);

        JButton btnCadastrarPeriodico = new JButton("Cadastrar Periódico");
        btnCadastrarPeriodico.setPreferredSize(new Dimension(250, 60));
        btnCadastrarPeriodico.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCadastrarPeriodico.setBackground(new Color(0, 139, 139)); // Ciano
        btnCadastrarPeriodico.setForeground(Color.WHITE);
        btnCadastrarPeriodico.setFocusPainted(false);
        btnCadastrarPeriodico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("CadastroPeriodico");
            }
        });
        buttonPanel.add(btnCadastrarPeriodico);

        JButton btnListarLivros = new JButton("Listar Livros");
        btnListarLivros.setPreferredSize(new Dimension(250, 60));
        btnListarLivros.setFont(new Font("Arial", Font.PLAIN, 18));
        btnListarLivros.setBackground(new Color(60, 179, 113));
        btnListarLivros.setForeground(Color.WHITE);
        btnListarLivros.setFocusPainted(false);
        btnListarLivros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "Funcionalidade de Listar Livros em breve!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonPanel.add(btnListarLivros);

        JButton btnSair = new JButton("Sair");
        btnSair.setPreferredSize(new Dimension(250, 60));
        btnSair.setFont(new Font("Arial", Font.PLAIN, 18));
        btnSair.setBackground(new Color(220, 20, 60)); // Vermelho
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Fecha a aplicação
            }
        });
        buttonPanel.add(btnSair);

        add(buttonPanel, BorderLayout.CENTER);
    }
}