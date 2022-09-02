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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {
    private static final List<Evento> eventos = new ArrayList<>();
    
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
            outputLine = "ESCOLHA UM TIPO DE AÇÃO ABAIXO: INSERIR DADOS de participante - 1 DELETAR DADOS - 2 LISTAR DADOS - 3 ATUALIZAR DADOS - 4 ";
            out.println(outputLine);
            
            while ((inputLine = in.readLine()) != null) {
                String texto = "";
                String[] textoSplitado = inputLine.split(";");
                switch (textoSplitado[0]) {
                    case "CRIAREVENTO":
                        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
                        Date data = formatter1.parse(textoSplitado[3]);
                        eventos.add(new Evento(textoSplitado[1], Double.parseDouble(textoSplitado[2]), data));
                        break;
                    case "RECUPERAREVENTO":
                        for(Evento e : eventos){
                         texto += e.toString() + "\n";
                        }
                        break;
                    case "ATUALIZAREVENTO":
                        break;
                    case "EXCLUIREVENTO":
                        break;
                    case "LISTARPARTICIPANTES":
                        break;
                    case "INSERIRPARTICIPANTE":
                        break;
                    case "ATUALIZARPARTICIPANTE":
                        break;
                    case "INSERIRVENDEDOR":
                        break;
                    case "ATUALIZARVENDEDOR":
                        break;
                    case "RECUPERARPESSOA":
                        break;
                    case "EXCLUIRPESSOA":
                        break;
                    default:
                        texto = "Escolhe certo";
                        break;
                }
                outputLine = texto;
                out.println(outputLine);
                if (outputLine.equals("Bye.")) {
                    break;
                }else{
                    out.println(outputLine);
                }
            }
        } catch (Exception e) {
            System.out.println("Algo deu Errado");
            System.out.println(e.getMessage());
        }
    }
}
