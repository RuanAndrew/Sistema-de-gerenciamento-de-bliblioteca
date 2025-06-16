package br.edu.ifpe.lpoo.project.Ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.edu.ifpe.lpoo.project.business.ControllerSearchIsbn;
import br.edu.ifpe.lpoo.project.entities.Livro;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JCadastroLivro {

	private JFrame frame;
//	private JTextField isbnTextField;
//	private JTextField titleTextField;
//	private JTextField autorTextField;
//	private JTextField editoraTextField;
//	private JTextField numPaginasTextField;
//	private JTextField anoPublicacaoTextField;
//	private JTextField generoTextField;

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
//		frame = new JFrame();
//		frame.setBounds(100, 100, 932, 600);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(null);
//		
//		JLabel isbnLabel = new JLabel("ISBN");
//		isbnLabel.setBounds(58, 63, 45, 13);
//		frame.getContentPane().add(isbnLabel);
//		
//		isbnTextField = new JTextField();
//		isbnTextField.setBounds(58, 75, 152, 19);
//		frame.getContentPane().add(isbnTextField);
//		isbnTextField.setColumns(10);
//		
//		JLabel titleLbl = new JLabel("Título");
//		titleLbl.setBounds(58, 104, 45, 13);
//		frame.getContentPane().add(titleLbl);
//		
//		titleTextField = new JTextField();
//		titleTextField.setColumns(10);
//		titleTextField.setBounds(58, 116, 152, 19);
//		frame.getContentPane().add(titleTextField);
//		
//		JLabel autorLbl = new JLabel("Autor");
//		autorLbl.setBounds(58, 145, 45, 13);
//		frame.getContentPane().add(autorLbl);
//		
//		autorTextField = new JTextField();
//		autorTextField.setColumns(10);
//		autorTextField.setBounds(58, 157, 152, 19);
//		frame.getContentPane().add(autorTextField);
//		
//		editoraTextField = new JTextField();
//		editoraTextField.setColumns(10);
//		editoraTextField.setBounds(58, 198, 152, 19);
//		frame.getContentPane().add(editoraTextField);
//		
//		JLabel editoraLbl = new JLabel("Editora");
//		editoraLbl.setBounds(58, 186, 45, 13);
//		frame.getContentPane().add(editoraLbl);
//		
//		numPaginasTextField = new JTextField();
//		numPaginasTextField.setColumns(10);
//		numPaginasTextField.setBounds(58, 239, 152, 19);
//		frame.getContentPane().add(numPaginasTextField);
//		
//		JLabel numPaginasLbl = new JLabel("Número de páginas");
//		numPaginasLbl.setBounds(58, 227, 132, 13);
//		frame.getContentPane().add(numPaginasLbl);
//		
//		anoPublicacaoTextField = new JTextField();
//		anoPublicacaoTextField.setColumns(10);
//		anoPublicacaoTextField.setBounds(58, 280, 152, 19);
//		frame.getContentPane().add(anoPublicacaoTextField);
//		
//		JLabel anoPublicacaorLbl = new JLabel("Ano de publicação");
//		anoPublicacaorLbl.setBounds(58, 268, 152, 13);
//		frame.getContentPane().add(anoPublicacaorLbl);
//		
//		JLabel generoLbl = new JLabel("Gênero");
//		generoLbl.setBounds(58, 309, 45, 13);
//		frame.getContentPane().add(generoLbl);
//		
//		generoTextField = new JTextField();
//		generoTextField.setColumns(10);
//		generoTextField.setBounds(58, 321, 152, 19);
//		frame.getContentPane().add(generoTextField);
//		
//		JButton searchButton = new JButton("Consultar");
//		searchButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				String isbn = isbnTextField.getText().trim();
//				if(isbn.isEmpty()) {
//					JOptionPane.showMessageDialog(null, "Informe um ISBN");
//					return;
//				}
//				
//				String resposta = ControllerSearchIsbn.searchBookByIsbn(isbn);
//				if(resposta == null || resposta.equals("{}")) {
//					JOptionPane.showMessageDialog(null, "Livro não encontrado ou erro na API.");
//				    return;
//				}
//				
//				Livro livro = ControllerSearchIsbn.completeFields(resposta, isbn);
//				titleTextField.setText(livro.getTitulo());
//				autorTextField.setText(livro.getAutor());
//				editoraTextField.setText(livro.getEditora());
//				numPaginasTextField.setText(String.valueOf(livro.getNumeroPaginas()));
//				anoPublicacaoTextField.setText(livro.getAnoPublicação());
//				generoTextField.setText(livro.getGenero());				
//			}
//		});
//		searchButton.setBounds(127, 354, 110, 21);
//		frame.getContentPane().add(searchButton);
		
	}
}
