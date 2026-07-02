package controller;

import java.util.ArrayList;

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

    public RotaController(VeiculoRepository veiculoRepository,
                          CidadeRepository cidadeRepository,
                          EletropostoRepository eletropostoRepository) {

        this.veiculoRepository = veiculoRepository;
        this.cidadeRepository = cidadeRepository;
        this.eletropostoRepository = eletropostoRepository;
    }

    public String simularViagem(int veiculoId, int cidadeId) {

        Veiculo veiculo =
                veiculoRepository.buscarPorId(veiculoId);

        Cidade cidade =
                cidadeRepository.buscarPorId(cidadeId);

        if (veiculo == null) {
            return "Veiculo não encontrado.";
        }

        if (cidade == null) {
            return "Cidade não encontrada.";
        }

        double autonomiaAtual =
                veiculo.calcularAutonomiaAtual();

        double distancia =
                cidade.getDistanciaDaCapital();

        String resultado = "";

        resultado += "\nSIMULAÇÃO DE VIAGEM\n";
        resultado += "--------------------------\n";
        resultado += "Veiculo: " + veiculo.getModelo() + "\n";
        resultado += "Destino: " + cidade.getNome() + "\n";
        resultado += "Distancia da capital: " + distancia + " km\n";
        resultado += "Autonomia atual: " + autonomiaAtual + " km\n";
        resultado += "--------------------------\n";

        if (autonomiaAtual >= distancia) {

            resultado += "A viagem é possível com a bateria atual.\n";

        } else {

            resultado += "É necessária uma recarga.\n";
            resultado += "Eletropostos disponíveis:\n";
            resultado += "--------------------------\n";

            ArrayList<Eletroposto> postos =
                    eletropostoRepository.listar();

            if (postos.size() == 0) {
                resultado += "Nenhum eletroposto cadastrado.\n";
                return resultado;
            }

            for (int i = 0; i < postos.size(); i++) {

                Eletroposto posto =
                        postos.get(i);

                Cidade cidadePosto =
                        cidadeRepository.buscarPorId(
                                posto.getCidadeId()
                        );

                resultado += "Nome: " + posto.getNome() + "\n";

                if (cidadePosto != null) {
                    resultado += "Cidade: " + cidadePosto.getNome() + "\n";
                } else {
                    resultado += "Cidade: não encontrada\n";
                }

                resultado += "Localização: " + posto.getLocalizacao() + "\n";
                resultado += "Conectores: " + posto.getTiposConectoresDisponiveis() + "\n";
                resultado += "Potência: " + posto.getPotenciaCargaKw() + " kW\n";
                resultado += "Preço por kWh: R$ " + posto.getPrecoPorKwh() + "\n";
                resultado += "Vagas disponíveis: " + posto.getVagasDisponiveis() + "\n";
                resultado += "--------------------------\n";
            }
        }

        return resultado;
    }
}