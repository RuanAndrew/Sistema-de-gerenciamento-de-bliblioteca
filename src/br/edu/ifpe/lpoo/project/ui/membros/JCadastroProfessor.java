package br.edu.ifpe.lpoo.project.ui.membros;

import br.edu.ifpe.lpoo.project.business.MembroService;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class JCadastroProfessor extends JPanel {

    private MainFrame mainFrame;

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JTextField txtMatricula;
    private JTextField txtAreaAtuacao;
    private JTextField txtDepartamento;


    public JCadastroProfessor(MainFrame mainFrame) {
        this.mainFrame =  mainFrame;
        initialize();
    }

    private void initialize() {

        setLayout(null);
        setBackground(new Color(248, 248, 255));

        JLabel lblTituloPrincipal = new JLabel("Cadastro de Professor como Membro");
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

        // Área de Atuação
        JLabel areaAtuacaoLabel = new JLabel("Área de Atuação:");
        areaAtuacaoLabel.setBounds(80, 235, 120, 25);
        areaAtuacaoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(areaAtuacaoLabel);
        txtAreaAtuacao = new JTextField();
        txtAreaAtuacao.setBounds(250, 235, 300, 30);
        add(txtAreaAtuacao);

        // Departamento
        JLabel departamentoLabel = new JLabel("Departamento:");
        departamentoLabel.setBounds(80, 270, 120, 25);
        departamentoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(departamentoLabel);
        txtDepartamento = new JTextField();
        txtDepartamento.setBounds(250, 270, 300, 30);
        add(txtDepartamento);


        // Botão Cadastrar
        JButton btnCadastrar = new JButton("Cadastrar Professor");
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


        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String cpf = txtCpf.getText();
                String email = txtEmail.getText();
                String matricula = txtMatricula.getText();
                String areaAtuacao = txtAreaAtuacao.getText();
                String departamento = txtDepartamento.getText();

                try {
                    MembroService controller = new MembroService();
                    controller.cadastrarProfessor(nome, cpf, email, matricula, areaAtuacao, departamento);
                    JOptionPane.showMessageDialog(JCadastroProfessor.this, "O Professor foi cadastrado com sucesso!", "Cadastro Concluído", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();

                } catch (BusinessExcepition be) {
                    JOptionPane.showMessageDialog(JCadastroProfessor.this, be.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(JCadastroProfessor.this, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    private void clearFields(){
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtMatricula.setText("");
        txtAreaAtuacao.setText("");
        txtDepartamento.setText("");
    }
}