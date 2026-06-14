package controller;

import java.util.Scanner;

import model.Veiculo;
import model.VeiculoEletrico;
import model.VeiculoHibrido;
import repository.VeiculoRepository;

public class VeiculoController {

    private VeiculoRepository repository;
    private Scanner sc;

    public VeiculoController(VeiculoRepository repository, Scanner sc) {
        this.repository = repository;
        this.sc = sc;
    }

    public void cadastrar() {

        System.out.println("\n1 -Veiculo Eletrico");
        System.out.println("2 -Veiculo Hibrido");
        System.out.print("Escolha: ");
        int tipo = sc.nextInt();
        sc.nextLine();

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (repository.buscarPorId(id) != null) {
            System.out.println("Ja existe um veiculo com esse ID.");
            return;
        }

        System.out.print("Modelo: ");
        String modelo = sc.nextLine();

        System.out.print("Autonomia maxima (km): ");
        double autonomia = sc.nextDouble();

        System.out.print("Carga da bateria (%): ");
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

            repository.cadastrar(veiculo);

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

            repository.cadastrar(veiculo);

        } else {

            System.out.println("Tipo invalido.");
            return;
        }

        System.out.println("Veiculo cadastrado com sucesso.");
    }

    public void buscar() {

        System.out.print("ID do veiculo: ");
        int id = sc.nextInt();

        Veiculo veiculo = repository.buscarPorId(id);

        if (veiculo != null) {
            System.out.println(veiculo);
        } else {
            System.out.println("Veiculo nao encontrado.");
        }
    }

    public void listar() {

        Veiculo[] lista = repository.listar();

        if (repository.getQuantidade() == 0) {
            System.out.println("Nenhum veiculo cadastrado.");
            return;
        }

        for (int i = 0; i < repository.getQuantidade(); i++) {
            System.out.println(lista[i]);
        }
    }

    public void excluir() {

        System.out.print("ID do veiculo: ");
        int id = sc.nextInt();

        if (repository.excluir(id)) {
            System.out.println("Veiculo removido.");
        } else {
            System.out.println("Veiculo nao encontrado.");
        }
    }

    public void atualizar() {

        System.out.print("ID do veiculo que deseja atualizar: ");
        int id = sc.nextInt();

        Veiculo antigo = repository.buscarPorId(id);

        if (antigo == null) {
            System.out.println("Veiculo nao encontrado.");
            return;
        }

        sc.nextLine();

        System.out.print("Modelo: ");
        String modelo = sc.nextLine();

        System.out.print("Autonomia maxima: ");
        double autonomia = sc.nextDouble();

        System.out.print("Carga bateria: ");
        double bateria = sc.nextDouble();

        System.out.print("Consumo kWh/km: ");
        double consumo = sc.nextDouble();

        System.out.print("Tempo recarga completa: ");
        int tempoRecarga = sc.nextInt();
        sc.nextLine();

        if (antigo instanceof VeiculoEletrico) {

            System.out.print("Tipo conector: ");
            String conector = sc.nextLine();

            System.out.print("Tempo recarga rapida: ");
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

            repository.atualizar(id, novo);

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

            repository.atualizar(id, novo);
        }

        System.out.println("Veiculo atualizado.");
    }
}