package br.edu.ifpe.lpoo.project.ui.acervo;

import br.edu.ifpe.lpoo.project.business.AcervoService;
import br.edu.ifpe.lpoo.project.business.CatalogacaoService;
import br.edu.ifpe.lpoo.project.entities.acervo.Ebook;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.ui.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JCadastroEbook extends JPanel {

    private MainFrame mainFrame;
    private JTextField txtTitulo;
    private JTextField txtAutores;
    private JTextField txtAnoPublicacao;
    private JTextField txtEditora;
    private JTextField txtIsbn;
    private JTextField txtNumeroPaginas;
    private JTextField txtGenero;
    private JComboBox<String> comboIdioma;
    private JComboBox<String> comboFormatoDigital;
    private JTextField txtUrl;
    private CatalogacaoService catalogacaoService;

    public JCadastroEbook(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.catalogacaoService = new CatalogacaoService();
        initialize();
    }

    private void initialize() {
        setLayout(null);
        setBackground(new Color(248, 248, 255));

        // Título da tela de cadastro
        JLabel titleLabel = new JLabel("Cadastro de E-book");
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
        txtIsbn.setBounds(250, 95, 200, 30);
        add(txtIsbn);
        JButton btnBuscarIsbn = new JButton("Buscar");
        btnBuscarIsbn.setBounds(450, 95, 100, 30);
        add(btnBuscarIsbn);

        // --- Titulo ---
        JLabel tituloLabel = new JLabel("Título:");
        tituloLabel.setBounds(80, 130, 120, 25);
        tituloLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(tituloLabel);
        txtTitulo = new JTextField();
        txtTitulo.setBounds(250, 130, 300, 30);
        add(txtTitulo);

        // --- Autor(s) ---
        JLabel autoresLabel = new JLabel("Autor:");
        autoresLabel.setBounds(80, 165, 120, 25);
        autoresLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(autoresLabel);
        txtAutores = new JTextField();
        txtAutores.setBounds(250, 165, 300, 30);
        add(txtAutores);

        // --- Ano de Publicação ---
        JLabel anoPublicacaoLabel = new JLabel("Ano Publicação:");
        anoPublicacaoLabel.setBounds(80, 200, 120, 25);
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
        numeroPaginasLabel.setBounds(80, 270, 120, 25);
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

        String[] idiomas = {"Português", "Inglês", "Espanhol", "Francês", "Alemão"};
        comboIdioma = new JComboBox<>(idiomas);
        comboIdioma.setBounds(250, 340, 300, 30);
        add(comboIdioma);

        // --- Formato Digital ---
        JLabel formatoDigitalLabel = new JLabel("Formato Digital:");
        formatoDigitalLabel.setBounds(80, 375, 120, 25);
        formatoDigitalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(formatoDigitalLabel);

        String[] formatos = {"PDF", "EPUB", "MOBI"};
        comboFormatoDigital = new JComboBox<>(formatos);
        comboFormatoDigital.setBounds(250, 375, 300, 30);
        add(comboFormatoDigital);

        // --- URL ---
        JLabel urlLabel = new JLabel("URL:");
        urlLabel.setBounds(80, 410, 120, 25);
        urlLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(urlLabel);
        txtUrl = new JTextField();
        txtUrl.setBounds(250, 410, 300, 30);
        add(txtUrl);

        // --- Botão Buscar ---
        btnBuscarIsbn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = txtIsbn.getText().trim();
                if(isbn.isEmpty()){
                    JOptionPane.showMessageDialog(JCadastroEbook.this,"Por favor, insira um ISBN para buscar.","ISBN Vazio", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try{
                    Ebook ebookEncontrado = catalogacaoService.buscarDadosEbookPorIsbn(isbn);
                    if (ebookEncontrado != null) {
                        preencherFormulario(ebookEncontrado);
                        JOptionPane.showMessageDialog(JCadastroEbook.this, "Dados do ebook preenchidos. Verifique as informações e adicione a quantidade.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(JCadastroEbook.this, "Nenhum ebook encontrado para este ISBN. Prossiga com o cadastro manual.", "Não Encontrado", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (BusinessExcepition ex) {
                    JOptionPane.showMessageDialog(JCadastroEbook.this, "Erro ao buscar dados: " + ex.getMessage(), "Erro na API", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- Botão Cadastrar ---
        JButton btnCadastrar = new JButton("Cadastrar E-book");
        btnCadastrar.setBackground(new Color(60, 179, 113));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCadastrar.setBounds(350, 470, 180, 40);
        add(btnCadastrar);
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (txtTitulo.getText().trim().isEmpty() ||
                        txtAutores.getText().trim().isEmpty() ||
                        txtAnoPublicacao.getText().trim().isEmpty() ||
                        txtEditora.getText().trim().isEmpty() ||
                        txtIsbn.getText().trim().isEmpty() ||
                        txtNumeroPaginas.getText().trim().isEmpty() ||
                        txtGenero.getText().trim().isEmpty() ||
                        txtUrl.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(JCadastroEbook.this,
                            "Por favor, preencha todos os campos antes de cadastrar.",
                            "Campos Vazios",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String titulo = txtTitulo.getText();
                String autores = txtAutores.getText();
                String anoPublicacao = txtAnoPublicacao.getText();
                String editora = txtEditora.getText();
                String isbn = txtIsbn.getText();
                String numeroPaginas = txtNumeroPaginas.getText();
                String genero = txtGenero.getText();
                String idioma = (String) comboIdioma.getSelectedItem();
                String formatoDigital = (String) comboFormatoDigital.getSelectedItem();
                String url = txtUrl.getText();

                try {
                    AcervoService controller = new AcervoService();
                    controller.cadastrarEbook(titulo, autores, anoPublicacao, editora, isbn, numeroPaginas, genero, idioma, formatoDigital, url);
                    JOptionPane.showMessageDialog(JCadastroEbook.this, "O e-book foi cadastrado com sucesso em nosso sistema.", "Cadastro Concluído", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(JCadastroEbook.this, ne.getMessage(), "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                } catch (BusinessExcepition be) {
                    JOptionPane.showMessageDialog(JCadastroEbook.this, be.getMessage(), "Erro de Negócio", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- Botão Voltar ---
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(new Color(255, 140, 0));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnVoltar.setBounds(150, 470, 180, 40);
        add(btnVoltar);
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("GerenciarAcervoPanel");
                clearFields();
            }
        });
    }

    private void preencherFormulario(Ebook ebook) {
        txtTitulo.setText(ebook.getTitulo() != null ? ebook.getTitulo() : "");
        txtAutores.setText(ebook.getAutor() != null ? cleanString(ebook.getAutor()) : "");
        txtAnoPublicacao.setText(String.valueOf(ebook.getAnoPublicacao()));
        txtEditora.setText(ebook.getEditora() != null ? cleanString(ebook.getEditora()) : "");
        txtNumeroPaginas.setText(String.valueOf(ebook.getNumeroPaginas()));
        txtGenero.setText(ebook.getGenero() != null ? cleanString(ebook.getGenero()) : "");
    }

    private String cleanString(String input) {
        if (input == null || input.length() < 2) {
            return "";
        }
        return input.substring(1, input.length() - 1);
    }

    private void clearFields() {
        txtTitulo.setText("");
        txtAutores.setText("");
        txtAnoPublicacao.setText("");
        txtEditora.setText("");
        txtIsbn.setText("");
        txtNumeroPaginas.setText("");
        txtGenero.setText("");
        comboIdioma.setSelectedIndex(0);
        comboFormatoDigital.setSelectedIndex(0);
        txtUrl.setText("");
    }
}