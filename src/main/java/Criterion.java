public class Criterion
{
    private String _name;
    private Matrix _preferenceMatrix;
    private Criterion _rootCriterion;
    private Criterion[] _subCriteria;

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public void setPreferenceMatrix(Matrix preferenceMatrix)
    {
        _preferenceMatrix = preferenceMatrix;
    }

    public void setRootCriterion(Criterion rootCriterion)
    {
        _rootCriterion = rootCriterion;
    }

    public void setSubCriteria(Criterion[] subCriteria)
    {
        _subCriteria = subCriteria;
    }

    public Matrix getPreferenceMatrix()
    {
        return _preferenceMatrix;
    }

    public Criterion getRootCriterion()
    {
        return _rootCriterion;
    }

    public Criterion[] getSubCriteria()
    {
        return _subCriteria;
    }
}
