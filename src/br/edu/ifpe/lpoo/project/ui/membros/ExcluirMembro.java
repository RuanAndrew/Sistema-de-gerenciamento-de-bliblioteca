package br.edu.ifpe.lpoo.project.ui.membros;

import br.edu.ifpe.lpoo.project.business.MembroService;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;


public class ExcluirMembro extends JFrame {

    private JTextField campoCpfExcluir;
    private JButton botaoExcluir;

    private MembroService membroService;

    public ExcluirMembro() {
        this.membroService = new MembroService();

        setTitle("Excluir Membro da Biblioteca");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 180);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(painelPrincipal);

        JPanel painelEntrada = criarPainelEntrada();
        painelPrincipal.add(painelEntrada, BorderLayout.CENTER);

        JPanel painelBotoes = criarPainelBotoes();
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
    }


    private JPanel criarPainelEntrada() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new TitledBorder("Insira o ID do Membro a ser Excluído"));

        JLabel labelId = new JLabel("ID do Membro:");
        campoCpfExcluir = new JTextField();

        painel.add(labelId, BorderLayout.WEST);
        painel.add(campoCpfExcluir, BorderLayout.CENTER);

        return painel;
    }


    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botaoExcluir = new JButton("Excluir Membro");
        botaoExcluir.setFont(new Font("SansSerif", Font.BOLD, 14));
        botaoExcluir.setForeground(new Color(211, 47, 47)); // Cor vermelha para indicar perigo

        botaoExcluir.addActionListener(this::realizarExclusao);

        painel.add(botaoExcluir);
        return painel;
    }



    private void realizarExclusao(ActionEvent e) {
        String cpfTexto = campoCpfExcluir.getText();
        if (cpfTexto == null || cpfTexto.isBlank()) {
            JOptionPane.showMessageDialog(this, "O campo ID não pode estar vazio.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int resposta = JOptionPane.showConfirmDialog(
                this,
                "Tem a certeza de que deseja excluir o membro com o ID " + cpfTexto + "?\nEsta ação não pode ser desfeita.",
                "Confirmação de Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (resposta == JOptionPane.YES_OPTION) {
                int cpf = Integer.parseInt(cpfTexto);
                
                membroService.excluirMembro(cpf);

                JOptionPane.showMessageDialog(this, "Membro com ID " + cpf + " excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                campoCpfExcluir.setText("");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, insira apenas números.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (BusinessExcepition ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir membro: " + ex.getMessage(), "Erro de Negócio", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new ExcluirMembro().setVisible(true);
        });
    }
}
