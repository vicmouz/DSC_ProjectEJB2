/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarecorporativo.validador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author victor
 */
public class ValidadorPais implements ConstraintValidator<ValidaPais, String>{
    private List<String> pais;

    

    @Override
    public void initialize(ValidaPais validaPais) {
        this.pais = new ArrayList<>();
        this.leitor();
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        return valor == null ? false : pais.contains(valor);
    }
    
    public void leitor() {
        Scanner ler = new Scanner("./src/main/resources/arquivos/siglaPaises.txt");
        int count = 0;
        String nomePais = ler.nextLine();

        try {
            FileReader arq = new FileReader(nomePais);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine(); // lê a primeira linha // a variável "linha" recebe o valor "null" quando o processo // de repetição atingir o final do arquivo texto
            linha = linha.substring(0, linha.indexOf("-"));
            pais.add(linha);
            count++;
            while (linha != null && count < 249) {

                linha = lerArq.readLine(); // lê da segunda até a última linha
                linha = linha.substring(0, linha.indexOf("-"));
                count++;
                pais.add(linha);
                
            }

            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
    }
}
