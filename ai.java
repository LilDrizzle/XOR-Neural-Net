/*
yuumf


logistic function:
    1
  ------
  1 + e^-x
  
1st derivative of logisitic function:
    e^x
  -------
  ((e^x) + 1)^2



log:
 day 1 - 9pm-2am
 day 2 - 1pm-
 
*/

import java.util.ArrayList;
import java.util.Random;

/*
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
    
    public int getValue() 
    {
        return value;
    }
    
    public void setValue(int val)
    {
        value = val;
    }



public class InputNode extends Node
{
    public InputNode()
    {
        //TODO (maybe) set parents arraylist to null
        super();
    } 
}


public class Layer1 extends Node
{
    double originalValue;
    
    public Layer1()
    {
        super();
        originalValue = 0;
    }
    public double calculate()//real value
    {
        originalValue = calculateFromParents();
        value = MathHelper.doLogisticsFunction(calculateFromParents());
        return value;
    }
    
    public double calculateFromParents()//original value
    {
        double sum = 0;
        for(Synape s : this.parents)
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


public class OutputNode extends Node
{
    double originalValue;//value pre-logistic function 
    
    public OutputNode()
    {
        super();
        originalValue = 0;
    }
    

    public double getOriginalValue() {
        return originalValue;
    }
    
    
    public double calculateValue()
    {
        originalValue = calculateFromParents();
        value = MathHelper.doLogisticsFunction(originalValue);
        return value;
    }
    
    public double calculateFromParents()//copypasta
    {
        double sum = 0;
        for(Synape s : this.parents)
        {
            double weight = s.getWeight();
            double value = s.getNode().getValue();
            sum += (weight * value);
        }
        return sum;
    }
}


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

} //the super class bracket

*/

public class ai
{
    public static final int TARGET = 1;
    public static final int NUM_HIDDEN_NODES = 3;
    public static final int NUM_INPUT_NODES = 2;
    
    public static void main(String[] args) 
    {
        
        
        Random generator = new Random();
       
            
        //instantiate lists for nodes
        ArrayList<Node> allNodes = new ArrayList<Node>();
        ArrayList<InputNode> inputNodes = new ArrayList<InputNode>(NUM_INPUT_NODES);
        ArrayList<Layer1> hiddenNodes = new ArrayList<Layer1>(NUM_HIDDEN_NODES);
        OutputNode theFinalNode;
        
        //instantiate nodes
        for(int x = 0; x < NUM_HIDDEN_NODES; x++)
        {
            hiddenNodes.add(new Layer1());
        }
		
        for(int x = 0; x < NUM_INPUT_NODES; x++)
        {
            inputNodes.add(new InputNode());
        }
		
        theFinalNode = new OutputNode();
        
        //set value of input nodes
        for(InputNode n : inputNodes)
        {
            n.setValue(1);
        }
        
        //instantiated in loop
        ArrayList<Double> inputNodesMatrix;
        ArrayList<Double> layer1OriginalValues;
        ArrayList<Double> originalHiddenToOuterWeights;
        ArrayList<Double> originalInputToHiddenWeights;
        
        //list of all nodes
        allNodes.add(theFinalNode);
        for(Node n : hiddenNodes)
        {
            allNodes.add(n);
        }
		
        for(Node n : inputNodes)
        {
            allNodes.add(n);
        }
        
        //set all nodes' weights to randome number between 0 and 1
        for(Node n : allNodes)
        {
            for(Synapse s : n.parents)
            {
            
                s.setWeight(Math.random()); //default is (0,1)//should be 0.69
            }
        }
        System.out.printf("The initial value is: %f%n", theFinalNode.calculateValue()); 
        /***********
        *MAIN LOOP
        ************/
        while(theFinalNode.calculateValue() != TARGET)
        {
            /********
            *PRINTING
            *******/
            double holderVal = theFinalNode.calculateValue();//i use this three times on the next line, and it may be a little cpu intensive
            System.out.printf("Value is: %f, Target is: %f, Error: %f%n", holderVal, (float)TARGET, Math.abs(holderVal - TARGET));
            /**********
            * VARIABLES
            ************/
            inputNodesMatrix = new ArrayList<Double>();
            for (int i = 0; i < inputNodes.size(); i++)
            {
                inputNodesMatrix.add(new Double(inputNodes.get(i).getValue()));
            }
            
            layer1OriginalValues = new ArrayList<Double>();
            for (int i = 0; i < hiddenNodes.size(); i++)
            {
                layer1OriginalValues.add(new Double(hiddenNodes.get(i).getOriginalValue()));
            }
            
            originalHiddenToOuterWeights = new ArrayList<Double>();
            for (Synapse s : theFinalNode.parents)
            {
                originalHiddenToOuterWeights.add(new Double(s.getWeight()));
            } 

			originalInputToHiddenWeights = new ArrayList<Double>();
			for (int i = 0; i < inputNodes.size(); i++) 
			{
				for (int x = 0; x < hiddenNodes.size(); x++)
				{
					originalInputToHiddenWeights.add(new Double(hiddenNodes.get(x).parents.get(i).getWeight()));
				}
			}
        
        /*
        check output
        ------
        change first set of weights (inner-hidden synapses)
          new weights = old weights + delta weights
                delta weights = delta hidden sum / inputs
                    delta hidden sum = delta output sum / hidden-to-outer weights (original weights) * S'(hidden sums)
                        hidden sums = original values of hidden layer before logisitc 
                        delta output sum = S'(sum) * (output sum margin of error)
                            output sum margin of error = Target (0) - Calculater (logistified output layer value)
                            sum = original value of output layer 
        
        change second set of weights (hidden-outer synapses)                                       
            new weights = old weights + delta weights
                delta weights = delta output sum / hidden layer results
                    hidden layer results = {original hidden values logistified}
                    delta output sum = S'(sum) * (output sum margin of error)
                            output sum margin of error = Target (0) - Calculater (logistified output layer value)
                            sum = original value of output layer
        ------------------------
        
           
        */
			
			
           //hanging first set
           ArrayList<Double> firstSetHolder = new ArrayList<Double>();
		   firstSetHolder = MathHelper.addOneRowArrayLists((originalInputToHiddenWeights), 
				   ((MathHelper.divide3MatrixBy2Matrix((MathHelper.multiplyMatrices(
				   (MathHelper.divideNumberBy3Matrix((deltaOutputSum(theFinalNode.calculateValue(), 
				   theFinalNode.getOriginalValue())), originalHiddenToOuterWeights)),
				   (MathHelper.derivativeOfLogisticMatrix(layer1OriginalValues)))), (inputNodesMatrix))) ));
			
			int holder = 0;
			for (int i = 0; i < inputNodes.size(); i++) {
				for (int x = 0; x < hiddenNodes.size(); x++) {
					hiddenNodes.get(x).parents.get(i).setWeight(firstSetHolder.get(holder).doubleValue());
					holder++;
				}
			}

			
		   for (int i = 0; i < hiddenNodes.size(); i++)
           {
               for (Synapse s : hiddenNodes.get(i).parents)
               {
                   s.setWeight(firstSetHolder.get(i).doubleValue());
               }
           }
                   //delta weights
                       //(MathHelper.divide3MatrixBy2Matrix((MathHelper.multiplyMatrices((MathHelper.divideNumberBy3Matrix((deltaOutputSum(theFinalNode.calculateValue(), theFinalNode.getOriginalValue())), originalHiddenToOuterWeights)), (derivativeOfLogisticMatrix(layer1OriginalValues)))), (inputNodesMatrix))
                       
                       //delta hidden sum
                           //MathHelper.multiplyMatrices((MathHelper.divideNumberBy3Matrix((deltaOutputSum(theFinalNode.calculateValue(), theFinalNode.getOriginalValue())), originalHiddenToOuterWeights)), (derivativeOfLogisticMatrix(layer1OriginalValues)))
                           
                               //delta output sum / hidden-to-outer weights (original weights)
                                   //MathHelper.divideNumberBy3Matrix((deltaOutputSum(theFinalNode.calculateValue(), theFinalNode.getOriginalValue())), originalHiddenToOuterWeights)
                               //S'
                                   //derivativeOfLogisticMatrix(layer1OriginalValues)
                           
                           //(MathHelper.numberMultiplyMatrix( deltaOutputSum(theFinalNode.calculateValue(), MathHelper.divideNumberBy3Matrix(theFinalNode.getOriginalValue()), originalHiddenToOuterWeights )), MathHelper.divide3MatrixBy2Matrix(derivativeOfLogisticMatrix(layer1OriginalValues), (inputNodesMatrix)))  // need to write inverseMatrix
                
               
           
			ArrayList<Double> newHiddenToOuterWeights = new ArrayList<Double>();
			for (int i = 0; i < hiddenNodes.size(); i++) {
				newHiddenToOuterWeights.add(new Double(theFinalNode.parents.get(i).getWeight()));
			}
            // changing second set
			ArrayList<Double> secondSetHolder = new ArrayList<Double>();
			secondSetHolder = MathHelper.addOneRowArrayLists(originalHiddenToOuterWeights, 
				(MathHelper.divideNumberBy3Matrix((deltaOutputSum(theFinalNode.calculateValue(), 
				theFinalNode.getOriginalValue())), newHiddenToOuterWeights)));
            for (int i = 0; i < hiddenNodes.size(); i++)
            {
                theFinalNode.parents.get(i).setWeight(secondSetHolder.get(i).doubleValue());
            }
            
            
        }//end loop
        System.out.println("we did it reddit!");
    }//end main
    
    public static double deltaOutputSum(double calculated, double originalValue)
    {
        return MathHelper.derivativeOfLogistic(originalValue) * (TARGET - calculated);
    }
}//end class

/*
public static class MathHelper
{
    
    public static double doLogisticFunction(double value)
    {
        return (1/(1 + Math.pow(Math.E, -value)));
    }
    
    public static ArrayList<Double> addOneRowArrayLists(ArrayList<Double> one, ArrayList<Double> two)
    {
        ArrayList<Double> newArray = new ArrayList<Double>();
        //assuming they are the same size 
        for(int x = 0; x < one.size(); x++)
        {
            newArray.add(new Double(one.get(x).doubleValue() + two.get(x).doubleValue()));
        }
        return newArray;
    }
    
    //holy shit i think this is gonna be hard
    //http://www.purplemath.com/modules/mtrxinvr.htm
    //gonna need multiple for each type of matrix
    public static ArrayList<Double> inverseMatrix(ArrayList<Double> matrix)
    {
        
    }
    
    public static ArrayList<Double> numberMultiplyMatrix(double number, ArrayList<Double> matrix)
    {
        ArrayList<Double> holder = new ArrayList<Double>();
        for (int i = 0; i < matrix.size(); i++)
        {
            holder.add(new Double(matrix.get(i).doubleValue() * number));
        }
        return holder;
    }
    
    public static ArrayList<Double> multiplyMatrices(ArrayList<Double> one, ArrayList<Double> two)
    {
        ArrayList<Double> newArray = new ArrayList<Double>();
        //assuming they are the same size
        for(int x = 0; x < one.size(); x++)
        {
            newArray.add(new Double(one.get(x).doubleValue() * two.get(x).doubleValue()));
        }
        return newArray;
    }
    
    public static double derivativeOfLogistic(double value) //sigmoid prime
    {
        return (Math.pow(Math.E, value)) / (Math.pow((Math.pow(Math.E, value) + 1), 2));
    }
    
    public static ArrayList<Double> derivativeOfLogisticMatrix(ArrayList<Double> values) //sigmoid prime
    {
        ArrayList<Double> holder = new ArrayList<Double>();
        for (int i = 0; i < values.size(); i++)
        {
            holder.add(new Double( (Math.pow(Math.E, values.get(i).doubleValue())) / (Math.pow((Math.pow(Math.E, values.get(i).doubleValue()) + 1), 2))));
        }
        return holder;  
    }
    
    public static ArrayList<Double> divide3MatrixBy2Matrix(ArrayList<Double> three, ArrayList<Double> two) 
    {
        ArrayList<Double> holderArray = new ArrayList<Double>();
        for (int i = 0; i < three.size(); i++)
        {
            for (int x = 0; x < two.size(); x++)
            {
                holderArray.add(new Double(three.get(i).doubleValue() / two.get(x).doubleValue()));
            }
        }
        return holderArray;
    }
    
    public static ArrayList<Double> divideNumberBy3Matrix(double number, ArrayList<Double> three)
    {
        ArrayList<Double> holderArray = new ArrayList<Double>();
        for (int i = 0; i < three.size(); i++)
        {
            holderArray.add(new Double(number / three.get(i).doubleValue()));
        }
        return holderArray;
    }
}
*/