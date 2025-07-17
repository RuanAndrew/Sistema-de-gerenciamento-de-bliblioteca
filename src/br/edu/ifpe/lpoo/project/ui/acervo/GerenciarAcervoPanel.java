package br.edu.ifpe.lpoo.project.ui.acervo;

import br.edu.ifpe.lpoo.project.ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciarAcervoPanel extends JPanel {

    private MainFrame mainFrame;

    public GerenciarAcervoPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

// Título/Cabeçalho
        JLabel welcomeLabel = new JLabel("Gerenciamento de Acervo");
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
        btnCadastrarLivro.setBackground(new Color(59, 130, 246));
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
        btnCadastrarEbook.setBackground(new Color(59, 130, 246));
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
        btnCadastrarPeriodico.setBackground(new Color(59, 130, 246));
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
                mainFrame.showPanel("ListarLivros");
            }
        });
        buttonPanel.add(btnListarLivros);

        JButton btnListarEbooks = new JButton("Listar Ebooks");
        btnListarEbooks.setPreferredSize(new Dimension(250, 60));
        btnListarEbooks.setFont(new Font("Arial", Font.PLAIN, 18));
        btnListarEbooks.setBackground(new Color(60, 179, 113));
        btnListarEbooks.setForeground(Color.WHITE);
        btnListarEbooks.setFocusPainted(false);
        btnListarEbooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("ListarEbooks");
            }
        });
        buttonPanel.add(btnListarEbooks);

        JButton btnListarPeriodicos = new JButton("Listar Periodico");
        btnListarPeriodicos.setPreferredSize(new Dimension(250, 60));
        btnListarPeriodicos.setFont(new Font("Arial", Font.PLAIN, 18));
        btnListarPeriodicos.setBackground(new Color(60, 179, 113));
        btnListarPeriodicos.setForeground(Color.WHITE);
        btnListarPeriodicos.setFocusPainted(false);
        btnListarPeriodicos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("ListarPeriodicos");
            }
        });
        buttonPanel.add(btnListarPeriodicos);

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

    private void initialize(){
        //setLayout();
    }


}
