import java.util.Scanner;

public class Project1
{
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
            else if (input[0].equalsIgnoreCase("H"))
                displayOptions();
            else if (!input[0].equalsIgnoreCase("E")) // anything else but the defaults
                System.out.println("Must make valid input!");
            
            System.out.print("\n");
        }
        
        System.out.println("Thank you for using my program!");
    }

    public static void displayOptions()
    {
        String[] cmd = { "I", "D", "P", "S", "E", "H" };
        String[] desc = { "Insert a value", "Delete a value", "Find predecessor", "Find successor", "Exit the program", "Display this message" };

        for (int i = 0; i < 6; ++i)
            System.out.println(cmd[i] + "\t" + desc[i]);
    }
}
