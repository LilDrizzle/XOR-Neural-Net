public class Layer1 extends Node
{
    double originalValue;
    
    public Layer1()
    {
        super();
        originalValue = 0;
    }
	
    public double calculate()
    {
        originalValue = calculateFromParents();
        setValue(MathHelper.doLogisticsFunction(calculateFromParents()));
        return getValue();
    }
    
    public double calculateFromParents()
    {
        double sum = 0;
        for(Synapse s : this.parents)
        {
            double weight = s.getWeight();
            double value = s.getNode().getValue();
            sum += (weight * value);
        }
        return sum;
    }
    
    public double getOriginalValue()
    {
        return originalValue;
    }
}