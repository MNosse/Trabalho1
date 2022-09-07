package Conexao;

import model.Evento;
import model.Participante;
import model.Pessoa;
import model.Vendedor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server {
    public static Map<String, Evento> eventos = new HashMap<>();
    
    public static void main(String args[]) throws IOException {
        BufferedReader mainReader = new BufferedReader(new InputStreamReader(System.in));
        int serverPort;
    
        System.out.println("Insira a porta deseja utilizar no server (ex.: 80)");
        while(true) {
            try {
                serverPort = Integer.parseInt(mainReader.readLine());
                break;
            } catch(NumberFormatException | IOException e) {
                System.out.println("Insira uma porta valida (ex.: 80)");
            }
        }
        
        
    
        ServerSocket serverSocket = new ServerSocket(serverPort);
        serverSocket.setReuseAddress(true);
        while(true) {
            System.out.println("Aguardando conexao...");
            try(Socket clientSocket = serverSocket.accept()) {
                System.out.println("contectado com: " + clientSocket.getInetAddress().getHostAddress());
                InputStream in = clientSocket.getInputStream();
                OutputStream out = clientSocket.getOutputStream();
                byte[] bytesRecebidos = new byte[1024];
                int qtdBytesLidos = in.read(bytesRecebidos);
                String mensagemRecebida = new String(bytesRecebidos, 0, qtdBytesLidos);
                String mensagemAEnviar;
                String[] mensagemSplitada = mensagemRecebida.split(";");
                switch(mensagemSplitada[0]) {
                    case "CRIAREVENTO":
                        if(mensagemSplitada.length == 4) {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                mensagemAEnviar = criarEvento(mensagemSplitada[1], Double.parseDouble(mensagemSplitada[2]), formatter.parse(mensagemSplitada[3]));
                            } catch(ParseException e) {
                                mensagemAEnviar = "Informe uma data valida";
                            } catch(NumberFormatException e) {
                                mensagemAEnviar = "Informe um preco valido";
                            }
                        } else {
                            mensagemAEnviar = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "RECUPERAREVENTO":
                        if(mensagemSplitada.length == 2) {
                            mensagemAEnviar = recuperarEvento(mensagemSplitada[1]);
                        } else {
                            mensagemAEnviar = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "ATUALIZAREVENTO":
                        if(mensagemSplitada.length == 4) {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                mensagemAEnviar = atualizarEvento(mensagemSplitada[1], Double.parseDouble(mensagemSplitada[2]), formatter.parse(mensagemSplitada[3]));
                            } catch(ParseException e) {
                                mensagemAEnviar = "Informe uma data valida";
                            } catch(NumberFormatException e) {
                                mensagemAEnviar = "Informe um preco valido";
                            }
                        } else {
                            mensagemAEnviar = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "EXCLUIREVENTO":
                        if(mensagemSplitada.length == 2) {
                            mensagemAEnviar = excluirEvento(mensagemSplitada[1]);
                        } else {
                            mensagemAEnviar = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "LISTAREVENTOS":
                        mensagemAEnviar = listarEventos();
                        break;
                    case "LISTARPESSOAS":
                        if(mensagemSplitada.length == 2) {
                            mensagemAEnviar = listarPessoas(mensagemSplitada[1]);
                        } else {
                            mensagemAEnviar = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "LISTARPARTICIPANTES":
                        if(mensagemSplitada.length == 2) {
                            mensagemAEnviar = listarParticipantes(mensagemSplitada[1]);
                        } else {
                            mensagemAEnviar = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "INSERIRPARTICIPANTE":
                        if(mensagemSplitada.length == 6) {
                            try {
                                mensagemAEnviar = inserirParticipante(mensagemSplitada[1], mensagemSplitada[2], mensagemSplitada[3], mensagemSplitada[4], Integer.parseInt(mensagemSplitada[5]));
                            } catch(NumberFormatException e) {
                                mensagemAEnviar = "Informe uma idade valida";
                            }
                        } else {
                            mensagemAEnviar = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "ATUALIZARPARTICIPANTE":
                        if(mensagemSplitada.length == 6) {
                            try {
                                mensagemAEnviar = atualizarParticipante(mensagemSplitada[1], mensagemSplitada[2], mensagemSplitada[3], mensagemSplitada[4], Integer.parseInt(mensagemSplitada[5]));
                            } catch(NumberFormatException e) {
                                mensagemAEnviar = "Informe uma idade valida";
                            }
                        } else {
                            mensagemAEnviar = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "LISTARVENDEDORES":
                        if(mensagemSplitada.length == 2) {
                            mensagemAEnviar = listarVendedores(mensagemSplitada[1]);
                        } else {
                            mensagemAEnviar = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "INSERIRVENDEDOR":
                        if(mensagemSplitada.length == 6) {
                            mensagemAEnviar = inserirVendedor(mensagemSplitada[1], mensagemSplitada[2], mensagemSplitada[3], mensagemSplitada[4], mensagemSplitada[5]);
                        } else {
                            mensagemAEnviar = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "ATUALIZARVENDEDOR":
                        if(mensagemSplitada.length == 6) {
                            mensagemAEnviar = atualizarVendedor(mensagemSplitada[1], mensagemSplitada[2], mensagemSplitada[3], mensagemSplitada[4], mensagemSplitada[5]);
                        } else {
                            mensagemAEnviar = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "RECUPERARPESSOA":
                        if((mensagemSplitada.length == 3)) {
                            mensagemAEnviar = recuperarPessoa(mensagemSplitada[1], mensagemSplitada[2]);
                        } else {
                            mensagemAEnviar = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "EXCLUIRPESSOA":
                        if((mensagemSplitada.length == 3)) {
                            mensagemAEnviar = excluirPessoa(mensagemSplitada[1], mensagemSplitada[2]);
                        } else {
                            mensagemAEnviar = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    default:
                        mensagemAEnviar = "Opcao invalida";
                        break;
                }
                out.write(mensagemAEnviar.getBytes());
            } catch(Exception e) {
                System.out.println("Algo deu Errado");
                System.out.println(e.getMessage());
            }
        }
    }
    
    private static String criarEvento(String nome, double preco, Date dataRealizacao) {
        Evento eventoCriado = new Evento(nome, preco, dataRealizacao);
        Evento eventoExistente = eventos.get(nome);
        if(eventoExistente == null) {
            eventos.put(nome, eventoCriado);
            return "Evento criado com sucesso";
        } else {
            return "Evento ja existente";
        }
    }
    
    private static String recuperarEvento(String nome) {
        Evento evento = eventos.get(nome);
        if(evento != null) {
            return evento.toString();
        } else {
            return "Nao existe um evento com esse nome";
        }
    }
    
    private static String atualizarEvento(String nome, double preco, Date dataRealizacao) {
        Evento evento = eventos.get(nome);
        if(evento != null) {
            evento.setPreco(preco);
            evento.setDataRealizacao(dataRealizacao);
            return "Evento atualizado com sucesso";
        } else {
            return "Nao existe um evento com esse nome";
        }
    }
    
    private static String excluirEvento(String nome) {
        if(eventos.get(nome) != null) {
            eventos.remove(nome);
            return "Evento excluido com sucesso";
        } else {
            return "Nao existe um evento com esse nome";
        }
    }
    
    private static String listarEventos() {
        int size = eventos.size();
        if (size > 0) {
            String retorno = "";
            if (size < 10) {
                retorno = "0";
            }
            retorno += size;
            for (String key : eventos.keySet()) {
                retorno += "\n"+eventos.get(key).toString();
            }
            return retorno;
        } else {
            return "0";
        }
    }
    
    private static String listarPessoas(String nome) {
        Evento evento = eventos.get(nome);
        if(evento != null) {
            List<Pessoa> pessoas = evento.listaDePessoas();
            int size = pessoas.size();
            if(size > 0) {
                String retorno = "";
                if(size < 10) {
                    retorno = "0";
                }
                retorno += size;
                for(Pessoa pessoa : pessoas) {
                    retorno += "\n" + pessoa.toString();
                }
                return retorno;
            } else {
                return "0";
            }
        } else {
            return "Nao existe um evento com esse nome";
        }
    }
    
    private static String listarParticipantes(String nome) {
        Evento evento = eventos.get(nome);
        if(evento != null) {
            List<Pessoa> participantes = evento.listaDeParticipantes();
            int size = participantes.size();
            if(size > 0) {
                String retorno = "";
                if(size < 10) {
                    retorno = "0";
                }
                retorno += size;
                for(Pessoa participante : participantes) {
                    retorno += "\n" + participante.toString();
                }
                return retorno;
            } else {
                return "0";
            }
        } else {
            return "Nao existe um evento com esse nome";
        }
    }
    
    private static String inserirParticipante(String nomeEvento, String cpf, String nomeParticipante, String endereco, int idade) {
        Evento evento = eventos.get(nomeEvento);
        if(evento != null){
            if (evento.getPessoas().get(cpf) == null) {
                evento.adicionarPessoa(new Participante(cpf, nomeParticipante, endereco, idade));
                return "Participante inserido com sucesso";
            } else {
                return "Ja existe uma pessoa com esse CPF";
            }
        } else {
            return "Nao existe um evento com esse nome";
        }
    }
    
    private static String atualizarParticipante(String nomeEvento, String cpf, String nomeParticipante, String endereco, int idade) {
        Evento evento = eventos.get(nomeEvento);
        if(evento != null){
            Map<String, Pessoa> pessoas = evento.getPessoas();
            Pessoa pessoa = pessoas.get(cpf);
            if (pessoa != null) {
                if (pessoa.getClass().equals(Participante.class)) {
                    Participante participante = new Participante(cpf, nomeParticipante, endereco, idade);
                    pessoas.replace(cpf, pessoa, participante);
                    return "Participante atualizado com sucesso";
                } else {
                    return "Esse cpf pertence a um vendedor";
                }
            } else {
                return "Nao existe uma pessoa com esse CPF";
            }
        } else {
            return "Nao existe um evento com esse nome";
        }
    }
    
    private static String listarVendedores(String nome) {
        Evento evento = eventos.get(nome);
        if(evento != null) {
            List<Pessoa> vendedores = evento.listaDeVendedores();
            int size = vendedores.size();
            if(size > 0) {
                String retorno = "";
                if(size < 10) {
                    retorno = "0";
                }
                retorno += size;
                for(Pessoa vendedor : vendedores) {
                    retorno += "\n" + vendedor.toString();
                }
                return retorno;
            } else {
                return "0";
            }
        } else {
            return "Nao existe um evento com esse nome";
        }
    }
    
    private static String inserirVendedor(String nomeEvento, String cpf, String nomeParticipante, String endereco, String ramoVenda) {
        Evento evento = eventos.get(nomeEvento);
        if(evento != null){
            if (evento.getPessoas().get(cpf) == null) {
                evento.adicionarPessoa(new Vendedor(cpf, nomeParticipante, endereco, ramoVenda));
                return "Vendedor inserido com sucesso";
            } else {
                return "Ja existe uma pessoa com esse CPF";
            }
        } else {
            return "Nao existe um evento com esse nome";
        }
    }
    
    private static String atualizarVendedor(String nomeEvento, String cpf, String nomeVendedor, String endereco, String ramoVenda) {
        Evento evento = eventos.get(nomeEvento);
        if(evento != null){
            Map<String, Pessoa> pessoas = evento.getPessoas();
            Pessoa pessoa = pessoas.get(cpf);
            if (pessoa != null) {
                if (pessoa.getClass().equals(Vendedor.class)) {
                    Vendedor vendedor = new Vendedor(cpf, nomeVendedor, endereco, ramoVenda);
                    pessoas.replace(cpf, pessoa, vendedor);
                    return "Vendedor atualizado com sucesso";
                } else {
                    return "Esse cpf pertence a um participante";
                }
            } else {
                return "Nao existe uma pessoa com esse CPF";
            }
        } else {
            return "Nao existe um evento com esse nome";
        }
    }
    
    public static String recuperarPessoa(String nomeEvento, String cpf) {
        Evento evento = eventos.get(nomeEvento);
        if(evento != null) {
            Pessoa pessoa = evento.getPessoas().get(cpf);
            if (pessoa != null) {
                return pessoa.toString();
            } else {
                return "Nao existe uma pessoa com esse cpf cadastrada nesse evento";
            }
        } else {
            return "Nao existe um evento com esse nome";
        }
    }
    
    private static String excluirPessoa(String nomeEvento, String cpf) {
        Evento evento = eventos.get(nomeEvento);
        if(evento != null) {
            Pessoa pessoa = evento.getPessoas().get(cpf);
            if (pessoa != null) {
                evento.removerPessoa(cpf);
                return "Pessoa removida com sucesso";
            } else {
                return "Nao existe uma pessoa com esse cpf cadastrada nesse evento";
            }
        } else {
            return "Nao existe um evento com esse nome";
        }
    }
}
