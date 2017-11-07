
package prBanco;
/**
 * La clase Banco modela una entidad bancaria cuyas cuentas habr� que gestionar
 * 
 * @author Jos� Luis Mart�n Morales
 * @version Version 0.1 Esquema Inicial
 * 
 */
public class Banco {
	private static final int NCTAS = 10; //n�mero de cuentas m�ximo en el banco
	private static final int NUMCTALIBRE = 1001; //primer n�mero de cuenta
	
	private String nombre; //nombre del banco
	private Cuenta [] ctas; //colecci�n de cuentas
	private int ppl; //primera posicion libre del array
	private int unca;//primer n�mero de cuenta disponible
	
	/**
	 * M�todo para crear un objeto Banco dado su nombre y el n�mero m�ximo 
	 * de cuentas que gestiona (longitud de ctas).
	 * 
	 * @param nombre	Nombre del banco
	 * @param tamArray  Tama�o del array ctas
	 */
	public Banco(String nombre, int tamArray){
		this.nombre = nombre;
		ctas = new Cuenta[tamArray];
		ppl = 0;
		unca = NUMCTALIBRE;
	}
	
	public Banco (String nombre){
		this.nombre = nombre;
		ctas = new Cuenta[NCTAS];
		ppl = 0;
		unca = NUMCTALIBRE;
	}
	
	/**
	 * M�todo que abre una nueva cuenta para el cliente indicado.
	 * 
	 * @param nombre Nombre del cliente
	 * @param saldo  Saldo inicial de la cuenta
	 * @return El n�mero indentificador de la nueva cuenta creada
	 */
	public int abrirCuenta(String nombre,double saldo){
		
		if(ppl == ctas.length){			
			// El array esta lleno de cuentas, tiene que doblar su espacio
			doblarEspacioArrayCuentas();			
		}
		int numeroCuenta = unca;
		ctas[ppl] =  new Cuenta(nombre, numeroCuenta, saldo);
		unca++;
		ppl++;
		return numeroCuenta;
	}
	
	public int abrirCuenta(String nombre){
		if(ppl == ctas.length){			
			// El array esta lleno de cuentas, tiene que doblar su espacio
			doblarEspacioArrayCuentas();			
		}
		int numeroCuenta = unca;
		ctas[ppl] =  new Cuenta(nombre, numeroCuenta);
		unca++;
		ppl++;
		return numeroCuenta;
	}
	
	/**
	 * Dobla el espacio del Array de cuentas creando un nuevo array el dobre de grande
	 * y copiando toda la informaci�n de un array al otro
	 * 
	 */
	private void doblarEspacioArrayCuentas() {
		Cuenta [] nuevoCtas = new Cuenta[ctas.length*2];
		copiarCuentas(ctas, nuevoCtas);
		ctas = nuevoCtas;		
	}
	
	/**
	 * Copia las cuentas de un array peque�o a otro m�s grande en el mismo orden que estaban.
	 * 
	 * @param antiguoCtas contiene el array antiguo de cuentas
	 * @param nuevoCtas tendr� el nuevo array m�s grande con los datos de las cuentas
	 */
	private void copiarCuentas(Cuenta[] antiguoCtas, Cuenta[] nuevoCtas) {
		for(int i=0; i<ppl; i++){
			nuevoCtas[i] = antiguoCtas[i];
		}
		
	}	
	
	/**
	 * M�todo que cierra y elimina de la colecci�n la cuenta con el identificador dado
	 * 
	 * @param numCuenta  Identificador de la cuenta que hay que eliminar
	 */
	public void cerrarCuenta(int numCuenta){
		int posicionCuenta = posicionCuenta(numCuenta);
		if(posicionCuenta<0){
			throw new BancoException("ERROR: El n�mero de cuenta "+numCuenta+" no existe.");
		}
		
		ctas[posicionCuenta] = null;
		moverCuentas(posicionCuenta);
		ppl--;		
	}
	
	/**
	 * Mueve las cuentas compactando hacia la cabeza del array ocupando la 
	 * posici�n libre definida por posicionCuenta
	 * 
	 * @param posicionCuenta
	 */
	private void moverCuentas(int posicionCuenta) {
		
		for(int i=posicionCuenta; i < (ppl - 1); i++){
			ctas[i] = ctas[i+1];
		}
		
	}

	/**
	 *  M�todo auxiliar que devuelve la posici�n del array en que se encuentra la cuenta
	 *  cuyo identificador se pasa como par�metro
	 * 
	 * @param  numCuenta Identificador de la cuenta a buscar
	 * @return Un n�mero entre 0 y ctas.length-1, que indica la posici�n de la cuenta en el array
	 * @throws Lanza una excepci�n de tipo RuntimeException en caso de que no existe una cuenta
	 *  		en ctas con el n�mero de cuenta numCuenta.
	 */
	private int posicionCuenta(int numCuenta){
		int posicion = -1;
		int i = 0;
		
		while ((posicion == -1) && (i<ppl)){
			if(numCuenta == ctas[i].cuenta()){
				// He encontrado la cuenta
				posicion = i;
			}
			i++;
		}
		return posicion;
	}
	
	public void ingreso(int numCuenta, double cantidad){
		int posicion = posicionCuenta(numCuenta);
		if(posicion<0){
			throw new BancoException("ERROR: El n�mero de cuenta "+numCuenta+" no existe.");
		}
		try{
			ctas[posicion].ingreso(cantidad);
		}catch (IllegalArgumentException e){
			throw new BancoException("ERROR: Se intenta hacer un ingreso negativo de "+cantidad);
		}		
	}
	
	public double debito(int numCuenta, double cantidad){
		int posicion = posicionCuenta(numCuenta);
		if(posicion<0){
			throw new BancoException("ERROR: El n�mero de cuenta "+numCuenta+" no existe.");
		}
		double cantidadPagada = 0.0;
		double saldoCuenta = ctas[posicion].saldo();
		
		if(cantidad > saldoCuenta){
			ctas[posicion].debito(saldoCuenta);
			cantidadPagada = saldoCuenta;
		} else {
			ctas[posicion].debito(cantidad);
			cantidadPagada = cantidad;
		}
		
		return cantidadPagada;
		
	}
	
	public double saldo(int numCuenta){
		int posicion = posicionCuenta(numCuenta);
		if(posicion<0){
			throw new BancoException("ERROR: El n�mero de cuenta "+numCuenta+" no existe.");
		}
		
		return ctas[posicion].saldo();		
	}
	
	public double transferencia(int cuentaOrigen, int cuentaDestino, double cantidad){
		
		double pagado = debito(cuentaOrigen,cantidad);
		ingreso(cuentaDestino,pagado);
		
		return pagado;
	}
	
	/**
	 * M�todo que define la representaci�n textual del estado de los objetos de la clase
	 * Banco.
	 * El formato deseado es [(NombreCliente/NumCuenta) -> Saldo]
	 */
	public String toString(){
		// TubbiesBank: [[(Dixy/1002) -> 600.0] [(Tinky Winky/1003) -> 400.0] [(Lala/1004) -> 600.0] ]
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<ppl; i++){
			sb.append("["+ctas[i].toString()+"] ");
		}
		return nombre+": ["+sb.toString()+"]";
	}
}
