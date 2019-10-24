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
import softwarecorporativo.entidade.Pedido;

/**
 *
 * @author marcosbrasil98
 */
@Stateless(name = "ejb/PedidoServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class PedidoServico extends Servico<Pedido> {
  
@PostConstruct
    public void init() {
        super.setClasse(Pedido.class);
    }

    @Override
    public Pedido criar() {
        return new Pedido();
    }
    
    @Override
    public boolean existe(@NotNull Pedido pedido) {
        TypedQuery<Pedido> query = entityManager.createNamedQuery(Pedido.PedidoPorLog,Pedido.class);
        query.setParameter(1, pedido.getLog());
        return !query.getResultList().isEmpty();
    }
    
    public void persistirPedido(Pedido pedido) {
        entityManager.persist(pedido);
    }
    
    public Pedido atualizarPedido(Pedido pedido) {
        entityManager.merge(pedido);
        entityManager.flush();
        return pedido;
    }
    
    @TransactionAttribute(SUPPORTS)
    public Pedido consultarPorLog( String log) {
        return super.consultarEntidade(new Object[] {log}, Pedido.PedidoPorLog);
    }
}
