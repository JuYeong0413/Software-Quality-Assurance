import java.io.*;

public class Msg

{

  //***************************************************************************
  public static void ERROR(int error_number)
  {
    // Print the appropriate error message and handle illegal error #s
    switch (Math.abs(error_number))
    {
     case 0: Msg.wMsg(
             "PROGRAM INVOCATION ARGUMENT ERROR(S): Terminating the program...");
             break;

     case 1: Msg.wMsg(
             "USER EDIT FILE EMPTY:  No information read - file created.");
             break;

     case 2: Msg.wMsg(
             "USER EDIT FILE EMPTY:  File written but it will be an empty file.");
             break;

     case 3: Msg.wMsg(
             "SYNTAX ERROR IN COMMAND: ");
             break;
  
     case 4: Msg.wMsg(
             "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken.");
             break;

     case 5: Msg.wMsg(
             "ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.");
             break;

     case 6: Msg.wMsg(
             "A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken.");
             break;

     case 7: Msg.wMsg(
             "NO LINES IN YANK BUFFER TO PUT:  No action taken.");
             break;

     case 8: Msg.wMsg(
             "THERE ARE NO KEYWORDS AT TOP OF FILE TO INDEX:  No action taken.");
             break;

     case 9: Msg.wMsg(
             "THIS KEYWORD DOES NOT EXIST:  No action taken.");
             break;

     case 10:Msg.wMsg(
             "COLUMN VALUES MUST BE POSITIVE & NONZERO:  No action taken.");
             break;

     case 11:Msg.wMsg(
             "REVERSED OR BACKWARDS COLUMN RANGES ARE ILLEGAL:  No action taken.");
             break;

     default: Msg.wMsg(
              "<<<<< INTERNAL PROGRAM ERROR => Unknown error code used. >>>>>");
    }

    // If error number positive the output EOLN
    if(error_number >= 0) 
       Msg.wLMsg("");
  }

  //***************************************************************************
  public static void wMsg(String str)
  {
     System.out.print(str);
     System.out.flush();
  }

  //***************************************************************************
  public static void wLMsg(String str)
  {
     System.out.println(str);
     System.out.flush();
  }

  //***************************************************************************
  public static String rLMsg() throws IOException
  {
     BufferedReader stdin = new BufferedReader(
                            new InputStreamReader(System.in));
    
     return stdin.readLine();
  }

} // EndClass Msg 
