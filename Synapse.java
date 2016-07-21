public class Synapse
{
    private double weight;
    private Node nodePointer;
    
    public Synapse(Node n, double w)
    {
        nodePointer = n;
        weight = w;
    } 
    
    public void setWeight(double w) {
        weight = w;
    } 
    
    public double getWeight()
    {
        return weight;
    }
    
    public Node getNode()
    {
        return nodePointer;
    }
}