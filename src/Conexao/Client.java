package Conexao;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    
    public static void main(String args[]) throws IOException {
        BufferedReader mainReader = new BufferedReader(new InputStreamReader(System.in));
        String serverHost;
        int serverPort;
        
        System.out.println("Insira o endereco ip do servidor (ex.: 192.168.0.1)");
        serverHost = mainReader.readLine();
        while(!verificaHost(serverHost)) {
            System.out.println("Insira um endereco ip valido (ex.: 192.168.0.1)");
            serverHost = mainReader.readLine();
        }
        
        System.out.println("Insira a porta que deseja se conectar (ex.: 80)");
        while(true) {
            try {
                serverPort = Integer.parseInt(mainReader.readLine());
                break;
            } catch(NumberFormatException e) {
                System.out.println("Insira uma porta valida (ex.: 80)");
            }
        }
        
        try(Socket conn = new Socket(serverHost, serverPort)) {
            InputStream in = conn.getInputStream();
            OutputStream out = conn.getOutputStream();
            String mensagemAEnviar;
            String mensagemRecebida;

            while(true) {
                System.out.println("Qual operacao voce deseja fazer?\n1 - Criar um evento\n2 - Recuperar dados de um evento existente" +
                        "\n3 - Atualizar dados de um evento existente\n4 - Excluir um evento existente\n5 - Listar eventos existentes" +
                        "\n6 - Listar pessoas de um evento existente\n7 - Listar participantes de um evento existente" +
                        "\n8 - Listar vendedores de um evento existente\n9 - Inserir participante em um evento existente" +
                        "\n10 - Atualizar dados de um participante existente\n11 - Inserir vendedor em um evento exitente" +
                        "\n12 - Atualizar dados de um vendedor existente\n13 - Recuperar dados de uma pessoa existente" +
                        "\n14 - Excluir pessoa de um evento existente\n15 - Desconectar");
                mensagemAEnviar = mainReader.readLine();
                if(mensagemAEnviar != null) {
                    try {
                        switch(Integer.parseInt(mensagemAEnviar)) {
                            case 1:
                                //inserir evento
                                mensagemAEnviar = "CRIAREVENTO;";
                                System.out.println("Digite os dados do evento no formato 'nome;preco;data' (ex.: 'Vila Gaming;50.0;31/12/2022')");
                                mensagemAEnviar += mainReader.readLine();
                                break;
                            case 2:
                                //recuperar dados do evento
                                mensagemAEnviar = "RECUPERAREVENTO;";
                                System.out.println("Digite o nome do evento que deseja recuperar (ex.: 'Vila Gaming')");
                                mensagemAEnviar += mainReader.readLine();
                                break;
                            case 3:
                                // atualiza dados do evento
                                mensagemAEnviar = "ATUALIZAREVENTO;";
                                System.out.println("Digite os dados do evento no formato 'nome;preco;data' (ex.: 'Vila Gaming;50.0;31/12/2022')");
                                System.out.println("Obs.: O nome nao sera alterado pois trata-se da chave primaria");
                                mensagemAEnviar += mainReader.readLine();
                                break;
                            case 4:
                                //deleta um evento
                                mensagemAEnviar = "EXCLUIREVENTO;";
                                System.out.println("Digite o nome do evento que deseja excluir (ex.: 'Vila Gaming')");
                                mensagemAEnviar += mainReader.readLine();
                                break;
                            case 5:
                                //Listar eventos
                                mensagemAEnviar = "LISTAREVENTOS";
                                break;
                            case 6:
                                //Listar pessoas de um evento existente
                                mensagemAEnviar = "LISTARPESSOAS;";
                                System.out.println("Digite o nome do evento em que deseja buscar as pessoas (ex.: 'Vila Gaming')");
                                mensagemAEnviar += mainReader.readLine();
                                break;
                            case 7:
                                //Listar participantes de um evento existente
                                mensagemAEnviar = "LISTARPARTICIPANTES;";
                                System.out.println("Digite o nome do evento em que deseja buscar os participantes (ex.: 'Vila Gaming')");
                                mensagemAEnviar += mainReader.readLine();
                                break;
                            case 8:
                                //Listar vendedores de um evento existente
                                mensagemAEnviar = "LISTARVENDEDORES;";
                                System.out.println("Digite o nome do evento em que deseja buscar os vendedores (ex.: 'Vila Gaming')");
                                mensagemAEnviar += mainReader.readLine();
                                break;
                            case 9:
                                //Inserir participante em um evento existente
                                mensagemAEnviar = "INSERIRPARTICIPANTE;";
                                System.out.println("Digite o nome do evento, em seguida os dados do participante no formato 'nomeEvento;cpf;nomeParticipante;endereco;idade'");
                                System.out.println("(ex.: 'Vila Gaming;16759885015;Mateus Nosse;Rua Carlos Odebrecht n. 26;19')");
                                mensagemAEnviar += mainReader.readLine();
                                break;
                            case 10:
                                //Atualizar dados de um participante existente
                                mensagemAEnviar = "ATUALIZARPARTICIPANTE;";
                                System.out.println("Digite o nome do evento, em seguida os dados do participante no formato 'nomeEvento;cpf;nomeParticipante;endereco;idade' (ex.: 'Vila Gaming;16759885015;Mateus Nosse;Rua Odebrecht n. 26;19')");
                                System.out.println("Obs.: O cpf nao sera alterado pois trata-se da chave primaria");
                                mensagemAEnviar += mainReader.readLine();
                                break;
                            case 11:
                                //Inserir vendedor em um evento exitente
                                mensagemAEnviar = "INSERIRVENDEDOR;";
                                System.out.println("Digite o nome do evento, em seguida os dados do vendedor no formato 'nomeEvento;cpf;nomeVenddor;endereco;ramoDeVenda' ");
                                System.out.println("(ex.: 'Vila Gaming;49467716038;Ana Reinert;Rua Emilio Becker n. 68;Vestuario')");
                                mensagemAEnviar += mainReader.readLine();
                                break;
                            case 12:
                                //Atualizar dados de um vendedor existente
                                mensagemAEnviar = "ATUALIZARVENDEDOR;";
                                System.out.println("Digite o nome do evento, em seguida os dados do vendedor no formato 'nomeEvento;cpf;nomeVendedor;endereco;ramoDeVenda' (ex.: 'Vila Gaming;49467716038;Ana Reinert;Rua Emilio Becker n. 68;Vestuario')");
                                System.out.println("Obs.: O cpf nao sera alterado pois trata-se da chave primaria");
                                mensagemAEnviar += mainReader.readLine();
                                break;
                            case 13:
                                mensagemAEnviar = "RECUPERARPESSOA;";
                                System.out.println("Digite o nome do evento e cpf do participante no formato 'nomeEvento;cpf' (ex.: 'Vila Gaming;16759885015')");
                                mensagemAEnviar += mainReader.readLine();
                                break;
                            case 14:
                                mensagemAEnviar = "EXCLUIRPESSOA;";
                                System.out.println("Digite o nome do evento e cpf do participante no formato 'nomeEvento;cpf' (ex.: 'Vila Gaming;49467716038')");
                                mensagemAEnviar += mainReader.readLine();
                                break;
                            case 15:
                                mensagemAEnviar = "DESCONECTAR";
                            default:
                                System.out.println("Numero invalido");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Informe um numero");
                    }
                }
                out.write(mensagemAEnviar.getBytes());
                if (mensagemAEnviar.equals("DESCONECTAR")) {
                    break;
                }
                byte[] bytesRecebidos = new byte[1024];
                int qtdBytesLidos = in.read(bytesRecebidos);
                mensagemRecebida = new String(bytesRecebidos, 0, qtdBytesLidos);
                System.out.println(mensagemRecebida);
            }
        } catch(UnknownHostException e) {
            System.err.println("Nao foi possivel conectar-se com o servidor ");
            System.exit(1);
        } finally {
            mainReader.close();
        }
    }
    
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
}
