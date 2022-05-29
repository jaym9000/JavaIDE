import java.io.*;
import java.util.*;

class Project
{
   String name;
   JavaFile[] listOfFiles;
   int count;
   
   Project(String prjName)
   {
      name = prjName;
      listOfFiles = new JavaFile[5];
      count = 0;
   }
   
   void addFile(JavaFile file)
   {
      if (count == listOfFiles.length)
      {
         increaseSize();
      }
      listOfFiles[count] = file;
      count++;
   }
   
   private void increaseSize()
   {
      JavaFile[] temp = new JavaFile[listOfFiles.length * 2];
      for (int i = 0; i<count; i++)
      {
         temp[i] = listOfFiles[i];
      }
      listOfFiles = temp;
   }
   
   int search(String filename)
   {
      boolean found = false;
      int i = 0;
      while (i<count && !found)
      {
         if (filename.equals(listOfFiles[i].getName()))
         {
            found = true;
         }
         else
         {
            i++;
         }
      }
      if (found)
      {
         return i;
      }
      else
      {
         return -1;
      }
   }
   
   JavaFile getJavaFile(int index)
   {
      return listOfFiles[index];
   }
   
   void remove(int index)
   {
      if (index >= count)
      {
         System.out.println("Error: try to remove file outside range");
         return;
      }
      for (int i=index; i<count-1; i++)
      {
         listOfFiles[i] = listOfFiles[i+1];
      }
      count--;
   }
   
   public String toString()
   {
      String str = "Project " + name + " contains:\n";
      for (int i=0; i<count; i++)
      {
         str += "\t" + listOfFiles[i] + "\n";
      }
      return str;
   }
   
   String compile() throws Exception
   {
      Runtime env = Runtime.getRuntime();
      Process p;
      Scanner scan;
      String line;
      String result = "";
      
      boolean OK = true;
      int i = 0;
      while (i<count && OK)
      {
         p = env.exec("javac " + listOfFiles[i].getName() + ".java");
         scan = new Scanner(p.getErrorStream());
         if (!scan.hasNext())
            result += "Successfully compiled " + listOfFiles[i].getName() + "\n";
         else 
            result += "Compiling error with " + listOfFiles[i].getName() + "\n";

         while (scan.hasNext())
         {
            line = scan.nextLine();
            result += line + "\n";
            OK = false;
         }
         p.waitFor();
         i++;
      }
      return result;
   }
   
   String run() throws Exception
   {
      Runtime env = Runtime.getRuntime();
      Process p = env.exec("java " + name);
      
      Scanner scan = new Scanner(p.getInputStream());
      String line;
      String result = "";
      
      while (scan.hasNext())
      {
         line = scan.nextLine();
         result += line +"\n";
      }
      p.waitFor();
      return result;
   }
}

