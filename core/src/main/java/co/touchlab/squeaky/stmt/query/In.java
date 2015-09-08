package co.touchlab.squeaky.stmt.query;

import co.touchlab.squeaky.dao.SqueakyOpenHelper;
import co.touchlab.squeaky.field.FieldType;
import co.touchlab.squeaky.stmt.ArgumentHolder;
import co.touchlab.squeaky.stmt.Where;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Internal class handling the SQL 'in' query part. Used by {@link Where#in}.
 * 
 * @author graywatson
 */
public class In extends BaseComparison
{

	private Iterable<?> objects;
	private final boolean in;

	public In(SqueakyOpenHelper openHelper, String columnName, FieldType fieldType, Iterable<?> objects, boolean in) throws SQLException {
		super(openHelper, columnName, fieldType, null, true);
		this.objects = objects;
		this.in = in;
	}

	public In(SqueakyOpenHelper openHelper, String columnName, FieldType fieldType, Object[] objects, boolean in) throws SQLException {
		super(openHelper, columnName, fieldType, null, true);
		// grrrr, Object[] should be Iterable
		this.objects = Arrays.asList(objects);
		this.in = in;
	}

	@Override
	public void appendOperation(StringBuilder sb) {
		if (in) {
			sb.append("IN ");
		} else {
			sb.append("NOT IN ");
		}
	}

	@Override
	public void appendValue(StringBuilder sb, List<ArgumentHolder> columnArgList)
			throws SQLException {
		sb.append('(');
		boolean first = true;
		for (Object value : objects) {
			if (value == null) {
				throw new IllegalArgumentException("one of the IN values for '" + columnName + "' is null");
			}
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			// for each of our arguments, add it to the output
			super.appendArgOrValue(fieldType, sb, columnArgList, value);
		}
		sb.append(") ");
	}
}
