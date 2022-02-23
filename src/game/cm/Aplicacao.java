package game.cm;

import game.cm.modelo.Tabuleiro;

public class Aplicacao {
	public static void main(String[] args) {
		
		
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		
		tabuleiro.abrirCampo(0, 0);
		tabuleiro.marcarCampo(1, 2);
	
		
		System.out.println(tabuleiro);
	}
}
