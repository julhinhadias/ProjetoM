package controller;

import model.Cidade;
import model.Eletroposto;
import model.Veiculo;
import repository.CidadeRepository;
import repository.EletropostoRepository;
import repository.VeiculoRepository;

public class RotaController {

    private VeiculoRepository veiculoRepository;
    private CidadeRepository cidadeRepository;
    private EletropostoRepository eletropostoRepository;

    public RotaController(
            VeiculoRepository veiculoRepository,
            CidadeRepository cidadeRepository,
            EletropostoRepository eletropostoRepository) {

        this.veiculoRepository = veiculoRepository;
        this.cidadeRepository = cidadeRepository;
        this.eletropostoRepository = eletropostoRepository;
    }

    public void simularViagem(int veiculoId, int cidadeId) {

        Veiculo veiculo =
                veiculoRepository.buscarPorId(veiculoId);

        Cidade cidade =
                cidadeRepository.buscarPorId(cidadeId);

        if (veiculo == null || cidade == null) {

            System.out.println("Dados invalidos.");
            return;
        }

        double autonomia =
                veiculo.calcularAutonomiaAtual();

        if (autonomia >= cidade.getDistanciaDaCapital()) {

            System.out.println("Viagem possivel.");
        } else {

            System.out.println("Recarga necessaria.");
            System.out.println("Eletropostos encontrados:");

            Eletroposto[] postos =
                    eletropostoRepository.listar();

            for (int i = 0;
                 i < eletropostoRepository.getQuantidade();
                 i++) {

                if (postos[i].getCidadeId() == cidadeId) {
                    System.out.println(postos[i]);
                }
            }
        }
    }
}