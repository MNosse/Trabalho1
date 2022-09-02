package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evento {
    private String nome;
    private double preco;
    private Date dataRealizacao;
    private List<Pessoa> pessoas;
    
    public Evento(String nome, double preco, Date dataRealizacao) {
        this.nome = nome;
        this.preco = preco;
        this.dataRealizacao = dataRealizacao;
        this.pessoas = new ArrayList<Pessoa>();
    }
    
    public void adicionarPessoa(Pessoa pessoa) {
        pessoas.add(pessoa);
    }
    
    public void removerPessoa(int id) {
        pessoas.remove(id);
    }
    
    public List<Pessoa> listaDeParticipantes() {
        List<Pessoa> participantes = new ArrayList<Pessoa>();
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getClass().equals(Participante.class)) {
                participantes.add(pessoa);
            }
        }
        return participantes;
    }
    
    public List<Pessoa> listaDeVendedores() {
        List<Pessoa> vendedores = new ArrayList<Pessoa>();
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getClass().equals(Vendedor.class)) {
                vendedores.add(pessoa);
            }
        }
        return vendedores;
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
    
    public List<Pessoa> getPessoas() {
        return pessoas;
    }
    
    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
    
    @Override
    public String toString() {
        SimpleDateFormat formatador = new SimpleDateFormat("DD/mm/YYYY");
        String retorno = "Nome: " + nome + ", Preco do ingresso: " + preco + ", Data de realizacao: " + formatador.format(dataRealizacao)+"\nPessoas:\n";
        for (Pessoa pessoa : pessoas) {
            retorno += pessoa.toString() + "\n";
        }
        return retorno;
    }
}
