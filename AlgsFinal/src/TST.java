//template for tst found here: https://www.youtube.com/watch?v=lX8YmSO4NnU

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class TST<Value> {
    private Node root;

    private class Node {
        private Value val;
        private char c;
        private Node left, mid, right;
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if (c < x.c) {
            x.left = put(x.left, key, val, d);
        } else if (c > x.c) {
            x.right = put(x.right, key, val, d);
        } else if (d < key.length() -1) {
            x.mid = put(x.mid, key, val, d+1);
        } else {
            x.val = val;
        }
        return x;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Value get (String key) {
        Node x = get(root, key, 0);
        if (x == null) {
            return null;
        } else {
            return x.val;
        }
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        } else {
            char c = key.charAt(d);
            if (c < x.c) {
                return get(x.left, key, d);
            } else if (c > x.c) {
                return get(x.right, key, d);
            } else if (d < key.length() -1) {
                return get(x.mid, key, d+1);
            } else {
                return x;
            }
        }
    }
    public ArrayList<Value> like(String search) {
        Node x = get(root, search.toUpperCase(Locale.ROOT), 0);
        ArrayList<Value> results = new ArrayList<>();
        if(x != null ) {
            if(x.val != null) {
                results.add(x.val);
            }
            like(results, x.mid);
        }
        return results;
    }

    private void like(ArrayList<Value> results, Node x ) {
        if(x != null) {
            if (x.val != null){
                results.add(x.val);
            }
            if(x.left != null)      like(results, x.left);
            if(x.mid != null)       like(results, x.mid);
            if(x.right != null)     like(results, x.right);
        }

    }
}