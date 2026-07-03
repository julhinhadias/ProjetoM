package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.CidadeController;
import model.Cidade;

public class TelaCidade extends JFrame {

    private CidadeController cidadeController;

    private JTextField campoId;
    private JTextField campoNome;
    private JTextField campoEstado;
    private JTextField campoDistancia;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaCidade(CidadeController cidadeController) {

        this.cidadeController = cidadeController;

        configurarJanela();
        montarTela();
        atualizarTabela();
    }

    private void configurarJanela() {

        setTitle("Gerenciamento de Cidades");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void montarTela() {

        JPanel painelFormulario =
                new JPanel(new GridLayout(4, 2, 10, 10));

        campoId = new JTextField();
        campoNome = new JTextField();
        campoEstado = new JTextField();
        campoDistancia = new JTextField();

        painelFormulario.add(new JLabel("ID:"));
        painelFormulario.add(campoId);

        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(campoNome);

        painelFormulario.add(new JLabel("Estado:"));
        painelFormulario.add(campoEstado);

        painelFormulario.add(new JLabel("Distância da Capital:"));
        painelFormulario.add(campoDistancia);

        JPanel painelBotoes =
                new JPanel(new GridLayout(1, 5, 10, 10));

        JButton botaoCadastrar =
                new JButton("Cadastrar");

        JButton botaoAtualizar =
                new JButton("Atualizar");

        JButton botaoExcluir =
                new JButton("Excluir");

        JButton botaoLimpar =
                new JButton("Limpar");

        JButton botaoVoltar =
                new JButton("Voltar");

        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoAtualizar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoLimpar);
        painelBotoes.add(botaoVoltar);

        modeloTabela =
                new DefaultTableModel();

        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Estado");
        modeloTabela.addColumn("Distância da Capital");

        tabela =
                new JTable(modeloTabela);

        JScrollPane painelTabela =
                new JScrollPane(tabela);

        JPanel painelSuperior =
                new JPanel(new BorderLayout());

        painelSuperior.add(painelFormulario, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        add(painelSuperior, BorderLayout.NORTH);
        add(painelTabela, BorderLayout.CENTER);

        botaoCadastrar.addActionListener(e -> cadastrarCidade());

        botaoAtualizar.addActionListener(e -> atualizarCidade());

        botaoExcluir.addActionListener(e -> excluirCidade());

        botaoLimpar.addActionListener(e -> limparCampos());

        botaoVoltar.addActionListener(e -> dispose());

        tabela.getSelectionModel().addListSelectionListener(e -> preencherCampos());

        TemaRosa.aplicar(getContentPane());
        TemaRosa.aplicarTabela(tabela);
    }

    private void cadastrarCidade() {

        try {

            int id =
                    converterInt(campoId.getText());

            String nome =
                    campoNome.getText().trim();

            String estado =
                    campoEstado.getText().trim();

            double distancia =
                    converterDouble(campoDistancia.getText());

            if (nome.isEmpty() || estado.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos."
                );

                return;
            }

            if (cidadeController.buscarPorId(id) != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Já existe uma cidade com esse ID."
                );

                return;
            }

            Cidade cidade =
                    new Cidade(id, nome, estado, distancia);

            cidadeController.cadastrar(cidade);

            JOptionPane.showMessageDialog(
                    this,
                    "Cidade cadastrada com sucesso."
            );

            limparCampos();
            atualizarTabela();

        } catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID e distância devem ser números válidos."
            );
        }
    }

    private void atualizarCidade() {

        try {

            int id =
                    converterInt(campoId.getText());

            String nome =
                    campoNome.getText().trim();

            String estado =
                    campoEstado.getText().trim();

            double distancia =
                    converterDouble(campoDistancia.getText());

            if (nome.isEmpty() || estado.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos."
                );

                return;
            }

            Cidade cidade =
                    new Cidade(id, nome, estado, distancia);

            boolean atualizou =
                    cidadeController.atualizar(id, cidade);

            if (atualizou) {

                JOptionPane.showMessageDialog(
                        this,
                        "Cidade atualizada com sucesso."
                );

                limparCampos();
                atualizarTabela();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Cidade não encontrada."
                );
            }

        } catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID e distância devem ser números válidos."
            );
        }
    }

    private void excluirCidade() {

        try {

            int id =
                    converterInt(campoId.getText());

            int confirmacao =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Deseja realmente excluir esta cidade?",
                            "Confirmar exclusão",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirmacao == JOptionPane.YES_OPTION) {

                boolean excluiu =
                        cidadeController.excluir(id);

                if (excluiu) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Cidade excluída com sucesso."
                    );

                    limparCampos();
                    atualizarTabela();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Cidade não encontrada."
                    );
                }
            }

        } catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe um ID válido."
            );
        }
    }

    private void atualizarTabela() {

        modeloTabela.setRowCount(0);

        ArrayList<Cidade> cidades =
                cidadeController.listar();

        for (int i = 0; i < cidades.size(); i++) {

            Cidade cidade =
                    cidades.get(i);

            modeloTabela.addRow(
                    new Object[]{
                            cidade.getId(),
                            cidade.getNome(),
                            cidade.getEstado(),
                            cidade.getDistanciaDaCapital()
                    }
            );
        }
    }

    private void preencherCampos() {

        int linhaSelecionada =
                tabela.getSelectedRow();

        if (linhaSelecionada >= 0) {

            campoId.setText(
                    modeloTabela.getValueAt(linhaSelecionada, 0).toString()
            );

            campoNome.setText(
                    modeloTabela.getValueAt(linhaSelecionada, 1).toString()
            );

            campoEstado.setText(
                    modeloTabela.getValueAt(linhaSelecionada, 2).toString()
            );

            campoDistancia.setText(
                    modeloTabela.getValueAt(linhaSelecionada, 3).toString()
            );
        }
    }

    private void limparCampos() {

        campoId.setText("");
        campoNome.setText("");
        campoEstado.setText("");
        campoDistancia.setText("");

        tabela.clearSelection();
    }

    private double converterDouble(String texto) {

        return Double.parseDouble(
                texto.trim().replace(",", ".")
        );
    }

    private int converterInt(String texto) {

        return Integer.parseInt(
                texto.trim()
        );
    }
}