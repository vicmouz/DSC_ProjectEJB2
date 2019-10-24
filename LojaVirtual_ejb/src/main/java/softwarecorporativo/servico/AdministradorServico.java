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
import softwarecorporativo.entidade.Administrador;

/**
 *
 * @author victor
 */
@Stateless(name = "ejb/AdministradorServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class AdministradorServico extends Servico<Administrador> {

    @PostConstruct
    public void init() {
        super.setClasse(Administrador.class);
    }

    @Override
    public Administrador criar() {
        return new Administrador();
    }
    
    @Override
    public boolean existe(@NotNull Administrador administrador) {
        TypedQuery<Administrador> query = entityManager.createNamedQuery(Administrador.AdministradorPorCPF, Administrador.class);
        query.setParameter(1, administrador.getCpf());
        return !query.getResultList().isEmpty();
    }
    
    public void persistirAdministrador(Administrador administrador) {
        entityManager.persist(administrador);
    }
    
    public Administrador atualizarAdministrador(Administrador administrador) {
        entityManager.merge(administrador);
        entityManager.flush();
        return administrador;
    }
    
    @TransactionAttribute(SUPPORTS)
    public Administrador consultarPorCPF(@CPF String cpf) {
        return super.consultarEntidade(new Object[] {cpf}, Administrador.AdministradorPorCPF);
    }
}
