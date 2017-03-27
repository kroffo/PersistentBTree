import java.io.*;

public class BTree implements Serializable {

    // Set the minimum degree (T) of the BTree
    private final int T = 100;

    // numNodes is used for naming the files
    // the nodes are saved to.
    private static int numNodes = 0;

    // The name of the BTree
    // Used to store node files in
    // a subdirectory for this BTree
    private String name;

    // The name of the root node
    private String root;



    // For testing purposes
    public String toString() {
        Node r = readNode(root);
        return r.toString();
    }

    public String getName() {
        return name;
    }

    /* A node class to represent the nodes in the BTree
     *
     * Each node will be written to a file, and a node will
     * only be read into memory when necessary.
     * This allows the program to use a lot more data
     * that can be stored in RAM.
    */
    private class Node implements Serializable {
        String name;
        int numberKeys;
        Key[] keys;
        boolean leaf;
        String[] children;

        public Node() {
            name = "Node" + (++numNodes);
            keys = new Key[ 2*T - 1 ];
            numberKeys = 0;
            leaf = true;
            children = new String[ 2*T ];
        }

        // For testing purposes
        public String toString() {
            String line = name + ":\n\tKeys: ";
            for(int i=0; i<numberKeys; ++i) {
                Key k = keys[i];
                line += (k == null) ? "NULL " : k.id + " ";
            }
            line += "\n\tChildren: ";
            if(!leaf) {
                for (int i = 0; i < numberKeys + 1; ++i) {
                    Node n = readNode(children[i]);
                    line += n.name + " ";
                }
                for (int i = 0; i < numberKeys + 1; ++i) {
                    Node n = readNode(children[i]);
                    line += "\n" + n.toString();
                }
            }
            return line;
        }
    }

    /*
     * A Key has an id (the key String) and
     * a value. The value is the index in
     * DataArray where the key's corresponding
     * data point is stored.
    */
    private class Key implements Comparable, Serializable {
        String id;
        int value;

        public Key(String i, int val) {
            id = i;
            value = val;
        }

        @Override
        public int compareTo(Object o) {
            if(!(o instanceof Key))
                return 0;
            Key k = (Key)o;
            return this.id.compareTo(k.id);
        }
    }

    /*
     * When a new tree is created, write the
     * root node so the tree is persistent
    */
    public BTree(String name) {
        this.name = name;
        Node r = new Node();
        root = r.name;
        writeNode(root, r);
    }

    /*
     * Insert the key, value pair into the tree
     * and update the tree
    */
    public void insert(String key, int v) {
        Key k = new Key(key, v);
        insertKey(k);
        Loader.writeTree(this);
    }

    /*
     * Insert the a Key object into the tree
     *
     * Code is based on pseudocode from
     *    http://staff.ustc.edu.cn/~csli/graduate/algorithms/book6/chap19.htm
     */
    public void insertKey(Key key) {
        Node r = readNode(root);
        if(r.numberKeys == 2*T - 1) {
            Node s = new Node();
            root = s.name;
            s.leaf = false;
            s.children[0] = r.name;
            splitChild(s,0,r);
            insertNonfull(s,key);
        } else {
            insertNonfull(r,key);
        }
    }

    /*
     * Split a node (y) into two children of a parent node (x)
     * with the children inserted next to the key at index i
     *
     * Code is based on pseudocode from
     *    http://staff.ustc.edu.cn/~csli/graduate/algorithms/book6/chap19.htm
     */
    public void splitChild(Node x, int i, Node y) {
        Node z = new Node();
        z.leaf = y.leaf;
        z.numberKeys = T-1;
        for(int j=0, length=T-1; j<length; ++j) {
            z.keys[j] = y.keys[j + T];
            y.keys[j + T] = null;
        }
        if(!y.leaf)
            for(int j=0, length=T; j<length; ++j) {
                z.children[j] = y.children[j + T];
                y.children[j + T] = null;
            }

        y.numberKeys = T-1;

        for(int j=x.numberKeys, min = i; j>min; --j)
            x.children[j+1] = x.children[j];
        x.children[i+1] = z.name;

        for(int j=x.numberKeys-1, min=i-1; j>min; --j)
            x.keys[j+1] = x.keys[j];
        x.keys[i] = y.keys[T-1];
        y.keys[T-1] = null;

        ++x.numberKeys;

        writeNode(y.name, y);
        writeNode(z.name, z);
        writeNode(x.name, x);
    }

    /*
     * Insert a key into a node (x) if it is not full,
     * otherwise attempt to add to it's appropriate child
     *
     * Code is based on pseudocode from
     *    http://staff.ustc.edu.cn/~csli/graduate/algorithms/book6/chap19.htm
     */
    public void insertNonfull(Node x, Key key) {
        int i = x.numberKeys-1;
        if(x.leaf) {
            while(i >= 0 && key.compareTo(x.keys[i]) < 0)
                x.keys[i+1] = x.keys[i--];
            x.keys[i+1] = key;
            ++x.numberKeys;
            writeNode(x.name, x);
        } else {
            while(i >= 0 && key.compareTo(x.keys[i]) < 0)
                --i;
            ++i;
            Node c = readNode(x.children[i]);
            if(c.numberKeys == 2*T - 1) {
                splitChild(x, i, c);
                if(key.compareTo(x.keys[i]) > 0)
                    ++i;
            }
            if(!x.children[i].equals(c.name))
                c = readNode(x.children[i]);
            insertNonfull(c,key);
        }
    }

    // Search the entire tree for the key
    public int search(String k) {
        Node r = readNode(root);
        Key key = findKey(r, k, 0, r.numberKeys - 1);
        return (key != null) ? key.value : -1;
    }

    /* Returns the Key with ID k if it is in node x,
     * or one of its children and null otherwise
     *
     * Uses binary search to find the key
    */
    private Key findKey(Node x, String k, int min_index, int max_index) {

        // Start searching in the middle of the keys
        int index = (max_index + min_index) / 2;

        int comparison = k.compareTo(x.keys[index].id);

        if(comparison == 0) { // If we've found the key, return it.
            return x.keys[index];

            // If we have not found the key, we must try to search to the left or right of the key
        } else if(comparison < 0 && min_index != index) {
            return findKey(x, k, min_index, index-1);
        } else if(comparison > 0 && max_index != index) {
            return findKey(x, k, index+1, max_index);
        }

        // We could not search in the direction we needed to.
        // Return null if there is no more searching to be done (node is a leaf)
        // otherwise get the child node to the appropriate direction of the search index
        if(x.leaf)
            return null;

        // Get the appropriate child and search for the key there.
        int child_index = (comparison < 0) ? index : index + 1;
        Node child = readNode(x.children[child_index]);
        return findKey(child, k, 0, child.numberKeys - 1);
    }

    // Write a BTree node to a persistent file
    public void writeNode(String name, Node n) {
        try (
                FileOutputStream fileOut = new FileOutputStream("Data/" + this.name + "_" + name);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
        ) {
            out.writeObject(n);
            //System.out.println("Serialized data is saved in " + name);
        }catch(IOException i) {
            i.printStackTrace();
        }
    }


    // Read a BTree node from persistent file
    public Node readNode(String name) {
        try (
                FileInputStream fileIn = new FileInputStream("Data/" + this.name + "_" + name);
                ObjectInputStream in = new ObjectInputStream(fileIn);
        ) {
            return (Node)in.readObject();
        } catch(IOException i) {
            i.printStackTrace();
            return null;
        } catch(ClassNotFoundException c) {
            System.out.println("Node class not found");
            c.printStackTrace();
            return null;
        }
    }
}
