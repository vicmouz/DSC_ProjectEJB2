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
import softwarecorporativo.entidade.TipoProduto;
import softwarecorporativo.servico.TipoProdutoServico;

/**
 *
 * @author marcosbrasil98
 */
public class TipoProdutoTest extends Teste{
  
   private TipoProdutoServico tipoprodutoservico;
  
    @Before
    public void setUp() throws NamingException {
        tipoprodutoservico = (TipoProdutoServico) container.getContext().lookup("java:global/classes/ejb/TipoProdutoServico!softwarecorporativo.servico.TipoProdutoServico");
    }
    
    @After
    public void tearDown() {
        tipoprodutoservico = null;
    }
    
    
    
    @Test
    public void existeTipoProduto() {
        TipoProduto tipoproduto = tipoprodutoservico.criar();
        tipoproduto.setNome("Camisa");
        assertTrue(tipoprodutoservico.existe(tipoproduto));
    }
    
    @Test
    public void getTipoProdutoPorNome() {
        TipoProduto tipoproduto = tipoprodutoservico.consultarPorNome("Chaveiro");
        assertNotNull(tipoproduto);
        assertEquals("Chaveiro", tipoproduto.getNome());
    }
    
      
    @Test
    public void getTipoProdutoPorId() {
        assertNotNull(tipoprodutoservico.consultarPorId(new Long(3)));
    }
    
    @Test
    public void persistir() {
        TipoProduto tipoProduto = tipoprodutoservico.criar();
        tipoProduto.setId(new Long(8));
        tipoProduto.setNome("Travesseiro");
        tipoprodutoservico.persistirTipoProduto(tipoProduto);
        assertNotNull(tipoProduto.getId());
        
    }
    
    }

