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
import softwarecorporativo.entidade.TamanhoProduto;

/**
 *
 * @author marcosbrasil98
 */
@Stateless(name = "ejb/TamanhoServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class TamanhoServico extends Servico<TamanhoProduto>{
  
 @PostConstruct
    public void init() {
        super.setClasse(TamanhoProduto.class);
    }

    @Override
    public TamanhoProduto criar() {
        return new TamanhoProduto();
    }
    
    @Override
    public boolean existe(@NotNull TamanhoProduto tamanhoProduto) {
        TypedQuery<TamanhoProduto> query = entityManager.createNamedQuery(TamanhoProduto.TamanhoProdutoPorTipo, TamanhoProduto.class);
        query.setParameter(1, tamanhoProduto.getTipo());
        return !query.getResultList().isEmpty();
    }
    
    public void persistirTamanhoProduto(TamanhoProduto tamanho) {
        entityManager.persist(tamanho);
    }
    
    public TamanhoProduto atualizarTamanho(TamanhoProduto tamanhoProduto) {
        entityManager.merge(tamanhoProduto);
        entityManager.flush();
        return tamanhoProduto;
    }
    
    @TransactionAttribute(SUPPORTS)
    public TamanhoProduto consultarPorTipo( String tipo) {
        return super.consultarEntidade(new Object[] {tipo}, TamanhoProduto.TamanhoProdutoPorTipo);
    }
}