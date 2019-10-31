/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarecorporativo.entidade;
import softwarecorporativo.validador.ValidaPais;
import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author marcosbrasileiro
 */
@Embeddable
public class EnderecoCliente extends Entidade implements Serializable{
 
 @NotNull
 @Size(max=100)
 @Column(name="ENDERECO_NOME")
 private String nome;
 
 @NotBlank
 @Size(max = 10)
 @Column(name="ENDERECO_NUMERO")
 private String numero;
 
 @NotNull
 @Size(max = 200)
 @Column(name="ENDERECO_COMPLEMENTO")
  private String complemento;
 
 @NotBlank
 @Size(max=20)
 @Column(name="ENDERECO_BAIRRO")
 private String bairro;
 
 @NotBlank
 @Size(max=40)
 @Column(name="ENDERECO_CIDADE")
 private String cidade;
 
 @NotBlank
 @Size(max=20)
 
 @Column(name="ENDERECO_CEP")
 private String cep;
 
 @NotNull
 @Size(max = 30)
 @Column(name="ENDERECO_ESTADO")
 private String estado;
 
 @NotNull
 @Size(max = 2)
 @ValidaPais
 @Column(name="ENDERECO_PAIS")
 private String pais;

public boolean possui(String nome){
       return nome.contains(nome);
   }

 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

 
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
 


}
