public class UserCmd

{
     String  cmdLine   = null;
     char    cmdName   = ' ';
     int     intArgs[] = {   0,    0,    0,    0,    0};
     String  strArgs[] = {null, null, null, null, null};
     String  optArgs[] = {null, null, null, null, null};
     boolean syntaxOK  = false;

     // Constructor (do nothing -- all necessary done above)
     public UserCmd() {}

     // Set methods
     public void setCmdLine  (String cmd_line)        {cmdLine = cmd_line ;}
     public void setCmdName  (char cmd_name)          {cmdName = cmd_name ;}
     public void setIntArgs  (int arg,    int argNum) {intArgs[argNum]=arg;} 
     public void setStrArgs  (String arg, int argNum) {strArgs[argNum]=arg;}
     public void setOptArgs  (String arg, int argNum) {optArgs[argNum]=arg;}
     public void setOkSyntax ()                       {syntaxOK    = true ;}

     // Get methods
     public String  getCmdLine  ()           {return cmdLine        ;}
     public char    getCmdName  ()           {return cmdName        ;}
     public int     getIntArgs  (int argNum) {return intArgs[argNum];} 
     public String  getStrArgs  (int argNum) {return strArgs[argNum];}
     public String  getOptArgs  (int argNum) {return optArgs[argNum];}
     public boolean getOkSyntax ()           {return syntaxOK       ;}

} // EndClass UserCmd
