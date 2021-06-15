public class DLL
{
    // Implements a DLL with dummy nodes and optimized traverse

    // INNER CLASS FOR SPECIFICATION OF DLL ELEMENT CLASS ==================

    private class DLLelement
    {
        private DLLelement next;
        private Object     data;
        private DLLelement prev;

        // Various constructors (all possible combinations for flexibility)

        DLLelement(DLLelement nxt, Object dat, DLLelement prv)
        {
           next = nxt;
           data = dat;
           prev = prv;
        }
        DLLelement()                                { this(null, null, null); }
        DLLelement(Object dat)                      { this(null, dat,  null); }
        DLLelement(DLLelement nxt, Object dat)      { this(nxt,  dat,  null); }
        DLLelement(Object dat, DLLelement prv)      { this(null, dat,  prv ); }
        DLLelement(DLLelement nxt, DLLelement prv)  { this(nxt,  null, prv ); }
    
        // Public access methods to the three variables

        public void       setNext (DLLelement nxt)  { next = nxt;  }
        public void       setData (Object     dat)  { data = dat;  }
        public void       setPrev (DLLelement prv)  { prev = prv;  }

        public DLLelement getNext ()                { return next; }
        public Object     getData ()                { return data; }
        public DLLelement getPrev ()                { return prev; }
    
    } //EndClass DLLelement
   
    // VARIABLE DECLARATIONS ====================================================

    private DLLelement headptr,   tailptr,   currptr;
    private int        Num_Nodes, Curr_Node;

    // CONSTRUCTOR ==============================================================

    DLL()
    { 
        setReset();
    }

    private void setReset()
    { 
        headptr = new DLLelement(null,null,null);
        tailptr = new DLLelement(null,null,null);

        headptr.setNext(tailptr);
        tailptr.setPrev(headptr);

        currptr=headptr;
        Num_Nodes=Curr_Node=0;
    }

    // PUBLIC SERVICE FUNCTIONS =================================================


    public void clearDLL()
    { 
        setReset();
    }


    public int numberDLL()
    {
        return Num_Nodes;
    }


    public boolean emptyDLL()
    {
        return Num_Nodes == 0;
    }


    public Object getDLL(int elnum)
    { 
        findEL(elnum);
        return (currptr.getData());
    }


    public void putDLL(int elnum, Object data)
    { 
        findEL(elnum);
        currptr.setData(data);
    }


    public void insertDLL(int elnum, Object data)
    { 
        // Inserts after elnum element number (0 allowed for insert before first)
        findEL(elnum);
       
        // Create new element with user data and pointers to surrounding nodes
        DLLelement ptr = new DLLelement(currptr.getNext(), 
                                        data, 
                                       (currptr.getNext()).getPrev());
   
        // Reset pointers in surrounding nodes to new node
        (currptr.getNext()).setPrev(ptr);
        currptr.setNext(ptr);

        currptr=ptr;
        Num_Nodes++;
        Curr_Node++;
    }


    public void deleteDLL(int elnum)
    { 
        findEL(elnum);

        (currptr.getPrev()).setNext(currptr.getNext());
        (currptr.getNext()).setPrev(currptr.getPrev());

        currptr=currptr.getPrev();
        Num_Nodes--;
        Curr_Node--;
    }


    // PRIVATE SUPPORT FUNCTIONS ================================================

    private void findEL(int elnum)
    { 
    
        // NOTE:  This is a "weasely" version of the traverse since I was too
        //        lazy to traverse up/down from currnode (so I just did up/down
        //        from tailptr/headptr)!!  

        // Note: if elnum == Curr_Node then will just skip everything!!

        if(elnum == Curr_Node)                     // node current case
           return;
        else if(elnum == 0) {                      // node #0 zero case
           currptr=headptr;
           Curr_Node=0;
        }
        else if(elnum == Curr_Node-1) {            // prev node case
           currptr=currptr.getPrev();
           Curr_Node--;
        }
        else if(elnum == Curr_Node+1) {            // next node case
           currptr=currptr.getNext();
           Curr_Node++;
        }
        else if(elnum >= (Num_Nodes-elnum+1)) {    // search from top case
           currptr=headptr;
           for(int i=1; i<=elnum; i++)
	       currptr=currptr.getNext();
           Curr_Node=elnum;
        }
        else if(elnum < (Num_Nodes-elnum+1)) {     // search from bottom case
           currptr=tailptr;
           for(int i=Num_Nodes; i>=elnum; i--)
	       currptr=currptr.getPrev();
           Curr_Node=elnum;
        }
        else {
           System.out.println("Internal Error In Find_El_DLL method!!!");
        }
    }

    public void Debug(int flag)
    { 
        System.out.println("=============================================");
        System.out.println("DLL DEBUG:");
        System.out.println("headptr   == "  +  headptr  );
        System.out.println("currptr   == "  +  currptr  );
        System.out.println("tailptr   == "  +  tailptr  );
        System.out.println("Num_Nodes == "  +  Num_Nodes);
        System.out.println("Curr_Node == "  +  Num_Nodes);
        System.out.println();

        // Flag: <0 entire list, =0 current node, >0 node number

        if(flag < 0) {
           DLLelement holdptr = headptr;
           for(int i=0; i<=(Num_Nodes+1); i++) {
	       System.out.println(i + "  " + holdptr.getNext() 
                                    + "  " + holdptr.getPrev());
           holdptr=holdptr.getNext();
           }
        }
        else if(flag == 0) {
           System.out.println(currptr           
                              + "  " + currptr.getNext()
                              + "  " + currptr.getPrev());
        }
        else {
           findEL(flag);
    	   System.out.println(currptr           
                              + "  " + currptr.getNext()
                              + "  " + currptr.getPrev());
        }
        System.out.println("=============================================");
}

} // EndCLass DLL 
