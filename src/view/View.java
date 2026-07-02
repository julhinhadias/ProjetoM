package view;

import java.util.Scanner;
import java.util.ArrayList;

import controller.CidadeController;
import controller.EletropostoController;
import controller.RotaController;
import controller.VeiculoController;

import model.Cidade;
import model.Eletroposto;
import model.Veiculo;
import model.VeiculoEletrico;
import model.VeiculoHibrido;

import repository.CidadeRepository;
import repository.EletropostoRepository;
import repository.VeiculoRepository;

public class View {

    private Scanner sc;

    private VeiculoRepository veiculoRepository;
    private CidadeRepository cidadeRepository;
    private EletropostoRepository eletropostoRepository;

    private VeiculoController veiculoController;
    private CidadeController cidadeController;
    private EletropostoController eletropostoController;
    private RotaController rotaController;

    public View() {

        sc = new Scanner(System.in);

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
    }

    public void iniciar() {

        int opcao;

        do {

            System.out.println("\nBEM VINDO AO GREEN ROUTE: ");
            System.out.println("O que deseja acessar? ");
            System.out.println("------------------------");
            System.out.println("1 - Crud do sistema: ");
            System.out.println("2 - Simulação de viagem: ");
            System.out.println("0 - Sair ");
            System.out.println("------------------------");

            System.out.print("Escolha: ");
            opcao = sc.nextInt();

            switch (opcao) {

                case 1:
                    menuCrud();
                    break;

                case 2:
                    simularViagem();
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção invalida.");
            }

        } while (opcao != 0);

        sc.close();
    }
    public void menuCrud() {

        int opcao;

        do {

            System.out.println("---------------------------");
            System.out.println("\nGerenciamento de Dados: ");
            System.out.println("1 - Veiculos");
            System.out.println("2 - Cidades");
            System.out.println("3 - Eletropostos");
            System.out.println("0 - Voltar");
            System.out.println("---------------------------");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();

            switch (opcao) {

                case 1:
                    menuVeiculos();
                    break;

                case 2:
                    menuCidades();
                    break;

                case 3:
                    menuEletropostos();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    public void menuVeiculos() {

        int opcao;

        do {

            System.out.println("\nVEICULOS:");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Buscar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Excluir");
            System.out.println("5 - Listar");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();

            switch (opcao) {

                case 1:
                    cadastrarVeiculo();
                    break;

                case 2:
                    buscarVeiculo();
                    break;

                case 3:
                    atualizarVeiculo();
                    break;

                case 4:
                    excluirVeiculo();
                    break;

                case 5:
                    listarVeiculos();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    public void menuCidades() {

        int opcao;

        do {

            System.out.println("\nCIDADES:");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Buscar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Excluir");
            System.out.println("5 - Listar");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();

            switch (opcao) {

                case 1:
                    cadastrarCidade();
                    break;

                case 2:
                    buscarCidade();
                    break;

                case 3:
                    atualizarCidade();
                    break;

                case 4:
                    excluirCidade();
                    break;

                case 5:
                    listarCidades();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    public void menuEletropostos() {

        int opcao;

        do {

            System.out.println("\nELETROPOSTOS:");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Buscar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Excluir");
            System.out.println("5 - Listar");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();

            switch (opcao) {

                case 1:
                    cadastrarEletroposto();
                    break;

                case 2:
                    buscarEletroposto();
                    break;

                case 3:
                    atualizarEletroposto();
                    break;

                case 4:
                    excluirEletroposto();
                    break;

                case 5:
                    listarEletropostos();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }
    public void cadastrarVeiculo() {

        System.out.println("\n1 -Veiculo Eletrico");
        System.out.println("2 -Veiculo Hibrido");
        System.out.print("Escolha: ");
        int tipo = sc.nextInt();
        sc.nextLine();

        System.out.print("id: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (veiculoController.buscarPorId(id) != null) {
            System.out.println("Já existe um veiculo com esse ID.");
            return;
        }

        System.out.print("Modelo do veículo: ");
        String modelo = sc.nextLine();

        System.out.print("Autonomia maxima (km): ");
        double autonomia = sc.nextDouble();

        System.out.print("Carga da bateria atual (%): ");
        double bateria = sc.nextDouble();

        System.out.print("Consumo kWh/km: ");
        double consumo = sc.nextDouble();

        System.out.print("Tempo de recarga completa (min): ");
        int tempoRecarga = sc.nextInt();
        sc.nextLine();

        if (tipo == 1) {

            System.out.print("Tipo de conector: ");
            String conector = sc.nextLine();

            System.out.print("Tempo de recarga rapida (min): ");
            int tempoRapido = sc.nextInt();

            VeiculoEletrico veiculo = new VeiculoEletrico(
                    id,
                    modelo,
                    autonomia,
                    bateria,
                    consumo,
                    tempoRecarga,
                    conector,
                    tempoRapido
            );

            veiculoController.cadastrar(veiculo);

        } else if (tipo == 2) {

            System.out.print("Capacidade do tanque: ");
            double tanque = sc.nextDouble();

            System.out.print("Consumo combustivel (km/l): ");
            double consumoComb = sc.nextDouble();
            sc.nextLine();

            System.out.print("Tipo de combustivel: ");
            String combustivel = sc.nextLine();

            VeiculoHibrido veiculo = new VeiculoHibrido(
                    id,
                    modelo,
                    autonomia,
                    bateria,
                    consumo,
                    tempoRecarga,
                    tanque,
                    consumoComb,
                    combustivel
            );

            veiculoController.cadastrar(veiculo);

        } else {

            System.out.println("Tipo inválido.");
            return;
        }

        System.out.println("Veiculo cadastrado.");
    }

    public void buscarVeiculo() {

        System.out.print("id do veiculo: ");
        int id = sc.nextInt();

        Veiculo veiculo = veiculoController.buscarPorId(id);

        if (veiculo != null) {

            System.out.println("ID: " + veiculo.getId());
            System.out.println("Modelo: " + veiculo.getModelo());
            System.out.println("Autonomia Máxima: " + veiculo.getAutonomiaMaxima() + " km");
            System.out.println("Carga da Bateria: " + veiculo.getCargaBateriaAtual() + "%");
            System.out.println("Consumo: " + veiculo.getConsumoKwhPorKm() + " kWh/km");
            System.out.println("Tempo de Recarga Completa: " + veiculo.getTempoRecargaCompleta() + " min");

        } else {
            System.out.println("Veiculo não encontrado.");
        }
    }

    public void listarVeiculos() {
        //E AQ
        ArrayList<Veiculo> lista = veiculoController.listar();

        if (lista.size() == 0) {
            System.out.println("Nenhum veículo cadastrado.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {

            Veiculo veiculo = lista.get(i);

            System.out.println("ID: " + veiculo.getId());
            System.out.println("Modelo: " + veiculo.getModelo());
            System.out.println("Autonomia Máxima: " + veiculo.getAutonomiaMaxima() + " km");
            System.out.println("Carga da Bateria: " + veiculo.getCargaBateriaAtual() + "%");
            System.out.println("Consumo: " + veiculo.getConsumoKwhPorKm() + " kWh/km");
            System.out.println("Tempo de Recarga Completa: " + veiculo.getTempoRecargaCompleta() + " min");
            System.out.println();
        }
    }
    public void excluirVeiculo() {

        System.out.print("id do veiculo: ");
        int id = sc.nextInt();

        if (veiculoController.excluir(id)) {
            System.out.println("Veiculo excluído.");
        } else {
            System.out.println("Veiculo não encontrado.");
        }
    }

    public void atualizarVeiculo() {

        System.out.print("id do veiculo que deseja atualizar: ");
        int id = sc.nextInt();

        Veiculo antigo = veiculoController.buscarPorId(id);

        if (antigo == null) {
            System.out.println("Veiculo nao encontrado.");
            return;
        }

        sc.nextLine();

        System.out.print("Modelo: ");
        String modelo = sc.nextLine();

        System.out.print("Autonomia maxima: ");
        double autonomia = sc.nextDouble();

        System.out.print("Carga bateria atual (%): ");
        double bateria = sc.nextDouble();

        System.out.print("Consumo kWh/km: ");
        double consumo = sc.nextDouble();

        System.out.print("Tempo recarga completa (min): ");
        int tempoRecarga = sc.nextInt();
        sc.nextLine();

        if (antigo instanceof VeiculoEletrico) {

            System.out.print("Tipo conector: ");
            String conector = sc.nextLine();

            System.out.print("Tempo recarga rapida (min): ");
            int tempoRapido = sc.nextInt();

            VeiculoEletrico novo = new VeiculoEletrico(
                    id,
                    modelo,
                    autonomia,
                    bateria,
                    consumo,
                    tempoRecarga,
                    conector,
                    tempoRapido
            );

            veiculoController.atualizar(id, novo);

        } else if (antigo instanceof VeiculoHibrido) {

            System.out.print("Capacidade tanque: ");
            double tanque = sc.nextDouble();

            System.out.print("Consumo combustivel: ");
            double consumoComb = sc.nextDouble();
            sc.nextLine();

            System.out.print("Tipo combustivel: ");
            String combustivel = sc.nextLine();

            VeiculoHibrido novo = new VeiculoHibrido(
                    id,
                    modelo,
                    autonomia,
                    bateria,
                    consumo,
                    tempoRecarga,
                    tanque,
                    consumoComb,
                    combustivel
            );

            veiculoController.atualizar(id, novo);
        }

        System.out.println("Veiculo atualizado.");
    }

    public void cadastrarCidade() {

        System.out.print("id: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Estado: ");
        String estado = sc.nextLine();

        System.out.print("Distância da capital: ");
        double distancia = sc.nextDouble();

        Cidade cidade = new Cidade(id, nome, estado, distancia);

        cidadeController.cadastrar(cidade);

        System.out.println("Cidade cadastrada.");
    }

    public void buscarCidade() {

        System.out.print("id da cidade: ");
        int id = sc.nextInt();

        Cidade cidade = cidadeController.buscarPorId(id);

        if (cidade != null) {
            System.out.println(cidade);
        } else {
            System.out.println("Cidade não encontrada.");
        }
    }

    public void listarCidades() {

        ArrayList<Cidade> cidades = cidadeController.listar();

        if (cidades.size() == 0) {
            System.out.println("Nenhuma cidade cadastrada.");
            return;
        }

        for (int i = 0; i < cidades.size(); i++) {
            System.out.println(cidades.get(i));
        }
    }

    public void excluirCidade() {

        System.out.print("id da cidade: ");
        int id = sc.nextInt();

        if (cidadeController.excluir(id)) {
            System.out.println("Cidade removida.");
        } else {
            System.out.println("Cidade nao encontrada.");
        }
    }

    public void atualizarCidade() {

        System.out.print("Novo id da cidade: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Novo nome: ");
        String nome = sc.nextLine();

        System.out.print("Novo estado: ");
        String estado = sc.nextLine();

        System.out.print("Nova distancia: ");
        double distancia = sc.nextDouble();

        Cidade cidade = new Cidade(id, nome, estado, distancia);

        if (cidadeController.atualizar(id, cidade)) {
            System.out.println("Cidade atualizada.");
        } else {
            System.out.println("Cidade nao encontrada.");
        }
    }

    public void cadastrarEletroposto() {

        System.out.print("id: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Localização: ");
        String localizacao = sc.nextLine();

        System.out.print("id da cidade: ");
        int cidadeId = sc.nextInt();
        sc.nextLine();

        System.out.print("Conectores disponíveis : ");
        String conectores = sc.nextLine();

        System.out.print("Potência: ");
        double potencia = sc.nextDouble();

        System.out.print("Preço por kWh: ");
        double preco = sc.nextDouble();

        System.out.print("Vagas: ");
        int vagas = sc.nextInt();

        Eletroposto eletroposto = new Eletroposto(
                id, nome, localizacao,
                cidadeId, conectores,
                potencia, preco, vagas
        );

        eletropostoController.cadastrar(eletroposto);

        System.out.println("Eletroposto cadastrado!");
    }

    public void buscarEletroposto() {

        System.out.print("id: ");
        int id = sc.nextInt();

        Eletroposto e = eletropostoController.buscarPorId(id);

        if (e != null) {

            System.out.println("ID: " + e.getId());
            System.out.println("Nome: " + e.getNome());
            System.out.println("Localização: " + e.getLocalizacao());
            System.out.println("ID da Cidade: " + e.getCidadeId());
            System.out.println("Conectores Disponíveis: " + e.getTiposConectoresDisponiveis());
            System.out.println("Potência: " + e.getPotenciaCargaKw() + " kW");
            System.out.println("Preço por kWh: R$ " + e.getPrecoPorKwh());
            System.out.println("Vagas Disponíveis: " + e.getVagasDisponiveis());

        } else {
            System.out.println("Eletroposto não encontrado!");
        }
    }

    public void listarEletropostos() {

        ArrayList<Eletroposto> lista = eletropostoController.listar();

        //AJEITEI AQUI
        if (lista.size() == 0) {
            System.out.println("Nenhum eletroposto cadastrado.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {

            Eletroposto eletroposto = lista.get(i);

            System.out.println("ID: " + eletroposto.getId());
            System.out.println("Nome: " + eletroposto.getNome());
            System.out.println("Localização: " + eletroposto.getLocalizacao());
            System.out.println("ID da Cidade: " + eletroposto.getCidadeId());
            System.out.println("Conectores Disponíveis: " + eletroposto.getTiposConectoresDisponiveis());
            System.out.println("Potência: " + eletroposto.getPotenciaCargaKw() + " kW");
            System.out.println("Preço por kWh: R$ " + eletroposto.getPrecoPorKwh());
            System.out.println("Vagas Disponíveis: " + eletroposto.getVagasDisponiveis());
            System.out.println();
        }
    }
    public void excluirEletroposto() {

        System.out.print("id: ");
        int id = sc.nextInt();

        if (eletropostoController.excluir(id)) {
            System.out.println("Eletroposto excluído.");
        } else {
            System.out.println("Eletroposto não encontrado.");
        }
    }

    public void atualizarEletroposto() {

        System.out.print("Novo id: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Novo Nome: ");
        String nome = sc.nextLine();

        System.out.print("Nova Localização: ");
        String localizacao = sc.nextLine();

        System.out.print("Nova id da cidade: ");
        int cidadeId = sc.nextInt();
        sc.nextLine();

        System.out.print("Conectores disponíveis: ");
        String conectores = sc.nextLine();

        System.out.print("Potência: ");
        double potencia = sc.nextDouble();

        System.out.print("Novo Preço: ");
        double preco = sc.nextDouble();

        System.out.print("Vagas: ");
        int vagas = sc.nextInt();

        Eletroposto eletroposto = new Eletroposto(
                id, nome, localizacao,
                cidadeId, conectores,
                potencia, preco, vagas
        );

        if (eletropostoController.atualizar(id, eletroposto)) {
            System.out.println("Atualizado.");
        } else {
            System.out.println("Não encontrado.");
        }
    }

    public void simularViagem() {

        System.out.print("id do veiculo: ");
        int veiculoId = sc.nextInt();

        System.out.print("id da cidade do seu destino: ");
        int cidadeId = sc.nextInt();

        String resultado =
                rotaController.simularViagem(
                        veiculoId,
                        cidadeId
                );

        System.out.println(resultado);
    }
}

