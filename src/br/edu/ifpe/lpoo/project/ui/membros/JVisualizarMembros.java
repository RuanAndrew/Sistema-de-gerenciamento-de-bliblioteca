package br.edu.ifpe.lpoo.project.ui.membros;

import br.edu.ifpe.lpoo.project.business.MembroService;
import br.edu.ifpe.lpoo.project.entities.membros.Membro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessException;
import br.edu.ifpe.lpoo.project.ui.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class JVisualizarMembros extends JPanel {

    private MainFrame mainFrame;
    private JTable tabelaMembros;
    private DefaultTableModel tableModel;
    private final MembroService membroService;

    public JVisualizarMembros(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.membroService = new MembroService();
        initialize();
        carregarMembros();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                carregarMembros();
            }
        });
    }

    private void initialize() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(248, 248, 255));

        JLabel lblTitulo = new JLabel("Gerenciamento de Membros", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lblTitulo, BorderLayout.NORTH);

        // --- Tabela de Membros ---
        String[] colunas = {"ID", "Nome", "CPF", "Matrícula", "Tipo", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaMembros = new JTable(tableModel);
        tabelaMembros.setFillsViewportHeight(true);
        tabelaMembros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaMembros.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabelaMembros.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(tabelaMembros);
        add(scrollPane, BorderLayout.CENTER);

        // --- Painel de Botões ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        // Botão Voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(new Color(255, 140, 0));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setOpaque(true);
        btnVoltar.setBorderPainted(false);
        btnVoltar.addActionListener(e -> mainFrame.showPanel("GerenciarMembrosPanel"));
        painelBotoes.add(btnVoltar);
        
        add(painelBotoes, BorderLayout.SOUTH);
    }


    private void carregarMembros() {
        tableModel.setRowCount(0);
        try {
            List<Membro> membros = membroService.listarTodosItens();
            for (Membro membro : membros) {
                Object[] rowData = {
                    membro.getId(),
                    membro.getNome(),
                    membro.getCpf(),
                    membro.getMatricula(),
                    membro.getTipomembro().toString(),
                    membro.getStatusmembro().toString()
                };
                tableModel.addRow(rowData);
            }
        } catch (BusinessException be) {
            JOptionPane.showMessageDialog(mainFrame, "Erro ao carregar membros: " + be.getMessage(), "Erro de Negócio", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainFrame, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    private void excluirMembroSelecionado() {
        int selectedRow = tabelaMembros.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Por favor, selecione um membro na tabela para excluir.", "Nenhum Membro Selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }


        Integer cpf = (Integer) tableModel.getValueAt(selectedRow, 2);
        String nomeParaExibir = (String) tableModel.getValueAt(selectedRow, 1);
        TipoMembro tipoMembro = (TipoMembro) tableModel.getValueAt(selectedRow, 4);

        int resposta = JOptionPane.showConfirmDialog(
            mainFrame,
            "Tem a certeza de que deseja excluir o membro '" + nomeParaExibir + "' (CPF: " + cpf + ")?\nEsta ação não pode ser desfeita.",
            "Confirmação de Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (resposta == JOptionPane.YES_OPTION) {
            try {
                membroService.excluirMembro(cpf, tipoMembro);
                JOptionPane.showMessageDialog(mainFrame, "Membro excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarMembros();
            } catch (BusinessException be) {
                carregarMembros(); // Atualiza a tabela para remover a linha
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}

