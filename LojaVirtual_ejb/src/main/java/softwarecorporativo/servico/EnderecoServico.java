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
import softwarecorporativo.entidade.EnderecoCliente;

/**
 *
 * @author marcosbrasil98
 */
@Stateless(name = "ejb/EnderecoServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class EnderecoServico extends Servico<EnderecoCliente>{
        
  @PostConstruct
    public void init() {
        super.setClasse(EnderecoCliente.class);
    }

    @Override
    public EnderecoCliente criar() {
        return new EnderecoCliente();
    }
    
    @Override
    public boolean existe(@NotNull EnderecoCliente endereco) {
        TypedQuery<EnderecoCliente> query = entityManager.createNamedQuery(EnderecoCliente.EnderecoPorCep, EnderecoCliente.class);
        query.setParameter(1, endereco.getCep());
        return !query.getResultList().isEmpty();
    }
    
    public void persistirEndereco(EnderecoCliente endereco) {
        entityManager.persist(endereco);
    }
    
    public EnderecoCliente atualizarEndereco(EnderecoCliente endereco) {
        entityManager.merge(endereco);
        entityManager.flush();
        return endereco;
    }
    
    @TransactionAttribute(SUPPORTS)
    public EnderecoCliente consultarPorCEP( String cpf) {
        return super.consultarEntidade(new Object[] {cpf}, EnderecoCliente.EnderecoPorCep);
    }  
}
