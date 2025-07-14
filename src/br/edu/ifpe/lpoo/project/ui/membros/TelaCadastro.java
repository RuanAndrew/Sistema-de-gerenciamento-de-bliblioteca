package br.edu.ifpe.lpoo.project.ui.membros;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaCadastro extends JFrame {

    public TelaCadastro() {

        setTitle("Menu Principal de Cadastros - Sistema da Biblioteca");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao fechar esta janela
        setLocationRelativeTo(null); // Centraliza na tela
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(painelPrincipal);

        JLabel labelTitulo = new JLabel("Selecione uma Opção", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        painelPrincipal.add(labelTitulo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 15, 15)); // 3 linhas, 1 coluna, com espaçamento
        painelBotoes.setBorder(new EmptyBorder(20, 50, 20, 50)); // Margem interna para afastar os botões das bordas


        JButton btnCadastrarAluno = new JButton("Cadastrar Aluno");
        configurarBotao(btnCadastrarAluno);
        btnCadastrarAluno.addActionListener(this::abrirTelaCadastroAluno);
        painelBotoes.add(btnCadastrarAluno);


        JButton btnCadastrarProfessor = new JButton("Cadastrar Professor");
        configurarBotao(btnCadastrarProfessor);
        btnCadastrarProfessor.addActionListener(this::abrirTelaCadastroProfessor);
        painelBotoes.add(btnCadastrarProfessor);

        JButton btnCadastrarPesquisador = new JButton("Cadastrar Pesquisador");
        configurarBotao(btnCadastrarPesquisador);
        btnCadastrarPesquisador.addActionListener(this::abrirTelaCadastroPesquisador);
        painelBotoes.add(btnCadastrarPesquisador);

        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
    }


    private void configurarBotao(JButton botao) {
        botao.setFont(new Font("SansSerif", Font.PLAIN, 16));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }


    private void abrirTelaCadastroAluno(ActionEvent e) {

        JCadastroAluno telaAluno = new JCadastroAluno();
        telaAluno.setVisible(true);
    }

    private void abrirTelaCadastroProfessor(ActionEvent e) {
        JCadastroProfessor telaProfessor = new JCadastroProfessor();
        telaProfessor.setVisible(true);
    }

    private void abrirTelaCadastroPesquisador(ActionEvent e) {
        JCadastroPesquisador telaPesquisador = new JCadastroPesquisador();
        telaPesquisador.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new TelaCadastro().setVisible(true);
        });
    }
}