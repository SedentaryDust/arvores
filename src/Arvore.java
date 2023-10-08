import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

class ARVORE {

    public int num;
    public ARVORE dir, esq;

    public static ARVORE inserir(ARVORE aux, int num) {
        if (aux == null) {
            aux = new ARVORE();
            aux.num = num;
            aux.esq = null;
            aux.dir = null;

        } else if (num < aux.num) {
            aux.esq = inserir(aux.esq, num);
        } else {
            aux.dir = inserir(aux.dir, num);
        }
        return aux;
    }

    int size(ARVORE root) {
        if (root == null) {
            return 1;
        }
        return size(root.dir) + size(root.esq);
    }

    public static String imprimir(ARVORE aux) {
        String retorno;
        retorno = "(";
        if (aux != null) {
            retorno += "C" + aux.num;
            retorno += imprimir(aux.esq);
            retorno += imprimir(aux.dir);
        }
        retorno += ")";
        return retorno;
    }

    public static boolean localizar(ARVORE aux, int num, boolean loc) {
        if (aux != null && !loc) {
            if (aux.num == num) {
                loc = true;
            } else if (num < aux.num) {
                loc = localizar(aux.esq, num, loc);
            } else {
                loc = localizar(aux.dir, num, loc);
            }
        }
        return loc;
    }

    public static ARVORE excluir(ARVORE aux, int num) {
        ARVORE p, p2, r = null;
        if (aux.num == num) {
            if (aux.esq == aux.dir) {
                return null;
            } else if (aux.esq == null) {
                return aux.dir;
            } else if (aux.dir == null) {
                return aux.esq;
            } else {
                p2 = aux.dir;
                p = aux.dir;
                while (p.esq != null) {
                    r = p;
                    p = p.esq;
                }
                aux.num = p.num;
                p = null;
                r.esq = null;
                return aux;
            }
        } else if (aux.num < num) {
            aux.dir = excluir(aux.dir, num);
        } else {
            aux.esq = excluir(aux.esq, num);
        }
        return aux;
    }

    public static void WriteLog(String log) {

        try {
            // Abre o arquivo para escrita
            BufferedWriter writer = new BufferedWriter(new FileWriter("analise.txt", true));

            // Escreve a string no arquivo
            writer.write(log);

            // Fecha o arquivo
            writer.close();

            System.out.println("String escrita com sucesso no arquivo 'analise.txt'.");
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao escrever no arquivo 'analise.txt': " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        int[] dados = {100, 500, 1000, 10000, 20000};
        int relay = 0;

        Random rnd = new Random(1);

        while (relay < dados.length) {
            ARVORE tree = new ARVORE();
            long tempoInicial = System.nanoTime();
            for (int i = 0; i < dados[relay]; i++) {
                int key = rnd.nextInt(1, 999999999);
                tree = inserir(tree, key);

            }
            long tempofinal = System.nanoTime() - tempoInicial;
            String analise = "tempo de execução -> " + tempofinal + "ns" + " Tamanho final da Arvore Bin -> " + tree.size(tree) + "\n";
            WriteLog(analise);
            int find  = rnd.nextInt(1 , 999999999);
            long tempoInicialF = System.nanoTime();
            boolean finded =  localizar(tree , find, false);
            long tempofinalF = System.nanoTime() - tempoInicialF;
            analise = "Achou:"+finded+" Tempo necessario para achar numa arvore Bin de tamanho -> " + tree.size(tree)+ " foi de -> " + tempofinalF+"ns\n";
            WriteLog(analise);

            relay++;
        }
    }
}