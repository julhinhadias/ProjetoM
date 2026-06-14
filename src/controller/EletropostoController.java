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

        System.out.print("Conectores : ");
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

        System.out.println("Eletroposto cadastrado.");
    }

    public void buscar() {

        System.out.print("ID: ");
        int id = sc.nextInt();

        Eletroposto e = repository.buscarPorId(id);

        if (e != null) {
            System.out.println(e);
        } else {
            System.out.println("Eletroposto nao encontrado.");
        }
    }

    public void listar() {

        Eletroposto[] lista = repository.listar();

        for (int i = 0; i < repository.getQuantidade(); i++) {
            System.out.println(lista[i]);
        }
    }

    public void excluir() {

        System.out.print("ID: ");
        int id = sc.nextInt();

        if (repository.excluir(id)) {
            System.out.println("Eletroposto removido.");
        } else {
            System.out.println("Eletroposto nao encontrado.");
        }
    }

    public void atualizar() {

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Localizacao: ");
        String localizacao = sc.nextLine();

        System.out.print("Cidade ID: ");
        int cidadeId = sc.nextInt();
        sc.nextLine();

        System.out.print("Conectores: ");
        String conectores = sc.nextLine();

        System.out.print("Potencia: ");
        double potencia = sc.nextDouble();

        System.out.print("Preco: ");
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
            System.out.println("Nao encontrado.");
        }
    }
}