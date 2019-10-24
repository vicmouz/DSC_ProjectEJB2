/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarecorporativo.ejb.test;

import java.util.List;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import softwarecorporativo.entidade.CorProduto;
import softwarecorporativo.entidade.ImagemProduto;
import softwarecorporativo.entidade.Produto;
import softwarecorporativo.entidade.TipoProduto;
import softwarecorporativo.servico.ProdutoServico;

/**
 *
 * @author victor
 */
public class ProdutoTest extends Teste{
    private ProdutoServico produtoServico;
    
    @Before
    public void setUp() throws NamingException {
        produtoServico = (ProdutoServico) container.getContext().lookup("java:global/classes/ejb/ProdutoServico!softwarecorporativo.servico.ProdutoServico");
    }
    
    @After
    public void tearDown() {
        produtoServico = null;
    }
    
    @Test
    public void existeProduto() { 
        Produto produto = produtoServico.criar();
        produto.setId(1l);
        assertTrue(produtoServico.existe(produto));
    }
    
    @Test
    public void getProdutoPorId() { 
        Produto produto = produtoServico.consultarPorID(5l);
        assertNotNull(produto);
        assertEquals("Boné Enorme", produto.getNome());
    }
    
    
    
    @Test
    public void getProdutoPorID() { 
        assertNotNull(produtoServico.consultarPorID(new Long(2)));
    }
    
    @Test
    public void persistir() { 
        Produto produto = produtoServico.criar();
        produto.setNome("Short");
        produto.setPreco(12.5);
        produto.setDescricao("Ótima qualidade");
        produto.setQuantidade(250);
       TipoProduto tp = new TipoProduto();
       tp = criarTP();
       produto.setTipoProduto(tp);
     
        produtoServico.persistirProduto(produto);
        assertNotNull(produto.getId());
        
    }
    
    @Test
    public void atualizar() { 
        Produto produto = produtoServico.consultarPorID(new Long(2));
        produto.setNome("Atualizando"); 
        produtoServico.atualizar(produto);
        produto = produtoServico.consultarPorID(new Long(2));
        assertEquals("Atualizando", produto.getNome());
    }
    
    
    

    public TipoProduto criarTP() {
    TipoProduto tp = new TipoProduto();
        tp.setNome("Short");
        return tp;
    }

}
