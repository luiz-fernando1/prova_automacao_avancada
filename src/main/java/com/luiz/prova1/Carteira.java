package com.luiz.prova1;

public class Carteira {

	private double saldo;
	public int[] qtdAtivo;

	public Carteira(double _saldo) {
		this.saldo = _saldo;
		qtdAtivo = new int[4];
	}

	public synchronized double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public boolean temAtivo(int ativo) {
		if (qtdAtivo[ativo] < 1) {
			return false;
		}
		return true;
	}

	public void saque(double valor) {
		setSaldo(getSaldo() - valor);
	}

	public void deposito(double valor) {
		setSaldo(getSaldo() + valor);
	}

	public synchronized void registrarCarteira(int ativo, double valor, boolean tipoOperacao) {

		if (tipoOperacao) {
			saque(valor);
			qtdAtivo[ativo]++;

		} else if (!tipoOperacao) {
			deposito(valor);
			qtdAtivo[ativo]--;
		}
	}
}