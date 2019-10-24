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
import softwarecorporativo.entidade.ImagemProduto;

/**
 *
 * @author marcosbrasil98
 */
@Stateless(name = "ejb/ImagemServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class ImagemServico extends Servico<ImagemProduto>{
    
 @PostConstruct
    public void init() {
        super.setClasse(ImagemProduto.class);
    }

    @Override
    public ImagemProduto criar() {
        return new ImagemProduto();
    }
    
    @Override
    public boolean existe(@NotNull ImagemProduto imagem) {
        TypedQuery<ImagemProduto> query = entityManager.createNamedQuery(ImagemProduto.ImagemProdutoPorOutraCor, ImagemProduto.class);
        query.setParameter(1, imagem.getOutraCor());
        return !query.getResultList().isEmpty();
    }
    
    public void persistirImagem(ImagemProduto imagemProduto) {
        entityManager.persist(imagemProduto);
    }
    
    public ImagemProduto atualizarImagem(ImagemProduto imagemProduto) {
        entityManager.merge(imagemProduto);
        entityManager.flush();
        return imagemProduto;
    }
    
    @TransactionAttribute(SUPPORTS)
    public ImagemProduto consultarPorcor( String cor) {
        return super.consultarEntidade(new Object[] {cor}, ImagemProduto.ImagemProdutoPorOutraCor);
    }   
}
