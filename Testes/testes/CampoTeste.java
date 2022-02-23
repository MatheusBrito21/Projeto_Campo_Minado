package testes;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.cm.excecao.ExplosaoException;
import game.cm.modelo.Campo;

public class CampoTeste {

	private Campo campo;
	
	//BeforeEach - executado antes dos testes
	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3);
	}
	
	//testa a adi��o de um vizinho ao campo atual(3,3)
	@Test
	void testeAddVizinhoLados() {
		Campo vizinho = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeAddVizinhoDiagonal() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	//testa um campo n�o vizinho de (3,3) - deve retornar false
	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(1, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	
	@Test
	void testaValorMarcado() {
		//o campo por padrao nao esta marcado -retorna false
		assertFalse(campo.isMarcado());
	}
		
	@Test
	void testaAlternarMarcacao1() {
		//marca o campo - retorna true
		campo.alternarMarcao();
		assertTrue(campo.isMarcado());
	}
	@Test
	void testaMarcarEDesmarcar() {
		//clica para marcar e para desmarcar - retorna false
		campo.alternarMarcao();
		campo.alternarMarcao();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testaAbrirCampoNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}
	@Test
	void testaAbrirCampoNaoMinadoMarcado() {
		campo.alternarMarcao();
		assertFalse(campo.abrir());
	}
	@Test
	void testaAbrirCampoMinado() {
		campo.minar();
		
		assertThrows(ExplosaoException.class, () ->{
			campo.abrir();
		});
	}
	
	@Test
	void abrirComVizinhos() {
		Campo campo11 = new Campo(1, 1);
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo11.isAberto() && campo22.isAberto());
	}
	
	@Test 
	void objetivoConcluido(){
		campo.abrir();
		assertTrue(campo.objetivoAlcancado());
	}
		
}
