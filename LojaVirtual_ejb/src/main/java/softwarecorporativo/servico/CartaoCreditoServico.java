/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarecorporativo.servico;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import org.hibernate.validator.constraints.CreditCardNumber;
import softwarecorporativo.entidade.Administrador;
import softwarecorporativo.entidade.CartaoCredito;

/**
 *
 * @author victor
 */
@Stateless(name = "ejb/CartaoCreditoServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class CartaoCreditoServico extends Servico<CartaoCredito>{

    @PostConstruct
    public void init() {
        super.setClasse(CartaoCredito.class);
    }
    
    @Override
    public CartaoCredito criar() {
        return new CartaoCredito();
    }
    
    @Override
    public boolean existe(@NotNull CartaoCredito cartaoCredito) {
        TypedQuery<CartaoCredito> query = entityManager.createNamedQuery(CartaoCredito.PorNumero, CartaoCredito.class);
        query.setParameter(1, cartaoCredito.getNumero());
        return !query.getResultList().isEmpty();
    }
    
    public void persistirCartaoCredito(CartaoCredito cartaoCredito) {
        entityManager.persist(cartaoCredito);
    }
    
    public CartaoCredito atualizarCartaoCredito(CartaoCredito cartaoCredito) {
        entityManager.merge(cartaoCredito);
        entityManager.flush();
        return cartaoCredito;
    }
    
    @TransactionAttribute(SUPPORTS)
    public CartaoCredito consultarPorNumero(@CreditCardNumber(ignoreNonDigitCharacters = true) String numero) {
        return super.consultarEntidade(new Object[] {numero}, CartaoCredito.PorNumero);
    }
}
