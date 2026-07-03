package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.CidadeController;
import controller.EletropostoController;
import controller.RotaController;
import controller.VeiculoController;

import repository.CidadeRepository;
import repository.EletropostoRepository;
import repository.VeiculoRepository;

public class TelaPrincipal extends JFrame {

    private VeiculoRepository veiculoRepository;
    private CidadeRepository cidadeRepository;
    private EletropostoRepository eletropostoRepository;

    private VeiculoController veiculoController;
    private CidadeController cidadeController;
    private EletropostoController eletropostoController;
    private RotaController rotaController;

    public TelaPrincipal() {

        veiculoRepository =
                new VeiculoRepository();

        cidadeRepository =
                new CidadeRepository();

        eletropostoRepository =
                new EletropostoRepository();

        veiculoController =
                new VeiculoController(veiculoRepository);

        cidadeController =
                new CidadeController(cidadeRepository);

        eletropostoController =
                new EletropostoController(eletropostoRepository);

        rotaController =
                new RotaController(
                        veiculoRepository,
                        cidadeRepository,
                        eletropostoRepository
                );

        configurarJanela();
        montarTela();
    }

    private void configurarJanela() {

        setTitle("GreenRoute");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void montarTela() {

        JLabel titulo =
                new JLabel("GREENROUTE - SISTEMA DE ROTAS", JLabel.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JButton botaoCidades =
                new JButton("Gerenciar Cidades");

        JButton botaoVeiculos =
                new JButton("Gerenciar Veículos");

        JButton botaoEletropostos =
                new JButton("Gerenciar Eletropostos");

        JButton botaoRotas =
                new JButton("Planejador de Rotas Inteligente");

        JButton botaoSair =
                new JButton("Sair");

        JPanel painelBotoes =
                new JPanel();

        painelBotoes.setLayout(
                new GridLayout(5, 1, 10, 10)
        );

        painelBotoes.add(botaoCidades);
        painelBotoes.add(botaoVeiculos);
        painelBotoes.add(botaoEletropostos);
        painelBotoes.add(botaoRotas);
        painelBotoes.add(botaoSair);

        add(titulo, BorderLayout.NORTH);
        add(painelBotoes, BorderLayout.CENTER);

        botaoCidades.addActionListener(
                e -> abrirTelaCidades()
        );

        botaoVeiculos.addActionListener(
                e -> abrirTelaVeiculos()
        );

        botaoEletropostos.addActionListener(
                e -> abrirTelaEletropostos()
        );

        botaoRotas.addActionListener(
                e -> abrirTelaRotas()
        );

        botaoSair.addActionListener(
                e -> System.exit(0)
        );

        TemaRosa.aplicar(getContentPane());
    }

    private void abrirTelaCidades() {

        TelaCidade telaCidade =
                new TelaCidade(cidadeController);

        telaCidade.setVisible(true);
    }

    private void abrirTelaVeiculos() {

        TelaVeiculo telaVeiculo =
                new TelaVeiculo(veiculoController);

        telaVeiculo.setVisible(true);
    }

    private void abrirTelaEletropostos() {

        TelaEletroposto telaEletroposto =
                new TelaEletroposto(
                        eletropostoController,
                        cidadeController
                );

        telaEletroposto.setVisible(true);
    }

    private void abrirTelaRotas() {

        TelaPlanejadorRota telaPlanejadorRota =
                new TelaPlanejadorRota(
                        veiculoController,
                        cidadeController,
                        rotaController
                );

        telaPlanejadorRota.setVisible(true);
    }
}