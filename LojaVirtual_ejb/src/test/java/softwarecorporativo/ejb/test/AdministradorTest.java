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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import softwarecorporativo.entidade.Administrador;
import softwarecorporativo.servico.AdministradorServico;

/**
 *
 * @author victor
 */
public class AdministradorTest extends Teste{
    
    private AdministradorServico administradorServico;
    
    @Before
    public void setUp() throws NamingException {
        administradorServico = (AdministradorServico) container.getContext().lookup("java:global/classes/ejb/AdministradorServico!softwarecorporativo.servico.AdministradorServico");
    }
    
    @After
    public void tearDown() {
        administradorServico = null;
    }
    
    
    
    @Test
    public void existeAdministrador() {
        Administrador administrador = administradorServico.criar();
        administrador.setCpf("829.339.430-77");
        assertTrue(administradorServico.existe(administrador));
    }
    
    @Test
    public void getAdministradorPorCPF() {
        Administrador administrador = administradorServico.consultarPorCPF("884.250.750-41");
        assertNotNull(administrador);
        assertEquals("Rakin de Paula", administrador.getNome());
    }
    
    @Test(expected = EJBException.class)
    public void consultarAdministradorCPFInvalido() {
        try {
            administradorServico.consultarPorCPF("222.111.444-98");
        } catch (EJBException ex) {
            assertTrue(ex.getCause() instanceof ConstraintViolationException);
            throw ex;
        }
    }
    
    @Test
    public void getAdministradorPorId() {
        assertNotNull(administradorServico.consultarPorId(new Long(2)));
    }
    
    @Test
    public void persistir() {
        Administrador administrador = administradorServico.criar();
        administrador.setCpf("212.762.055-03");
        administrador.setEmail("jose@gmail.com");
        administrador.setNome("Natanael");
        administrador.setId(3l);
        administrador.setPermissao("Concedida");
        
        administradorServico.persistirAdministrador(administrador);
        assertNotNull(administrador.getId());
        
    }
    
    @Test
    public void atualizar() {
        Administrador administrador = administradorServico.consultarPorId(new Long(2));
        administrador.setEmail("mbf1998@gmail.com"); 
        administradorServico.atualizar(administrador);
        administrador = administradorServico.consultarPorId(new Long(2));
        assertEquals("mbf1998@gmail.com", administrador.getEmail());
    }
    
    @Test(expected = EJBException.class)
    public void atualizarInvalido() {
        Administrador administrador = administradorServico.consultarPorId(new Long(2));
        administrador.setCpf("073.350.244-54"); //cpf inválido
        try {
            administradorServico.atualizar(administrador);
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
