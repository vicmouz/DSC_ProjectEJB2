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
import softwarecorporativo.entidade.Produto;

/**
 *
 * @author victor
 */
@Stateless(name = "ejb/ProdutoServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class ProdutoServico extends Servico<Produto> {

    @PostConstruct
    public void init() {
        super.setClasse(Produto.class);
    }

    @Override
    public Produto criar() {
        return new Produto();
    }
    
    @Override
    public boolean existe(@NotNull Produto entidade) {
        TypedQuery<Produto> query = entityManager.createNamedQuery(Produto.ProdutoPorID,Produto.class);
        query.setParameter(1, entidade.getId());
        return !query.getResultList().isEmpty();
    }
    
    public void persistirProduto(Produto produto) {
        entityManager.persist(produto);
    }
    
    @TransactionAttribute(SUPPORTS)
    public Produto consultarPorID(@NotNull Long id) {
        return super.consultarEntidade(new Object[] {id}, Produto.ProdutoPorID);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
