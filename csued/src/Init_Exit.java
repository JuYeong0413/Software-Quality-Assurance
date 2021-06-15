import java.io.*;

public class Init_Exit

{
   String hold_file_name;
   External_File editFile = new External_File();
   boolean Fatal_Error = false, open_failed;

   public Init_Exit(String args[], File_Buffer FILE) throws IOException
   {
       int line_count=0;

       // Check for incorrect number of arguments and handle any errors.

       if(args.length != 1) 
       {
          Fatal_Error=true;
          Msg.ERROR(0);
          return;
       }

       // No errors, so save the user edit file name from invoke arg list

       hold_file_name = args[0];

       // Open on user edit file name and read the file if it exists

       open_failed = editFile.openR(hold_file_name);
       if(!open_failed) 
       {
           while(!editFile.havehitEOF()) 
               FILE.AddLine(line_count++,editFile.getLine());
           editFile.close();
       }
       else 
           Msg.ERROR(1);

       // Set CLN regardless of above opening

       FILE.SetCLN( Math.min(FILE.NumLins(),1) );
   }


   public boolean Start_Failed() 
   {
      return Fatal_Error;
   }


   public void Do_Update(File_Buffer FILE) throws IOException
   {
       int i,Nlines;

       // Check to see if file update wanted -- if not then skip rewriiting file.

       if(FILE.getUpdateFlag()) 
       {
          // open the user edit file and write the entire buffer to the file

          open_failed = editFile.openW(hold_file_name);
          Nlines=FILE.NumLins();

          if(Nlines == 0)                    // File nulled if NO Buff lines!
             Msg.ERROR(2);
          else                               // Rewrite file with all buff lines.
             for(i=1; i<Nlines; i++)
                 editFile.writeln(FILE.GetLine(i));

          editFile.close();
       }
   }

} // EndClass Init_Exit 
