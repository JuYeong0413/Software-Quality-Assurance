public class PQueue extends Queue
{
    PQueue()
    {
       super();
    }

    public Object getQueue(int numel)
    { 
        return TheQueue.getDLL(numel);
    }

    public void deleteQueue(int numel)
    { 
        TheQueue.deleteDLL(numel);
    }

    public void insertQueue(int numel, Object value)
    { 
        TheQueue.insertDLL(numel,value);
    }

} // EndClass PQueue
