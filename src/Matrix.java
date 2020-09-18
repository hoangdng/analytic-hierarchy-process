import java.util.Arrays;

public class Matrix
{
    private double[][] _matrix;

    public Matrix(double[][] matrix)
    {
        _matrix = matrix;
    }

    public void printMatrix()
    {

        for (int i = 0; i < _matrix.length; i++)
        {
            for (int j = 0; j < _matrix[i].length; j++)
            {
                System.out.print(_matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public double[] calculateWeightVector()
    {
        //Calculate the sum of each column in weight matrix
        int matrixDegree = _matrix.length;
        double[] sumCols = new double[matrixDegree];
        for (int i = 0; i < matrixDegree; i++)
        {
            for (int j = 0; j < matrixDegree; j++)
            {
                sumCols[i] += _matrix[j][i];
            }
        }
        //Divide each elements in matrix to the corresponding sum of column (normalize this matrix)
        for (int i = 0; i < matrixDegree; i++)
        {
            for (int j = 0; j < matrixDegree; j++)
            {
                _matrix[i][j] = _matrix[i][j] / sumCols[j];
            }
        }
        //Calculate the sum of each row in the new matrix
        double[] sumRows = new double[matrixDegree];
        for (int i = 0; i < matrixDegree; i++)
        {
            for (int j = 0; j < matrixDegree; j++)
            {
                sumRows[i] += _matrix[i][j];
            }
        }
        //Divide sum of each row to sum of all rows
        double sumOfAllRows = Arrays.stream(sumRows).reduce(0, (element1, element2) -> element1 + element2);
        double[] weightVector = new double[matrixDegree];
        for (int i = 0; i < matrixDegree; i++)
        {
            weightVector[i] = sumRows[i] / sumOfAllRows;
        }
        return weightVector;
    }

    public void printWeightVector()
    {
        double[] weightVector = calculateWeightVector();

        System.out.println("-------------");
        for (int i = 0; i < weightVector.length; i++)
        {
            System.out.println(weightVector[i]);
        }
        System.out.println("-------------");
    }
}
