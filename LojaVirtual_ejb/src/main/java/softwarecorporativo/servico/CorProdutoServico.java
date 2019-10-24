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
import softwarecorporativo.entidade.CorProduto;

/**
 *
 * @author marcosbrasil98
 */
@Stateless(name = "ejb/CorProdutoServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class CorProdutoServico extends Servico<CorProduto>{
 
  @PostConstruct
    public void init() {
        super.setClasse(CorProduto.class);
    }

    @Override
    public CorProduto criar() {
        return new CorProduto();
    }
    
    @Override
    public boolean existe(@NotNull CorProduto corProduto) {
        TypedQuery<CorProduto> query = entityManager.createNamedQuery(CorProduto.CorPorTipo, CorProduto.class);
        query.setParameter(1, corProduto.getTipo());
        return !query.getResultList().isEmpty();
    }
    
    public void persistirCorProduto(CorProduto cor) {
        entityManager.persist(cor);
    }
    
    public CorProduto atualizarCor(CorProduto corProduto) {
        entityManager.merge(corProduto);
        entityManager.flush();
        return corProduto;
    }
    
    @TransactionAttribute(SUPPORTS)
    public CorProduto consultarPorTipo( String tipo) {
        return super.consultarEntidade(new Object[] {tipo}, CorProduto.CorPorTipo);
    }
}
