package br.edu.ifpe.lpoo.project.ui.membros;

import br.edu.ifpe.lpoo.project.business.MembroService;
import br.edu.ifpe.lpoo.project.exceptions.BusinessException;
import br.edu.ifpe.lpoo.project.ui.MainFrame;

import javax.swing.*;
import java.awt.*;


public class JCadastroAluno extends JPanel {

    private MainFrame mainFrame;

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JTextField txtMatricula;
    private JTextField txtCurso;

    public JCadastroAluno(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initialize();
    }

    private void initialize() {

        setLayout(null);
        setBackground(new Color(248, 248, 255));

        JLabel lblTituloPrincipal = new JLabel("Cadastro de Aluno como Membro");
        lblTituloPrincipal.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTituloPrincipal.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloPrincipal.setBounds(10, 25, 614, 30);
        add(lblTituloPrincipal);

        // Nome
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(80, 95, 120, 25);
        nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(nomeLabel);
        txtNome = new JTextField();
        txtNome.setBounds(250, 95, 300, 30);
        add(txtNome);

        // CPF
        JLabel cpfJLabel = new JLabel("CPF:");
        cpfJLabel.setBounds(80, 130, 120, 25);
        cpfJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(cpfJLabel);
        txtCpf = new JTextField();
        txtCpf.setBounds(250, 130, 300, 30);
        add(txtCpf);

        // E-mail
        JLabel emailJLabel = new JLabel("E-mail:");
        emailJLabel.setBounds(80, 165, 120, 25);
        emailJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(emailJLabel);
        txtEmail = new JTextField();
        txtEmail.setBounds(250, 165, 300, 30);
        add(txtEmail);

        // Matrícula
        JLabel matriculaJLabel = new JLabel("Matrícula:");
        matriculaJLabel.setBounds(80, 200, 120, 25);
        matriculaJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(matriculaJLabel);
        txtMatricula = new JTextField();
        txtMatricula.setBounds(250, 200, 300, 30);
        add(txtMatricula);

        // Curso
        JLabel cursoLabel = new JLabel("Curso:");
        cursoLabel.setBounds(80, 235, 120, 25);
        cursoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(cursoLabel);
        txtCurso = new JTextField();
        txtCurso.setBounds(250, 235, 300, 30);
        add(txtCurso);

        // Botão Cadastrar
        JButton btnCadastrar = new JButton("Cadastrar Aluno");
        btnCadastrar.setBackground(new Color(60, 179, 113));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setOpaque(true);
        btnCadastrar.setBorderPainted(false);
        btnCadastrar.setBounds(335, 420, 200, 40);
        add(btnCadastrar);

        // Botão Voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(new Color(255, 140, 0));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setOpaque(true);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setBounds(115, 420, 200, 40);
        add(btnVoltar);

        btnVoltar.addActionListener(e -> {
            clearFields();
            mainFrame.showPanel("GerenciarMembrosPanel");
        });

        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String email = txtEmail.getText();
            String matricula = txtMatricula.getText();
            String curso = txtCurso.getText();

            try {
                MembroService controller = new MembroService();
                controller.cadastrarAluno(nome, cpf, email, matricula, curso);
                JOptionPane.showMessageDialog(this, "O Aluno foi cadastrado com sucesso!", "Cadastro Concluído", JOptionPane.INFORMATION_MESSAGE);
                clearFields();

            } catch (BusinessException be) {
                JOptionPane.showMessageDialog(this, be.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    private void clearFields() {
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtMatricula.setText("");
        txtCurso.setText("");
        txtNome.requestFocusInWindow();
    }
}