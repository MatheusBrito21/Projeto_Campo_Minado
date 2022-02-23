package game.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import game.cm.excecao.ExplosaoException;

public class Campo {
	private final int coluna;
	private final int linha;
	
	private boolean minado = false;
	private boolean aberto = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	public Campo(int linha, int coluna) {
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
			if(vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}else {
			return false;			
		}
	}
	
	boolean vizinhancaSegura() {
			return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	public boolean isMarcado() {
		return marcado;
	}
	public boolean isAberto() {
		return aberto;
	}
	public boolean isFechado() {
		return !isAberto();
	}
	public boolean isMinado() {
		return minado;
	}
	public void minar() {
		minado = true;
	}
	
	public int getLinha() {
		return linha;
	}
	public int getColuna() {
		return coluna;
	}
	
	
	public boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reset() {
		minado = false;
		aberto = false;
		marcado = false;
	}
	
	public String toString() {
		if(marcado) {
			return "x";
		}else if(aberto && minado) {
			return "*";
		}else if(aberto && minasNaVizinhanca()>0) {
			return Long.toString(minasNaVizinhanca());
		}else if(aberto) {
			return " ";
		}else {
			return "?";
		}
	}

}
