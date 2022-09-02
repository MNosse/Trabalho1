package Conexao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        String serverHost;
        int serverPort;
        System.out.println("Insira o endereco ip do servidor (ex.: 192.168.0.1)");
        while (true) {
            serverHost = scanner.next();
            String[] bytes = serverHost.split("\\.");
            try {
                for(int i = 0; i < 4; i++) {
                    Integer.parseInt(bytes[i]);
                }
                break;
            } catch(NumberFormatException e) {
                System.out.println("Insira um endereco ip valido (ex.: 192.168.0.1)");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Insira um endereco ip valido (ex.: 192.168.0.1)");
            }
        }
        System.out.println("Insira a porta que deseja se conectar (ex.: 80");
        while (true) {
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
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equalsIgnoreCase("Sair"))
                    break;
                
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
            System.exit(1);
        }
    }
}
