package model;

import java.text.SimpleDateFormat;
import java.util.*;


public class Evento {
    private String nome;
    private double preco;
    private Date dataRealizacao;
    private Map<String, Pessoa> pessoas;
    
    public Evento(String nome, double preco, Date dataRealizacao) {
        this.nome = nome;
        this.preco = preco;
        this.dataRealizacao = dataRealizacao;
        this.pessoas = new HashMap<String, Pessoa>();
    }
    
    public void adicionarPessoa(Pessoa pessoa) {
        pessoas.put(pessoa.getCpf(), pessoa);
    }
    
    public void removerPessoa(String cpf) {
        pessoas.remove(cpf);
    }
    
    public List<Pessoa> listaDePessoas() {
        List<Pessoa> listaDePessoas = new ArrayList<Pessoa>();
        for (String key : pessoas.keySet()) {
            listaDePessoas.add(pessoas.get(key));
        }
        return listaDePessoas;
    }
    
    public List<Pessoa> listaDeParticipantes() {
        List<Pessoa> listaDeParticipantes = new ArrayList<Pessoa>();
        for (String key : pessoas.keySet()) {
            Pessoa pessoa = pessoas.get(key);
            if (pessoa.getClass().equals(Participante.class)) {
                listaDeParticipantes.add(pessoa);
            }
        }
        return listaDeParticipantes;
    }
    
    public List<Pessoa> listaDeVendedores() {
        List<Pessoa> listaDeVendedores = new ArrayList<Pessoa>();
        for (String key : pessoas.keySet()) {
            Pessoa pessoa = pessoas.get(key);
            if (pessoa.getClass().equals(Vendedor.class)) {
                listaDeVendedores.add(pessoa);
            }
        }
        return listaDeVendedores;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public Date getDataRealizacao() {
        return dataRealizacao;
    }
    
    public void setDataRealizacao(Date dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }
    
    public Map<String, Pessoa> getPessoas() {
        return pessoas;
    }
    
    public void setPessoas(Map<String, Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        sb.append("Nome: ").append(nome).append(", Preco do ingresso: ").append(preco).append(", Data de realizacao: ").append(formatador.format(dataRealizacao)).append("\nPessoas:\n");
        int size = pessoas.size();
        if (size > 0) {
            if(size < 10) {
                sb.append("0");
            }
            sb.append(size);
            for(String key : pessoas.keySet()) {
                sb.append("\n").append(pessoas.get(key).toString());
            }
        } else {
            sb.append("0");
        }
        return sb.toString();
    }
}
