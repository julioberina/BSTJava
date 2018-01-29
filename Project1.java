/*
Programmer: Julio Berina
CS 241
Project Due Date: Jan 30
*/

import java.util.Scanner;

/*
This is the main class for the entire project which contains the main method
and a few other methods used in main
*/
public class Project1
{

	/*
	  This is the main method that simply takes command-line arguments
	  and where the entire program lives in
	 */
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        BinarySearchTree bst = new BinarySearchTree();
        System.out.println("Please enter the sequence of values:  ");

        // make an array of strings by chopping the spaces for separation
        String[] strSeq = scan.nextLine().trim().split(" +");
        System.out.print("\n");
      
        // for command input later
        String[] input = { "", "" };

        for (String str : strSeq)
            bst.insert(Integer.parseInt(str));
        
        System.out.print("Pre-order:  ");
        bst.preOrderPrint();
        System.out.print("In-order:  ");
        bst.inOrderPrint();
        System.out.print("Post-order:  ");
        bst.postOrderPrint();
        System.out.print("\n");

        while (!input[0].equalsIgnoreCase("E"))
        {
            System.out.print("Command?  ");
            input = scan.nextLine().trim().split(" +");
			
            if (input[0].equalsIgnoreCase("I"))
            {
                if (input.length == 1)
                    System.out.println("Must use insert with a value!");
                else
                {
                    if (bst.insert(Integer.parseInt(input[1])))
                        bst.inOrderPrint();
                    else
                        System.out.println("Value is already in tree!");
                }
            }
            else if (input[0].equalsIgnoreCase("D"))
            {
                if (input.length == 1)
                    System.out.println("Must use delete with a value!");
                else
                {
                    if (bst.delete(Integer.parseInt(input[1])))
                        bst.inOrderPrint();
                    else
                        System.out.println("Value is not in tree!");
                }
            }
			else if (input[0].equalsIgnoreCase("P"))
			{
				if (input.length == 1)
					System.out.println("Must use predecessor with a value!");
				else
				{
					int[] numref = new int[1];
					numref[0] = Integer.parseInt(input[1]);
					
					if (bst.preOf(numref))
						System.out.println(numref[0]);
					else
						System.out.println(numref[0] + " does not exist or have a predecessor!");
				}
			}
			else if (input[0].equalsIgnoreCase("S"))
			{
				if (input.length == 1)
					System.out.println("Must use successor with a value!");
				else
				{
					int[] numref = new int[1];
					numref[0] = Integer.parseInt(input[1]);
					
					if (bst.sucOf(numref))
						System.out.println(numref[0]);
					else
						System.out.println(numref[0] + " does not exist or have a successor!");
				}
			}
			else if (input[0].equalsIgnoreCase("H"))
				displayOptions();
			else if (!input[0].equalsIgnoreCase("E")) // anything else but the defaults
				System.out.println("Must make valid input!");
            
            System.out.print("\n");
        }
        
        System.out.println("Thank you for using my program!");
    }
	
	/*
	  This is a method that displays the choices a user has to interact with the binary search tree or the program in general
	 */
    public static void displayOptions()
    {
        String[] cmd = { "I", "D", "P", "S", "E", "H" };
        String[] desc = { "Insert a value", "Delete a value", "Find predecessor", "Find successor", "Exit the program", "Display this message" };
		
        for (int i = 0; i < 6; ++i)
            System.out.println(cmd[i] + "\t" + desc[i]);
    }
}
