package controller;

import java.util.Scanner;

import model.Eletroposto;
import repository.EletropostoRepository;

public class EletropostoController {

    private EletropostoRepository repository;
    private Scanner sc;

    public EletropostoController(EletropostoRepository repository, Scanner sc) {
        this.repository = repository;
        this.sc = sc;
    }

    public void cadastrar() {

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

        repository.cadastrar(eletroposto);

        System.out.println("Eletroposto cadastrado!");
    }

    public void buscar() {

        System.out.print("id: ");
        int id = sc.nextInt();

        Eletroposto e = repository.buscarPorId(id);

        if (e != null) {
            System.out.println(e);
        } else {
            System.out.println("Eletroposto não encontrado!");
        }
    }

    public void listar() {

        Eletroposto[] lista = repository.listar();

        if (repository.getQuantidade() == 0) {
            System.out.println("Nenhum eletroposto cadastrado.");
            return;
        }

        for (int i = 0; i < repository.getQuantidade(); i++) {

            System.out.println("ID: " + lista[i].getId());
            System.out.println("Nome: " + lista[i].getNome());
            System.out.println("Localização: " + lista[i].getLocalizacao());
            System.out.println("ID da Cidade: " + lista[i].getCidadeId());
            System.out.println("Conectores Disponíveis: " + lista[i].getTiposConectoresDisponiveis());
            System.out.println("Potência: " + lista[i].getPotenciaCargaKw() + " kW");
            System.out.println("Preço por kWh: R$ " + lista[i].getPrecoPorKwh());
            System.out.println("Vagas Disponíveis: " + lista[i].getVagasDisponiveis());
            System.out.println();
        }
    }

    public void excluir() {

        System.out.print("id: ");
        int id = sc.nextInt();

        if (repository.excluir(id)) {
            System.out.println("Eletroposto excluído.");
        } else {
            System.out.println("Eletroposto não encontrado.");
        }
    }

    public void atualizar() {

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

        if (repository.atualizar(id, eletroposto)) {
            System.out.println("Atualizado.");
        } else {
            System.out.println("Não encontrado.");
        }
    }
}