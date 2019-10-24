/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarecorporativo.ejb.test;

import java.io.File;
import java.io.FileInputStream;
import javax.naming.NamingException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import softwarecorporativo.entidade.CorProduto;
import softwarecorporativo.entidade.ImagemProduto;
import softwarecorporativo.servico.ImagemServico;

/**
 *
 * @author marcosbrasil98
 */
public class ImagemProdutoTest extends Teste{
    
    private ImagemServico imagemservico;
    
    @Before
    public void setUp() throws NamingException {
        imagemservico = (ImagemServico) container.getContext().lookup("java:global/classes/ejb/ImagemServico!softwarecorporativo.servico.ImagemServico");
    }
    @After
    public void tearDown() {
        imagemservico = null;
    }
    
    
    
    @Test
    public void existeImagem() {
        ImagemProduto imagem = imagemservico.criar();
        imagem.setOutraCor("3 cores");
        assertTrue(imagemservico.existe(imagem));
    }   
   
    
    @Test
    public void getImagemPorId() {
        assertNotNull(imagemservico.consultarPorId(new Long(2)));
    }
    
    @Test
    public void persistir() {   
         File file = new File("/resources/imagens/chaveiro_azul.jpeg");
        byte[] bFile = new byte[(int) file.length()];

        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
}
        ImagemProduto imagem = imagemservico.criar();
        imagem.setId(9l);
        imagem.setImagem(bFile);
        imagem.setOutraCor("15 cores ");
        
        
        
        
        imagemservico.persistirImagem(imagem);
        assertNotNull(imagem.getId());
        
    }
    
    @Test
    public void atualizar() {
        ImagemProduto imagem = imagemservico.consultarPorId(new Long(2));
        imagem.setOutraCor("99 cores"); 
        imagemservico.atualizarImagem(imagem);
        imagem = imagemservico.consultarPorId(new Long(2));
        assertEquals("99 cores", imagem.getOutraCor());
    }
    
   
    
    
    
}
