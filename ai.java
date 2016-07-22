/*
yuumf
bagvd


logistic function:
    1
  ------
  1 + e^-x
  
1st derivative of logisitic function:
    e^x
  -------
  ((e^x) + 1)^2



log:
 day 1 - 9pm-2am												July 20, 2016
 day 2 - 1pm-5:30pm,  9:45pm-10:45pm				July 21, 2016
 
 total time: 10.5 hours
 
 
 
 
*/

import java.util.ArrayList;
import java.util.Random;


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
        
        
        //associate the nodes together
        for(Node h : hiddenNodes)
        {
            for(Node i : inputNodes)
            {
                h.addParent(new Synapse(i, 0.0));
            }
        }
        
        for(Node n : hiddenNodes)
        {
            theFinalNode.addParent(new Synapse(n, 0.0));
        }
        
		
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
        
		
        //set all nodes' weights to random numbers between 0 and 1
        for(Node n : allNodes)
        {
            for(Synapse s : n.parents)
            {
                s.setWeight(Math.random()); //default is from 0-1
            }
        }
        System.out.printf("The initial value is: %f%n", theFinalNode.calculateValue()); 
      
	    /**********
        *MAIN LOOP*
        **********/
        while(theFinalNode.calculateValue() != TARGET)
        {
			
            /*********
            *PRINTING*
            *********/
            double holderVal = theFinalNode.calculateValue();//i use this three times on the next line, and it may be a little cpu intensive
            System.out.printf("Value is: %f, Target is: %f, Error: %f%n", holderVal, (float)TARGET, Math.abs(holderVal - TARGET));
            
			
			/**********
            * VARIABLES*
            **********/
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
            
        
 
           //changing first set
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

NOTES


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
		
		
		Used to think about changing the first set of synapses:
			//delta weights
                       //(MathHelper.divide3MatrixBy2Matrix((MathHelper.multiplyMatrices((MathHelper.divideNumberBy3Matrix((deltaOutputSum(theFinalNode.calculateValue(), theFinalNode.getOriginalValue())), originalHiddenToOuterWeights)), (derivativeOfLogisticMatrix(layer1OriginalValues)))), (inputNodesMatrix))
                       
                       //delta hidden sum
                           //MathHelper.multiplyMatrices((MathHelper.divideNumberBy3Matrix((deltaOutputSum(theFinalNode.calculateValue(), theFinalNode.getOriginalValue())), originalHiddenToOuterWeights)), (derivativeOfLogisticMatrix(layer1OriginalValues)))
                           
                               //delta output sum / hidden-to-outer weights (original weights)
                                   //MathHelper.divideNumberBy3Matrix((deltaOutputSum(theFinalNode.calculateValue(), theFinalNode.getOriginalValue())), originalHiddenToOuterWeights)
                               //S'
                                   //derivativeOfLogisticMatrix(layer1OriginalValues)
                           
                           //(MathHelper.numberMultiplyMatrix( deltaOutputSum(theFinalNode.calculateValue(), MathHelper.divideNumberBy3Matrix(theFinalNode.getOriginalValue()), originalHiddenToOuterWeights )), MathHelper.divide3MatrixBy2Matrix(derivativeOfLogisticMatrix(layer1OriginalValues), (inputNodesMatrix)))  // need to write inverseMatrix
                
        
           
 */