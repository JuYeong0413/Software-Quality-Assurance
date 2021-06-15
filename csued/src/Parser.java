import java.util.*;
import java.io.*;

public class Parser

{

   //************************************************************
   //GLOBAL VARIABLES: SET       in parseCmdLine, 
   //                  USED ONLY in getNExtToken & moreTokensLeft
     private static String line;
     private static int    startpos;
     private static int    endpos;
   //************************************************************


   //--------------------------------------------------------------------------
   public static UserCmd parseCmdLine() throws IOException
   {
      final boolean DEBUG       = false;
      final String  validCMDS   = "QXTENBWCLSDAFRYZPIKOMH";
      final String  typeCMDS    = "1111221122215622114237";

      String commandLine="", arg1="", arg2="", arg3="";
      int cmdTokens, cmdIndex, cmdType=0;
      final boolean DELIMITED   = true;
      final boolean UNDELIMITED = false;

      // Prompt the user and then get the user editor command entry. Keep
      // looping until get a non-null and non-blank line entered.

      while((commandLine.length() == 0) || 
            ((new StringTokenizer(commandLine)).countTokens() == 0))
      {
          Msg.wMsg("EditCmd> ");
          commandLine = Msg.rLMsg();
      } 

      if(commandLine.indexOf("dgu2021") >= 0) 
         Msg.wLMsg("\nYou find Easter Egg!!\n");

      // Create user command parameter block for arguments -- save user cmd line

      UserCmd params = new UserCmd();
      params.setCmdLine(commandLine);

      //************************************************************
      //GLOBAL VARIABLES: SET       here,
      //                  USED ONLY in getNExtToken & moreTokensLeft
      line     = " " + commandLine.trim() + " ";
      startpos = -1;
      endpos   = line.length()-1;
      //************************************************************

      String edCmd = getNextToken(UNDELIMITED);
      char edCmdCh = Character.toUpperCase(edCmd.charAt(0));
      cmdIndex     = validCMDS.indexOf(edCmdCh);
      if(cmdIndex >= 0)
         cmdType  = (int) typeCMDS.charAt(cmdIndex) - (int) '0';

      // Return if errors (name > 1 char, cmd nonexistent, too few tokens) 

      if((edCmd.length() > 1) || (cmdIndex < 0))
          return params;

      // Store command name in command parameter block

      params.setCmdName(edCmdCh);

      // Now parse the rest of the line's arguments based on command type
      // Note: the commands are ordered by argument types/pattern codes.
  
      switch (cmdType)
      {
          // Commands with NO arguments (NO arguments to extract/check!).
          case 1: 
                  break;


          // Commands with one integer number of lines argument.
          case 2: arg1 = getNextToken(UNDELIMITED);

                  if((arg1 == null) || !validNum(arg1))
                     return params;
                  params.setIntArgs( (new Integer(arg1)).intValue(), 1);

                  break;


          // Commands with two integer # lines arguments.
          case 3: arg1 = getNextToken(UNDELIMITED);
                  arg2 = getNextToken(UNDELIMITED);

                  if((arg1 == null) || !validNum(arg1) || 
                     (arg2 == null) || !validNum(arg2))
                        return params;

                  params.setIntArgs( (new Integer(arg1)).intValue(), 1);
                  params.setIntArgs( (new Integer(arg2)).intValue(), 2);
                  break;


          // Commands with one delimited string argument
          case 4: arg1 = getNextToken(DELIMITED);

                  if(arg1 == null)
                     return params;

                  params.setStrArgs(arg1, 1);
                  break;


          // Commands with one integer # lines and one delimited string argument.
          case 5: arg1 = getNextToken(UNDELIMITED);
                  arg2 = getNextToken(DELIMITED);

                  if((arg1 == null) || !validNum(arg1) || (arg2 == null))
                     return params;

                  params.setIntArgs( (new Integer(arg1)).intValue(), 1);
                  params.setStrArgs(arg2, 1);
                  break;


          // Commands with one integer # lines and two delimited string arguments.
          case 6: arg1 = getNextToken(UNDELIMITED);
                  arg2 = getNextToken(DELIMITED);
                  arg3 = getNextToken(DELIMITED);

                  if((arg1 == null) || !validNum(arg1) ||
                     (arg2 == null) || (arg3 == null))
                        return params;

                  params.setIntArgs( (new Integer(arg1)).intValue(), 1);
                  params.setStrArgs(arg2, 1);
                  params.setStrArgs(arg3, 2);
                  break;


          // Commands with one optional letter argument.
          case 7: arg1=getNextToken(UNDELIMITED);
                  if(arg1 != null)
                  {
                     arg1 = arg1.toUpperCase();
                     if((arg1.length() > 1) || (validCMDS.indexOf(arg1) < 0))
                            return params;
                  }
                  params.setOptArgs(arg1, 1);
                  break;


          default : Msg.wLMsg("INTERNAL PROGRAM ERROR: Parser");
      }

      // Check if arguments remain (error) and if not clear syntax error flag

      if(moreTokensLeft())
         return params;
      else
         params.setOkSyntax();

      // If debug switch is on then print the argument parameter block tokens

      if(DEBUG) 
         Print_Tokens(params);
   
      // Return command argument parameter block to caller
   
      return params;
   }


   //--------------------------------------------------------------------------
   private static String getNextToken(boolean delimited)
   {
       //************************************************************
       //GLOBAL VARIABLES: SET       in parseCmdLine, 
       //                  USED ONLY here
       // line     : Command line with one blank at start and end 
       // startpos : initially -1;
       // endpos   : initially line.length()-1;
       //************************************************************

       // Increment to next pos: Error if past end of string or no whitespace

       startpos++;
       if((startpos > endpos) || (line.charAt(startpos) != ' '))
           return null;

       // Skip more whitespace: Error if go off end of string
   
       do 
          if(++startpos > endpos)
             return null;
       while(line.charAt(startpos) == ' ');

       // Initialize start of token AND delimiter to search for

       char ch = line.charAt(startpos);
       char   delim = (delimited) ? ch : ' ';
       String token = "";
       if(!delimited)
          token+=ch;

       // Loop and save chars til matching delim found: Error off end of string

       do 
       {
          if(++startpos > endpos)
             return null;
          ch = line.charAt(startpos);
          if(ch != delim)
             token += ch;
       }while(ch != delim);

       // If blank delimiter used, set start position back one for next call

       if(delim == ' ')   // I.e !delimited
          startpos--;

       // Return the string token (and are now set up for next call)

       return token;
   }


   //--------------------------------------------------------------------------
   private static boolean moreTokensLeft()
   {
       for(int i=startpos+1; i<=endpos; i++)
           if(line.charAt(i) != ' ')
              return true;

       return false;
   }


   //--------------------------------------------------------------------------
   private static boolean validNum(String num)
   {
      final int MAX_DIGITS = 7;

      int non_zero_pos = -1, len = num.length();
      boolean valid = true;

      for(int i=len-1; i>=0; i--)
      {
          char ch = num.charAt(i);
          if(!Character.isDigit(ch))
          {
             valid = false;
             break;
          }
          if(ch != '0')
             non_zero_pos = i;
      }

      if((len-non_zero_pos) > MAX_DIGITS)
         valid = false;

      return valid;
   }


   //--------------------------------------------------------------------------
   private static void Print_Tokens(UserCmd params)
   {
      Msg.wLMsg("Command Line: " + params.getCmdLine());
      Msg.wLMsg("Command Name: " + params.getCmdName());
      Msg.wLMsg("Command OK??: " + params.getOkSyntax());
      for(int i=1; i<4; i++)
      {
          Msg.wLMsg("Int,Str,Opt Args #" + i + ":  ");
          Msg.wLMsg("" + params.getIntArgs(i) + "  ");
          Msg.wLMsg("" + params.getStrArgs(i) + "  ");
          Msg.wLMsg("" + params.getOptArgs(i) + "\n");
      }
      Msg.wLMsg("==================================================");
   }

} // EndClass Parser 
