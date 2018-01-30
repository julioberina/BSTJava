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

    /* standard public methods of BST */

	/*
	  This is the constructor for the class which initializes the root to null
	*/
    public BinarySearchTree()
    {
        root = null;
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
            root = new BSTNode(value);
        else
            return insertNode(root, value);

        return true;
    }

	/*
	  This method takes an integer param (value) and returns false when the tree is empty.
	  Otherwise, it delegates its result to deleteNode starting from the root looking for value.
	  Its primary function is to delete a node with the value from integer param or false if there is
	  no node on the tree with that value
	*/
    public boolean delete(int value)
    {
		boolean[] result = new boolean[1];
		result[0] = false;

        if (root == null)
            return false;

		deleteNode(root, value, result);
		return result[0];
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
	  This method is a boolean that takes a node, integer and boolean array (pass by reference)
      It traverses the tree in an in-order manner to look for the value. Either false is
      returned when value is not found or the proper algorithm is applied to make the deletion
      of the node containing the value happen.
	 */
    private BSTNode deleteNode(BSTNode currentNode, int value, boolean[] found)
	{
		if(currentNode == null)
			return null;

		if (currentNode.getData() > value)
			currentNode.setLeftChild(deleteNode(currentNode.getLeftChild(), value, found));
		else if (currentNode.getData() < value)
			currentNode.setRightChild(deleteNode(currentNode.getRightChild(), value, found));
		else
		{
			found[0] = true;

			if (currentNode.isLeaf())
            {
                if (currentNode == root)
                    root = null;

				return null;
            }
			else if (currentNode.hasLeftChildOnly())
            {
                if (currentNode == root)
                    root = root.getLeftChild();

				return currentNode.getLeftChild();
            }
			else if (currentNode.hasRightChildOnly())
            {
                if (currentNode == root)
                    root = root.getRightChild();
				return currentNode.getRightChild();
            }

			BSTNode swapper = rightMostNode(currentNode.getLeftChild());
			int data = swapper.getData();
			currentNode.setData(data);
			currentNode.setLeftChild(deleteNode(currentNode.getLeftChild(), data, found));
		}

		return currentNode;
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
