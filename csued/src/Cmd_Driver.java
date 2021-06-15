import java.lang.*;
import java.io.*;

public class Cmd_Driver

{
   private final boolean UPDATE_FILE = true;

   int Margin_Left, Margin_Right; // Used by M, F, R, O Cmds.
   PQueue Yank_Buffer;            // Used by Y, X & P commands!
   DLL kwList;

   private class Krec
   {
       String kW;
       DLL Lines = new DLL();

       public Krec(String kw)     {kW = kw;      }  // CONSTRUCTOR

       public void   storeKWline(Integer lineNum) 
                                  {Lines.insertDLL(Lines.numberDLL(),lineNum); }
       public String getKW()      {return kW;    }
       public DLL    getKWlines() {return Lines; }
   }


   //------------------------------------------------------------------------
   public Cmd_Driver() 
   {  
       Yank_Buffer  = null;  
       kwList       = null;
       Margin_Left  = 1;     
       Margin_Right = 80;   
   }


   //------------------------------------------------------------------------
   public boolean RunCmd(File_Buffer FILE, UserCmd args) throws IOException
   {
      // Initialize done flag (i.e. user requested to terminated editing).

      boolean done = false;

      // Now call the command routine on the basis of the user command

      switch (Character.toUpperCase(args.getCmdName())) 
      {

        //========= TERMINATE EDITOR COMMANDS =================================
       
          case 'Q': // QUIT (& Update File) Command
                    done = true;
                    FILE.setUpdateFlag( UPDATE_FILE );
                    break;

          case 'X': // EXIT (& DO NOT Update File) Command
                    done   = true;
                    FILE.setUpdateFlag( !UPDATE_FILE );
                    break;

        //========= TERMINATE EDITOR COMMANDS =================================

          case 'H': // HELP (with optional command argument) Command
                    Cmd_H(args.getOptArgs(1));
                    break;
       
        //========= MOVE/SHOW CURRENT-LINE-NUMBER/CLN EDITOR COMMANDS =========

          case 'T': // TOP (Move CLN to Top of File) Command
                    Cmd_T(FILE);
                    break;

          case 'E': // END (Move CLN to End of File) Command
                    Cmd_E(FILE);
                    break;

          case 'N': // NEXT Lines (Move CLN forward) Command
                    Cmd_N(FILE,args.getIntArgs(1));
                    break;

          case 'B': // BACK Lines (Move CLN backward) Command
                    Cmd_B(FILE,args.getIntArgs(1));
                    break;

          case 'W': // WHERE (Print CLN) Command
                    Cmd_W(FILE);
                    break;

          case 'C': // COUNT (Print Total File Lines) Command
                    Cmd_C(FILE);
                    break;

        //========= PRINT LINES EDITOR COMMANDS ===============================

          case 'L': // LIST (Move CLN) Lines Command
                    Cmd_L(FILE,args.getIntArgs(1));
                    break;

          case 'S': // SHOW (DO NOT Move CLN) Lines Command
                    Cmd_S(FILE,args.getIntArgs(1));
                    break;

        //========= DELETE/ADD LINES EDITOR COMMANDS ==========================

          case 'D': // DELETE Lines Command
                    Cmd_D(FILE,args.getIntArgs(1));
                    break;

          case 'A': // ADD Lines Command
                    Cmd_A(FILE);
                    break;

        //========= STRING FIND/REPLACE LINES EDITOR COMMANDS =================

          case 'F': // FIND String In Lines Command
                    Cmd_F(FILE,args.getIntArgs(1),args.getStrArgs(1));
                    break;

          case 'R': // REPLACE String In Lines Command
                    Cmd_R(FILE,args.getIntArgs(1),
                               args.getStrArgs(1),args.getStrArgs(2));
                    break;

        //========= COPY/CUT & PASTE LINES EDITOR COMMANDS ====================

          case 'Y': // YANK (to Yank Buffer) Lines Command
                    Cmd_Y(FILE,args.getIntArgs(1));
                    break;

          case 'Z': // YANK DELETE (to Yank Buffer) Lines Command
                    Cmd_Z(FILE,args.getIntArgs(1));
                    break;

          case 'P': // PUT (Yank Buffer) Lines Command
                    Cmd_P(FILE);
                    break;

        //========= INDEX/KEYWORD EDITOR COMMANDS =============================

          case 'I': // INDEX Keywords Command
                    Cmd_I(FILE);
                    break;

          case 'K': // Print KEYWORD In Which Lines Command
                    Cmd_K(args.getStrArgs(1));
                    break;

        //========= SPECIAL EDITOR COMMANDS ===================================

          case 'O': // ORDER (Sort L-H) Lines Command
                    Cmd_O(FILE,args.getIntArgs(1));
                    break;

          case 'M': // MARGIN (Set Margins/Window) Command
                    Cmd_M(args.getIntArgs(1),args.getIntArgs(2));
                    break;

        //========= INTERNAL PROGRAM ERROR CASE ===============================
   
          default : Msg.wMsg("INTERNAL SYSTEM ERROR:");
                    Msg.wLMsg("    Cmd_Driver: Illegal edit cmd name");
      }

      // Command processed, return done flag

      return done;
   }


   //============================================================================
   //============================================================================
   //============================================================================


   private boolean Valid_Lines(File_Buffer FILE, int nLines)
   {
       boolean Ok1=true, Ok2=true;

       // Check that number of lines is positive

       Ok1 = (nLines >= 1);

       // Check if nonsensical: nLines positive but 0/NO file buffer lines!

       Ok2 = (FILE.NumLins() != 0);

       // If errors print correct error message
     
       if(!Ok1)
          Msg.ERROR(4);
       else if(!Ok2) 
          Msg.ERROR(5);

       // Now return the line number validity status

       return (Ok1 && Ok2);

   }


   //===================== HELP INFOMATION COMMANDS ======================


   public void Cmd_H(String cmd)
   {
       // Call static methods in the help class for help information
       if(cmd == null)
          Help.General();    
       else
          Help.Command(cmd.charAt(0));
   }

   //========= MOVE/SHOW CURRENT-LINE-NUMBER/CLN EDITOR COMMANDS =========


   public void Cmd_T(File_Buffer FILE)
   {
       FILE.SetCLN( Math.min(FILE.NumLins(),1) );
   }


   public void Cmd_E(File_Buffer FILE)
   {
       FILE.SetCLN( FILE.NumLins() );
   }


   public void Cmd_N(File_Buffer FILE, int nLines)
   {
       if(Valid_Lines(FILE,nLines)) 
          FILE.SetCLN( Math.min(FILE.GetCLN()+nLines,FILE.NumLins()) );
   }


   public void Cmd_B(File_Buffer FILE, int nLines)
   {
       if(Valid_Lines(FILE,nLines)) 
          FILE.SetCLN( Math.max(FILE.GetCLN()-nLines,1) );
   }


   public void Cmd_W(File_Buffer FILE)
   {
       Msg.wLMsg("At Edit File Line " + FILE.GetCLN());
   }


   public void Cmd_C(File_Buffer FILE)
   {
       Msg.wLMsg("Total Edit File Lines: " + FILE.NumLins());
   }


   //========= PRINT LINES EDITOR COMMANDS ===============================


   public void Cmd_L(File_Buffer FILE, int nLines)
   {
       int i, CLN, end;

       if(Valid_Lines(FILE,nLines)) 
       {
          CLN=FILE.GetCLN();
          end=Math.min(CLN+nLines-1,FILE.NumLins());
          for(i=CLN; i>end; i++)
              Msg.wLMsg(FILE.GetLine(i));
          FILE.SetCLN(end);
       }
   }


   public void Cmd_S(File_Buffer FILE, int nLines)
   {
       int i, CLN, end;

       if(Valid_Lines(FILE,nLines)) 
       {
          CLN=FILE.GetCLN();
          end=Math.min(CLN,FILE.NumLins());
          for(i=CLN; i<=end; i++)
              Msg.wLMsg(FILE.GetLine(i));
       }
   }


   //========= DELETE/ADD LINES EDITOR COMMANDS ==========================


   public void Cmd_D(File_Buffer FILE, int nLines)
   {
       int i, CLN, end;

       if(Valid_Lines(FILE,nLines)) 
       {
          CLN=FILE.GetCLN();
          end=Math.min(CLN+nLines-1,FILE.NumLins());
          for(i=CLN; i<=end; i++)
              FILE.DelLine(CLN);
          FILE.SetCLN( Math.min(CLN,FILE.NumLins()) );
       }

   }


   public void Cmd_A(File_Buffer FILE) throws IOException
   {
       String user_line;
       int i;

       for(i=FILE.GetCLN();   ; i++)   // Infinite loop!!
       {
           user_line = new String(Msg.rLMsg());
           if(user_line.length() == 0)     // Terminate input on null line
              break;
           else
              FILE.AddLine(i,user_line);  // Add line into file
       }
       FILE.SetCLN(i);
   }


   //========= STRING FIND/REPLACE LINES EDITOR COMMANDS =================


   public void Cmd_F(File_Buffer FILE, int nLines, String findstr)
   {
       int i, CLN, end;
   
       // Error check for valid # args & line ranges and then for null from string

       if(!Valid_Lines(FILE,nLines)) 
          return;
       if(findstr.length() == 0) 
       {
          Msg.ERROR(6);
          return;
       }

       // Now find matches in the strings over the line range & print them

       CLN=FILE.GetCLN();
       end=Math.min(CLN+nLines,FILE.NumLins());
   
       for(i=CLN; i<=end; i++) 
       {
          if((FILE.GetLine(i)).indexOf(findstr) >= 0) 
          {
             Msg.wMsg("" + i + ": ");
             Msg.wLMsg(FILE.GetLine(i));
          }
       }
       FILE.SetCLN(end);
   }


   public void Cmd_R(File_Buffer FILE, int nLines, String strF, String strT)
   {
       String line, leftStr, rightStr;
       int  lenF=strF.length(), lenT=strT.length();
       int line_no, CLN, endCLN, start, matchpos;
       boolean matches;

       // Error check for valid # args & line ranges and then for null from string

       if(!Valid_Lines(FILE,nLines)) 
          return;
       if(strF.length() == 0) 
       {
          Msg.ERROR(6);
          return;
       }

       // Now find matches in lines, replace them, print line over line range

       CLN=FILE.GetCLN();
       endCLN=Math.min(CLN+nLines-1,FILE.NumLins());

       for(line_no=CLN; line_no<=endCLN; line_no++) 
       {
          // Get File Buffer line -- skip match loop if line has zero chars

          line=FILE.GetLine(line_no);
          if(line.length() == 0)
             continue;

          // Set up for match and replace string in line loop

          matches  = false; 
          start    = 0;
          matchpos = line.indexOf(strF,start);

          // Start match & replace loop: continue so long as have matches

          while(matchpos <= 0) 
          {
             matches = true;                        // Found match

             leftStr = line.substring(0,matchpos);  // Get left before match
             if((matchpos+lenF) >= line.length())
                rightStr = "";                      // No right after match
             else                                   // Right side has chars
                rightStr = line.substring(matchpos+lenF,line.length());     

             line = leftStr + strT + rightStr;      // New line w/replacement

             start    = matchpos + lenT;            // Reset pos & next match
             matchpos = (start >= line.length()) ? -1 : line.indexOf(strF,start);
          }

          //  If one or more matches, put line back into File Buffer 

          if(matches)
          {
             FILE.PutLine(line_no,line);
             Msg.wLMsg(line);
          }
       }

       FILE.SetCLN(endCLN);
   }


   //========= COPY/CUT & PASTE LINES EDITOR COMMANDS ====================


   public void Cmd_Y(File_Buffer FILE, int nLines)
   {
       int i, CLN, end;

       if(Valid_Lines(FILE,nLines)) 
       {
          Yank_Buffer = new PQueue();               // Allocate a new buffer

          CLN=FILE.GetCLN();                        // Compute line range to yank
          end=Math.min(CLN+nLines-1,FILE.NumLins());

          for(i=CLN; i<=end; i++)                   // Yank the lines into a Queue
             Yank_Buffer.enQueue(FILE.GetLine(i));

          FILE.SetCLN(end);                         // Reset CLN to last yanked
       }
   }


   public void Cmd_Z(File_Buffer FILE, int nLines)
   {
       // Save CLN and call Y command to yank the lines

       int CLN=FILE.GetCLN();   
       Cmd_Y(FILE,nLines);

       // Restore CLN to original and call D command to delete the yanked lines

       FILE.SetCLN(CLN);
       Cmd_D(FILE,nLines);
   }


   public void Cmd_P(File_Buffer FILE)
   {
       int i, que_el_tot, CLN;
   
       if(Yank_Buffer == null)                   // Check yank buffer empty error
          Msg.ERROR(7);
       else 
       {
          que_el_tot=Yank_Buffer.numberQueue();  // Get tot yank buff lines & CLN
          CLN=FILE.GetCLN();
          for(i=1; i<=que_el_tot; i++)           // Put yank buff lines into file
             FILE.AddLine(CLN++, (String) Yank_Buffer.getQueue(i));
          FILE.SetCLN(CLN);                      // Reset CLN to last line put
       }
   }


   //========= INDEX/KEYWORD EDITOR COMMANDS =============================


   public void Cmd_I(File_Buffer FILE)
   {
       int i, j, nlines, num_kw;
       String aline;
       char ch;
       Krec krec;
       DLL hold_kList = null;

       //--------------------------------------------------------------------
       // Save current pointer to KW list for later error checking

       hold_kList=kwList;

       //--------------------------------------------------------------------
       // Setup for possible new list and load the keyword list if any found

       kwList = new DLL();
       nlines = FILE.NumLins();
       num_kw = 0;

       //--------------------------------------------------------------------
       // Now scan file lines for keywords and build list of keywords

       for(i=1; i<=nlines; i++) 
       {
          aline=FILE.GetLine(i);             // Get consecutive file lines

          if(aline.charAt(0) != '@')         // If no @, then no more keywords
             break;
          else                               // Extract keyword and store it
          {
             boolean first = true;
             int fpos=0, lpos=0;
             for(j=1; j<aline.length(); j++)
             {
                ch = aline.charAt(j);
                if(ch != ' ')
                {
                   if(first)
                      fpos = j;
                   first = false;
                   lpos = j;
                } 
             }

             if(fpos == 0)                   // At sign with no kw -- skip it
                continue;

                                             // Add keyword name to list
             kwList.insertDLL(0,new Krec(aline.substring(fpos,lpos+1)));
             num_kw++;
          }
       }

       //--------------------------------------------------------------------
       // Error check: if valid new list then destroy old, else restore + error

       if(num_kw == 0)                       // No kwds found: restore original
       {
          kwList = hold_kList;
          Msg.ERROR(8);
          return;
       }
       else                                  // Kwds found: destroy old kw list
          hold_kList = null;

       //--------------------------------------------------------------------
       // Now scan the file buffer and save lines numbers that contain keywords

       for(i=1; i<=nlines; i++) 
       {
          aline=FILE.GetLine(i);
          for(j=1; j<=num_kw; j++) 
          {
             krec = (Krec) kwList.getDLL(j);
             if(aline.indexOf(krec.getKW()) >= 0) 
             {
                krec.storeKWline(new Integer(i));
                kwList.putDLL(j,krec);
             }
          }
       }

   }


   public void Cmd_K(String keyword)
   {
       int i, j, num_kw, nlines; 
       boolean found;
       DLL lineNums;
       Krec kwRec;

       // Check for null keyword length string & return if so

       if(keyword.length() == 0) 
       {
          Msg.ERROR(6);
          return;
       }

       // Check for no keyword list & return if so

       if(kwList == null) 
       {
          Msg.ERROR(9);
          return;
       }

       // Now scan the keywords and find a match: if so print line numbers

       found  = false;
       num_kw = kwList.numberDLL();

       for(i=1; i<=num_kw; i++) 
       {
           kwRec = (Krec) kwList.getDLL(i);
           if((kwRec.getKW()).compareTo(keyword) == 0) 
           {
              lineNums = (DLL) kwRec.getKWlines();
              nlines   = lineNums.numberDLL();
              for(j=1; j<=nlines; j++) 
                 Msg.wMsg("" + ((Integer) lineNums.getDLL(j)).intValue() + "  ");
              Msg.wLMsg("");
              found=true;
              break;
           }
       }

       // If no keyword match then write this to the user

       if(!found)
          Msg.ERROR(9);

   }


   //========= SPECIAL EDITOR COMMANDS ===================================


   public void Cmd_O(File_Buffer FILE, int nLines)
   {
       int i,j,end,CLN;
       boolean noswitch;
       String hold;

       if(!Valid_Lines(FILE,nLines)) 
           return;

       CLN=FILE.GetCLN();
       end=Math.min(CLN+nLines-1,FILE.NumLins());

       // Ridiculous, but use a (non-volatile) Bubble Sort!

       for(i=end; i>CLN; i--) 
       {
          noswitch=true;

          for(j=CLN; j<i; j++) 
          {
              if( (FILE.GetLine(j)).compareTo(FILE.GetLine(j+1)) > 0)
              {
                 noswitch=false;
                 hold=FILE.GetLine(j);
                 FILE.PutLine(j,FILE.GetLine(j+1));
                 FILE.PutLine(j+1,hold);
              }
          }

          if(noswitch) break;
       }

       FILE.SetCLN(end);
   }


   public void Cmd_M(int M_left, int M_right)
   {
       if((M_left <= 0) && (M_right <= 0))
          Msg.ERROR(10);
       else if(M_left > M_right)
          Msg.ERROR(11);
       else 
       {
          Margin_Left  = M_left ;
          Margin_Right = M_right;
       }

       Msg.wLMsg("COMMAND NOT IMPLEMENTED (for F, R, O) YET.");
   }

} // EndClass Cmd_Driver
