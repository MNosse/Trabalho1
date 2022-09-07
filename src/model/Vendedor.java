package model;

public class Vendedor extends Pessoa{
    private String ramoVenda;
    
    public Vendedor(String cpf, String nome, String endereco, String ramoVenda) {
        super(cpf, nome, endereco);
        this.ramoVenda = ramoVenda;
    }
    
    public String getRamoVenda() {
        return ramoVenda;
    }
    
    public void setRamoVenda(String ramoVenda) {
        this.ramoVenda = ramoVenda;
    }
    
    @Override
    public String toString() {
        return super.toString() + ";" + ramoVenda;
    }
}
