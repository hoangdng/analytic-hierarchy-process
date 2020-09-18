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
        String[] criteria = scannerCriteria.nextLine().split(" ");

        int criteriaMatrixDegree = criteria.length;
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
        double[][] alternativesMatrix = new double[alternativesMatrixDegree][alternativesMatrixDegree];
        for (int i = 0; i < alternativesMatrixDegree; i++)
        {
            scannerAlternatives.nextLine(); //to skip the name of criterion which the matrix is based on
            for (int j = 0; j < alternativesMatrixDegree; j++)
            {
                alternativesMatrix[i][j] = Double.parseDouble(scannerAlternatives.next());
            }
        }

        for (int i = 0; i <= alternativesMatrixDegree; i++)
        {
            for (int j = 0; j < alternativesMatrixDegree; j++)
            {
                System.out.print(alternativesMatrix[i][j]);
            }
            System.out.println();
        }
        //Apply Ahp to input data
        Matrix compareMatrix = new Matrix("Compare matrix", criteriaMatrix);
        double[] weightVectorOfCompareMatrix = compareMatrix.calculateWeightVector();
    }
}
