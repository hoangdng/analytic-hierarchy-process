import java.util.Arrays;

public class Matrix
{
    private String _name;
    private double[][] _weightMatrix;

    public Matrix(String name, double[][] weightMatrix)
    {
        _name = name;
        _weightMatrix = weightMatrix;
    }

    public double[] calculateWeightVector()
    {
        //Calculate the sum of each column in weight matrix
        int matrixDegree = _weightMatrix.length;
        double[] sumCols = new double[matrixDegree];
        for (int i = 0; i < matrixDegree; i++)
        {
            for (int j = 0; j < matrixDegree; j++)
            {
                sumCols[i] += _weightMatrix[j][i];
            }
        }
        //Divide each elements in matrix to the corresponding sum of column (normalize this matrix)
        for (int i = 0; i < matrixDegree; i++)
        {
            for (int j = 0; j < matrixDegree; j++)
            {
                _weightMatrix[i][j] = _weightMatrix[i][j] / sumCols[j];
            }
        }
        //Calculate the sum of each row in the new matrix
        double[] sumRows = new double[matrixDegree];
        for (int i = 0; i < matrixDegree; i++)
        {
            for (int j = 0; j < matrixDegree; j++)
            {
                sumRows[i] += _weightMatrix[i][j];
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
}
