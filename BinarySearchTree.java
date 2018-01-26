class BinarySearchTree
{
    // Class representing nodes of the BST with data and left and right children
    class BSTNode
    {
        private int data;
        private BSTNode left;
        private BSTNode right;

        public BSTNode(int data) // constructor for BSTNode
        {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        // getters
        public int getData() { return data; }
        public BSTNode getLeftChild() { return left; }
        public BSTNode getRightChild() { return right; }

        // setters
        public void setData(int data) { this.data = data; }
        public void setLeftChild(BSTNode child) { left = child; }
        public void setRightChild(BSTNode child) { right = child; }

        // convenience methods
        public boolean hasLeftChild() { return (this.left != null); }
        public boolean hasRightChild() { return (this.right != null); }
        public boolean hasLeftChildOnly() { return (hasLeftChild() && this.right == null); }
        public boolean hasRightChildOnly() { return (hasRightChild() && this.left == null); }
        public boolean isLeaf() { return (this.left == null && this.right == null); }
    }

    private BSTNode root;
    private BSTNode maxNode; // for deletion purposes;
    
    // public methods
    public BinarySearchTree()
    {
        root = null;
        maxNode = new BSTNode(Integer.MAX_VALUE); // create node containing maximum integer value
        maxNode.setLeftChild(root);
    }

    public boolean insert(int value)
    {
        if (root == null)
        {
            root = new BSTNode(value);
            maxNode.setLeftChild(root); // for deletion purposes
        }
        else
            return insertNode(root, value);

        return true;
    }

    public boolean delete(int value)
    {
        if (root == null)
            return false;
        else
            return deleteNode(maxNode, value);
    }

    public void preOrderPrint()
    {
        preOrderTraverse(root);
        System.out.print("\n");
    }
    
    public void inOrderPrint()
    {
        inOrderTraverse(root);
        System.out.print("\n");
    }

    public void postOrderPrint()
    {
        postOrderTraverse(root);
        System.out.print("\n");
    }
    
    // private methods used by public methods
    private boolean insertNode(BSTNode currentNode, int value)
    {
        if (value < currentNode.getData())
        {
            if (currentNode.getLeftChild() == null)
                currentNode.setLeftChild(new BSTNode(value));
            else
                return insertNode(currentNode.getLeftChild(), value);

            return true;
        }

        else if (value > currentNode.getData())
        {
            if (currentNode.getRightChild() == null)
                currentNode.setRightChild(new BSTNode(value));
            else
                return insertNode(currentNode.getRightChild(), value);
            
            return true;
        }

        return false;
    }

    private boolean deleteNode(BSTNode currentNode, int value)
    {
        if (value <= currentNode.getData()) // = is for the case max integer is inserted
        {
            if (currentNode.getLeftChild() == null)
                return false;
            else if (currentNode.getLeftChild().getData() == value)
            {
                BSTNode swapper = null; // node with value to replace currentNode's value

                if (currentNode.getLeftChild().isLeaf())
                {
                    if (currentNode.getLeftChild() == root)
                        root = null;
                    else
                        currentNode.setLeftChild(null);
                }
                else if (currentNode.getLeftChild().hasLeftChildOnly()) // has left child but no right child
                    currentNode.setLeftChild(currentNode.getLeftChild().getLeftChild());
                else if (currentNode.getLeftChild().hasRightChildOnly()) // has right child but no left child
                    currentNode.setLeftChild(currentNode.getLeftChild().getRightChild());
                else // has left and right child
                {
                    swapper = rightMostNode(currentNode.getLeftChild().getLeftChild());
                    currentNode.getLeftChild().setData(swapper.getData());
                    removeLeaf(currentNode.getLeftChild(), swapper);
                }

                root = maxNode.getLeftChild(); // in case it was a root deletion
                return true;
            }
            else
                return deleteNode(currentNode.getLeftChild(), value);
        }
        else
        {
            if (currentNode.getRightChild() == null)
                return false;
            else if (currentNode.getRightChild().getData() == value)
            {
                BSTNode swapper = null;

                if (currentNode.getRightChild().isLeaf())
                {
                    currentNode.setRightChild(null);
                    return true;
                }
                else if (currentNode.getRightChild().hasLeftChildOnly()) // has left child but no right child
                    currentNode.setRightChild(currentNode.getRightChild().getLeftChild());
                else if (currentNode.getRightChild().hasRightChildOnly()) // has right child but no left child
                    currentNode.setRightChild(currentNode.getRightChild().getRightChild());
                else // has left and right child
                {
                    swapper = rightMostNode(currentNode.getRightChild().getLeftChild());                    
                    currentNode.getRightChild().setData(swapper.getData());
                    removeLeaf(currentNode.getRightChild(), swapper);
                }

                root = maxNode.getLeftChild(); // in case it was a root deletion
                return true;
            }
            else
                return deleteNode(currentNode.getRightChild(), value);
        }
    }

    private void removeLeaf(BSTNode currentNode, BSTNode targetNode)
    {
        if (currentNode != null)
        {
            if (currentNode.getLeftChild() == targetNode)
                currentNode.setLeftChild(null);
            else if (currentNode.getRightChild() == targetNode)
                currentNode.setRightChild(null);
            else
            {
                removeLeaf(currentNode.getLeftChild(), targetNode);
                removeLeaf(currentNode.getRightChild(), targetNode);
            }
        }
    }

    private BSTNode rightMostNode(BSTNode currentNode)
    {
        if (!currentNode.hasRightChild())
            return currentNode;
        else
            return rightMostNode(currentNode.getRightChild());
    }
    
    private void preOrderTraverse(BSTNode currentNode)
    {
        if (currentNode != null)
        {
            System.out.print(currentNode.getData() + " ");
            preOrderTraverse(currentNode.getLeftChild());
            preOrderTraverse(currentNode.getRightChild());
        }
    }

    private void inOrderTraverse(BSTNode currentNode)
    {
        if (currentNode != null)
        {
            inOrderTraverse(currentNode.getLeftChild());
            System.out.print(currentNode.getData() + " ");
            inOrderTraverse(currentNode.getRightChild());
        }
    }

    private void postOrderTraverse(BSTNode currentNode)
    {
        if (currentNode != null)
        {
            postOrderTraverse(currentNode.getLeftChild());
            postOrderTraverse(currentNode.getRightChild());
            System.out.print(currentNode.getData() + " ");
        }
    }
}
