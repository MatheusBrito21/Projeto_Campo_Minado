package game.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import game.cm.excecao.ExplosaoException;

public class Tabuleiro {
	private int qtdLinhas;
	private int qtdColunas;
	private int qtdMinas;
	
	private final List<Campo> listaCampos = new ArrayList<>();

	public Tabuleiro(int qtdLinhas, int qtdColunas, int qtdMinas) {
		this.qtdLinhas = qtdLinhas;
		this.qtdColunas = qtdColunas;
		this.qtdMinas = qtdMinas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	/*Recebe as coordenadas do campo que será Marcado
	  */
	public void abrirCampo(int linha, int coluna) {
		try {
			listaCampos.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrir());
		} catch (ExplosaoException e) {
			listaCampos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}
	
	/*Recebe as coordenadas do campo que será Marcado
	  */
	public void marcarCampo(int linha, int coluna) {
		listaCampos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcao());
	}
	
	/*
	 * Gera os campos do tabuleiro
	 * Utiliza a quantidade de linhas e colunas passadas
	 * no construtor*/
	private void gerarCampos() {
		for (int linha = 0; linha < qtdLinhas; linha++) {
			for (int coluna = 0; coluna < qtdColunas; coluna++) {
				listaCampos.add(new Campo(linha,coluna));
			}
		}
	}
	
	private void associarVizinhos() {
		for(Campo c1: listaCampos) {
			for(Campo c2: listaCampos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		//Retorna as minas que serão minadas
		Predicate<Campo> minado = c -> c.isMinado();
		//A quantidade de minas será passada no construtor do Tabuleiro
		do {
			//gera um indice alatorio da lista de campos
			int campoAleatorio = (int)(Math.random()*listaCampos.size());
			//coloca uma mina no indice gerado
			listaCampos.get(campoAleatorio).minar();
			//retorna a quantidade de minas armadas
			minasArmadas = listaCampos.stream().filter(minado).count();
		} while (minasArmadas < qtdMinas);
	}
	
	/*Quando o objetivo de todos os campos for alcançado
	 * o jogo estará ganho*/
	public boolean jogoGanho() {
		return listaCampos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciarJogo() {
		listaCampos.stream().forEach(c -> c.reset());
		sortearMinas();
	}
	
	/*Imprime o Tabuleiro formatado no padrão do Campo Minado*/
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//primeiro espaço da linha das colunas
		sb.append("  ");
		//imprimir os indices das colunas
		for (int c = 0; c < qtdColunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		//pular linha do indice
		sb.append("\n");
		
		int i =0;
		for (int l = 0; l < qtdLinhas; l++) {
			//imprime os indices das linhas
			sb.append(l);
			sb.append(" ");
			
			for (int c = 0; c < qtdColunas; c++) {
				sb.append(" ");
				sb.append(listaCampos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
