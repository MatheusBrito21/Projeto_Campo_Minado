package game.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import game.cm.excecao.ExplosaoException;

public class Campo {
	private int coluna;
	private int linha;
	
	private boolean minado = false;
	private boolean aberto = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	public Campo(int coluna, int linha) {
		this.coluna = coluna;
		this.linha = linha;
	}
	
	public boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna!= vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else if(deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}
		else {
			return false;
		}
	}
	
	//marca o campo
	public void alternarMarcao() {
		if(!aberto) {
			marcado = !marcado;
		}
	}
	
	//abre o campo
	public boolean abrir() {
		if(!aberto && !marcado) {
			aberto = true;
			
			if(minado) {
				throw new ExplosaoException();
			}
			if(vizinhacaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}else {
			return false;			
		}
	}
	
	boolean vizinhacaSegura() {
			return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	public boolean isMarcado() {
		return marcado;
	}
	public boolean isAberto() {
		return aberto;
	}
	
	public void minar() {
		minado = true;
	}
}
