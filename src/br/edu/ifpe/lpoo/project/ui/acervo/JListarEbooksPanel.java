package br.edu.ifpe.lpoo.project.ui.acervo;

import br.edu.ifpe.lpoo.project.business.AcervoService;
import br.edu.ifpe.lpoo.project.entities.acervo.Ebook;
import br.edu.ifpe.lpoo.project.entities.acervo.ItemAcervo;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.ui.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class JListarEbooksPanel extends JPanel {

    private MainFrame mainFrame;
    private JTable table;
    private DefaultTableModel tableModel;
    private AcervoService acervoService;

    public JListarEbooksPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.acervoService = new AcervoService();
        initialize();
        carregarEbooks();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                carregarEbooks();
            }
        });
    }

    private void initialize() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 255));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título do Painel
        JLabel titleLabel = new JLabel("Listagem de E-books");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Modelo da Tabela
        String[] columnNames = {"ID", "Título", "Autor(es)", "Ano", "Formato", "ISBN"};
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
        table.getColumnModel().getColumn(1).setPreferredWidth(250); // Título
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Autor(es)
        table.getColumnModel().getColumn(3).setPreferredWidth(60);  // Ano
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Formato
        table.getColumnModel().getColumn(5).setPreferredWidth(200); // ISBN

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

    private void carregarEbooks() {
        tableModel.setRowCount(0);

        try {
            List<ItemAcervo> todosItens = acervoService.listarTodosItens();
            for (ItemAcervo item : todosItens) {
                if (item instanceof Ebook) {
                    Ebook ebook = (Ebook) item;
                    Object[] rowData = {
                            ebook.getId(),
                            ebook.getTitulo(),
                            ebook.getAutor(),
                            ebook.getAnoPublicacao(),
                            ebook.getFormatoDigital(),
                            ebook.getIsbn()
                    };
                    tableModel.addRow(rowData);
                }
            }
        } catch (BusinessExcepition e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar e-books: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}