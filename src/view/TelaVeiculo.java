package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.VeiculoController;
import model.Veiculo;
import model.VeiculoEletrico;
import model.VeiculoHibrido;

public class TelaVeiculo extends JFrame {

    private VeiculoController veiculoController;

    private JComboBox<String> comboTipo;

    private JTextField campoId;
    private JTextField campoModelo;
    private JTextField campoAutonomia;
    private JTextField campoBateria;
    private JTextField campoConsumoKwh;
    private JTextField campoTempoRecarga;

    private JTextField campoConector;
    private JTextField campoTempoRapido;

    private JTextField campoTanque;
    private JTextField campoConsumoCombustivel;
    private JTextField campoCombustivel;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaVeiculo(VeiculoController veiculoController) {

        this.veiculoController = veiculoController;

        configurarJanela();
        montarTela();
        atualizarCamposTipo();
        atualizarTabela();
    }

    private void configurarJanela() {

        setTitle("Gerenciamento de Veículos");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void montarTela() {

        JPanel painelFormulario =
                new JPanel(new GridLayout(12, 2, 10, 5));

        comboTipo =
                new JComboBox<>();

        comboTipo.addItem("Elétrico");
        comboTipo.addItem("Híbrido");

        campoId = new JTextField();
        campoModelo = new JTextField();
        campoAutonomia = new JTextField();
        campoBateria = new JTextField();
        campoConsumoKwh = new JTextField();
        campoTempoRecarga = new JTextField();

        campoConector = new JTextField();
        campoTempoRapido = new JTextField();

        campoTanque = new JTextField();
        campoConsumoCombustivel = new JTextField();
        campoCombustivel = new JTextField();

        painelFormulario.add(new JLabel("Tipo de Veículo:"));
        painelFormulario.add(comboTipo);

        painelFormulario.add(new JLabel("ID:"));
        painelFormulario.add(campoId);

        painelFormulario.add(new JLabel("Modelo:"));
        painelFormulario.add(campoModelo);

        painelFormulario.add(new JLabel("Autonomia Máxima (km):"));
        painelFormulario.add(campoAutonomia);

        painelFormulario.add(new JLabel("Carga da Bateria Atual (%):"));
        painelFormulario.add(campoBateria);

        painelFormulario.add(new JLabel("Consumo kWh/km:"));
        painelFormulario.add(campoConsumoKwh);

        painelFormulario.add(new JLabel("Tempo Recarga Completa (min):"));
        painelFormulario.add(campoTempoRecarga);

        painelFormulario.add(new JLabel("Tipo de Conector:"));
        painelFormulario.add(campoConector);

        painelFormulario.add(new JLabel("Tempo Recarga Rápida (min):"));
        painelFormulario.add(campoTempoRapido);

        painelFormulario.add(new JLabel("Capacidade do Tanque:"));
        painelFormulario.add(campoTanque);

        painelFormulario.add(new JLabel("Consumo Combustível (km/l):"));
        painelFormulario.add(campoConsumoCombustivel);

        painelFormulario.add(new JLabel("Tipo de Combustível:"));
        painelFormulario.add(campoCombustivel);

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
        modeloTabela.addColumn("Tipo");
        modeloTabela.addColumn("Modelo");
        modeloTabela.addColumn("Autonomia");
        modeloTabela.addColumn("Bateria");
        modeloTabela.addColumn("Consumo");
        modeloTabela.addColumn("Tempo Recarga");
        modeloTabela.addColumn("Informação Extra");

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

        comboTipo.addActionListener(e -> atualizarCamposTipo());

        botaoCadastrar.addActionListener(e -> cadastrarVeiculo());

        botaoAtualizar.addActionListener(e -> atualizarVeiculo());

        botaoExcluir.addActionListener(e -> excluirVeiculo());

        botaoLimpar.addActionListener(e -> limparCampos());

        botaoVoltar.addActionListener(e -> dispose());

        tabela.getSelectionModel().addListSelectionListener(
                e -> preencherCampos()
        );
    }

    private void atualizarCamposTipo() {

        String tipo =
                comboTipo.getSelectedItem().toString();

        boolean eletrico =
                tipo.equals("Elétrico");

        campoConector.setEnabled(eletrico);
        campoTempoRapido.setEnabled(eletrico);

        campoTanque.setEnabled(!eletrico);
        campoConsumoCombustivel.setEnabled(!eletrico);
        campoCombustivel.setEnabled(!eletrico);
    }

    private void cadastrarVeiculo() {

        try {

            int id =
                    converterInt(campoId.getText());

            if (veiculoController.buscarPorId(id) != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Já existe um veículo com esse ID."
                );

                return;
            }

            Veiculo veiculo =
                    criarVeiculoPelosCampos(id);

            veiculoController.cadastrar(veiculo);

            JOptionPane.showMessageDialog(
                    this,
                    "Veículo cadastrado com sucesso."
            );

            limparCampos();
            atualizarTabela();

        } catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha os campos numéricos corretamente."
            );

        } catch (IllegalArgumentException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage()
            );
        }
    }

    private void atualizarVeiculo() {

        try {

            int id =
                    converterInt(campoId.getText());

            if (veiculoController.buscarPorId(id) == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Veículo não encontrado."
                );

                return;
            }

            Veiculo veiculo =
                    criarVeiculoPelosCampos(id);

            veiculoController.atualizar(id, veiculo);

            JOptionPane.showMessageDialog(
                    this,
                    "Veículo atualizado com sucesso."
            );

            limparCampos();
            atualizarTabela();

        } catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha os campos numéricos corretamente."
            );

        } catch (IllegalArgumentException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage()
            );
        }
    }

    private void excluirVeiculo() {

        try {

            int id =
                    converterInt(campoId.getText());

            int confirmacao =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Deseja realmente excluir este veículo?",
                            "Confirmar exclusão",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirmacao == JOptionPane.YES_OPTION) {

                boolean excluiu =
                        veiculoController.excluir(id);

                if (excluiu) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Veículo excluído com sucesso."
                    );

                    limparCampos();
                    atualizarTabela();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Veículo não encontrado."
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

    private Veiculo criarVeiculoPelosCampos(int id) {

        String modelo =
                campoModelo.getText().trim();

        if (modelo.isEmpty()) {
            throw new IllegalArgumentException("Informe o modelo do veículo.");
        }

        double autonomia =
                converterDouble(campoAutonomia.getText());

        double bateria =
                converterDouble(campoBateria.getText());

        double consumoKwh =
                converterDouble(campoConsumoKwh.getText());

        int tempoRecarga =
                converterInt(campoTempoRecarga.getText());

        String tipo =
                comboTipo.getSelectedItem().toString();

        if (tipo.equals("Elétrico")) {

            String conector =
                    campoConector.getText().trim();

            if (conector.isEmpty()) {
                throw new IllegalArgumentException("Informe o tipo de conector.");
            }

            int tempoRapido =
                    converterInt(campoTempoRapido.getText());

            return new VeiculoEletrico(
                    id,
                    modelo,
                    autonomia,
                    bateria,
                    consumoKwh,
                    tempoRecarga,
                    conector,
                    tempoRapido
            );

        } else {

            double tanque =
                    converterDouble(campoTanque.getText());

            double consumoCombustivel =
                    converterDouble(campoConsumoCombustivel.getText());

            String combustivel =
                    campoCombustivel.getText().trim();

            if (combustivel.isEmpty()) {
                throw new IllegalArgumentException("Informe o tipo de combustível.");
            }

            return new VeiculoHibrido(
                    id,
                    modelo,
                    autonomia,
                    bateria,
                    consumoKwh,
                    tempoRecarga,
                    tanque,
                    consumoCombustivel,
                    combustivel
            );
        }
    }

    private void atualizarTabela() {

        modeloTabela.setRowCount(0);

        ArrayList<Veiculo> veiculos =
                veiculoController.listar();

        for (int i = 0; i < veiculos.size(); i++) {

            Veiculo veiculo =
                    veiculos.get(i);

            String tipo;
            String extra;

            if (veiculo instanceof VeiculoEletrico) {

                VeiculoEletrico eletrico =
                        (VeiculoEletrico) veiculo;

                tipo = "Elétrico";
                extra = "Conector: " + eletrico.getTipoConector();

            } else {

                VeiculoHibrido hibrido =
                        (VeiculoHibrido) veiculo;

                tipo = "Híbrido";
                extra = "Combustível: " + hibrido.getTipoCombustivel();
            }

            modeloTabela.addRow(
                    new Object[]{
                            veiculo.getId(),
                            tipo,
                            veiculo.getModelo(),
                            veiculo.getAutonomiaMaxima(),
                            veiculo.getCargaBateriaAtual(),
                            veiculo.getConsumoKwhPorKm(),
                            veiculo.getTempoRecargaCompleta(),
                            extra
                    }
            );
        }
    }

    private void preencherCampos() {

        int linhaSelecionada =
                tabela.getSelectedRow();

        if (linhaSelecionada < 0) {
            return;
        }

        int id =
                Integer.parseInt(
                        modeloTabela.getValueAt(
                                linhaSelecionada,
                                0
                        ).toString()
                );

        Veiculo veiculo =
                veiculoController.buscarPorId(id);

        if (veiculo == null) {
            return;
        }

        campoId.setText(String.valueOf(veiculo.getId()));
        campoModelo.setText(veiculo.getModelo());
        campoAutonomia.setText(String.valueOf(veiculo.getAutonomiaMaxima()));
        campoBateria.setText(String.valueOf(veiculo.getCargaBateriaAtual()));
        campoConsumoKwh.setText(String.valueOf(veiculo.getConsumoKwhPorKm()));
        campoTempoRecarga.setText(String.valueOf(veiculo.getTempoRecargaCompleta()));

        if (veiculo instanceof VeiculoEletrico) {

            VeiculoEletrico eletrico =
                    (VeiculoEletrico) veiculo;

            comboTipo.setSelectedItem("Elétrico");

            campoConector.setText(eletrico.getTipoConector());
            campoTempoRapido.setText(String.valueOf(eletrico.getTempoRecargaRapida()));

            campoTanque.setText("");
            campoConsumoCombustivel.setText("");
            campoCombustivel.setText("");

        } else if (veiculo instanceof VeiculoHibrido) {

            VeiculoHibrido hibrido =
                    (VeiculoHibrido) veiculo;

            comboTipo.setSelectedItem("Híbrido");

            campoTanque.setText(String.valueOf(hibrido.getCapacidadeTanqueCombustivel()));
            campoConsumoCombustivel.setText(String.valueOf(hibrido.getConsumoCombustivel()));
            campoCombustivel.setText(hibrido.getTipoCombustivel());

            campoConector.setText("");
            campoTempoRapido.setText("");
        }

        atualizarCamposTipo();
    }

    private void limparCampos() {

        campoId.setText("");
        campoModelo.setText("");
        campoAutonomia.setText("");
        campoBateria.setText("");
        campoConsumoKwh.setText("");
        campoTempoRecarga.setText("");

        campoConector.setText("");
        campoTempoRapido.setText("");

        campoTanque.setText("");
        campoConsumoCombustivel.setText("");
        campoCombustivel.setText("");

        comboTipo.setSelectedItem("Elétrico");

        tabela.clearSelection();

        atualizarCamposTipo();
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