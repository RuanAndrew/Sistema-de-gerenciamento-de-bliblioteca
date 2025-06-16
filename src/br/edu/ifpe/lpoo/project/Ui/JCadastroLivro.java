package br.edu.ifpe.lpoo.project.Ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JCadastroLivro {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JCadastroLivro window = new JCadastroLivro();
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
	public JCadastroLivro() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel isbnLabel = new JLabel("ISBN");
		isbnLabel.setBounds(105, 128, 45, 13);
		frame.getContentPane().add(isbnLabel);
		
		textField = new JTextField();
		textField.setBounds(105, 140, 96, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
	}
}
