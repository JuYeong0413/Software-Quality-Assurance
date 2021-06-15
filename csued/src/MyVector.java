public class MyVector
{
    protected final static int  DEFCAPACITY  = 100; // Default init, size>0
    protected final static int  DEFEXTENSION =  20; // Default ext,  size>0
    protected final static char DEFEXTMETHOD = 'F'; // Default ext. method

    protected Object elementData[];   // The data array of objects
    protected int    elementCount;    // Number of current elements stored
    protected int    capacityOrig;    // Initial size (original)
    protected int    extensionOrig;   // Extension size (original)
    protected int    currExtInc;      // Current Extension size
    protected char   extensionMeth;   // Extension method

    //************************** CONSTRUCTORS ****************************

    public MyVector()
    {
        constructVector(DEFCAPACITY,DEFEXTENSION,DEFEXTMETHOD); 
    }
    
    public MyVector(int initialCapacity)
    {
        constructVector(initialCapacity,DEFEXTENSION,DEFEXTMETHOD); 
    }

    public MyVector(int initialCapacity, int capacityIncr)
    {
        constructVector(initialCapacity,capacityIncr,DEFEXTMETHOD); 
    }

    public MyVector(int initialCapacity, char extMethod)
    {
        constructVector(initialCapacity,DEFEXTENSION,extMethod);
    }

    public MyVector(int initialCapacity, int capacityIncr, char extMethod)
    {
        constructVector(initialCapacity,capacityIncr,extMethod); 
    }

    //********************** CLASS SUPPORT METHODS **********************

    private void constructVector(int initSize,int initExtSize,char extMethod)
    {
        elementData   = new Object[initSize];
        capacityOrig  = initSize;
        extensionOrig = initExtSize;
        currExtInc    = initExtSize;
        extensionMeth = extMethod;
        elementCount  = 0;
    }

    protected void ensureCapacity(int spaceWanted)
    {
        // If current array space GE wanted size then simply return

        if (elementData.length >= spaceWanted) return;
        
        // Space too small: Extend starting from current array length

        int newLength = elementData.length; 

        // Extend by speficied method/algorithm till space GT space wanted

        while (newLength < spaceWanted) 
             switch (extensionMeth)
             {
                 case 'F': newLength  += extensionOrig;  break;
                 case 'D': newLength  += currExtInc;
                           currExtInc *= 2;              break;
                 case 'O': newLength  += capacityOrig;   break;
                 case 'C': newLength  *= 2;              break;
             }

        // Create new array, copy values into it, reassign to array  

        Object newElementData[] = new Object[newLength];
        for (int i = 0; i < elementCount; i++) 
            newElementData[i] = elementData[i];
        elementData = newElementData;
    }

    //*********************** MAINTENANCE METHODS ***********************

    public void trimToSize()
    {
        // Create new array to hold current elements, copy into it, reset

        Object newElementData[] = new Object[elementCount];
        for (int i = 0; i < elementCount; i++) 
            newElementData[i] = elementData[i];
        elementData = newElementData;
    }

    //********************** INFORMATIONAL METHODS **********************

    public int size()
    {
        return elementCount;
    }

    public int capacity()
    {
        return elementData.length;
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    //******************** FIND/SEARCH/MATCH METHODS ********************

    public boolean contains(Object elem)
    {
        // Search for matching object:  true if found, false otherwise

        for (int i = 0; i < elementCount; i++) 
            if (elem.equals(elementData[i])) 
                return true;
        return false;
    }

    //******************* GET/PUT (LOAD/STORE) METHODS ******************

    public Object elementAt(int index)
    {
        return elementData[index];
    }

    public void setElementAt(Object obj, int index)
    {
        elementData[index] = obj;
    }

    //*********************** ADD/INSERT METHODS ************************

    public void addElement(Object obj)
    {
        // Check/obtain enough space, then add element to end of array

        ensureCapacity(elementCount+1);
        elementData[elementCount] = obj;
        elementCount++;
    }

    public void insertElementAt(Object obj, int index)
    {
        // Check/obtain enough space, open hole in array, insert element

        ensureCapacity(elementCount+1);
        for (int i = elementCount; i > index; i--) 
            elementData[i] = elementData[i-1];
        elementData[index] = obj;
        elementCount++;
    }

    //********************** DELETE/REMOVE METHODS **********************

    public boolean removeElement(Object element)
    {
        // Find first matching object from top of array and remove it

        for (int i = 0; i < elementCount; i++)
            if (element.equals(elementData[i])) 
            { 
               removeElementAt(i);   
               return true;
            }
        return false;
    }

    public void removeElementAt(int where)
    {
        // Remove one element by shifting all elements up one to close hole

        elementCount--;
        while (where < elementCount)   // BETTER DONE WITH A FOR LOOP!!
        {
            elementData[where] = elementData[where+1];
            where++;
        }
        elementData[elementCount] = null; 
    }

    public void removeAllElements()
    {
        constructVector(capacityOrig, extensionOrig, extensionMeth);
    }

} // EndClass MyVector 
