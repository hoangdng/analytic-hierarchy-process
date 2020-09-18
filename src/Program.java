import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

        double[] weightVectorOfPriceMatrix = criteria[0].getWeightMatrix().calculateWeightVector();
        criteria[0].getWeightMatrix().printWeightVector();

        double[] weightVectorOfDistanceMatrix = criteria[1].getWeightMatrix().calculateWeightVector();
        criteria[1].getWeightMatrix().printWeightVector();

        double[] weightVectorOfLaborMatrix = criteria[2].getWeightMatrix().calculateWeightVector();
        criteria[2].getWeightMatrix().printWeightVector();

        double[] weightVectorOfWageMatrix = criteria[3].getWeightMatrix().calculateWeightVector();
        criteria[3].getWeightMatrix().printWeightVector();
    }
}
