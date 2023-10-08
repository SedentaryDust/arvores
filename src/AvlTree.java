import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class AvlTree {
    private AvlNode root = null;

    public AvlTree() {
        root = null;
    }

    public void clear() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public AvlNode getRootNode() {
        return root;
    }

    private static int height(AvlNode tree) {
        return tree == null ? -1 : tree.height;
    }

    private int max(int leftH, int rigthH) {
        return Math.max(leftH, rigthH);
    }
    private static int size(AvlNode root){
        if(root == null){
            return 1;
        }
        return size(root.right) + size(root.left);
    }

    private int getFactor(AvlNode tree) {
        return height(tree.left) - height(tree.right);
    }

    public boolean insert(int x) {
        root = insert(x, root);
        return true;
    }

    private AvlNode insert(int x, AvlNode tree) {
        if (tree == null) {
            tree = new AvlNode(x, null, null);
        } else if (x < tree.key) {
            tree.left = insert(x, tree.left);
        } else if (x > tree.key) {
            tree.right = insert(x, tree.right);
        }
        tree = balance(tree);
        return tree;
    }

    public AvlNode balance(AvlNode tree) {
        if (getFactor(tree) == 2) {
            if (getFactor(tree.left) > 0) {
                tree = doRightRotation(tree);
            } else {
                tree = doDoubleRightRotation(tree);
            }
        } else if (getFactor(tree) == -2) {
            if (getFactor(tree.right) < 0) {
                tree = doLeftRotation(tree);
            } else {
                tree = doDoubleLeftRotation(tree);
            }
        }
        tree.height = max(height(tree.left), height(tree.right)) + 1;
        return tree;
    }

    private static AvlNode doRightRotation(AvlNode n2) {
        AvlNode n1 = n2.left;
        n2.left = n1.right;
        n1.right = n2;
        n2.height = Math.max(height(n2.left), height(n2.right)) + 1;
        n1.height = Math.max(height(n1.left), n2.height) + 1;
        return n1;

    }

    private static AvlNode doLeftRotation(AvlNode n1) {
        AvlNode n2 = n1.right;
        n1.right = n2.left;
        n2.left = n1;
        n1.height = Math.max(height(n1.left), height(n1.right)) + 1;
        n2.height = Math.max(height(n2.right), n1.height) + 1;
        return n2;

    }

    private static AvlNode doDoubleRightRotation(AvlNode n3) {
        n3.left = doLeftRotation(n3.left);
        return doRightRotation(n3);
    }

    private static AvlNode doDoubleLeftRotation(AvlNode n3) {
        n3.right = doRightRotation(n3.right);
        return doLeftRotation(n3);
    }

    public AvlNode search(AvlNode root, int key) {
        if (root == null) {
            return root;
        }
        if (root.key == key) {
            return root;
        }
        if (key < root.key) {
            return search(root.left, key);
        } else {
            return search(root.right, key);
        }

    }
    public boolean delete(int x) {
        if (root == null) {
            return false; // A árvore está vazia, não há nada para excluir.
        }
        boolean[] result = new boolean[1]; // Para armazenar o resultado da exclusão.
        root = delete(x, root, result);
        return result[0]; // Retorna true se a exclusão for bem-sucedida, caso contrário, false.
    }

    private AvlNode delete(int x, AvlNode tree, boolean[] result) {
        if (tree == null) {
            result[0] = false; // O elemento não foi encontrado na árvore.
            return tree;
        }

        if (x < tree.key) {
            tree.left = delete(x, tree.left, result);
        } else if (x > tree.key) {
            tree.right = delete(x, tree.right, result);
        } else {
            // Encontramos o elemento a ser excluído.

            // Se o nó possui dois filhos.
            if (tree.left != null && tree.right != null) {
                // Encontre o nó mínimo na subárvore direita.
                AvlNode minRight = findMin(tree.right);

                // Substitua o valor do nó atual pelo valor do nó mínimo.
                tree.key = minRight.key;

                // Exclua o nó mínimo da subárvore direita.
                tree.right = delete(minRight.key, tree.right, result);
            } else {
                // Caso contrário, o nó tem zero ou um filho.
                tree = (tree.left != null) ? tree.left : tree.right;
                result[0] = true; // A exclusão foi bem-sucedida.
            }
        }

        // Rebalanceie a árvore após a exclusão.
        tree = balance(tree);

        // Atualize a altura do nó atual.
        tree.height = max(height(tree.left), height(tree.right)) + 1;

        return tree;
    }

    private AvlNode findMin(AvlNode tree) {
        while (tree.left != null) {
            tree = tree.left;
        }
        return tree;
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


    public static void main(String[] args) {
        int[] dados = {100, 500, 1000, 10000, 20000};
        int relay = 0;

        Random rnd = new Random(1);

        while (relay < dados.length) {
            AvlTree tree = new AvlTree();
            long tempoInicial = System.nanoTime();
            for (int i = 0; i < dados[relay]; i++) {
                int key = rnd.nextInt(1, 999999999);
                tree.insert(key);

            }
            long tempofinal = System.nanoTime() - tempoInicial;
            String analise = "tempo de execução -> " + tempofinal + "ns" + " Tamanho final da Arvore AVL -> " + size(tree.root)+ "\n";
            WriteLog(analise);
            int find  = rnd.nextInt(1 , 999999999);
            long tempoInicialF = System.nanoTime();
            AvlNode node =  tree.search(tree.root , find);
            boolean finded = node != null;
            long tempofinalF = System.nanoTime() - tempoInicialF;
            analise = "Achou:"+finded+" Tempo necessario para achar numa arvore AVL de tamanho -> " + size(tree.root)+ " foi de -> " + tempofinalF+"ns\n";
            WriteLog(analise);

            relay++;
        }
    }

}
