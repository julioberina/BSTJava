/*
This class contains all the classes, variables, and methods that make up a full implementation
of a binary search tree
*/

class BinarySearchTree
{
	/*
     This is a class representing nodes of the BST with data and left and right children
	*/
    class BSTNode
    {
        private int data;
        private BSTNode left;
        private BSTNode right;

		/*
		  This is the constructor for BSTNode which takes a data parameter
		  to give the node an initial value
		 */
        public BSTNode(int data)
        {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        /* Getters */
		
		// returns the node's data attribute
        public int getData() { return data; }

		// returns the node's left attribute a.k.a. the left child of the node
        public BSTNode getLeftChild() { return left; }

		// returns the node's right attribute a.k.a the right child of the node
        public BSTNode getRightChild() { return right; }

        /* Setters */

		// sets the data attribute of the node to the integer supplied in param
        public void setData(int data) { this.data = data; }

		// sets the left child of the node to BSTNode supplied in param
        public void setLeftChild(BSTNode child) { left = child; }
		
		// sets the right child of the node to BSTNode supplied in param
        public void setRightChild(BSTNode child) { right = child; }

        /* These are convenience methods that ease algorithmic operations on BST nodes */

		// checks if node has a left child
        public boolean hasLeftChild() { return (this.left != null); }

		// checks if node has a right child
        public boolean hasRightChild() { return (this.right != null); }

		// checks if node solely has a left child (no right child)
        public boolean hasLeftChildOnly() { return (hasLeftChild() && this.right == null); }

		// checks if a node solely has a right child (no left child)
        public boolean hasRightChildOnly() { return (hasRightChild() && this.left == null); }

		// checks if node has completely no children at all
        public boolean isLeaf() { return (this.left == null && this.right == null); }
    }

    private BSTNode root;
    private BSTNode maxNode; // for deletion purposes;
    
    /* standard public methods of BST */

	/*
	  This is the constructor for the class which initializes the root to null
	 */
    public BinarySearchTree()
    {
        root = null;
        maxNode = new BSTNode(Integer.MAX_VALUE); // create node containing maximum integer value
        maxNode.setLeftChild(root); // for deletion purposes
    }

	/*
	  This method is a boolean that takes an integer and traverses the tree in an in-order manner.
	  It creates a node and sets its data to the int param (value) when the root is null and returns true.
	  Otherwise, it delegates its result to insertNode starting at root and looking for a place for value.
	  Its primary function is to insert a node with the value from integer param or false if it already
	  exists in the tree
	 */
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

	/*
	  This method takes an integer param (value) and returns false when the tree is empty.
	  Otherwise, it delegates its result to deleteNode starting from maxNode looking for value.
	  Its primary function is to delete a node with the value from integer param or false if there is
	  no node on the tree with that value
	 */
    public boolean delete(int value)
    {
        if (root == null)
            return false;
        else
            return deleteNode(maxNode, value);
    }

	/*
	  This method is a boolean which takes an integer array param typically with a size of 1 since
	  it wraps one integer to emulate pass-by-reference like functionality. It returns true while
	  replacing the value in the array with the data attribute of its predecessor node or false if the 
	  node with the value either does not exist or have a predecessor
	 */
    public boolean preOf(int[] ref)
    {
		return predecessor(root, ref);
    }

	/*
	  This method is a boolean which takes an integer array param typically with a size of 1 since
	  it wraps one integer to emulate pass-by-reference like functionality. It returns true while 
	  replacing the value in the array with the data attribute of its successor node or false if the 
	  node with the value either does not exist or have a predecessor
	 */
    public boolean sucOf(int[] ref)
    {
		return successor(root, ref);
    }

	/*
	  This method simply prints the binary search tree in a pre-order manner.
	 */
    public void preOrderPrint()
    {
        preOrderTraverse(root);
        System.out.print("\n");
    }
    
	/*
	  This method simply prints the binary search tree in an in-order manner.
	 */
    public void inOrderPrint()
    {
        inOrderTraverse(root);
        System.out.print("\n");
    }

	/*
	  This method simply prints the binary search tree in a post-order manner
	 */
    public void postOrderPrint()
    {
        postOrderTraverse(root);
        System.out.print("\n");
    }
    
    /* private methods used by public methods */

	/*
	  This method is a boolean that takes a node and an integer.  It looks for a place to insert the
	  value by traversing the tree in an in-order manner.  Once it finds a spot for the value, it
	  creates the node and sets it as the left or right child of the appropriate node and returns true.
	  Returns false when it spots a node with the value already in the tree
	 */
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

	/*
	  This method is a boolean that takes a node and an integer.  It looks for the node whose child 
	  contains the value to delete from the tree while traversing the tree in an in-order manner.  It
	  return false when the child in which the direction of the traversal goes towards is null (value
	  not in the tree in other words). Otherwise, it implements the proper algorithm to delete the
	  node from the tree.
	 */
    private boolean deleteNode(BSTNode currentNode, int value)
    {
        if (value <= currentNode.getData()) // = is for the case Integer.MAX_VALUE is inserted
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
                    removeLeaf(currentNode.getLeftChild(), swapper); // delete the duplicate of currentNode
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
                BSTNode swapper = null; // node with value to replace currentNode's value

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
                    removeLeaf(currentNode.getRightChild(), swapper); // delete the duplicate of currentNode
                }

                root = maxNode.getLeftChild(); // in case it was a root deletion
                return true;
            }
            else
                return deleteNode(currentNode.getRightChild(), value);
        }
    }

	/*
	  This is a method that takes a node and a reference array with 1 integer for pass-by-reference
	  reasons. Its primary function is to find the predecessor node of the node with the integer in
	  ref through in-order traversal by finding the rightmost node in the left subtree or the last right
	  turn in the traversal. returns true if a node is found or false if currentNode is null or no preNode.
	 */
    private boolean predecessor(BSTNode currentNode, int[] ref)
    {
		if (currentNode != null)
		{
			if (ref[0] < currentNode.getData())
				return predecessor(currentNode.getLeftChild(), ref);
			else if (ref[0] > currentNode.getData())
				return predecessor(currentNode.getRightChild(), ref);
			else
			{
				BSTNode preNode = null;
				
				if (currentNode.hasLeftChild())
					preNode = rightMostNode(currentNode.getLeftChild());
				else
					preNode = lastRight(root, null, ref);
				
				if (preNode != null)
					ref[0] = preNode.getData();
				else
					return false;
				
				return true;
			}
		}
		else
			return false;
    }

	/*
	  This is a method that takes a node and a reference array with 1 integer for pass-by-reference
	  reasons. Its primary function is to find the successor node of the node with the integer in
	  ref through in-order traversal by finding the leftmost node in the right subtree or the last left
	  turn in the traversal. returns true if a node is found or false if currentNode is null or no sucNode.
	 */
    private boolean successor(BSTNode currentNode, int[] ref)
    {
		if (currentNode != null)
		{
			if (ref[0] < currentNode.getData())
				return successor(currentNode.getLeftChild(), ref);
			else if (ref[0] > currentNode.getData())
				return successor(currentNode.getRightChild(), ref);
			else
			{
				BSTNode sucNode = null;
				
				if (currentNode.hasRightChild())
					sucNode = leftMostNode(currentNode.getRightChild());
				else
					sucNode = lastLeft(root, null, ref);
				
				if (sucNode != null)
					ref[0] = sucNode.getData();
				else
					return false;
				
				return true;
			}
		}
		else
			return false;
    }

	/*
	  This method takes two nodes, currentNode and targetNode.  It knows from the start that 
	  targetNode is a leaf.  It simply traverses the tree to to check which of the currentNode's 
	  children points to targetNode.  Once targetNode is located, currentNode removes reference
	  of that node to be deleted
	 */
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

	/*
	  This is a method that takes two nodes and an int array containing 1 integer for pass by
	  reference as arguments.  It traverses the tree in an in-order manner starting with currentNode
	  to look for the node containing the integer (ref). Set value of lastLeftNode to currentNode if
	  going to the left.  Otherwise, keep as is and return lastLeftNode. Used in finding successor
	 */
    private BSTNode lastLeft(BSTNode currentNode, BSTNode lastLeftNode, int[] ref)
    {
		if (currentNode == null)
			return null;
		else
		{
			if (ref[0] < currentNode.getData())
				return lastLeft(currentNode.getLeftChild(), currentNode, ref);
			else if (ref[0] > currentNode.getData())
				return lastLeft(currentNode.getRightChild(), lastLeftNode, ref);
			else
				return lastLeftNode;
		}
    }

	/*
	  This is a method that takes two nodes and an int array containing 1 integer for pass by
	  reference as arguments.  It traverses the tree in an in-order manner starting with currentNode
	  to look for the node containing the integer (ref). Set value of lastRightNode to currentNode if
	  going to the right. Otherwise, keep as is and return lastRightNode. Used in finding predecessor
	 */
    private BSTNode lastRight(BSTNode currentNode, BSTNode lastRightNode, int[] ref)
    {
		if (currentNode == null)
			return null;
		else
		{
			if (ref[0] < currentNode.getData())
				return lastRight(currentNode.getLeftChild(), lastRightNode, ref);
			else if (ref[0] > currentNode.getData())
				return lastRight(currentNode.getRightChild(), currentNode, ref);
			else
				return lastRightNode;
		}
    }
	
	/*
	  This method takes a node param to start from and returns the leftmost node from there
	 */
    private BSTNode leftMostNode(BSTNode currentNode)
    {
		if (!currentNode.hasLeftChild())
			return currentNode;
		else
			return leftMostNode(currentNode.getLeftChild());
    }
	
	/*
	  This method takes a node param to start from and returns the rightmost node from there
	 */
    private BSTNode rightMostNode(BSTNode currentNode)
    {
        if (!currentNode.hasRightChild())
            return currentNode;
        else
            return rightMostNode(currentNode.getRightChild());
    }
    
	/*
	  This method prints all the values of the tree through a pre-order traversal.
	 */
    private void preOrderTraverse(BSTNode currentNode)
    {
        if (currentNode != null)
        {
            System.out.print(currentNode.getData() + " ");
            preOrderTraverse(currentNode.getLeftChild());
            preOrderTraverse(currentNode.getRightChild());
        }
    }
	
	/*
	  This method prints all the values of the tree through an in-order traversal.
	 */
    private void inOrderTraverse(BSTNode currentNode)
    {
        if (currentNode != null)
        {
            inOrderTraverse(currentNode.getLeftChild());
            System.out.print(currentNode.getData() + " ");
            inOrderTraverse(currentNode.getRightChild());
        }
    }

	/*
	  This method prints all the values of the tree through an post-order traversal.
	 */
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
