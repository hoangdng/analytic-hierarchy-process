public class Criterion
{
    private String _name;
    private Matrix _weightMatrix;
    private double[] _weightVector;
    private Criterion[] _subCriteria;

    public Criterion(String name, Matrix weightMatrix, Criterion[] subCriteria)
    {
        _name = name;
        _weightMatrix = weightMatrix;
        _subCriteria = subCriteria;
        _weightVector = _weightMatrix.calculateWeightVector();
    }

    public void printCriterionMatrix()
    {
        System.out.println(_name);
        _weightMatrix.printMatrix();
    }

    public Matrix getWeightMatrix()
    {
        return _weightMatrix;
    }

    public String getName()
    {
        return _name;
    }

    public double[] getWeightVector()
    {
        return _weightVector;
    }

    public Criterion[] getSubCriteria()
    {
        return _subCriteria;
    }
}
