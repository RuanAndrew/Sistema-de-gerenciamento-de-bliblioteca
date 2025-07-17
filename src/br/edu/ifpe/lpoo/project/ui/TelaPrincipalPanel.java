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

        JButton btnCadastrarLivro = new JButton("Acervo");
        btnCadastrarLivro.setPreferredSize(new Dimension(250, 60));
        btnCadastrarLivro.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCadastrarLivro.setBackground(new Color(59, 130, 246));
        btnCadastrarLivro.setForeground(Color.WHITE);
        btnCadastrarLivro.setFocusPainted(false);
        btnCadastrarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("GerenciarAcervoPanel");
            }
        });
        buttonPanel.add(btnCadastrarLivro);

        JButton btnCadastrarAluno = new JButton("Membros");
        btnCadastrarAluno.setPreferredSize(new Dimension(250, 60));
        btnCadastrarAluno.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCadastrarAluno.setBackground(new Color(59, 130, 246));
        btnCadastrarAluno.setForeground(Color.WHITE);
        btnCadastrarAluno.setFocusPainted(false);
        btnCadastrarAluno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("GerenciarMembrosPanel");
            }
        });
        buttonPanel.add(btnCadastrarAluno);

        JButton btnRealizarEmprestimo = new JButton("Realizar Empréstimo");
        btnRealizarEmprestimo.setPreferredSize(new Dimension(250, 60));
        btnRealizarEmprestimo.setFont(new Font("Arial", Font.PLAIN, 18));
        btnRealizarEmprestimo.setBackground(new Color(16, 185, 129));
        btnRealizarEmprestimo.setForeground(Color.WHITE);
        btnRealizarEmprestimo.setFocusPainted(false);
        btnRealizarEmprestimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("RealizarEmprestimo");
            }
        });
        buttonPanel.add(btnRealizarEmprestimo);

        JButton btnSair = new JButton("Sair");
        btnSair.setPreferredSize(new Dimension(250, 60));
        btnSair.setFont(new Font("Arial", Font.PLAIN, 18));
        btnSair.setBackground(new Color(239, 68, 68));
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