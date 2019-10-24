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
import org.hibernate.validator.constraints.br.CPF;
import softwarecorporativo.entidade.ClienteUsuario;

/**
 *
 * @author marcosbrasil98
 */
@Stateless(name = "ejb/ClienteServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class ClienteServico extends Servico<ClienteUsuario> {
    
    @PostConstruct
    public void init() {
        super.setClasse(ClienteUsuario.class);
    }

    @Override
    public ClienteUsuario criar() {
        return new ClienteUsuario();
    }
    
    @Override
    public boolean existe(@NotNull ClienteUsuario cliente) {
        TypedQuery<ClienteUsuario> query = entityManager.createNamedQuery(ClienteUsuario.Clienteporcpf, ClienteUsuario.class);
        query.setParameter(1, cliente.getCpf());
        return !query.getResultList().isEmpty();
    }
    
    public void persistirCliente(ClienteUsuario cliente) {
        entityManager.persist(cliente);
    }
    
    public ClienteUsuario atualizarCliente(ClienteUsuario cliente) {
        entityManager.merge(cliente);
        entityManager.flush();
        return cliente;
    }
    
    @TransactionAttribute(SUPPORTS)
    public ClienteUsuario consultarPorCPF(@CPF String cpf) {
        return super.consultarEntidade(new Object[] {cpf}, ClienteUsuario.Clienteporcpf);
    }
}
