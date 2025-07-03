package br.edu.ifpe.lpoo.project.ui;

import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.business.MembroService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class JCadastroPesquisador {
    private JFrame frame;
    private JTextField textField;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JTextField txtMatricula;
    private JTextField txtInstituicao;
    /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JCadastroPesquisador window = new JCadastroPesquisador();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JCadastroPesquisador() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Cadastro de Pesquisador");
		frame.setSize(650, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(new Color(248, 248, 255));

		//nome:
		JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(80, 95, 120, 25);
		nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(nomeLabel);
		txtNome = new JTextField();
		txtNome.setBounds(250, 95, 300, 30);
		frame.getContentPane().add(txtNome);

		//cpf:
		JLabel cpfJLabel = new JLabel("CPF:");
		cpfJLabel.setBounds(80, 130, 120, 25);
		cpfJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(cpfJLabel);
		txtCpf = new JTextField();
		txtCpf.setBounds(250, 130, 300, 30);
		frame.getContentPane().add(txtCpf);

		//e-mail:
		JLabel emailJLabel = new JLabel("E-mail:");
		emailJLabel.setBounds(80, 165, 120, 25);
		emailJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(emailJLabel);
		txtEmail = new JTextField();
		txtEmail.setBounds(250, 165, 300, 30);
		frame.getContentPane().add(txtEmail);

		//matrícula:
		JLabel matriculaJLabel = new JLabel("Matrícula:");
		matriculaJLabel.setBounds(80, 200, 120, 25);
		matriculaJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(matriculaJLabel);
		txtMatricula = new JTextField();
		txtMatricula.setBounds(250, 200, 300, 30);
		frame.getContentPane().add(txtMatricula);

		//instituição:
		JLabel instituicaoJLabel = new JLabel("Instituição:");
		instituicaoJLabel.setBounds(80, 235, 120, 25);
		instituicaoJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(instituicaoJLabel);
		txtInstituicao = new JTextField();
		txtInstituicao.setBounds(250, 235, 300, 30);
		frame.getContentPane().add(txtInstituicao);

		//cadastrar:
		JButton btnCadastrar = new JButton("Cadastrar Orientador");
		btnCadastrar.setBackground(new Color(60, 179, 113));
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		btnCadastrar.setBounds(225, 370, 200, 40);
		frame.getContentPane().add(btnCadastrar);

		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = txtNome.getText();
				String cpf = txtCpf.getText();
				String email = txtEmail.getText();
				String matricula = txtMatricula.getText();
				String instituicao = txtInstituicao.getText();

				try {
					MembroService controller = new MembroService();
					controller.CadastrarPesquisador(nome, cpf, email, matricula, instituicao);
					JOptionPane.showMessageDialog(JCadastroPesquisador.this.frame, "O Pesquisador foi cadastrado com sucesso em nosso sistema.", "Cadastro Concluído", JOptionPane.INFORMATION_MESSAGE);
				}catch (NumberFormatException ne){
					JOptionPane.showMessageDialog(JCadastroPesquisador.this.frame, ne.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}catch (BusinessExcepition be){
					JOptionPane.showMessageDialog(JCadastroPesquisador.this.frame, be.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}

			
		});
	}
}