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
		
		/*Calcula a diferança absoluta entre as coordenadas
		 * do campo atual e do vizinho
		 * ex: C(3,3) - V(3,2) = 1 ; C(3,3) - (2,2) = 2;
		 * O delta geral deve ser igual a 1(lados-cima) ou 2(diagonal)
		 * */
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;
		
		if(deltaGeral == 1) {
			vizinhos.add(vizinho);
			return true;
		}else if(deltaGeral == 2) {
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
	
	/*Verifica quais vizinhos estão minados
	 * */
	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
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
	
	
	public boolean isMarcado() {
		return marcado;
	}
	public boolean isAberto() {
		return aberto;
	}
	public boolean isFechado() {
		return !isAberto();
	}
	
	public void setAberto(boolean aberto) {
		this.aberto = aberto;
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
