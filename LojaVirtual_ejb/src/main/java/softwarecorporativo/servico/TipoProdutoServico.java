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
import softwarecorporativo.entidade.TipoProduto;

/**
 *
 * @author marcosbrasil98
 */
@Stateless(name = "ejb/TipoProdutoServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)

public class TipoProdutoServico extends Servico<TipoProduto> {
    
    @PostConstruct
    public void init() {
        super.setClasse(TipoProduto.class);
    }

    @Override
    public TipoProduto criar() {
        return new TipoProduto();
    }
    
    @Override
    public boolean existe(@NotNull TipoProduto tipoproduto) {
        TypedQuery<TipoProduto> query = entityManager.createNamedQuery(TipoProduto.TipoProdutoPorNome, TipoProduto.class);
        query.setParameter(1, tipoproduto.getNome());
        return !query.getResultList().isEmpty();
    }
    
    public void persistirTipoProduto(TipoProduto tipoproduto) {
        entityManager.persist(tipoproduto);
    }
    
    public TipoProduto atualizarTipoProduto(TipoProduto tipoProduto) {
        entityManager.merge(tipoProduto);
        entityManager.flush();
        return tipoProduto;
    }
    @TransactionAttribute(SUPPORTS)
    public TipoProduto consultarPorNome(String nome) {
        return super.consultarEntidade(new Object[] {nome}, TipoProduto.TipoProdutoPorNome);
    }
}
