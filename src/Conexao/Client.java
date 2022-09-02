package Conexao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Client {
    
//    public static String inserir(BufferedReader stdIn) {
//        String mensagemParaServidor = "";
//        try {
//            String cpf = "";
//            //TODO fazer validação de cpf
//            while(cpf.isBlank()) {
//                System.out.println("Digite seu cpf");
//                cpf = stdIn.readLine();
//            }
//            mensagemParaServidor += cpf + ";";
//
//            String nome = "";
//            while(nome.isBlank()) {
//                System.out.println("Digite seu nome");
//                nome = stdIn.readLine();
//            }
//            mensagemParaServidor += nome + ";";
//
//            String endereco = "";
//            while(endereco.isBlank()) {
//                System.out.println("Digite seu endereço");
//                endereco = stdIn.readLine();
//            }
//            mensagemParaServidor += endereco + ";";
//            String nomeEvento = "";
//            while(nomeEvento.isBlank()) {
//                System.out.println("Digite o nome do evento que deseja ingressar");
//                nomeEvento = stdIn.readLine();
//            }
//            mensagemParaServidor += nomeEvento + ";";
//
//        } catch(IOException e) {
//            throw new RuntimeException(e);
//        }
//        return mensagemParaServidor;
//    }
    
    public static boolean verificaHost(String host) {
        String[] bytes = host.split("\\.");
        try {
            for(int i = 0; i < 4; i++) {
                Integer.parseInt(bytes[i]);
            }
            return true;
        } catch(Exception e) {
            return false;
        }
    }
    
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        
        String serverHost;
        int serverPort;
    
        System.out.println("Insira o endereco ip do servidor (ex.: 192.168.0.1)");
        serverHost = scanner.next();
        while(!verificaHost(serverHost)) {
            System.out.println("Insira um endereco ip valido (ex.: 192.168.0.1)");
            serverHost = scanner.next();
        }
        
        System.out.println("Insira a porta que deseja se conectar (ex.: 80");
        while(true) {
            try {
                serverPort = scanner.nextInt();
                break;
            } catch(NumberFormatException e) {
                System.out.println("Insira uma porta valida (ex.: 80)");
            }
        }
        
        try {
            Socket conn = new Socket(serverHost, serverPort);
            PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Scanner stdIn = new Scanner(System.in);
            String fromServer;
            String fromUser;
            
            while((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if(fromServer.equalsIgnoreCase("Sair"))
                    break;
    
    
                /**
                 * O Cliente imprime "Qual operação você deseja fazer?
                 * 1 - Criar um evento
                 * 2 - Recuperar dados de um evento existente
                 * 3 - Atualizar dados de um evento existente
                 * 4 - Excluir um evento existente
                 * 5 - Listar participantes de um evento existente
                 * 6 - Listar vendedores de um evento existente
                 * 7 - Inserir participante em um evento existente
                 * 8 - Atualizar dados de um participante existente
                 * 9 - Inserir vendedor em um evento exitente
                 * 10 - Atualizar dados de um vendedor existente
                 * 11 - Recuperar dados de uma pessoa existente
                 * 12 - Excluir pessoa de um evento existente
                 *
                 * O cliente faz um switch case
                 * switch (int escolha) {
                 *  case 1:
                 *      String mensagem = "CRIAREVENTO;"
                 *      O cliente imprime "Digite os dados do evento no formato 'nome;preco;data' (ex.: 'Vila Gaming;50.0;31/12/2022')"
                 *      mensagem += scanner.nextLine()
                 *
                 *  2 - String mensagem = "RECUPERAREVENTO;"
                 *  O cliente imprime "Digite o nome do evento que deseja recuperar (ex.: 'Vila Gaming')"
                 *
                 *  3 - String mensagem = "ATUALIZAREVENTO;"
                 *  O cliente imprime "Digite os dados do evento no formato 'nome;preco;data' (ex.: 'Vila Gaming;50.0;31/12/2022')"
                 *                      "Obs.: O nome nao sera alterado pois trata-se da chave primaria"
                 *
                 *  4 - String mensagem = "EXCLUIREVENTO;"
                 *  O cliente imprime "Digite o nome do evento que deseja excluir (ex.: 'Vila Gaming')"
                 *
                 *  5 - String mensagem = "LISTARPARTICIPANTES;"
                 *  O cliente imprime "Digite o nome do evento em que deseja buscar os participantes (ex.: 'Vila Gaming')"
                 *
                 *  6 - String mensagem = "LISTARVENDEDORES;"
                 *  O cliente imprime "Digite o nome do evento em que deseja buscar os vendedores (ex.: 'Vila Gaming')"
                 *
                 *  7 - String mensagem = "INSERIRPARTICIPANTE;"
                 *  O cliente imprime "Digite o nome do evento, em seguida os dados do participante no formato 'nomeEvento;cpf;nomeParticipante;endereco;idade' (ex.: 'Vila Gaming;16759885015;Mateus Nosse;Rua Carlos Odebrecht n. 26;19')"
                 *
                 *  8 - String mensagem = "ATUALIZARPARTICIPANTE;"
                 *  O cliente imprime "Digite o nome do evento, em seguida os dados do participante no formato 'nomeEvento;cpf;nomeParticipante;endereco;idade' (ex.: 'Vila Gaming;16759885015;Mateus Nosse;Rua Carlos Odebrecht n. 26;19')"
                 *                      "Obs.: O cpf nao sera alterado pois trata-se da chave primaria"
                 *
                 *  9 - String mensagem = "INSERIRVENDEDOR;"
                 *  O cliente imprime "Digite o nome do evento, em seguida os dados do vendedor no formato 'nomeEvento;cpf;nomeVenddor;endereco;ramoDeVenda' (ex.: 'Vila Gaming;49467716038;Ana Reinert;Rua Emilio Becker n. 68;Vestuario')"
                 *
                 *  10 - String mensagem = "ATUALIZARVENDEDOR;"
                 *  O cliente imprime "Digite o nome do evento, em seguida os dados do vendedor no formato 'nomeEvento;cpf;nomeVendedor;endereco;ramoDeVenda' (ex.: 'Vila Gaming;49467716038;Ana Reinert;Rua Emilio Becker n. 68;Vestuario')"
                 *                      "Obs.: O cpf nao sera alterado pois trata-se da chave primaria"
                 *
                 *  11 - String mensagem = "RECUPERARPESSOA;"
                 *  O cliente imprime "Digite o nome do evento e cpf do participante no formato 'nomeEvento;cpf' (ex.: 'Vila Gaming;16759885015)"
                 *
                 *  12 - String mensagem = "EXCLUIRPESSOA;"
                 *  O cliente imprime "Digite o nome do evento e cpf do participante no formato 'nomeEvento;cpf' (ex.: 'Vila Gaming;49467716038)"
                 *
                 */
    
    
    
                fromUser = stdIn.nextLine();
                if(fromUser != null) {
                    switch(Integer.parseInt(fromUser)) {
                        case 1:
                            //inserir evento
                            fromUser = "CRIAREVENTO;";
                            System.out.println("Digite os dados do evento no formato 'nome;preco;data' (ex.: Vila Gaming;50.0;31/12/2022)");
                            fromUser += stdIn.nextLine();
                            break;
                        case 2:
                            //recuperar dados do evento
                            fromUser = "RECUPERAREVENTO;";
                            System.out.println("Digite o nome do evento que deseja recuperar (ex.: 'Vila Gaming')");
                            fromUser += stdIn.nextLine();
                            break;
                        case 3:
                            // atualiza dados do evento
                            fromUser = "ATUALIZAREVENTO;";
                            System.out.println("Digite os dados do evento no formato 'nome;preco;data' (ex.: Vila Gaming;50.0;31/12/2022)");
                            System.out.println("Obs.: O nome nao sera alterado pois trata-se da chave primaria");
                            fromUser += stdIn.nextLine();
                            break;
                        case 4:
                            //deleta um evento
                            fromUser = "EXCLUIREVENTO;";
                            System.out.println("Digite o nome do evento que deseja excluir (ex.: 'Vila Gaming')");
                            fromUser += stdIn.nextLine();
                            break;
                        case 5:
                            //Listar participantes de um evento existente
                            fromUser = "LISTARPARTICIPANTES;";
                            System.out.println("Digite o nome do evento em que deseja buscar os vendedores (ex.: 'Vila Gaming')");
                            fromUser += stdIn.nextLine();
                            break;
                        case 6:
                            //Listar vendedores de um evento existente
                            fromUser = "LISTARVENDEDORES;";
                            System.out.println("Digite o nome do evento em que deseja buscar os vendedores (ex.: 'Vila Gaming')");
                            fromUser += stdIn.nextLine();
                            break;
                        case 7:
                            //Inserir participante em um evento existente
                            fromUser = "INSERIRPARTICIPANTE;";
                            System.out.println("Digite o nome do evento, em seguida os dados do participante no formato 'nomeEvento;cpf;nomeParticipante;endereco;idade'");
                            System.out.println("(ex.: 'Vila Gaming;16759885015;Mateus Nosse;Rua Carlos Odebrecht n. 26;19')");
                            fromUser += stdIn.nextLine();
                            break;
                        case 8:
                            //Atualizar dados de um participante existente
                            fromUser = "ATUALIZARPARTICIPANTE;";
                            System.out.println("Digite o nome do evento, em seguida os dados do participante no formato 'nomeEvento;cpf;nomeParticipante;endereco;idade' (ex.: 'Vila Gaming;16759885015;Mateus Nosse;Rua Odebrecht n. 26;19')");
                            System.out.println("Obs.: O cpf nao sera alterado pois trata-se da chave primaria");
                            fromUser += stdIn.nextLine();
                            break;
                        case 9:
                            //Inserir vendedor em um evento exitente
                            fromUser = "INSERIRVENDEDOR;";
                            System.out.println("Digite o nome do evento, em seguida os dados do vendedor no formato 'nomeEvento;cpf;nomeVenddor;endereco;ramoDeVenda' ");
                            System.out.println("(ex.: 'Vila Gaming;49467716038;Ana Reinert;Rua Emilio Becker n. 68;Vestuario')");
                            fromUser += stdIn.nextLine();
                            break;
                        case 10:
                            //Atualizar dados de um vendedor existente
                            fromUser = "ATUALIZARVENDEDOR;";
                            System.out.println("Digite o nome do evento, em seguida os dados do vendedor no formato 'nomeEvento;cpf;nomeVendedor;endereco;ramoDeVenda' (ex.: 'Vila Gaming;49467716038;Ana Reinert;Rua Emilio Becker n. 68;Vestuario')");
                            System.out.println("Obs.: O cpf nao sera alterado pois trata-se da chave primaria");
                            fromUser += stdIn.nextLine();
                            break;
                        case 11:
                            fromUser = "RECUPERARPESSOA;";
                            System.out.println("Digite o nome do evento e cpf do participante no formato 'nomeEvento;cpf' (ex.: 'Vila Gaming;16759885015)");
                            fromUser += stdIn.nextLine();
                            break;
                        case 12:
                            fromUser = "EXCLUIRPESSOA;";
                            System.out.println("Digite o nome do evento e cpf do participante no formato 'nomeEvento;cpf' (ex.: 'Vila Gaming;49467716038)");
                            fromUser += stdIn.nextLine();
                            break;
                        default:
                            System.out.println("Número inválido");
                    }
                }
                out.println(fromUser);
            }
            
//                fromUser = stdIn.readLine();
//                if(fromUser != null) {
//                    switch(Integer.parseInt(fromUser)) {
//                        case 1:
//                            //insere um participante
//                            fromUser += "INSERT;PARTICIPANTE";
//                            fromUser += inserir(stdIn);
//                            int idade = 0;
//                            while(idade <= 0) {
//                                System.out.println("Digite sua idade");
//                                idade = Integer.parseInt(stdIn.readLine());
//                            }
//                            fromUser += idade;
//                            break;
//                        case 2:
//                            //insere um vendedor
//                            fromUser += "INSERT;VENDEDOR";
//                            fromUser += inserir(stdIn);
//                            String ramoVenda = "";
//                            while(ramoVenda.isBlank()) {
//                                System.out.println("Digite o ramo da venda");
//                                ramoVenda = stdIn.readLine();
//                            }
//                            fromUser += ramoVenda;
//                            break;
//                        case 3:
//                            // insere um evento
//                            fromUser += "INSERT;EVENTO";
//                            String nome = "";
//                            while(nome.isBlank()) {
//                                System.out.println("Digite o nome do Evento");
//                                nome = stdIn.readLine();
//                            }
//                            fromUser += nome;
//                            double preco = -1.0;
//                            while(preco <0.0){
//                                System.out.println("Digite o preço do ingresso do Evento");
//                                preco = Double.parseDouble(stdIn.readLine());
//                            }
//                            fromUser += preco + ";";
//
//                            Date data =  new Date();
//                            while(preco <0.0){
//                                System.out.println("Digite a data do Evento padrão (DD/mm/YYYY");
//                                SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
//                                try {
//                                    data = formatter1.parse(stdIn.readLine());
//                                } catch(ParseException e) {
//                                    System.out.println("Data inválida");
//                                }
//                            }
//                            fromUser += preco + ";";
//                            break;
//                        case 4:
//                            //deleta uma pessoa
//                            fromUser += "DELETE;PESSOA;";
//                            System.out.println("Digite o cpf da pessoa que deseja remover");
//                            fromUser += stdIn.readLine();
//                        case 5:
//                            //deleta um evento
//                    }
//                            break;
//
//                    }
//                    out.println(fromUser);
//                }

        } catch(UnknownHostException e) {
            System.err.println("Nao foi possivel conectar-se com o servidor ");
            System.exit(1);
        } catch(IOException e) {
            System.err.println("Nao foi possivel conectar-se com o servidor ");
            System.exit(1);
        }
    }
}
