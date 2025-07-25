package br.edu.ifpe.lpoo.project.ui.acervo;

import br.edu.ifpe.lpoo.project.business.AcervoService;
import br.edu.ifpe.lpoo.project.exceptions.BusinessException;
import br.edu.ifpe.lpoo.project.ui.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JCadastroPeriodico extends JPanel {

    private MainFrame mainFrame;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtIssn;
    private JTextField txtEditora;
    private JTextField txtNumeroEdicao;
    private JTextField txtVolume;
    private JTextField txtAnoPublicacao;
    private JTextField txtGenero;
    private JTextField txtQuantidadeExemplares;
    private JComboBox<String> comboIdioma;

    public JCadastroPeriodico(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initialize();
    }

    private void initialize() {
        setLayout(null);
        setBackground(new Color(248, 248, 255));

        // Título da tela de cadastro
        JLabel titleLabel = new JLabel("Cadastro de Periódico");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(200, 30, 280, 30);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        // --- Título ---
        JLabel tituloLabel = new JLabel("Título:");
        tituloLabel.setBounds(80, 95, 140, 25);
        tituloLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(tituloLabel);
        txtTitulo = new JTextField();
        txtTitulo.setBounds(250, 95, 300, 30);
        add(txtTitulo);

        // --- Autor ---
        JLabel autorLabel = new JLabel("Autor:");
        autorLabel.setBounds(80, 130, 140, 25);
        autorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(autorLabel);
        txtAutor = new JTextField();
        txtAutor.setBounds(250, 130, 300, 30);
        add(txtAutor);

        // --- ISSN ---
        JLabel issnLabel = new JLabel("ISSN:");
        issnLabel.setBounds(80, 165, 140, 25);
        issnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(issnLabel);
        txtIssn = new JTextField();
        txtIssn.setBounds(250, 165, 300, 30);
        add(txtIssn);

        // --- Editora ---
        JLabel editoraLabel = new JLabel("Editora:");
        editoraLabel.setBounds(80, 200, 140, 25);
        editoraLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(editoraLabel);
        txtEditora = new JTextField();
        txtEditora.setBounds(250, 200, 300, 30);
        add(txtEditora);

        // --- Número da Edição ---
        JLabel numeroEdicaoLabel = new JLabel("Número da Edição:");
        numeroEdicaoLabel.setBounds(80, 235, 140, 25);
        numeroEdicaoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(numeroEdicaoLabel);
        txtNumeroEdicao = new JTextField();
        txtNumeroEdicao.setBounds(250, 235, 300, 30);
        add(txtNumeroEdicao);

        // --- Volume ---
        JLabel volumeLabel = new JLabel("Volume:");
        volumeLabel.setBounds(80, 270, 140, 25);
        volumeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(volumeLabel);
        txtVolume = new JTextField();
        txtVolume.setBounds(250, 270, 300, 30);
        add(txtVolume);

        // --- Ano de Publicação ---
        JLabel anoPublicacaoLabel = new JLabel("Ano de Publicação:");
        anoPublicacaoLabel.setBounds(80, 305, 140, 25);
        anoPublicacaoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(anoPublicacaoLabel);
        txtAnoPublicacao = new JTextField();
        txtAnoPublicacao.setBounds(250, 305, 300, 30);
        add(txtAnoPublicacao);

        // --- Gênero ---
        JLabel generoLabel = new JLabel("Gênero:");
        generoLabel.setBounds(80, 340, 140, 25);
        generoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(generoLabel);
        txtGenero = new JTextField();
        txtGenero.setBounds(250, 340, 300, 30);
        add(txtGenero);

        // --- Quantidade Exemplares ---
        JLabel quantidadeExemplaresLabel = new JLabel("Quantidade Exemplares:");
        quantidadeExemplaresLabel.setBounds(70, 375, 150, 25);
        quantidadeExemplaresLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(quantidadeExemplaresLabel);
        txtQuantidadeExemplares = new JTextField();
        txtQuantidadeExemplares.setBounds(250, 375, 300, 30);
        add(txtQuantidadeExemplares);

        // --- Idioma ---
        JLabel idiomaLabel = new JLabel("Idioma:");
        idiomaLabel.setBounds(80, 410, 140, 25);
        idiomaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(idiomaLabel);

        String[] idiomas = {"Português", "Inglês", "Espanhol", "Francês", "Alemão"};
        comboIdioma = new JComboBox<>(idiomas);
        comboIdioma.setBounds(250, 410, 300, 30);
        add(comboIdioma);

        // --- Botão Cadastrar ---
        JButton btnCadastrar = new JButton("Cadastrar Periódico");
        btnCadastrar.setBackground(new Color(60, 179, 113));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCadastrar.setBounds(350, 455, 180, 40);
        add(btnCadastrar);
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (txtTitulo.getText().trim().isEmpty() ||
                        txtAutor.getText().trim().isEmpty() ||
                        txtIssn.getText().trim().isEmpty() ||
                        txtEditora.getText().trim().isEmpty() ||
                        txtNumeroEdicao.getText().trim().isEmpty() ||
                        txtVolume.getText().trim().isEmpty() ||
                        txtAnoPublicacao.getText().trim().isEmpty() ||
                        txtGenero.getText().trim().isEmpty() ||
                        txtQuantidadeExemplares.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(JCadastroPeriodico.this,
                            "Por favor, preencha todos os campos antes de cadastrar.",
                            "Campos Vazios",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String titulo = txtTitulo.getText();
                String autor = txtAutor.getText();
                String issn = txtIssn.getText();
                String editora = txtEditora.getText();
                String numeroEdicao = txtNumeroEdicao.getText();
                String volume = txtVolume.getText();
                String anoPublicacao = txtAnoPublicacao.getText();
                String genero = txtGenero.getText();
                String idioma = (String) comboIdioma.getSelectedItem();
                String quantidadeExemplares = txtQuantidadeExemplares.getText();

                try {
                    AcervoService controller = new AcervoService();
                    controller.cadastrarPeriodico(titulo, autor, anoPublicacao, issn, editora, numeroEdicao, volume, genero, idioma,quantidadeExemplares);
                    JOptionPane.showMessageDialog(JCadastroPeriodico.this, "O periódico foi cadastrado com sucesso em nosso sistema.", "Cadastro Concluído", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(JCadastroPeriodico.this, ne.getMessage(), "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                } catch (BusinessException be) {
                    JOptionPane.showMessageDialog(JCadastroPeriodico.this, be.getMessage(), "Erro de Negócio", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- Botão Voltar ---
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(new Color(255, 140, 0));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnVoltar.setBounds(150, 455, 180, 40);
        add(btnVoltar);
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("GerenciarAcervoPanel");
                clearFields();
            }
        });
    }

    private void clearFields() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtIssn.setText("");
        txtEditora.setText("");
        txtNumeroEdicao.setText("");
        txtVolume.setText("");
        txtAnoPublicacao.setText("");
        txtGenero.setText("");
        txtQuantidadeExemplares.setText("");
        comboIdioma.setSelectedIndex(0);
    }
}