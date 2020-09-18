import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Program
{
    public static void main(String[] args) throws FileNotFoundException
    {
        //Get input criteria and their weight matrix
        FileInputStream fisCriteria = new FileInputStream("src/criteria.txt");
        Scanner scannerCriteria = new Scanner(fisCriteria);
        String[] criteriaName = scannerCriteria.nextLine().split(" ");

        int criteriaMatrixDegree = criteriaName.length;
        double[][] criteriaMatrix = new double[criteriaMatrixDegree][criteriaMatrixDegree];
        for (int i = 0; i < criteriaMatrixDegree; i++)
        {
            for (int j = 0; j < criteriaMatrixDegree; j++)
            {
                criteriaMatrix[i][j] = Double.parseDouble(scannerCriteria.next());
            }
        }

        //Get input alternatives
        FileInputStream fisAlternatives = new FileInputStream("src/alternatives.txt");
        Scanner scannerAlternatives = new Scanner(fisAlternatives);
        String[] alternatives = scannerAlternatives.nextLine().split(" ");

        //Get input alternatives matrix
        int alternativesMatrixDegree = alternatives.length;
        double[][] alternativesMatrix;

        Criterion[] criteria = new Criterion[criteriaName.length];
        String criterionName = "";

        for (int i = 0; i < criteria.length; i++)
        {
            alternativesMatrix = new double[alternativesMatrixDegree][alternativesMatrixDegree];
            criterionName = scannerAlternatives.nextLine();

            for (int j = 0; j < alternativesMatrixDegree; j++)
            {
                for (int k = 0; k < alternativesMatrixDegree; k++)
                {
                    double num = Double.parseDouble(scannerAlternatives.next());
                    alternativesMatrix[j][k] = num;
                }
            }

            scannerAlternatives.nextLine();
            criteria[i] = new Criterion(criterionName, new Matrix(alternativesMatrix));
        }

        //Calculate weight vectors
        Matrix compareMatrix = new Matrix(criteriaMatrix);
        double[] weightVectorOfCompareMatrix = compareMatrix.calculateWeightVector();
        compareMatrix.printWeightVector();

        /*criteria[0].getWeightMatrix().printWeightVector();
        criteria[1].getWeightMatrix().printWeightVector();
        criteria[2].getWeightMatrix().printWeightVector();
        criteria[3].getWeightMatrix().printWeightVector();*/

        //Calculate score of each alternative
        double[] scores = new double[alternatives.length];
        for (int i = 0; i < scores.length; i++)
        {
            for (int j = 0; j < weightVectorOfCompareMatrix.length; j++)
            {
                scores[i] += weightVectorOfCompareMatrix[j] * criteria[j].getWeightMatrix().calculateWeightVector()[i];
            }
        }

        for (int i = 0; i < scores.length; i++)
        {
            System.out.println(alternatives[i] + ": " + Math.round(scores[i] * 100) / 100.0);
        }

    }
}
