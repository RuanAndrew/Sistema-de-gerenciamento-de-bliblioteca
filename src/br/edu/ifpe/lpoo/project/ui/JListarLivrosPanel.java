package br.edu.ifpe.lpoo.project.ui;

import br.edu.ifpe.lpoo.project.business.AcervoService;
import br.edu.ifpe.lpoo.project.entities.acervo.ItemAcervo;
import br.edu.ifpe.lpoo.project.entities.acervo.Livro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class JListarLivrosPanel extends JPanel {

    private MainFrame mainFrame;
    private JTable table;
    private DefaultTableModel tableModel;
    private AcervoService acervoService;

    public JListarLivrosPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.acervoService = new AcervoService();
        initialize();
        carregarLivros();
    }

    private void initialize() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 255));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título do Painel
        JLabel titleLabel = new JLabel("Listagem de Livros");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);


        String[] columnNames = {"ID", "Título", "Autor", "Ano", "ISBN"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        // Define a largura das colunas
        table.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(300); // Título
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Autor
        table.getColumnModel().getColumn(3).setPreferredWidth(60);  // Ano
        table.getColumnModel().getColumn(4).setPreferredWidth(150); // ISBN

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Botão de Voltar
        JButton btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setBackground(new Color(255, 140, 0));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.addActionListener(e -> mainFrame.showPanel("TelaPrincipal"));

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.setBackground(new Color(240, 248, 255));
        southPanel.add(btnVoltar);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void carregarLivros() {
        // Limpa a tabela
        tableModel.setRowCount(0);

        try {
            List<ItemAcervo> todosItens = acervoService.listarTodosItens();
            for (ItemAcervo item : todosItens) {
                if (item instanceof Livro) {
                    Livro livro = (Livro) item;
                    Object[] rowData = {
                            livro.getId(),
                            livro.getTitulo(),
                            livro.getAutor(),
                            livro.getAnoPublicacao(),
                            livro.getIsbn()
                    };
                    tableModel.addRow(rowData);
                }
            }
        } catch (BusinessExcepition e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar livros: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}