package model;

public class Participante extends Pessoa{
    private int idade;
    
    public Participante(String cpf, String nome, String endereco, int idade) {
        super(cpf, nome, endereco);
        this.idade = idade;
    }
    
    public int getIdade() {
        return idade;
    }
    
    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Idade: " + idade;
    }
}
