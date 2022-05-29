import java.io.*;
import java.util.*;

class JavaFile
{
   String name;
   String content = "";
   String path;
   
   JavaFile(String Path)
   {
      path = Path;
      int lastslash = Path.lastIndexOf("\\");
      int dot = Path.indexOf(".");
      name = Path.substring(lastslash+1,dot) ;
   }
   
   
   String getName()
   {
      return name;
   }
   
   String getContent()
   {
      return content;
   }
   
   void setContent(String content)
   {
      this.content = content;
   }
   
   public String toString()
   {
      return name;
   }
   
   void load() throws FileNotFoundException
   {
      content = "";
     File file = new File(path);
      Scanner scan = new Scanner(new File(path));
      
      while (scan.hasNext())
      {
         content += scan.nextLine() + "\n";
      }
   }
   
   void save() throws IOException
   {
      PrintWriter outFile = new PrintWriter(path);
      outFile.println(content);
      outFile.close();
   }
}
