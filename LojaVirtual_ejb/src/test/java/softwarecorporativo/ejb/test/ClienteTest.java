/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarecorporativo.ejb.test;

import java.util.Calendar;
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
import softwarecorporativo.entidade.ClienteUsuario;
import softwarecorporativo.entidade.EnderecoCliente;
import softwarecorporativo.servico.ClienteServico;

/**
 *
 * @author marcosbrasil98
 */
public class ClienteTest extends Teste{
    
  private ClienteServico clienteservico;
  
   @Before
    public void setUp() throws NamingException {
        clienteservico = (ClienteServico) container.getContext().lookup("java:global/classes/ejb/ClienteServico!softwarecorporativo.servico.ClienteServico");
    }
  @After
    public void tearDown() {
        clienteservico = null;
    }
   @Test
    public void existeCliente() {
        ClienteUsuario cliente = clienteservico.criar();
        cliente.setCpf("767.170.840-22");
        assertTrue(clienteservico.existe(cliente));
    }
    
    @Test
    public void getClientePorCPF() {
        ClienteUsuario cliente = clienteservico.consultarPorCPF("767.170.840-22");
        assertNotNull(cliente);
        assertEquals("4lan", cliente.getNome());
    }
    
    @Test(expected = EJBException.class)
    public void consultarClienteCPFInvalido() {
        try {
            clienteservico.consultarPorCPF("111.222.444-62");
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);
            throw ex;
        }
    }
    
    @Test
    public void getClientePorId() {
        assertNotNull(clienteservico.consultarPorId(new Long(4)));
    }
    
    @Test
    public void persistir() {
        ClienteUsuario cliente = clienteservico.criar();
        cliente.setCpf("212.762.055-03");
        cliente.setEmail("jose@gmail.com");
        cliente.setNome("Natanael");
        cliente.setFixo("33391803");
         EnderecoCliente ec = new EnderecoCliente();
        ec.setNome("Avenida Paulista");
        ec.setNumero("425");
        ec.setBairro("São Paulo");
        ec.setCep("424242442");
        ec.setCidade("São Paulo");
        ec.setComplemento("Casa");
        ec.setEstado("São Paulo");
        ec.setPais("BR");
        cliente.setEndereco(ec);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1998);
        c.set(Calendar.MONTH, Calendar.AUGUST);
        c.set(Calendar.DAY_OF_MONTH, 24);
        cliente.setDataNascimento(c.getTime());
        cliente.setCelular("333333333");
        
        clienteservico.persistirCliente(cliente);
        assertNotNull(cliente.getId());
        
    }
    
    @Test
    public void atualizar() {
        ClienteUsuario cliente = clienteservico.consultarPorId(new Long(4));
        cliente.setEmail("mbf1998@gmail.com"); 
        clienteservico.atualizar(cliente);
        cliente = clienteservico.consultarPorId(new Long(4));
        assertEquals("mbf1998@gmail.com", cliente.getEmail());
    }
    
    @Test
    public void atualizarPais() {
        ClienteUsuario cliente = clienteservico.consultarPorId(new Long(4));
        EnderecoCliente ec = new EnderecoCliente();
        ec.setNome("Avenida Paulista");
        ec.setNumero("425");
        ec.setBairro("São Paulo");
        ec.setCep("424242442");
        ec.setCidade("São Paulo");
        ec.setComplemento("Casa");
        ec.setEstado("São Paulo");
        ec.setPais("BR");
        cliente.setEndereco(ec);
        clienteservico.atualizar(cliente);
        cliente = clienteservico.consultarPorId(new Long(4));
        assertEquals("BR", ec.getPais());
    }
    
    @Test(expected = EJBException.class)
    public void atualizarPaisInvalido() {
        ClienteUsuario cliente = clienteservico.consultarPorId(new Long(4));
        EnderecoCliente ec = new EnderecoCliente();
        ec.setNome("Avenida Paulista");
        ec.setNumero("425");
        ec.setBairro("São Paulo");
        ec.setCep("424242442");
        ec.setCidade("São Paulo");
        ec.setComplemento("Casa");
        ec.setEstado("São Paulo");
        ec.setPais("ZX");
        cliente.setEndereco(ec);
        clienteservico.atualizar(cliente);
        cliente = clienteservico.consultarPorId(new Long(4));
        assertEquals("BR", ec.getPais());
    }
    
    @Test(expected = EJBException.class)
    public void atualizarCPFInvalido() {
        ClienteUsuario cliente = clienteservico.consultarPorId(new Long(4));
        cliente.setCpf("071.322.240-99"); 
        try {
            clienteservico.atualizar(cliente);
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);
            ConstraintViolationException causa = (ConstraintViolationException) ex.getCause();
            for (ConstraintViolation erroValidacao : causa.getConstraintViolations()) {
                assertThat(erroValidacao.getMessage(),
                        CoreMatchers.anyOf(startsWith("CPF inválido"),
                                startsWith("o CPF deve estar de acordo com o padrão ")));
            }
            throw ex;
        }
    }    
}
