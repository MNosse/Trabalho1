package Conexao;

import model.Evento;
import model.Participante;
import model.Pessoa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Evento evento = new Evento("VILA GAMING", 20.00, null);
    
    public static void main(String args[]) {
        System.out.println("Aguardando conexão...");
        try (
                ServerSocket serverSocket = new ServerSocket(80);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("Conectado");
            System.out.println("contectado com: " + serverSocket.getInetAddress().getHostAddress());
            String inputLine, outputLine = null;
            
            // Initiate conversation with client
            outputLine = "ESCOLHA UM TIPO DE AÇÃO ABAIXO: INSERIR DADOS - 1 DELETAR DADOS - 2 LISTAR DADOS - 3 ATUALIZAR DADOS - 4 ";
            out.println(outputLine);
            
            while ((inputLine = in.readLine()) != null) {
                String texto = "";
                String[] textoSplitado = inputLine.split(";");
                switch (textoSplitado[0]) {
                    case "INSERT": {
                        int idade = Integer.parseInt(textoSplitado[4]);
                        Pessoa pessoa = new Participante(textoSplitado[1], textoSplitado[2], textoSplitado[3], idade);
                        evento.adicionarPessoa(pessoa);
                        break;
                    }
                    case "DELETE": {
                        for(Pessoa p : evento.getPessoas()) {
                            if(p.getCpf().equals(textoSplitado[1])){
                                evento.getPessoas().remove(p);
                            }
                        }
                        break;
                    }
                    case "LIST": {
                        texto = evento.getPessoas().toString();
                        break;
                    }
                    case "UPDATE": {
                        for(Pessoa p : evento.getPessoas()) {
                            if(p.getCpf().equals(textoSplitado[2])) {
                                p.setNome(textoSplitado[3]);
                                p.setEndereco(textoSplitado[4]);
                                texto = "Pessoa atualizada com sucesso";
                            }else{
                                texto = "Pessoa não encontrada";
                            }
                        }
                        break;
                    }
                    case "GET": {
                        for(Pessoa p : evento.getPessoas()){
                            if(p.getCpf().equals(textoSplitado[1])){
                                texto = p.toString();
                            }
                        }
                    }
                    default:
                        texto = "Escolhe certo";
                        break;
                }
                outputLine = texto;
                out.println(outputLine);
                outputLine = "ESCOLHA UM TIPO DE AÇÃO ABAIXO: INSERIR DADOS - 1 DELETAR DADOS - 2 LISTAR DADOS - 3 ATUALIZAR DADOS - 4 ";
                if (outputLine.equals("Bye.")) {
                    break;
                }else{
                    out.println(outputLine);
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + 80 + " or listening for a connection");
            System.out.println(e.getMessage());
        }
        
        try (
                ServerSocket serverSocket =  new ServerSocket(80);
                Socket cliente = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(cliente.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(cliente.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + 80 + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
