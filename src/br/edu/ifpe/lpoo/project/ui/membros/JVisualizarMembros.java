package br.edu.ifpe.lpoo.project.ui.membros;

import br.edu.ifpe.lpoo.project.business.MembroService;
import br.edu.ifpe.lpoo.project.entities.membros.Membro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Tela para visualizar todos os membros cadastrados no sistema,
 * utilizando o MembroService para buscar os dados.
 */
public class JVisualizarMembros extends JFrame {

    private JTable tabelaMembros;
    private DefaultTableModel tableModel;
    private final MembroService membroService;

    /**
     * Construtor da classe.
     */
    public JVisualizarMembros() {
        // Instancia o serviço que será usado para buscar os dados
        this.membroService = new MembroService();
        
        // Monta a interface gráfica
        initialize();
        
        // Carrega os dados na tabela assim que a janela é criada
        carregarMembros();
    }

    /**
     * Inicializa o conteúdo da janela (frame).
     */
    private void initialize() {
        // --- Configuração da Janela ---
        setTitle("Visualização de Membros");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // --- Painel Principal com Layout e Margens ---
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(painelPrincipal);

        // --- Título da Tela ---
        JLabel lblTitulo = new JLabel("Membros Cadastrados", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // --- Tabela de Membros ---
        // Define as colunas da tabela
        String[] colunas = {"ID", "Nome", "CPF", "Matrícula", "Tipo", "Status"};
        
        // Cria o modelo da tabela, tornando as células não editáveis
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
        
        // Adiciona a tabela a um painel com barra de rolagem
        JScrollPane scrollPane = new JScrollPane(tabelaMembros);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // --- Painel de Botões ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        
        // Botão Atualizar
        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener(e -> carregarMembros());
        painelBotoes.add(btnAtualizar);

        // Botão Voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(new Color(108, 117, 125));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setOpaque(true);
        btnVoltar.setBorderPainted(false);
        btnVoltar.addActionListener(e -> this.dispose());
        painelBotoes.add(btnVoltar);
        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
    }

    /**
     * Busca os dados no MembroService e popula a tabela.
     */
    private void carregarMembros() {
        // Limpa a tabela antes de carregar novos dados para evitar duplicatas
        tableModel.setRowCount(0);

        try {
            // Chama o método do serviço para obter a lista de todos os membros
            List<Membro> membros = membroService.listarTodosItens();
            
            if (membros.isEmpty()) {
                // Informa ao usuário se não houver membros
                JOptionPane.showMessageDialog(this, "Nenhum membro cadastrado no sistema.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Itera sobre a lista e adiciona cada membro como uma nova linha na tabela
                for (Membro membro : membros) {
                    Object[] rowData = {
                        membro.getId(), // Assumindo que a entidade Membro tem um método getId()
                        membro.getNome(),
                        membro.getCpf(),
                        membro.getMatricula(),
                        membro.getStatusmembro().toString(),
                        membro.getStatusmembro().toString()
                    };
                    tableModel.addRow(rowData);
                }
            }
        } catch (BusinessExcepition be) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar membros: " + be.getMessage(), "Erro de Negócio", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}