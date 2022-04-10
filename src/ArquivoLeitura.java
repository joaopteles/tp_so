import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;

public class ArquivoLeitura {

    private BufferedReader entrada;

    ArquivoLeitura(String nomeArquivo) {

        try {
            entrada = new BufferedReader(new FileReader(nomeArquivo));
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }

    @SuppressWarnings("finally")
    public String lerLinha() {
        String textoEntrada = null;
        try {
            textoEntrada = entrada.readLine();

        } catch (EOFException e) {
            textoEntrada = null;
        } catch (IOException e) {
            System.out.println("Erro de leitura: " + e);
            textoEntrada = null;
        } finally {
            return textoEntrada;
        }
    }

    public void fecharArq() {
        try {
            entrada.close();

        } catch (IOException e) {
            System.out.println("Erro no fechamento do arquivo: " + e);
        }
    }

}
