package game.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import game.cm.excecao.ExplosaoException;
import game.cm.excecao.SairException;
import game.cm.modelo.Tabuleiro;

public class TabuleiroConsole {
	
	private Tabuleiro tabuleiro;
	private Scanner ent =  new Scanner(System.in);
	
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuarJogo = true;
			
			while(continuarJogo) {
				cicloDoJogo();
				System.out.println("Jogar Novamente? (S/n)");
				String resposta = ent.next();
				
				if(resposta.equalsIgnoreCase("n")) {
					continuarJogo = false;
					throw new SairException();
				}else {
					tabuleiro.reiniciarJogo();
				}
			}
			
		} catch (SairException e) {
			System.out.println("Obrigado por jogar :)");
		}finally {
			ent.close();
		
		}
		
	}
	
	private void cicloDoJogo() {
		try {
			while (!tabuleiro.jogoGanho()) {
				System.out.println(tabuleiro);
				
				String digitado = capturarValorDigitado("Digite(x,y):");
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
				.map(e -> Integer.parseInt(e.trim())).iterator();
				
				digitado = capturarValorDigitado("1 - Abrir ou 2 (Des)Marcar:");
				
				if(digitado.equals("1")) {
					tabuleiro.abrirCampo(xy.next(), xy.next());
				}else if(digitado.equals("2")) {
					tabuleiro.marcarCampo(xy.next(), xy.next());
				}
				
			}
			
			System.out.println(tabuleiro);
			System.out.println("Você ganhou!!!");
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Fim de Jogo :(");
		}
	}
	
	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = ent.nextLine();
		
		if("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		return digitado;
	}
	
	
}
