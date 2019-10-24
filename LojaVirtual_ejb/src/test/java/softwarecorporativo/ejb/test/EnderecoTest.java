/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarecorporativo.ejb.test;

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
import softwarecorporativo.servico.EnderecoServico;
import softwarecorporativo.entidade.EnderecoCliente;
/**
 *
 * @author marcosbrasil98
 */
public class EnderecoTest extends Teste{

    private EnderecoServico enderecoservico;
    
    
    @Before
    public void setUp() throws NamingException {
        enderecoservico = (EnderecoServico) container.getContext().lookup("java:global/classes/ejb/EnderecoServico!softwarecorporativo.servico.EnderecoServico");
    }
    
    @After
    public void tearDown() {
        enderecoservico = null;
    }
   @Test
    public void existeEndereco() {
        EnderecoCliente endereco = enderecoservico.criar();
        endereco.setCep("42");
        assertTrue(enderecoservico.existe(endereco));
    }
    
    @Test
    public void getEnderecoPorCEP() {
        EnderecoCliente endereco = enderecoservico.consultarPorCEP("42");
        assertNotNull(endereco);
        assertEquals("Avenida Recife", endereco.getNome());
    }
    
   
    
    @Test
    public void getEnderecoPorId() {
        assertNotNull(enderecoservico.consultarPorId(new Long(2)));
    }
    
    @Test
    public void persistir() {
        EnderecoCliente ec = new EnderecoCliente();
        ec.setNome("Avenida Paulista");
        ec.setNumero("425");
        ec.setBairro("São Paulo");
        ec.setCep("424242442");
        ec.setCidade("São Paulo");
        ec.setComplemento("Casa");
        ec.setEstado("São Paulo");
        ec.setPais("BR");
        
        enderecoservico.persistirEndereco(ec);
        assertNotNull(ec.getId());
        
    }
    
    @Test
    public void atualizar() {
        EnderecoCliente endereco = enderecoservico.consultarPorId(new Long(2));
        endereco.setBairro("IFPE CITY"); 
        enderecoservico.atualizar(endereco);
        endereco = enderecoservico.consultarPorId(new Long(2));
        assertEquals("IFPE CITY", endereco.getBairro());
    }
    
    @Test
    public void atualizarPais() {
        EnderecoCliente endereco = enderecoservico.consultarPorId(new Long(2));
        assertNotNull(enderecoservico.consultarPorId(new Long(2)));
        endereco.setPais(("BR"));
        assertNotNull(enderecoservico.consultarPorId(new Long(2)));
        enderecoservico.atualizar(endereco);
        endereco = enderecoservico.consultarPorId(new Long(2));
        assertEquals("BR", endereco.getPais());
        assertNotNull(enderecoservico.consultarPorId(new Long(2)));
    }
    
   @Test(expected = EJBException.class)
    public void atualizarPaisInvalido() {
        EnderecoCliente endereco = enderecoservico.consultarPorId(new Long(4));
        endereco.setPais("ZX");
        enderecoservico.atualizar(endereco);
        endereco = enderecoservico.consultarPorId(new Long(4));
        assertEquals("ZX", endereco.getPais());
    }
    
}
