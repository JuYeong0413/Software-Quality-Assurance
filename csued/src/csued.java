import java.io.*;

class csued

{
    public static void main(String args[]) throws IOException
    {
    	
        File_Buffer FILE       = new File_Buffer();
        Init_Exit Start_End    = new Init_Exit(args,FILE);
        Cmd_Driver Run_Command = new Cmd_Driver();
        UserCmd commandLineTokens;
        boolean done = false;

        // Check if error in invocations arguments or edit file opening/reading

        if(!Start_End.Start_Failed()) 
        {                    

           // Loop over user commands & process valid commands until done.

           do 
           {
               commandLineTokens = Parser.parseCmdLine();
    	       if(commandLineTokens.getOkSyntax())
	           done = Run_Command.RunCmd(FILE,commandLineTokens); 
               else
               {
                   Msg.ERROR(-3);
                   Msg.wLMsg(commandLineTokens.getCmdLine());
               }
           } while (!done);

           // Done editing: update the edit file if requested and quit editor

           Start_End.Do_Update(FILE);
        }
    }
} //Endclass csued 
