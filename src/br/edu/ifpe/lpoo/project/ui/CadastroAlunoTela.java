package br.edu.ifpe.lpoo.project.ui;

import br.edu.ifpe.lpoo.project.business.MembroService;
import br.edu.ifpe.lpoo.project.entities.membros.Aluno;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // Import adicionado para a classe anônima
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * Tela (JFrame) para o cadastro de novos alunos no sistema da biblioteca.
 */
public class CadastroAlunoTela extends JFrame {

    // --- Componentes da UI ---
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JTextField txtMatricula;
    private JTextField txtCurso;
    private JButton btnCadastrar;

    // --- Camada de Negócios ---
    private MembroService membroService;

    public CadastroAlunoTela() {
        // Instancia a camada de serviço
        this.membroService = new MembroService();

        // --- Configurações da Janela Principal ---
        setTitle("Sistema de Biblioteca - Cadastro de Aluno");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null); // Centralizar na tela

        // --- Painel Principal ---
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(painelPrincipal);
        
        // --- Layout Manager ---
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preencher horizontalmente

        // --- Título do Formulário ---
        JLabel lblTitulo = new JLabel("Cadastro de Novo Aluno");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza
        painelPrincipal.add(lblTitulo, gbc);
        
        gbc.gridwidth = 1; // Reseta para o padrão
        gbc.anchor = GridBagConstraints.WEST; // Reseta para o padrão

        // --- Campo Nome ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelPrincipal.add(new JLabel("Nome Completo:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(25);
        painelPrincipal.add(txtNome, gbc);

        // --- Campo CPF ---
        gbc.gridx = 0;
        gbc.gridy = 2;
        painelPrincipal.add(new JLabel("CPF (apenas números):"), gbc);
        gbc.gridx = 1;
        txtCpf = new JTextField(25);
        painelPrincipal.add(txtCpf, gbc);

        // --- Campo E-mail ---
        gbc.gridx = 0;
        gbc.gridy = 3;
        painelPrincipal.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(25);
        painelPrincipal.add(txtEmail, gbc);

        // --- Campo Matrícula ---
        gbc.gridx = 0;
        gbc.gridy = 4;
        painelPrincipal.add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1;
        txtMatricula = new JTextField(25);
        painelPrincipal.add(txtMatricula, gbc);

        // --- Campo Curso ---
        gbc.gridx = 0;
        gbc.gridy = 5;
        painelPrincipal.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 1;
        txtCurso = new JTextField(25);
        painelPrincipal.add(txtCurso, gbc);

        // --- Botão Cadastrar ---
        btnCadastrar = new JButton("Cadastrar Aluno");
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE; // Tamanho preferido do botão
        gbc.anchor = GridBagConstraints.EAST; // Alinha à direita
        painelPrincipal.add(btnCadastrar, gbc);

        // --- Adiciona o Listener ao Botão ---
        // CORREÇÃO: Substituído o lambda (->) por uma classe anônima para compatibilidade.
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executarCadastro();
            }
        });
    }

    /**
     * Coleta os dados da tela, cria o objeto Aluno e chama o serviço de cadastro.
     */
    private void executarCadastro() {
        // 1. Coletar dados da UI
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String email = txtEmail.getText().trim();
        String matricula = txtMatricula.getText().trim();
        String curso = txtCurso.getText().trim();

        // 2. Validação básica na UI
        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || matricula.isEmpty() || curso.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro de Entrada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Criar o objeto de modelo (entidade)
        Aluno novoAluno = new Aluno(nome, cpf, email, matricula, curso);

        // 4. Enviar para a camada de serviço e tratar a resposta
        try {
            membroService.cadastrarAluno(novoAluno);
            JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Erro de Validação: " + ex.getMessage(), "Erro de Negócio", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Limpa todos os campos do formulário.
     */
    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtMatricula.setText("");
        txtCurso.setText("");
        txtNome.requestFocusInWindow();
    }

    /**
     * Ponto de entrada da aplicação.
     */
    public static void main(String[] args) {
        // CORREÇÃO: As linhas 139 e 143 foram substituídas por esta estrutura
        // que é compatível com todas as versões do Java.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CadastroAlunoTela().setVisible(true);
            }
        });
    }
}
