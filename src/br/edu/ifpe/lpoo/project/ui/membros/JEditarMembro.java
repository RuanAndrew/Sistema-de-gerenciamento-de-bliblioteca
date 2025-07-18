package br.edu.ifpe.lpoo.project.ui.membros;

import br.edu.ifpe.lpoo.project.business.MembroService;
import br.edu.ifpe.lpoo.project.entities.membros.*;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.enums.TipoMembro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class JEditarMembro extends JFrame {

    private MembroService membroService;
    private Membro membroAtual;

    private JTextField txtCpfBusca;
    private JButton btnBuscar;

    private JTextField txtNome, txtCpf, txtEmail, txtMatricula;
    private JTextField txtCampoEspecifico1, txtCampoEspecifico2;
    private JLabel lblCampoEspecifico1, lblCampoEspecifico2;
    private JComboBox<StatusMembro> comboStatus;
    private JButton btnSalvar;
    private JPanel painelEdicao;

    public JEditarMembro() {
        this.membroService = new MembroService();
        initialize();
    }

    private void initialize() {
        setTitle("Editar Membro por CPF");
        setSize(650, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(248, 248, 255));

        JPanel painelBusca = new JPanel(null);
        painelBusca.setBorder(new TitledBorder("1. Buscar Membro"));
        painelBusca.setBounds(20, 20, 590, 80);
        add(painelBusca);

        JLabel lblCpfBusca = new JLabel("Digite o CPF do Membro:");
        lblCpfBusca.setBounds(20, 30, 150, 25);
        painelBusca.add(lblCpfBusca);

        txtCpfBusca = new JTextField();
        txtCpfBusca.setBounds(180, 30, 200, 25);
        painelBusca.add(txtCpfBusca);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(400, 30, 150, 25);
        btnBuscar.addActionListener(e -> buscarMembro());
        painelBusca.add(btnBuscar);

        painelEdicao = new JPanel(null);
        painelEdicao.setBorder(new TitledBorder("2. Editar Dados"));
        painelEdicao.setBounds(20, 110, 590, 430);
        add(painelEdicao);

        criarCamposDeEdicao(painelEdicao);
        habilitarCampos(false);
    }

    private void criarCamposDeEdicao(JPanel painel) {
        addLabelETextField(painel, "Nome:", 40, txtNome = new JTextField());
        addLabelETextField(painel, "CPF:", 75, txtCpf = new JTextField());
        txtCpf.setEditable(false);
        txtCpf.setBackground(new Color(233, 236, 239));

        addLabelETextField(painel, "Email:", 110, txtEmail = new JTextField());
        addLabelETextField(painel, "Matrícula:", 145, txtMatricula = new JTextField());

        lblCampoEspecifico1 = new JLabel();
        lblCampoEspecifico1.setBounds(40, 180, 120, 25);
        lblCampoEspecifico1.setHorizontalAlignment(SwingConstants.RIGHT);
        painel.add(lblCampoEspecifico1);
        txtCampoEspecifico1 = new JTextField();
        txtCampoEspecifico1.setBounds(170, 180, 350, 30);
        painel.add(txtCampoEspecifico1);
        
        lblCampoEspecifico2 = new JLabel();
        lblCampoEspecifico2.setBounds(40, 215, 120, 25);
        lblCampoEspecifico2.setHorizontalAlignment(SwingConstants.RIGHT);
        painel.add(lblCampoEspecifico2);
        txtCampoEspecifico2 = new JTextField();
        txtCampoEspecifico2.setBounds(170, 215, 350, 30);
        painel.add(txtCampoEspecifico2);
        
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(40, 250, 120, 25);
        lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
        painel.add(lblStatus);
        comboStatus = new JComboBox<>(StatusMembro.values());
        comboStatus.setBounds(170, 250, 350, 30);
        painel.add(comboStatus);

        btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setBackground(new Color(0, 123, 255));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setOpaque(true);
        btnSalvar.setBorderPainted(false);
        btnSalvar.setBounds(200, 350, 200, 40);
        btnSalvar.addActionListener(e -> salvarAlteracoes());
        painel.add(btnSalvar);
    }

    private void buscarMembro() {
        String cpfTexto = txtCpfBusca.getText();
        if (cpfTexto.isBlank()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite um CPF para buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            this.membroAtual = membroService.buscarmembroPorcpf(cpfTexto);

            if (membroAtual != null) {
                preencherCampos(membroAtual);
                habilitarCampos(true);
            } else {
                habilitarCampos(false);
                limparCampos();
                JOptionPane.showMessageDialog(this, "Nenhum membro encontrado com o CPF " + cpfTexto, "Não Encontrado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (BusinessException be) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar: " + be.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCampos(Membro membro) {
        txtNome.setText(membro.getNome());
        txtCpf.setText(membro.getCpf());
        txtEmail.setText(membro.getEmail());
        txtMatricula.setText(membro.getMatricula());
        comboStatus.setSelectedItem(membro.getStatusmembro());

        lblCampoEspecifico1.setVisible(false);
        txtCampoEspecifico1.setVisible(false);
        lblCampoEspecifico2.setVisible(false);
        txtCampoEspecifico2.setVisible(false);

        if (membro instanceof Aluno) {
            lblCampoEspecifico1.setText("Curso:");
            txtCampoEspecifico1.setText(((Aluno) membro).getCurso());
            lblCampoEspecifico1.setVisible(true);
            txtCampoEspecifico1.setVisible(true);
        } else if (membro instanceof Professor) {
            lblCampoEspecifico1.setText("Área de Atuação:");
            txtCampoEspecifico1.setText(((Professor) membro).getAreaAtuacao());
            lblCampoEspecifico1.setVisible(true);
            txtCampoEspecifico1.setVisible(true);

            lblCampoEspecifico2.setText("Departamento:");
            txtCampoEspecifico2.setText(((Professor) membro).getDepartamento());
            lblCampoEspecifico2.setVisible(true);
            txtCampoEspecifico2.setVisible(true);
        } else if (membro instanceof Pesquisador) {
            lblCampoEspecifico1.setText("Instituição:");
            txtCampoEspecifico1.setText(((Pesquisador) membro).getInstituicao());
            lblCampoEspecifico1.setVisible(true);
            txtCampoEspecifico1.setVisible(true);
        }
    }
    
    private void salvarAlteracoes() {
        if (membroAtual == null) {
            JOptionPane.showMessageDialog(this, "Nenhum membro carregado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String matricula = txtMatricula.getText();
            StatusMembro novoStatus = (StatusMembro) comboStatus.getSelectedItem();

            Aluno alunoAtualizado = null;
            Professor professorAtualizado = null;
            Pesquisador pesquisadorAtualizado = null;

            if (membroAtual instanceof Aluno) {
                String curso = txtCampoEspecifico1.getText();
                alunoAtualizado = new Aluno(nome, email, membroAtual.getCpf(), matricula, TipoMembro.ALUNO, 0, novoStatus, curso);
                alunoAtualizado.setId(membroAtual.getId());
            } else if (membroAtual instanceof Professor) {
                String area = txtCampoEspecifico1.getText();
                String depto = txtCampoEspecifico2.getText();
                professorAtualizado = new Professor(nome, email, membroAtual.getCpf(), matricula, TipoMembro.PROFESSOR, 0, novoStatus, area, depto);
                professorAtualizado.setId(membroAtual.getId());
            } else if (membroAtual instanceof Pesquisador) {
                String instituicao = txtCampoEspecifico1.getText();
                pesquisadorAtualizado = new Pesquisador(nome, email, membroAtual.getCpf(), matricula, TipoMembro.PESQUISADOR, 0, novoStatus, instituicao);
                pesquisadorAtualizado.setId(membroAtual.getId());
            }
            
            membroService.atualizarMembro(alunoAtualizado, novoStatus);

            JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            habilitarCampos(false);
            limparCampos();
            txtCpfBusca.setText("");
            this.membroAtual = null;

        } catch (BusinessException be) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + be.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void habilitarCampos(boolean habilitar) {
        for (Component comp : painelEdicao.getComponents()) {
            if (comp instanceof JTextField || comp instanceof JComboBox) {
                comp.setEnabled(habilitar);
            }
        }
        txtCpf.setEnabled(false);
        btnSalvar.setEnabled(habilitar);
    }
    
    private void limparCampos() {
        for (Component comp : painelEdicao.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText("");
            }
        }
    }

    private void addLabelETextField(JPanel painel, String textoLabel, int yPos, JTextField textField) {
        JLabel label = new JLabel(textoLabel);
        label.setBounds(40, yPos, 120, 25);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        painel.add(label);

        textField.setBounds(170, yPos, 350, 30);
        painel.add(textField);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JEditarMembro().setVisible(true));
    }
}
