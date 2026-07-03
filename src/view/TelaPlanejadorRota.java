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
import javax.swing.JTextArea;

import controller.CidadeController;
import controller.RotaController;
import controller.VeiculoController;

import exception.AutonomiaInsuficienteException;
import exception.ConectorIncompativelException;

import model.Cidade;
import model.Veiculo;

public class TelaPlanejadorRota extends JFrame {

    private VeiculoController veiculoController;
    private CidadeController cidadeController;
    private RotaController rotaController;

    private JComboBox<Veiculo> comboVeiculos;
    private JComboBox<Cidade> comboCidades;

    private JTextArea areaResultado;

    public TelaPlanejadorRota(VeiculoController veiculoController,
                              CidadeController cidadeController,
                              RotaController rotaController) {

        this.veiculoController = veiculoController;
        this.cidadeController = cidadeController;
        this.rotaController = rotaController;

        configurarJanela();
        montarTela();
        carregarDados();
    }

    private void configurarJanela() {

        setTitle("Planejador de Rotas");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void montarTela() {

        JPanel painelFormulario =
                new JPanel(new GridLayout(3, 2, 10, 10));

        comboVeiculos =
                new JComboBox<>();

        comboCidades =
                new JComboBox<>();

        JButton botaoAtualizarListas =
                new JButton("Atualizar Listas");

        JButton botaoPlanejar =
                new JButton("Planejar Rota");

        painelFormulario.add(new JLabel("Selecione o Veículo:"));
        painelFormulario.add(comboVeiculos);

        painelFormulario.add(new JLabel("Selecione a Cidade de Destino:"));
        painelFormulario.add(comboCidades);

        painelFormulario.add(botaoAtualizarListas);
        painelFormulario.add(botaoPlanejar);

        areaResultado =
                new JTextArea();

        areaResultado.setEditable(false);
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);

        JScrollPane painelResultado =
                new JScrollPane(areaResultado);

        JButton botaoVoltar =
                new JButton("Voltar");

        JPanel painelInferior =
                new JPanel(new GridLayout(1, 1));

        painelInferior.add(botaoVoltar);

        add(painelFormulario, BorderLayout.NORTH);
        add(painelResultado, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        botaoAtualizarListas.addActionListener(
                e -> carregarDados()
        );

        botaoPlanejar.addActionListener(
                e -> planejarRota()
        );

        botaoVoltar.addActionListener(
                e -> dispose()
        );
    }

    private void carregarDados() {

        comboVeiculos.removeAllItems();
        comboCidades.removeAllItems();

        ArrayList<Veiculo> veiculos =
                veiculoController.listar();

        ArrayList<Cidade> cidades =
                cidadeController.listar();

        for (int i = 0; i < veiculos.size(); i++) {

            comboVeiculos.addItem(
                    veiculos.get(i)
            );
        }

        for (int i = 0; i < cidades.size(); i++) {

            comboCidades.addItem(
                    cidades.get(i)
            );
        }
    }

    private void planejarRota() {

        Veiculo veiculoSelecionado =
                (Veiculo) comboVeiculos.getSelectedItem();

        Cidade cidadeSelecionada =
                (Cidade) comboCidades.getSelectedItem();

        if (veiculoSelecionado == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cadastre pelo menos um veículo antes de planejar a rota."
            );

            return;
        }

        if (cidadeSelecionada == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cadastre pelo menos uma cidade antes de planejar a rota."
            );

            return;
        }

        try {

            String resultado =
                    rotaController.simularViagem(
                            veiculoSelecionado.getId(),
                            cidadeSelecionada.getId()
                    );

            String respostaFinal =
                    montarRespostaInteligente(resultado);

            areaResultado.setText(respostaFinal);

        } catch (AutonomiaInsuficienteException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage(),
                    "Autonomia insuficiente",
                    JOptionPane.WARNING_MESSAGE
            );

            areaResultado.setText(
                    montarRespostaInteligente(erro.getMessage())
            );

        } catch (ConectorIncompativelException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage(),
                    "Conector incompatível",
                    JOptionPane.WARNING_MESSAGE
            );

            areaResultado.setText(
                    montarRespostaInteligente(erro.getMessage())
            );

        } catch (IllegalArgumentException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private String montarRespostaInteligente(String resultado) {

        return "PLANEJADOR DE ROTAS\n" +
                "----------------------------------------\n" +
                "Dados reais simulados utilizados:\n" +
                "- Clima: condição normal\n" +
                "- Trânsito: fluxo moderado\n" +
                "- Critério: autonomia atual do veículo e distância até o destino\n" +
                "----------------------------------------\n\n" +
                resultado +
                "\nObservação IA";
    }
}