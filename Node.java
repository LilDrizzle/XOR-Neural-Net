import java.util.ArrayList;

public class Node 
{
	private double value;
    ArrayList<Synapse> parents;
    
    public Node()
    {
		parents = new ArrayList<Synapse>();
    }
    
    public void addParent(Synapse s)
    {
        parents.add(s);
    }
    
    public double getValue() 
    {
        return value;
    }
    
    public void setValue(double val)
    {
        value = val;
    }
}