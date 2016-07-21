import java.util.ArrayList;

public class MathHelper
{
    
    public static double doLogisticsFunction(double value)
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
   // public static ArrayList<Double> inverseMatrix(ArrayList<Double> matrix)
    //{
    //    
   // }
    
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