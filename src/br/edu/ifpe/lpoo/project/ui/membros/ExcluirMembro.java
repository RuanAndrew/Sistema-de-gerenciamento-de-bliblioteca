package br.edu.ifpe.lpoo.project.ui.membros;

import br.edu.ifpe.lpoo.project.business.MembroService;
import br.edu.ifpe.lpoo.project.entities.membros.Membro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ExcluirMembro extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private MembroService membroService;

    public ExcluirMembro() {
        setTitle("Gerenciar Membros da Biblioteca");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        membroService = new MembroService();

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Email", "Tipo"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);

        JButton botaoExcluir = new JButton("Excluir Membro Selecionado");
        botaoExcluir.addActionListener(e -> excluirMembroSelecionado());

        add(scrollPane, BorderLayout.CENTER);
        add(botaoExcluir, BorderLayout.SOUTH);

        carregarMembros();
    }

    private void carregarMembros() {
        modeloTabela.setRowCount(0);
        try {
            List<Membro> membros = membroService.listarTodosItens();
            for (Membro m : membros) {
                modeloTabela.addRow(new Object[]{
                    m.getId(), m.getNome(), m.getCpf(), m.getEmail(), m.getTipomembro()
                });
            }
        } catch (BusinessExcepition e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar membros: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirMembroSelecionado() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada != -1) {
            int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir o membro de ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    membroService.excluirMembro(id);
                    carregarMembros(); // Atualiza a tabela
                    JOptionPane.showMessageDialog(this, "Membro excluído com sucesso.");
                } catch (BusinessExcepition e) {
                    JOptionPane.showMessageDialog(this,
                            "Erro ao excluir membro: " + e.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um membro para excluir.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExcluirMembro().setVisible(true));
    }
}
