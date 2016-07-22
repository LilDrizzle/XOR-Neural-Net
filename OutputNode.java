public class OutputNode extends Node
{
    double originalValue; //value pre-logistic function 
    
    public OutputNode()
    {
        super();
        originalValue = 0;
    }
    

    public double getOriginalValue() 
	{
        return originalValue;
    }
    
    public double calculateValue()
    {
        originalValue = calculateFromParents();
        setValue(MathHelper.doLogisticsFunction(originalValue));
		return getValue();
    }
    
    public double calculateFromParents()
    {
        double sum = 0;
        for(Synapse s : this.parents)
        {
            double weight = s.getWeight();
            double value = ((Layer1)s.getNode()).calculate();
            sum += (weight * value);
        }
        return sum;
    }
}