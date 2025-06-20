package br.edu.ifpe.lpoo.project.ui;

import br.edu.ifpe.lpoo.project.business.AcervoService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
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
	private JTextField txtQuantidade;

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
		frame.setSize(650, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(new Color(248, 248, 255));


		// --- ISBN ---
		JLabel isbnLabel = new JLabel("ISBN:");
		isbnLabel.setBounds(80, 95, 120, 25);
		isbnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(isbnLabel);
		txtIsbn = new JTextField();
		txtIsbn.setBounds(250, 95, 300, 30);
		frame.getContentPane().add(txtIsbn);

		// --- Titulo ---
		JLabel tituloLabel = new JLabel("Título:");
		tituloLabel.setBounds(80, 130, 120, 25);
		tituloLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(tituloLabel);
		txtTitulo = new JTextField();
		txtTitulo.setBounds(250, 130, 300, 30);
		frame.getContentPane().add(txtTitulo);

		// --- Autor ---
		JLabel autorLabel = new JLabel("Autor:");
		autorLabel.setBounds(80, 165, 120, 25);
		autorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(autorLabel);
		txtAutor = new JTextField();
		txtAutor.setBounds(250, 165, 300, 30);
		frame.getContentPane().add(txtAutor);

		// --- Ano de Publicação ---
		JLabel anoPublicacaoLabel = new JLabel("Ano Publicação:");
		anoPublicacaoLabel.setBounds(80, 200, 130, 25);
		anoPublicacaoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(anoPublicacaoLabel);
		txtAnoPublicacao = new JTextField();
		txtAnoPublicacao.setBounds(250, 200, 300, 30);
		frame.getContentPane().add(txtAnoPublicacao);

		// --- Editora ---
		JLabel editoraLabel = new JLabel("Editora:");
		editoraLabel.setBounds(80, 235, 120, 25);
		editoraLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(editoraLabel);
		txtEditora = new JTextField();
		txtEditora.setBounds(250, 235, 300, 30);
		frame.getContentPane().add(txtEditora);

		// --- Número de Páginas ---
		JLabel numeroPaginasLabel = new JLabel("Número Páginas:");
		numeroPaginasLabel.setBounds(80, 270, 140, 25);
		numeroPaginasLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(numeroPaginasLabel);
		txtNumeroPaginas = new JTextField();
		txtNumeroPaginas.setBounds(250, 270, 300, 30);
		frame.getContentPane().add(txtNumeroPaginas);

		// --- Gênero ---
		JLabel generoLabel = new JLabel("Gênero:");
		generoLabel.setBounds(80, 305, 120, 25);
		generoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(generoLabel);
		txtGenero = new JTextField();
		txtGenero.setBounds(250, 305, 300, 30);
		frame.getContentPane().add(txtGenero);

		// --- Idioma ---
		JLabel idiomaLabel = new JLabel("Idioma:");
		idiomaLabel.setBounds(80, 340, 120, 25);
		idiomaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(idiomaLabel);
		txtIdioma = new JTextField();
		txtIdioma.setBounds(250, 340, 300, 30);
		frame.getContentPane().add(txtIdioma);

		// --- Quantidade ---
		JLabel quantidadeLabel = new JLabel("Quantidade:");
		quantidadeLabel.setBounds(80, 375, 120, 25);
		quantidadeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		frame.getContentPane().add(quantidadeLabel);
		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(250, 375, 300, 30);
		frame.getContentPane().add(txtQuantidade);

		// --- Botão Cadastrar ---
		JButton btnCadastrar = new JButton("Cadastrar Livro");
		btnCadastrar.setBackground(new Color(60, 179, 113)); // MediumSeaGreen
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding no botão
		btnCadastrar.setBounds(225, 430, 200, 40);
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
				String idioma = txtIdioma.getText();
				String quantidade = txtQuantidade.getText();

				AcervoService controller = new AcervoService();
				controller.CadastrarLivro(titulo,autor,anoPublicacao,editora,isbn,numeroPaginas,genero,idioma,quantidade);
				JOptionPane.showMessageDialog(JCadastroLivro.this.frame,"O livro foi cadastrado com sucesso em nosso sistema.", "Cadastro Concluído", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
}
