package br.edu.ifpe.lpoo.project.ui.emprestimo;

import br.edu.ifpe.lpoo.project.business.EmprestimoService;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.ui.MainFrame;

import javax.swing.*;
import java.awt.*;

public class JRealizarEmprestimoPanel extends JPanel {

    private MainFrame mainFrame;
    private EmprestimoService emprestimoService;

    private JTextField txtIdExemplar;
    private JTextField txtCpfMembro;
    private JComboBox<TipoMembro> comboTipoMembro;

    public JRealizarEmprestimoPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.emprestimoService = new EmprestimoService();
        initialize();
    }

    private void initialize() {
        setLayout(null);
        setBackground(new Color(248, 248, 255));

        // Título
        JLabel titleLabel = new JLabel("Realizar Novo Empréstimo");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(0, 30, 650, 30);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        // ID do Exemplar
        JLabel lblIdExemplar = new JLabel("ID do Exemplar:");
        lblIdExemplar.setBounds(80, 120, 150, 25);
        lblIdExemplar.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lblIdExemplar);
        txtIdExemplar = new JTextField();
        txtIdExemplar.setBounds(240, 120, 300, 30);
        add(txtIdExemplar);

        // CPF do Membro
        JLabel lblCpfMembro = new JLabel("CPF do Membro:");
        lblCpfMembro.setBounds(80, 160, 150, 25);
        lblCpfMembro.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lblCpfMembro);
        txtCpfMembro = new JTextField();
        txtCpfMembro.setBounds(240, 160, 300, 30);
        add(txtCpfMembro);

        // Tipo de Membro
        JLabel lblTipoMembro = new JLabel("Tipo de Membro:");
        lblTipoMembro.setBounds(80, 200, 150, 25);
        lblTipoMembro.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lblTipoMembro);
        comboTipoMembro = new JComboBox<>(TipoMembro.values());
        comboTipoMembro.setBounds(240, 200, 300, 30);
        add(comboTipoMembro);

        // Botão Emprestar
        JButton btnEmprestar = new JButton("Realizar Empréstimo");
        btnEmprestar.setBackground(new Color(60, 179, 113));
        btnEmprestar.setForeground(Color.WHITE);
        btnEmprestar.setBounds(150, 290, 200, 40);
        add(btnEmprestar);
        btnEmprestar.addActionListener(e -> executarEmprestimo());

        // Botão Voltar
        JButton btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setBackground(new Color(255, 140, 0));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBounds(370, 290, 200, 40);
        add(btnVoltar);
        btnVoltar.addActionListener(e -> {
            clearFields();
            mainFrame.showPanel("TelaPrincipal");
        });
    }

    private void executarEmprestimo() {
        String idExemplarStr = txtIdExemplar.getText().trim();
        String cpfMembro = txtCpfMembro.getText().trim();

        if (idExemplarStr.isEmpty() || cpfMembro.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idExemplar = Integer.parseInt(idExemplarStr);
            TipoMembro tipoMembro = (TipoMembro) comboTipoMembro.getSelectedItem();

            emprestimoService.emprestar(idExemplar, cpfMembro, tipoMembro);

            JOptionPane.showMessageDialog(this, "Empréstimo realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            clearFields();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O ID do Exemplar deve ser um número.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (BusinessExcepition | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Negócio", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtIdExemplar.setText("");
        txtCpfMembro.setText("");
        comboTipoMembro.setSelectedIndex(0);
        txtIdExemplar.requestFocusInWindow();
    }
}