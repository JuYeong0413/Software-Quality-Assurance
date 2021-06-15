import java.io.*;

public class External_File
/*
******************************************************************************
* DESCRIPTION:                                                               *
*     This class provides an interface for external file usage.  Given the   *
* String name of a file, this class opens the file and connects it to the    *
* program.  The user can then either read the file (if opened for reading)   *
* or write to the file (if opened for writing). (Cannot do both at same time)*
*                                                                            *
*   Constructor()      - Constructs but does nothing.  Must then call opens. *
*   Constructor(FN)    - Opens for READING on String file name.              *
*   Constructor(FN,RW) - Open file name for READING ('R') or WRITING ('W')   *
*   openR(FN)          - Open file for READING.  Returns T/F on open success *
*   openW(FN)          - Open file for WRITING.  Returns T/F on open success *
*   getLine()          - Returns next String line on file (READ only).       *
*   havehitEOF()       - Returns true if at EOF, false otherwise (READ only).*
*   write(STR)         - Writes String to file (WRITING ONLY).               *
*   writeln(STR)       - Writes String to file w/newline (WRITING ONLY).     *
*   close()            - Closes/disconnects the file from the program.       *
******************************************************************************
* NOTES:                                                                     *
*     1) The getLine method returns the next line of the file without the    *
*        end of line marker(s) whatever they may be on a particular system.  *
*     2) The functions in this class are file system independent (other than *
*        legal file names).                                                  *
******************************************************************************
* OTHER CLASSES USED:                                                        *
*     java.io: FileInputStream,  InputStreamReader,  BufferedReader,         *
*              FileOutputStream, OutputStreamWriter, BufferedWriter.         *
*****************************************************************************/
{
    private final boolean OPEN_ERROR = true;
    private Object Ext_File;
    boolean reading;
 
    //******************************************************  CONSTRUCTORS
    public External_File()
    {
       Ext_File = null;
    }

    public External_File(String ext_file) throws IOException
    {
       openR(ext_file);
    } 

    public External_File(String ext_file, char readwrite) throws IOException
    {
       if(readwrite == 'R')
          openR(ext_file);
       else if(readwrite == 'W')
          openW(ext_file);
       else
          Ext_File = null;
    } 

    //******************************************************   OPEN FOR READING
    public boolean openR(String ext_file) throws IOException
    {
       reading  = true;                               

       try
       {
           Ext_File = new BufferedReader(
                      new InputStreamReader(new FileInputStream(ext_file)));
       } 
       catch (FileNotFoundException exception)
       {
           Ext_File = null;
           return OPEN_ERROR;
       }

       return !OPEN_ERROR;
    }

    //******************************************************   OPEN FOR READING
    public boolean openW(String ext_file) throws IOException
    {
       reading  = false;                              // OPEN FOR WRITING

       try
       {
           Ext_File = new BufferedWriter(
                      new OutputStreamWriter(new FileOutputStream(ext_file)));
       } 
       catch (FileNotFoundException exception)
       {
           Ext_File = null;
           return OPEN_ERROR;
       }

       return !OPEN_ERROR;
    }

    //******************************************************  FOR READING ONLY
    public String getLine() throws IOException
    {
       return ((BufferedReader) Ext_File).readLine();
    } 

    //******************************************************  FOR READING ONLY
    public boolean havehitEOF() throws IOException
    {
       return !((BufferedReader) Ext_File).ready();
    } 

    //******************************************************  FOR WRITING ONLY
    public void write(String str) throws IOException
    {
       ((BufferedWriter) Ext_File).write(str,0,str.length());
    } 

    //******************************************************  FOR WRITING ONLY
    public void writeln(String str) throws IOException
    {
       write(str);
       ((BufferedWriter) Ext_File).newLine();
    } 

    //******************************************************  FOR EITHER 
    public void close() throws IOException
    {
       if(Ext_File != null)
       {
          if(reading)
             ((BufferedReader) Ext_File).close();
          else
             ((BufferedWriter) Ext_File).close();
       }
    } 

} // EndClass External_File 
