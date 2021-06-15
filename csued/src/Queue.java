public class Queue 
{
    protected DLL TheQueue;

    Queue()
    {
        TheQueue = new DLL();
    }

    public void enQueue(Object value)
    { 
        TheQueue.insertDLL(TheQueue.numberDLL(),value);
    }

    public Object deQueue()
    { 
        Object hold = TheQueue.getDLL(1);
        TheQueue.deleteDLL(1);
        return hold;
    }

    public void clearQueue()
    {
        TheQueue.clearDLL();
    }

    public int numberQueue()
    {
        return TheQueue.numberDLL();
    }

    public boolean emptyQueue()
    {
        return TheQueue.emptyDLL();
    }

} // EndClass Queue
