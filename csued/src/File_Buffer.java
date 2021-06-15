public class File_Buffer

{
   // The following are the six key functions that perform the operations 
   // necessary on the file stored in the File_Buffer data structure.  
   // Also below are three other functions to make the class more robust. 
   // In addition, the current line number (CLN) is maintained here with 
   // the GetCLN & SetCLN member functions. 
   //
   // The operations are currently interfaced to a doubly linked list class. 

   DLL Buffer;       // This is the actual FIle Buffer!!
   int CLN;          // This is acutal current line number
   boolean updateIt; // Flag marks whether buffer should be rewritten to disk

   public File_Buffer()
   {
       CLN      = 0;
       Buffer   = new DLL();
       updateIt = true;
   }

   public String GetLine(int line) 
   {
      return (String) Buffer.getDLL(line);
   }

   public void PutLine(int line, String buff_line)
   {
      Buffer.deleteDLL(line);
      Buffer.insertDLL(line-1,buff_line);
   }

   public void AddLine(int line, String Buff_line) 
   {
      Buffer.insertDLL(line,Buff_line);
   }

   public void DelLine(int line)
   {
      Buffer.deleteDLL(line);
   }

   public void setUpdateFlag(boolean yesno)
   {
      updateIt = yesno;
   }

   public boolean getUpdateFlag()
   {
      return updateIt;
   }

   public int NumLins()
   {
      return Buffer.numberDLL();
   }

   public int GetCLN() 
   {
      return CLN;
   }

   public void SetCLN(int cln_value) 
   {
      CLN = cln_value;
   }


} // EndClass File_Buffer 
