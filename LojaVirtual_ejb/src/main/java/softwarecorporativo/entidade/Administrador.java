/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarecorporativo.entidade;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author aluno
 */
@Entity
@Table (name="TB_ADMUSUARIO")
@DiscriminatorValue(value = "A")
@PrimaryKeyJoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
@Access(AccessType.FIELD)
@NamedQueries(
        {
            @NamedQuery(
                    name = "Administrador.PorPermissao",
                    query = "SELECT a FROM Administrador a WHERE a.permissao LIKE :permissao ORDER BY a.id"
            ),
            @NamedQuery(
                    name = "Administrador.PorNome",
                    query = "SELECT a FROM UsuarioGeral a WHERE a.nome LIKE :nome ORDER BY a.id"
            ),
            @NamedQuery(
                    name = "Administrador.AdministradorPorCpf",
                    query = "SELECT a FROM UsuarioGeral a WHERE a.cpf LIKE :cpf ORDER BY a.id"
            ),
            @NamedQuery(
                    name = "Administrador.PorEmail",
                    query = "SELECT a FROM UsuarioGeral a WHERE a.email LIKE :email ORDER BY a.id"
            ),
            @NamedQuery(
                    name = Administrador.AdministradorPorCPF,
                    query = "SELECT a FROM UsuarioGeral a WHERE a.cpf = ?1"
            )
        }
)

public class Administrador extends UsuarioGeral implements Serializable {
    public static final String AdministradorPorCPF = "AdministradorPorCPF";

    @NotBlank(message = "Administrador precisa da permissão")
    @Column(name = "ADM_PERMISSAO")
    private String permissao;
    
    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("softwarecorporativo.jpa.Administrador[");
        sb.append(super.toString());
        sb.append(", ");
        sb.append(this.permissao);
        sb.append("]");
        return sb.toString();
    }


  
}
