package game.cm;

import game.cm.modelo.Tabuleiro;
import game.cm.visao.TabuleiroConsole;

public class Aplicacao {
	public static void main(String[] args) {
		//chama o construtor do tabuleiro 
		//que inicia o jogo atraves do metodo executarJogo() em TabuleiroConsole
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		new TabuleiroConsole(tabuleiro);
	}
}
