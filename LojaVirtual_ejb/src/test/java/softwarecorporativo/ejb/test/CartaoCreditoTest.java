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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import softwarecorporativo.entidade.CartaoCredito;
import softwarecorporativo.entidade.ClienteUsuario;
import softwarecorporativo.servico.CartaoCreditoServico;

/**
 *
 * @author victor
 */
public class CartaoCreditoTest extends Teste{
    
    private CartaoCreditoServico cartaoCreditoServico;
    
    @Before
    public void setUp() throws NamingException {
        cartaoCreditoServico = (CartaoCreditoServico) container.getContext().lookup("java:global/classes/ejb/AdministradorServico!softwarecorporativo.servico.CartaoCreditoServico");
    }
    
    @After
    public void tearDown() {
        cartaoCreditoServico = null;
    }
    
    @Test
    public void existeCartaoCredito() {
        CartaoCredito cartaoCredito = cartaoCreditoServico.criar();
        cartaoCredito.setNumero("4073020000000002");
        assertTrue(cartaoCreditoServico.existe(cartaoCredito));
    } 
    
    @Test
    public void getCartaoCreditoPorNumero() {
        CartaoCredito cartaoCredito = cartaoCreditoServico.consultarPorNumero("4073020000000002");
        assertNotNull(cartaoCredito);
        assertEquals("VISA", cartaoCredito.getBandeira());
    }
    
    @Test(expected = EJBException.class)
    public void consultarCartaoCreditoNumeroInvalido() {
        try {
            cartaoCreditoServico.consultarPorNumero("1111222233334444");
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);
            throw ex;
        }
    }
    
    @Test
    public void getCartaoCreditoPorId() {
        assertNotNull(cartaoCreditoServico.consultarPorId(new Long(2)));
    }
    
    @Test
    public void persistir() {
        logger.info("Executando persistir()");
        CartaoCredito c = cartaoCreditoServico.criar();
        c.setBandeira("Master");
        Calendar data = Calendar.getInstance();
        data.set(Calendar.YEAR, 2222);
        data.set(Calendar.MONTH, Calendar.AUGUST);
        data.set(Calendar.DAY_OF_MONTH, 10);
        c.setDataExpiracao(data.getTime());
        c.setNumero("5595203580829570");
        c.setUsuario(criarClienteUsuario());
        
        cartaoCreditoServico.persistirCartaoCredito(c);
        assertNotNull(c.getId());
    }

    @Test
    public void Atualizar() {
        CartaoCredito cartaoCredito = cartaoCreditoServico.consultarPorId(new Long(2));
        cartaoCredito.setBandeira("VISA");
        cartaoCreditoServico.atualizar(cartaoCredito);
        cartaoCredito = cartaoCreditoServico.consultarPorId(new Long(2));
        assertEquals("VISA", cartaoCredito.getBandeira());
    }

    private ClienteUsuario criarClienteUsuario() {
        ClienteUsuario cliente = new ClienteUsuario();
        cliente.setId(4l);
        cliente.setNome("Cicrano Knittrel");
        cliente.setEmail("rakin@gmail.com");
        cliente.setCpf("797.141.400-56");
        cliente.setCelular("(81) 4002-8922");
        cliente.setFixo("(81) 8922-4002");
        cliente.setSenha("aaa22_AAA");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1997);
        c.set(Calendar.MONTH, Calendar.AUGUST);
        c.set(Calendar.DAY_OF_MONTH, 10);
        cliente.setDataNascimento(c.getTime());
        return cliente;
    }
    
    @Test(expected = EJBException.class)
    public void atualizarInvalido() {
        CartaoCredito cartaoCredito = cartaoCreditoServico.consultarPorId(new Long(2));
        cartaoCredito.setNumero("AAAABBBBCCCCDDDD");
        try {
            cartaoCreditoServico.atualizar(cartaoCredito);
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);
            ConstraintViolationException causa = (ConstraintViolationException) ex.getCause();
            for (ConstraintViolation erroValidacao : causa.getConstraintViolations()) {
                assertThat(erroValidacao.getMessage(),
                        CoreMatchers.anyOf(startsWith("Número inválido"),
                                startsWith("o número não deve conter letras")));
            }
            throw ex;
        }
    }
}
