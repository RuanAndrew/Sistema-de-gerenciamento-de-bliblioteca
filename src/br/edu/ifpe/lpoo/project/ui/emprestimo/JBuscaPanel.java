package br.edu.ifpe.lpoo.project.ui.emprestimo;

import br.edu.ifpe.lpoo.project.business.AcervoService;
import br.edu.ifpe.lpoo.project.entities.acervo.Exemplar;
import br.edu.ifpe.lpoo.project.exceptions.BusinessException;
import br.edu.ifpe.lpoo.project.ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JBuscaPanel extends JPanel {

    private MainFrame mainFrame;
    private AcervoService acervoService;

    private JTextField txtBusca;
    private JList<String> listaResultados;
    private DefaultListModel<String> listModel;

    public JBuscaPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.acervoService = new AcervoService();
        initialize();
    }

    private void initialize() {
        setLayout(null);
        setBackground(new Color(248, 248, 255));

        JLabel lblTitulo = new JLabel("Busca de Exemplares");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 20, 600, 30);
        add(lblTitulo);

        JLabel lblTermo = new JLabel("Nome do Livro:");
        lblTermo.setBounds(50, 80, 100, 25);
        add(lblTermo);

        txtBusca = new JTextField();
        txtBusca.setBounds(160, 80, 280, 30);
        add(txtBusca);

        // Botão de Pesquisar
        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(450, 80, 100, 30);
        btnPesquisar.setBackground(new Color(59, 130, 246));
        btnPesquisar.setForeground(Color.WHITE);
        add(btnPesquisar);

        // Lista para os resultados
        listModel = new DefaultListModel<>();
        listaResultados = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listaResultados);
        scrollPane.setBounds(50, 130, 500, 200);
        add(scrollPane);

        // Botões de Ação
        JButton btnRealizarEmprestimo = new JButton("Realizar Empréstimo");
        btnRealizarEmprestimo.setBounds(310, 350, 240, 40);
        btnRealizarEmprestimo.setBackground(new Color(16, 185, 129));
        btnRealizarEmprestimo.setForeground(Color.WHITE);
        add(btnRealizarEmprestimo);

        JButton btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setBounds(50, 350, 240, 40);
        btnVoltar.setBackground(new Color(255, 140, 0));
        btnVoltar.setForeground(Color.WHITE);
        add(btnVoltar);

        // --- Lógica dos botões ---

        btnPesquisar.addActionListener(e -> {
            String termo = txtBusca.getText().trim();
            listModel.clear();

            if (termo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite algo para pesquisar!");
                return;
            }

            try {
                List<Exemplar> resultados = acervoService.listarExemplaresPorTermo(termo);
                if (resultados.isEmpty()) {
                    listModel.addElement("Nenhum exemplar encontrado.");
                } else {
                    for (Exemplar ex : resultados) {
                        listModel.addElement("ID: " + ex.getIdExemplar() + "| Status: " + ex.getStatus() + "| Tipo: " + ex.getTipoItemAcervo());
                    }
                }
            } catch (BusinessException be) {
                JOptionPane.showMessageDialog(this, "Erro na busca: " + be.getMessage());
            }
        });

        btnRealizarEmprestimo.addActionListener(e -> {
                mainFrame.showPanel("RealizarEmprestimo");
        });

        btnVoltar.addActionListener(e -> {
            txtBusca.setText("");
            listModel.clear();
            mainFrame.showPanel("TelaPrincipal");
        });
    }
}