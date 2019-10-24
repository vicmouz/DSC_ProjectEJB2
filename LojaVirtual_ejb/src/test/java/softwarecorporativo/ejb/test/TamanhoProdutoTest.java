/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarecorporativo.ejb.test;

import javax.naming.NamingException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import softwarecorporativo.entidade.TamanhoProduto;
import softwarecorporativo.servico.TamanhoServico;

/**
 *
 * @author marcosbrasil98
 */
public class TamanhoProdutoTest extends Teste {
   
     private TamanhoServico tamanhoservico;
    
    @Before
    public void setUp() throws NamingException {
        tamanhoservico = (TamanhoServico) container.getContext().lookup("java:global/classes/ejb/TamanhoServico!softwarecorporativo.servico.TamanhoServico");
    }
    @After
    public void tearDown() {
        tamanhoservico = null;
    }
    
    
    
    @Test
    public void existeTamanho() {
        TamanhoProduto tamanhoProduto = tamanhoservico.criar();
        tamanhoProduto.setTipo("Caneca");
        assertTrue(tamanhoservico.existe(tamanhoProduto));
    }
    
    @Test
    public void getTamanhoPorTipo() {
        TamanhoProduto tamanhoProduto = tamanhoservico.consultarPorTipo("Camisa");
        assertNotNull(tamanhoProduto);
        assertEquals("Médio", tamanhoProduto.getNome());
    }
    
   
    
    @Test
    public void getTamanhoPorId() {
        assertNotNull(tamanhoservico.consultarPorId(new Long(2)));
    }
    
    @Test
    public void persistir() {   
        
        TamanhoProduto tamanhoProduto = tamanhoservico.criar();
        tamanhoProduto.setId(9l);
        tamanhoProduto.setAltura(23.4);
        tamanhoProduto.setLargura(23.1);
        tamanhoProduto.setNome("Teste");
        tamanhoProduto.setTipo("Testinho");
        tamanhoservico.persistirTamanhoProduto(tamanhoProduto);
        assertNotNull(tamanhoProduto.getId());
        
    }
    
    @Test
    public void atualizar() {
        TamanhoProduto tamanhoProduto = tamanhoservico.consultarPorId(new Long(2));
        tamanhoProduto.setNome("Sei lá"); 
        tamanhoservico.atualizar(tamanhoProduto);
        tamanhoProduto = tamanhoservico.consultarPorId(new Long(2));
        assertEquals("Sei lá", tamanhoProduto.getNome());
    }
    
   
    
    
}
