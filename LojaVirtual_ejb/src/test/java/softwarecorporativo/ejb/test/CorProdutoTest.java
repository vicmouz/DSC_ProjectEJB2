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
import softwarecorporativo.entidade.CorProduto;
import softwarecorporativo.servico.CorProdutoServico;

/**
 *
 * @author marcosbrasil98
 */
public class CorProdutoTest extends Teste {
    
    private CorProdutoServico corProdutoServico;
    
    @Before
    public void setUp() throws NamingException {
        corProdutoServico = (CorProdutoServico) container.getContext().lookup("java:global/classes/ejb/CorProdutoServico!softwarecorporativo.servico.CorProdutoServico");
    }
    @After
    public void tearDown() {
        corProdutoServico = null;
    }
    
    
    
    @Test
    public void existeCor() {
        CorProduto corProduto = corProdutoServico.criar();
        corProduto.setTipo("Caneca");
        assertTrue(corProdutoServico.existe(corProduto));
    }
    
    @Test
    public void getCorPorTipo() {
        CorProduto corProduto = corProdutoServico.consultarPorTipo("Camisa");
        assertNotNull(corProduto);
        assertEquals("Amarelo", corProduto.getNome());
    }
    
   
    
    @Test
    public void getCorPorId() {
        assertNotNull(corProdutoServico.consultarPorId(new Long(2)));
    }
    
    @Test
    public void persistir() {   
        
        CorProduto corProduto = corProdutoServico.criar();
        corProduto.setId(6l);
        corProduto.setNome("Azulado");
        corProduto.setTipo("Teste");
        corProdutoServico.persistirCorProduto(corProduto);
        assertNotNull(corProduto.getId());
        
    }
    
    @Test
    public void atualizar() {
        CorProduto corProduto = corProdutoServico.consultarPorId(new Long(2));
        corProduto.setNome("Cor nova"); 
        corProdutoServico.atualizar(corProduto);
        corProduto = corProdutoServico.consultarPorId(new Long(2));
        assertEquals("Cor nova", corProduto.getNome());
    }
    
}
