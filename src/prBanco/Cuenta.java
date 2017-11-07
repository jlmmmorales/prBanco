/**
 * @author José Luis Martín Morales
 * @version 05-11-2017 
 */
package prBanco;

public class Cuenta {
	private int numero; // Numero de cuenta
	private String titular; // Nombre del titular de la cuenta
	private double saldo; // Saldo de la cuenta
	
	public Cuenta(String t, int num, double s){
		if(s<0){
			throw new IllegalArgumentException("ERROR: Saldo inicial negativo.");
		}
		titular = t;
		numero = num;
		saldo = s;
	}
	
	public Cuenta(String t, int num){
		this(t, num, 0.0);
	}
	
	public void ingreso(double i){
		if(i<0){
			throw new IllegalArgumentException("ERROR: Ingreso negativo.");
		}
		saldo = saldo + i;
	}
	
	public void debito(double d){
		saldo = saldo - d;
	}
	
	public String titular(){
		return titular;
	}
	
	public double saldo(){
		return saldo;
	}
	
	public int cuenta(){
		return numero;
	}
	
	@Override
	public String toString(){
		return "("+titular+"/"+numero+") -> "+saldo;
	}
	
	@Override
	public boolean equals(Object o){
		boolean res = false;
		
		if (o instanceof Cuenta){
			Cuenta c = (Cuenta) o;
			res = (numero == c.cuenta());
		}
		
		return res;		
	}
	
	@Override
	public int hashCode(){
		return numero;
	}
}
