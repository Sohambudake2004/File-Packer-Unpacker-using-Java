

import java.util.*;
import java.io.*;

class PackerUnpacker
{
    public static void main(String Arg[]) throws Exception
    {
        Scanner sobj = new Scanner(System.in);
        
        System.out.println("-----------------------------------------------------");
        System.out.println("------- Marvellous Packer Unpacker CUI Module -------");
        System.out.println("-----------------------------------------------------");

        System.out.println("Select Activity:");
        System.out.println("1. Packing");
        System.out.println("2. Unpacking");
        System.out.print("Enter your choice (1/2): ");
        int choice = sobj.nextInt();
        sobj.nextLine();  // Consume the newline character
        
        if(choice == 1)
        {
            packFiles(sobj);
        }
        else if(choice == 2)
        {
            unpackFiles(sobj);
        }
        else
        {
            System.out.println("Invalid choice, exiting...");
        }
    }
    
    // Method for Packing Files
    public static void packFiles(Scanner sobj) throws Exception
    {
        System.out.println("----------------- Packing Activity ------------------");
        System.out.println();

        System.out.println("Enter the name of Directory that you want to open for packing : ");
        String FolderName = sobj.nextLine();

        File fobj = new File(FolderName);

        System.out.println("Enter the name of packed file that you want to create : ");
        String PackedFile = sobj.nextLine();

        File Packobj = new File(PackedFile);
        
        boolean bret = Packobj.createNewFile();
        if(bret == false)
        {
            System.out.println("Unable to create packed file");
            return;
        }

        FileOutputStream foobj = new FileOutputStream(Packobj);

        if(fobj.exists())
        {
            int i = 0, j = 0;
            int iCount = 0;

            File Arr[] = fobj.listFiles();

            String Header = null;
            int iRet = 0;
            byte Buffer[] = new byte[1024];
            FileInputStream fiobj = null;
            
            for(i = 0; i < Arr.length; i++)
            {
                Header = Arr[i].getName();
                
                if(Header.endsWith(".txt"))
                {
                    System.out.println("File packed with name : "+Header);
                    
                    Header = Header + " " + Arr[i].length();

                    for(j = Header.length(); j < 100; j++)
                    {
                        Header = Header + " ";
                    }

                    foobj.write(Header.getBytes(),0,100);

                    fiobj = new FileInputStream(Arr[i]);

                    while((iRet = fiobj.read(Buffer)) != -1)
                    {
                        foobj.write(Buffer,0,iRet);
                    }

                    fiobj.close();
                    iCount++;
                }
            }

            System.out.println("-----------------------------------------------------");
            System.out.println("Packing activity completed..");
            System.out.println("Number of files scan : "+Arr.length);
            System.out.println("Number of files packed : "+iCount);
            System.out.println("-----------------------------------------------------");

            System.out.println("Thank you for using Marvellous Packer Unpacker tool");
            foobj.close();
        }
        else
        {
            System.out.println("There is no such directory");
        }
    }
    
    // Method for Unpacking Files
    public static void unpackFiles(Scanner sobj) throws Exception
    {
        byte Header[] = new byte[100];
        int iRet = 0;
        String HeaderX = null;
        File obj = null;
        int FileSize = 0;
        FileOutputStream foobj = null;
        int iCount = 0;
        
        System.out.println("---------------- Unpacking Activity -----------------");
        System.out.println();

        System.out.println("Enter the name of Packed file that you want to open : ");
        String PackedFile = sobj.nextLine();

        File fobj = new File(PackedFile);

        if(!fobj.exists())
        {
            System.out.println("Unable to proceed as Packed file is missing...");
            return;
        }  

        FileInputStream fiobj = new FileInputStream(fobj);

        while((iRet = fiobj.read(Header,0,100)) > 0)
        {
            HeaderX = new String(Header);
            HeaderX = HeaderX.trim();

            String Tokens[] = HeaderX.split(" ");

            obj = new File(Tokens[0]);
            System.out.println("File drop with name : "+Tokens[0]);

            obj.createNewFile();

            FileSize = Integer.parseInt(Tokens[1]);
            byte Buffer[] = new byte[FileSize];

            fiobj.read(Buffer,0,FileSize);

            foobj = new FileOutputStream(obj);
            foobj.write(Buffer,0,FileSize);

            foobj.close();
            iCount++;
        }

        System.out.println("-----------------------------------------------------");
        System.out.println("Unpacking activity completed..");
        System.out.println("Number of files unpacked : "+iCount);
        System.out.println("-----------------------------------------------------");

        System.out.println("Thank you for using Marvellous Packer Unpacker tool");
        
        fiobj.close();
    }
}
