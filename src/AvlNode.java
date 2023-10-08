public class AvlNode {
    protected int height;
    protected int key;
    protected  AvlNode left,right;

    public AvlNode(int key){
        this(key , null , null);
    }
    public AvlNode(int key , AvlNode left ,AvlNode right){
        this.key = key;
        this.left = left;
        this.right = right;
        height = 0;
    }

}

