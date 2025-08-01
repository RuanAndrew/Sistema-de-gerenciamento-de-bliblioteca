package br.edu.ifpe.lpoo.project.ui.acervo;

import br.edu.ifpe.lpoo.project.business.AcervoService;
import br.edu.ifpe.lpoo.project.entities.acervo.ItemAcervo;
import br.edu.ifpe.lpoo.project.entities.acervo.Livro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessException;
import br.edu.ifpe.lpoo.project.ui.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                carregarLivros();
            }
        });
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
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int linhaSelecionada = table.getSelectedRow();
                    if (linhaSelecionada != -1) {
                        int idLivro = (int) tableModel.getValueAt(linhaSelecionada, 0);
                        mainFrame.mostrarDetalhesLivro(idLivro);
                    }
                }
            }
        });
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        // Define a largura das colunas
        table.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(250); // Título
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Autor
        table.getColumnModel().getColumn(3).setPreferredWidth(60);  // Ano
        table.getColumnModel().getColumn(4).setPreferredWidth(200); // ISBN

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Botão de Voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(new Color(255, 140, 0));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.addActionListener(e -> mainFrame.showPanel("GerenciarAcervoPanel"));

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
        } catch (BusinessException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar livros: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}