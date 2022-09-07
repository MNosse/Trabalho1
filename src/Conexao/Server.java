package Conexao;

import model.Evento;
import model.Participante;
import model.Pessoa;
import model.Vendedor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server {
    public static Map<String, Evento> eventos = new HashMap<>();
    
    public static void main(String args[]) {
        System.out.println("Aguardando conexao...");
        try(ServerSocket serverSocket = new ServerSocket(8080); Socket clientSocket = serverSocket.accept(); PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            serverSocket.setReuseAddress(true);
            System.out.println("contectado com: " + serverSocket.getInetAddress().getHostAddress());
            String mensagemRecebida;
            while((mensagemRecebida = in.readLine()) != null) {
                //recebe
                String texto = "";
                String[] textoSplitado = mensagemRecebida.split(";");
                SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
                Date data = new Date();
                int size = 0;
                switch(textoSplitado[0]) {
                    case "CRIAREVENTO":
                        if (textoSplitado.length == 4) {
                            data = formatter1.parse(textoSplitado[3]);
                            Evento evento = new Evento(textoSplitado[1], Double.parseDouble(textoSplitado[2]), data);
                            if(eventos.get(evento.getNome()) == null) {
                                eventos.put(evento.getNome(), evento);
                                texto = "Evento criado com sucesso";
                            } else {
                                texto = "Evento ja existente";
                            }
                        } else {
                            texto = "Quantidade de parametros diferente do esperado";
                        }
                        
                        break;
                    case "RECUPERAREVENTO":
                        if (textoSplitado.length == 2) {
                            if(eventos.get(textoSplitado[1]) != null) {
                                texto += eventos.get(textoSplitado[1]).toString();
                            } else {
                                texto = "Nao existe um evento com esse nome";
                            }
                        } else {
                            texto = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "ATUALIZAREVENTO":
                        if (textoSplitado.length == 4) {
                            data = formatter1.parse(textoSplitado[3]);
                            if(eventos.get(textoSplitado[1]) != null) {
                                eventos.get(textoSplitado[1]).setNome(textoSplitado[1]);
                                eventos.get(textoSplitado[1]).setPreco(Double.parseDouble(textoSplitado[2]));
                                eventos.get(textoSplitado[1]).setDataRealizacao(data);
                                texto = "Evento atualizado com sucesso";
                            } else {
                                texto = "Nao existe um evento com esse nome";
                            }
                        } else {
                            texto = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "EXCLUIREVENTO":
                        if (textoSplitado.length == 2) {
                            if(eventos.get(textoSplitado[1]) != null) {
                                eventos.remove(textoSplitado[1]);
                                texto = "Evento excluido com sucesso";
                            } else {
                                texto = "Nao existe um evento com esse nome";
                            }
                        } else {
                            texto = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "LISTAREVENTOS":
                        size = eventos.size();
                        if (size > 0) {
                            if (size < 10) {
                                texto += "0";
                            }
                            texto += size+"\n";
                            for (String key : eventos.keySet()) {
                                texto += eventos.get(key).toString()+"\n";
                            }
                        } else {
                            texto = "0";
                        }
                        break;
                    case "LISTARPESSOAS":
                        if (textoSplitado.length == 2) {
                            if(eventos.get(textoSplitado[1]) != null) {
                                List<Pessoa> pessoas = eventos.get(textoSplitado[1]).listaDePessoas();
                                size = pessoas.size();
                                if(size > 0) {
                                    if(size < 10) {
                                        texto += "0";
                                    }
                                    texto += size + "\n";
                                    for(Pessoa pessoa : pessoas) {
                                        texto += pessoa.toString() + "\n";
                                    }
                                } else {
                                    texto = "0";
                                }
                            } else {
                                texto = "Nao existe um evento com esse nome";
                            }
                        } else {
                            texto = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "LISTARPARTICIPANTES":
                        if (textoSplitado.length == 2) {
                            if(eventos.get(textoSplitado[1]) != null) {
                                List<Pessoa> participantes = eventos.get(textoSplitado[1]).listaDeParticipantes();
                                size = participantes.size();
                                if(size > 0) {
                                    if(size < 10) {
                                        texto += "0";
                                    }
                                    texto += size + "\n";
                                    for(Pessoa pessoa : participantes) {
                                        texto += pessoa.toString() + "\n";
                                    }
                                } else {
                                    texto = "0";
                                }
                            } else {
                                texto = "Nao existe um evento com esse nome";
                            }
                        } else {
                            texto = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "INSERIRPARTICIPANTE":
                        if (textoSplitado.length == 6) {
                            if(eventos.get(textoSplitado[1]) != null){
                                eventos.get(textoSplitado[1]).adicionarPessoa(new Participante(textoSplitado[2], textoSplitado[3], textoSplitado[4], Integer.parseInt(textoSplitado[5])));
                            } else {
                                texto = "Nao existe um evento com esse nome";
                            }
                        } else {
                            texto = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "ATUALIZARPARTICIPANTE":
                        //Fazer
                        break;
                    case "LISTARVENDEDORES":
                        if (textoSplitado.length == 2) {
                            if(eventos.get(textoSplitado[1]) != null) {
                                List<Pessoa> vendedores = eventos.get(textoSplitado[1]).listaDeVendedores();
                                size = vendedores.size();
                                if(size > 0) {
                                    if(size < 10) {
                                        texto += "0";
                                    }
                                    texto += size + "\n";
                                    for(Pessoa pessoa : vendedores) {
                                        texto += pessoa.toString() + "\n";
                                    }
                                } else {
                                    texto = "0";
                                }
                            } else {
                                texto = "Nao existe um evento com esse nome";
                            }
                        } else {
                            texto = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "INSERIRVENDEDOR":
                        if (textoSplitado.length == 6) {
                            if(eventos.get(textoSplitado[1]) != null) {
                                eventos.get(textoSplitado[1]).adicionarPessoa(new Vendedor(textoSplitado[2], textoSplitado[3], textoSplitado[4], textoSplitado[5]));
                            } else {
                                texto = "Nao existe um evento com esse nome";
                            }
                        } else {
                            texto = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "ATUALIZARVENDEDOR":
                        //fazer
                        break;
                    case "RECUPERARPESSOA":
                        if ((textoSplitado.length == 3)) {
                            if(eventos.get(textoSplitado[1]) != null) {
                                Evento ev = eventos.get(textoSplitado[1]);
                                if (ev.getPessoas().get(textoSplitado[2]) != null) {
                                    texto = ev.getPessoas().get(textoSplitado[2]).toString();
                                } else {
                                    texto = "Nao existe uma pessoa com esse cpf cadastrada nesse evento";
                                }
                            } else {
                                texto = "Nao existe um evento com esse nome";
                            }
                        } else {
                            texto = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "EXCLUIRPESSOA":
                        if ((textoSplitado.length == 3)) {
                            if(eventos.get(textoSplitado[1]) != null) {
                                Evento ev = eventos.get(textoSplitado[1]);
                                if (ev.getPessoas().get(textoSplitado[2]) != null) {
                                    ev.removerPessoa(textoSplitado[2]);
                                    texto = "Pessoa removida com sucesso";
                                } else {
                                    texto = "Nao existe uma pessoa com esse cpf cadastrada nesse evento";
                                }
                            } else {
                                texto = "Nao existe um evento com esse nome";
                            }
                        } else {
                            texto = "Quantidade de parametros diferente do esperado";
                        }
                        break;
                    case "DESCONECTAR":
                        serverSocket.close();
                        break;
                    default:
                        texto = "Opcao invalida";
                        break;
                }
                //enviar
                out.println(texto);
            }
        } catch(Exception e) {
            System.out.println("Algo deu Errado");
            System.out.println(e.getMessage());
        }
    }
}
