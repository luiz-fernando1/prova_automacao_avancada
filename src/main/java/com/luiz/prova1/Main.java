package com.luiz.prova1;

public class Main {

	public static void main(String[] args) {
		
		Corretora corretora = new Corretora();
		corretora.start();
		
		Cliente c1 = new Cliente("Luiz", corretora, 1000.00);
		c1.start();
		Cliente c2 = new Cliente("Pedro", corretora, 1000.00);
		c2.start();
		Cliente c3 = new Cliente("Augusto", corretora,1000.00);
		c3.start();
		Cliente c4 = new Cliente("Amanda", corretora, 1000.00);
		c4.start();
		Cliente c5 = new Cliente("Julia", corretora, 1000.00);
		c5.start();
		Cliente c6 = new Cliente("Joao", corretora, 1000.00);
		c6.start();
		Cliente c7 = new Cliente("Fabio", corretora, 1000.00);
		c7.start();
		Cliente c8 = new Cliente("Rafael", corretora, 1000.00);
		c8.start();
		Cliente c9 = new Cliente("Julio", corretora, 1000.00);
		c9.start();
		Cliente c10 = new Cliente("Henrique", corretora, 1000.00);
		c10.start();
	}

}
