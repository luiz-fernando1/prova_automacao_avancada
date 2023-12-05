package com.luiz.prova1;

public class Cliente extends Thread {

	// Array para analise de cada ativo
	private Analise[] analises = new Analise[4];

	// Tendencia -> true (comprado) false (vendido)
	private boolean[] trend = new boolean[4];

	// Criterios de stoploss
	private boolean[] draw = new boolean[4];

	// Dados Carteira
	private Corretora corretora;
	private Carteira carteira;

	private int lastAsset;
	private boolean lastTipeOp = false;

	public Cliente(String name, Corretora corretora, double saldo) {
		super(name);
		this.corretora = corretora;
		carteira = new Carteira(saldo);
		lastAsset = 10;

		// Analise de ativos individuais
		for (int i = 0; i < analises.length; i++) {
			analises[i] = new Analise(Corretora.publicPRICE.get(i));
			analises[i].start();
		}

	}

	public void run() {
		System.out.println("Cliente " + this.getName() + " criado com saldo de : R$ " + carteira.getSaldo());

		while (Corretora.adicionando()) {
			try {
				for (int i = 0; i < 4; i++) {
					trend[i] = analises[i].tendenciaGeral(); // 0 se compra,1 se vende, 2 se for drawdowm
					draw[i] = analises[i].drawDown();
					verifica(i, trend[i], draw[i]);
				}
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void verifica(int Ativo, boolean tendencia, boolean drawdown) {
		// Verifica se as duas operações sao iguais e se a operação é de compra ou venda
		if ((lastAsset != Ativo) || (lastTipeOp != tendencia)) {

			lastTipeOp = tendencia;
			lastAsset = Ativo;

			// Verifica se é comprado, vendido ou drawdown
			if (tendencia && (drawdown == false)) {
				System.out.println("Tendecia de ALTA p/ ativo " + Ativo + " do cliente " + this.getName());
				comprar(Ativo, tendencia);
			} else if (!tendencia && (drawdown == false)) {
				System.out.println("Tendecia de BAIXA p/ ativo " + Ativo + " do cliente " + this.getName());
				vender(Ativo, 5, tendencia);
			} else if (drawdown == true) {
				System.out.println("DRAWDOWN para o ativo " + Ativo + " do cliente " + this.getName());
				vender(Ativo, 10, tendencia);
			}

		} else {
			// System.out.println("Nao podem realizar duas operacoes iguais seguidas com o
			// mesmo ativo!");
		}

	}

	public void comprar(int ativo, boolean tendencia) {
		try {
			double valorAtual = 0;
			int posicao = Corretora.publicPRICE.get(ativo).size() - 1;
			valorAtual = Corretora.publicPRICE.get(ativo).get(posicao);

			if (carteira.getSaldo() > valorAtual) {
				boolean valida = this.corretora.registrarOperacao("Compra", this.getName(), ativo, valorAtual, posicao,
						carteira.getSaldo());
				if (valida) {
					carteira.registrarCarteira(ativo, valorAtual, tendencia);
				}
				Thread.sleep(500); // Dps q opera espera 500ms
			} else {
				System.out.println(this.getName() + " nao possui saldo para comprar o ativo " + ativo);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void vender(int ativo, int prioridade, boolean tendencia) {
		try {
			if (carteira.temAtivo(ativo)) {
				this.setPriority(prioridade);
				boolean valida = false;
				double valorAtual = 0;
				int posicao = Corretora.publicPRICE.get(ativo).size() - 1;
				valorAtual = Corretora.publicPRICE.get(ativo).get(posicao);

				if (prioridade == 10) {
					valida = this.corretora.registrarOperacao("DRAWDOWN", this.getName(), ativo, valorAtual, posicao,
							carteira.getSaldo());
				} else {
					valida = this.corretora.registrarOperacao("Venda", this.getName(), ativo, valorAtual, posicao,
							carteira.getSaldo());
				}

				if (valida) {
					carteira.registrarCarteira(ativo, valorAtual, tendencia);
				}

				this.setPriority(5);
				Thread.sleep(500); // Dps q opera espera 500ms
			} else {
				// System.out.println(this.getName() + " nao possui o ativo " + ativo + " para
				// venda");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}