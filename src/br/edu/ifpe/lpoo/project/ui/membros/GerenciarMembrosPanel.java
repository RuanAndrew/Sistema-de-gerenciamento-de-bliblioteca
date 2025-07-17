package br.edu.ifpe.lpoo.project.ui.membros;

import br.edu.ifpe.lpoo.project.ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciarMembrosPanel extends JPanel {

    private MainFrame mainFrame;

    public GerenciarMembrosPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        // Título/Cabeçalho
        JLabel welcomeLabel = new JLabel("Gerenciamento de Membros");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        // Painel para os botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton btnCadastrarAluno = new JButton("Cadastrar Aluno");
        btnCadastrarAluno.setPreferredSize(new Dimension(250, 60));
        btnCadastrarAluno.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCadastrarAluno.setBackground(new Color(59, 130, 246));
        btnCadastrarAluno.setForeground(Color.WHITE);
        btnCadastrarAluno.setFocusPainted(false);
        btnCadastrarAluno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("CadastroAluno");
            }
        });
        buttonPanel.add(btnCadastrarAluno);

        JButton btnCadastrarPesquisador = new JButton("Cadastrar Pesquisador");
        btnCadastrarPesquisador.setPreferredSize(new Dimension(250, 60));
        btnCadastrarPesquisador.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCadastrarPesquisador.setBackground(new Color(59, 130, 246));
        btnCadastrarPesquisador.setForeground(Color.WHITE);
        btnCadastrarPesquisador.setFocusPainted(false);
        btnCadastrarPesquisador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("CadastroPesquisador");
            }
        });
        buttonPanel.add(btnCadastrarPesquisador);

        JButton btnCadastrarProfessor = new JButton("Cadastrar Professor");
        btnCadastrarProfessor.setPreferredSize(new Dimension(250, 60));
        btnCadastrarProfessor.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCadastrarProfessor.setBackground(new Color(59, 130, 246));
        btnCadastrarProfessor.setForeground(Color.WHITE);
        btnCadastrarProfessor.setFocusPainted(false);
        btnCadastrarProfessor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("CadastroProfessor");
            }
        });
        buttonPanel.add(btnCadastrarProfessor);

        JButton btnVisualizarMembros = new JButton("Visualizar Membros");
        btnVisualizarMembros.setPreferredSize(new Dimension(250, 60));
        btnVisualizarMembros.setFont(new Font("Arial", Font.PLAIN, 18));
        btnVisualizarMembros.setBackground(new Color(16, 185, 129));
        btnVisualizarMembros.setForeground(Color.WHITE);
        btnVisualizarMembros.setFocusPainted(false);
        btnVisualizarMembros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("VisualizarMembros");
            }

        });
        buttonPanel.add(btnVisualizarMembros);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setPreferredSize(new Dimension(250, 60));
        btnVoltar.setFont(new Font("Arial", Font.PLAIN, 18));
        btnVoltar.setBackground(new Color(245, 158, 11));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("TelaPrincipal");
            }
        });
        buttonPanel.add(btnVoltar);

        add(buttonPanel, BorderLayout.CENTER);
    }
}
