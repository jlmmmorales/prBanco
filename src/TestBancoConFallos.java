/**
 * @author José Luis Martín Morales
 * @version 05-11-2017
 */


import prBanco.Banco;
import prBanco.BancoException;

public class TestBancoConFallos {

	public static void main(String[] args) {
		Banco b = new Banco("Bank", 4);
		int nPo = b.abrirCuenta("Po", 0);
		int nDixy = b.abrirCuenta("Dixy", 500);
		int nTinkyWinky = b.abrirCuenta("Tinky Winky", 200);
		try{
			b.ingreso(nPo, -200);			
		}catch (BancoException e){
			System.err.println(e.getMessage());
		}
		try{
			b.ingreso(1010, 300);		
		}catch (BancoException e){
			System.err.println(e.getMessage());
		}
		try{
			b.debito(1010, 100);
		}catch (BancoException e){
			System.err.println(e.getMessage());
		}
		try{
			b.cerrarCuenta(1010);			
		}catch (BancoException e){
			System.err.println(e.getMessage());
		}
		try{
			b.saldo(1010);			
		}catch (BancoException e){
			System.err.println(e.getMessage());
		}		
			
		System.out.println(b);

	}

}
