package br.edu.ifpe.lpoo.project.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class JCadastroLivro {

	private JFrame frame;
	private JTextField textField;
	private JTextField txtIsbn;
	private JTextField txtTitulo;
	private JTextField txtAutor;
	private JTextField txtAnoPublicacao;
	private JTextField txtEditora;
	private JTextField txtNumeroPaginas;
	private JTextField txtGenero;
	private JTextField txtIdioma;

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
		frame = new JFrame("Cadastro de Livro");
		frame.setSize(600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		// ISBN
		JLabel isbnLabel = new JLabel("ISBN");
		isbnLabel.setBounds(105, 128, 80, 13);
		frame.getContentPane().add(isbnLabel);
		txtIsbn = new JTextField();
		txtIsbn.setBounds(250, 125, 300, 19);
		frame.getContentPane().add(txtIsbn);
		txtIsbn.setColumns(10);
		// Titulo
		JLabel tituloLabel = new JLabel("Titulo");
		tituloLabel.setBounds(105, 158, 80, 13);
		frame.getContentPane().add(tituloLabel);
		txtTitulo = new JTextField();
		txtTitulo.setBounds(250, 155, 300, 19);
		frame.getContentPane().add(txtTitulo);
		txtTitulo.setColumns(10);

		// Autor
		JLabel autorLabel = new JLabel("Autor:");
		autorLabel.setBounds(105, 188, 80, 13);
		frame.getContentPane().add(autorLabel);
		txtAutor = new JTextField();
		txtAutor.setBounds(250, 185, 300, 19);
		frame.getContentPane().add(txtAutor);
		txtAutor.setColumns(10);

		// Ano de Publicação
		JLabel anoPublicacaoLabel = new JLabel("Ano Publicação:");
		anoPublicacaoLabel.setBounds(105, 218, 120, 13);
		frame.getContentPane().add(anoPublicacaoLabel);
		txtAnoPublicacao = new JTextField();
		txtAnoPublicacao.setBounds(250, 215, 300, 19);
		frame.getContentPane().add(txtAnoPublicacao);
		txtAnoPublicacao.setColumns(10);

		// Editora
		JLabel editoraLabel = new JLabel("Editora:");
		editoraLabel.setBounds(105, 248, 80, 13);
		frame.getContentPane().add(editoraLabel);
		txtEditora = new JTextField();
		txtEditora.setBounds(250, 245, 300, 19);
		frame.getContentPane().add(txtEditora);
		txtEditora.setColumns(10);

		// Número de Páginas
		JLabel numeroPaginasLabel = new JLabel("Número Páginas:");
		numeroPaginasLabel.setBounds(105, 278, 120, 13);
		frame.getContentPane().add(numeroPaginasLabel);
		txtNumeroPaginas = new JTextField();
		txtNumeroPaginas.setBounds(250, 275, 300, 19);
		frame.getContentPane().add(txtNumeroPaginas);
		txtNumeroPaginas.setColumns(10);

		// Gênero
		JLabel generoLabel = new JLabel("Gênero:");
		generoLabel.setBounds(105, 308, 80, 13);
		frame.getContentPane().add(generoLabel);
		txtGenero = new JTextField();
		txtGenero.setBounds(250, 305, 300, 19);
		frame.getContentPane().add(txtGenero);
		txtGenero.setColumns(10);

		// Idioma
		JLabel idiomaLabel = new JLabel("Gênero:");
		idiomaLabel.setBounds(105, 308, 80, 13);
		frame.getContentPane().add(idiomaLabel);
		txtIdioma = new JTextField();
		txtIdioma.setBounds(250, 305, 300, 19);
		frame.getContentPane().add(txtIdioma);
		txtIdioma.setColumns(10);

		// Botão Cadastrar
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(300, 350, 200, 25); // Posição e tamanho do botão
		frame.getContentPane().add(btnCadastrar);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String isbn = txtIsbn.getText();
				String titulo = txtTitulo.getText();
				String autor = txtAutor.getText();
				String anoPublicacao = txtAnoPublicacao.getText();
				String editora = txtEditora.getText();
				String numeroPaginas = txtNumeroPaginas.getText();
				String genero = txtGenero.getText();
			}
		});
	}
}
