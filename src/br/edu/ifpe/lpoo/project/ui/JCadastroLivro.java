package br.edu.ifpe.lpoo.project.ui;

import br.edu.ifpe.lpoo.project.business.AcervoService;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JCadastroLivro extends JPanel {

	private MainFrame mainFrame;
	private JTextField txtIsbn;
	private JTextField txtTitulo;
	private JTextField txtAutor;
	private JTextField txtAnoPublicacao;
	private JTextField txtEditora;
	private JTextField txtNumeroPaginas;
	private JTextField txtGenero;
	private JTextField txtIdioma;
	private JTextField txtQuantidade;

	public JCadastroLivro(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initialize();
	}

	private void initialize() {
		setLayout(null);
		setBackground(new Color(248, 248, 255));

		// Título da tela de cadastro
		JLabel titleLabel = new JLabel("Cadastro de Livro");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
		titleLabel.setBounds(200, 30, 250, 30);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLabel);

		// --- ISBN ---
		JLabel isbnLabel = new JLabel("ISBN:");
		isbnLabel.setBounds(80, 95, 120, 25);
		isbnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(isbnLabel);
		txtIsbn = new JTextField();
		txtIsbn.setBounds(250, 95, 300, 30);
		add(txtIsbn);

		// --- Titulo ---
		JLabel tituloLabel = new JLabel("Título:");
		tituloLabel.setBounds(80, 130, 120, 25);
		tituloLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(tituloLabel);
		txtTitulo = new JTextField();
		txtTitulo.setBounds(250, 130, 300, 30);
		add(txtTitulo);

		// --- Autor ---
		JLabel autorLabel = new JLabel("Autor:");
		autorLabel.setBounds(80, 165, 120, 25);
		autorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(autorLabel);
		txtAutor = new JTextField();
		txtAutor.setBounds(250, 165, 300, 30);
		add(txtAutor);

		// --- Ano de Publicação ---
		JLabel anoPublicacaoLabel = new JLabel("Ano Publicação:");
		anoPublicacaoLabel.setBounds(80, 200, 130, 25);
		anoPublicacaoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(anoPublicacaoLabel);
		txtAnoPublicacao = new JTextField();
		txtAnoPublicacao.setBounds(250, 200, 300, 30);
		add(txtAnoPublicacao);

		// --- Editora ---
		JLabel editoraLabel = new JLabel("Editora:");
		editoraLabel.setBounds(80, 235, 120, 25);
		editoraLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(editoraLabel);
		txtEditora = new JTextField();
		txtEditora.setBounds(250, 235, 300, 30);
		add(txtEditora);

		// --- Número de Páginas ---
		JLabel numeroPaginasLabel = new JLabel("Número Páginas:");
		numeroPaginasLabel.setBounds(80, 270, 140, 25);
		numeroPaginasLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(numeroPaginasLabel);
		txtNumeroPaginas = new JTextField();
		txtNumeroPaginas.setBounds(250, 270, 300, 30);
		add(txtNumeroPaginas);

		// --- Gênero ---
		JLabel generoLabel = new JLabel("Gênero:");
		generoLabel.setBounds(80, 305, 120, 25);
		generoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(generoLabel);
		txtGenero = new JTextField();
		txtGenero.setBounds(250, 305, 300, 30);
		add(txtGenero);

		// --- Idioma ---
		JLabel idiomaLabel = new JLabel("Idioma:");
		idiomaLabel.setBounds(80, 340, 120, 25);
		idiomaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(idiomaLabel);
		txtIdioma = new JTextField();
		txtIdioma.setBounds(250, 340, 300, 30);
		add(txtIdioma);

		// --- Quantidade ---
		JLabel quantidadeLabel = new JLabel("Quantidade:");
		quantidadeLabel.setBounds(80, 375, 120, 25);
		quantidadeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(quantidadeLabel);
		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(250, 375, 300, 30);
		add(txtQuantidade);

		// --- Botão Cadastrar ---
		JButton btnCadastrar = new JButton("Cadastrar Livro");
		btnCadastrar.setBackground(new Color(60, 179, 113));
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		btnCadastrar.setBounds(150, 470, 180, 40);
		add(btnCadastrar);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (txtIsbn.getText().trim().isEmpty() ||
						txtTitulo.getText().trim().isEmpty() ||
						txtAutor.getText().trim().isEmpty() ||
						txtAnoPublicacao.getText().trim().isEmpty() ||
						txtEditora.getText().trim().isEmpty() ||
						txtNumeroPaginas.getText().trim().isEmpty() ||
						txtGenero.getText().trim().isEmpty() ||
						txtIdioma.getText().trim().isEmpty() ||
						txtQuantidade.getText().trim().isEmpty()) {

					JOptionPane.showMessageDialog(JCadastroLivro.this,
							"Por favor, preencha todos os campos antes de cadastrar.",
							"Campos Vazios",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				String isbn = txtIsbn.getText().trim();
				String titulo = txtTitulo.getText().trim();
				String autor = txtAutor.getText().trim();
				String anoPublicacao = txtAnoPublicacao.getText().trim();
				String editora = txtEditora.getText().trim();
				String numeroPaginas = txtNumeroPaginas.getText().trim();
				String genero = txtGenero.getText().trim();
				String idioma = txtIdioma.getText().trim();
				String quantidade = txtQuantidade.getText().trim();

				try {
					AcervoService controller = new AcervoService();
					controller.cadastrarLivro(titulo, autor, anoPublicacao, editora, isbn, numeroPaginas, genero, idioma, quantidade);
					JOptionPane.showMessageDialog(JCadastroLivro.this, "O livro foi cadastrado com sucesso em nosso sistema.", "Cadastro Concluído", JOptionPane.INFORMATION_MESSAGE);
					clearFields();
				} catch (NumberFormatException ne) {
					JOptionPane.showMessageDialog(JCadastroLivro.this, ne.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				} catch (BusinessExcepition be) {
					JOptionPane.showMessageDialog(JCadastroLivro.this, be.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// --- Botão Voltar ---
		JButton btnVoltar = new JButton("Voltar ao Menu");
		btnVoltar.setBackground(new Color(255, 140, 0));
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		btnVoltar.setBounds(350, 470, 180, 40);
		add(btnVoltar);
		btnVoltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.showPanel("TelaPrincipal");
				clearFields();
			}
		});
	}

	private void clearFields() {
		txtIsbn.setText("");
		txtTitulo.setText("");
		txtAutor.setText("");
		txtAnoPublicacao.setText("");
		txtEditora.setText("");
		txtNumeroPaginas.setText("");
		txtGenero.setText("");
		txtIdioma.setText("");
		txtQuantidade.setText("");
	}
}