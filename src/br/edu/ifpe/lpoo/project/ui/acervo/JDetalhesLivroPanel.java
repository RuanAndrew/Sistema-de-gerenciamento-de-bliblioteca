package br.edu.ifpe.lpoo.project.ui.acervo;

import br.edu.ifpe.lpoo.project.business.AcervoService;
import br.edu.ifpe.lpoo.project.entities.acervo.Livro;
import br.edu.ifpe.lpoo.project.ui.MainFrame;

import javax.swing.*;
import java.awt.*;

public class JDetalhesLivroPanel extends JPanel {

    private final MainFrame mainFrame;
    private final AcervoService acervoService;

    private JLabel lblTituloData, lblAutorData, lblIsbnData, lblAnoData,
            lblEditoraData, lblPaginasData, lblGeneroData, lblIdiomaData;

    public JDetalhesLivroPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.acervoService = new AcervoService();
        initialize();
    }

    public void carregarDados(int idLivro) {
        try {
            Livro livro = (Livro) acervoService.buscarItemPorId(idLivro);
            if (livro != null) {
                lblTituloData.setText(livro.getTitulo());
                lblAutorData.setText(livro.getAutor());
                lblIsbnData.setText(livro.getIsbn());
                lblAnoData.setText("" + livro.getAnoPublicacao());
                lblEditoraData.setText(livro.getEditora());
                lblPaginasData.setText("" + livro.getNumeroPaginas());
                lblGeneroData.setText(livro.getGenero());
                lblIdiomaData.setText(livro.getIdioma());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar detalhes.");
        }
    }

    private void initialize() {
        setLayout(null);
        setBackground(new Color(248, 248, 255));

        // Título principal
        JLabel titleLabel = new JLabel("Detalhes do Livro");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 20, 600, 30);
        add(titleLabel);

        // Título
        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setFont(new Font("Arial",Font.BOLD,14));
        lblTitulo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTitulo.setBounds(50, 80, 150, 25);
        add(lblTitulo);

        lblTituloData = new JLabel();
        lblTituloData.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTituloData.setBounds(210, 80, 350, 25);
        add(lblTituloData);

        // Autor
        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setFont(new Font("Arial", Font.BOLD, 14));
        lblAutor.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAutor.setBounds(50, 110, 150, 25);
        add(lblAutor);

        lblAutorData = new JLabel();
        lblAutorData.setFont(new Font("Arial", Font.PLAIN, 14));
        lblAutorData.setBounds(210, 110, 350, 25);
        add(lblAutorData);

        // ISBN
        JLabel lblIsbn = new JLabel("ISBN:");
        lblIsbn.setFont(new Font("Arial", Font.BOLD, 14));
        lblIsbn.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIsbn.setBounds(50, 140, 150, 25);
        add(lblIsbn);

        lblIsbnData = new JLabel();
        lblIsbnData.setFont(new Font("Arial", Font.PLAIN, 14));
        lblIsbnData.setBounds(210, 140, 350, 25);
        add(lblIsbnData);

        // Ano
        JLabel lblAno = new JLabel("Ano de Publicação:");
        lblAno.setFont(new Font("Arial", Font.BOLD, 14));
        lblAno.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAno.setBounds(50, 170, 150, 25);
        add(lblAno);

        lblAnoData = new JLabel();
        lblAnoData.setFont(new Font("Arial", Font.PLAIN, 14));
        lblAnoData.setBounds(210, 170, 350, 25);
        add(lblAnoData);

        // Editora
        JLabel lblEditora = new JLabel("Editora:");
        lblEditora.setFont(new Font("Arial", Font.BOLD, 14));
        lblEditora.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEditora.setBounds(50, 200, 150, 25);
        add(lblEditora);

        lblEditoraData = new JLabel();
        lblEditoraData.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEditoraData.setBounds(210, 200, 350, 25);
        add(lblEditoraData);

        // Páginas
        JLabel lblPaginas = new JLabel("Nº de Páginas:");
        lblPaginas.setFont(new Font("Arial", Font.BOLD, 14));
        lblPaginas.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPaginas.setBounds(50, 230, 150, 25);
        add(lblPaginas);

        lblPaginasData = new JLabel();
        lblPaginasData.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPaginasData.setBounds(210, 230, 350, 25);
        add(lblPaginasData);

        // Gênero
        JLabel lblGenero = new JLabel("Gênero:");
        lblGenero.setFont(new Font("Arial", Font.BOLD, 14));
        lblGenero.setHorizontalAlignment(SwingConstants.RIGHT);
        lblGenero.setBounds(50, 260, 150, 25);
        add(lblGenero);

        lblGeneroData = new JLabel();
        lblGeneroData.setFont(new Font("Arial", Font.PLAIN, 14));
        lblGeneroData.setBounds(210, 260, 350, 25);
        add(lblGeneroData);

        // Idioma
        JLabel lblIdioma = new JLabel("Idioma:");
        lblIdioma.setFont(new Font("Arial", Font.BOLD, 14));
        lblIdioma.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIdioma.setBounds(50, 290, 150, 25);
        add(lblIdioma);

        lblIdiomaData = new JLabel();
        lblIdiomaData.setFont(new Font("Arial", Font.PLAIN, 14));
        lblIdiomaData.setBounds(210, 290, 350, 25);
        add(lblIdiomaData);

        // Botão de Voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(new Color(255, 140, 0));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBounds(250, 350, 100, 40);
        add(btnVoltar);

        btnVoltar.addActionListener(e -> mainFrame.showPanel("ListarLivros"));
    }
}