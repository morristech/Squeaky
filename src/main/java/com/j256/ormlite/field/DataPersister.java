package com.j256.ormlite.field;

import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.stmt.ArgumentHolder;

import java.lang.reflect.Field;
import java.sql.SQLException;

/**
 * Data type that provide Java class to/from database mapping.
 * 
 * <p>
 * If you are defining your own custom persister, then chances are you should extend {@link BaseDataType}. See
 * {@link DatabaseField#persisterClass()}.
 * </p>
 * 
 * @author graywatson
 */
public interface DataPersister extends FieldConverter {

	/**
	 * This makes a configuration object for the data-type or returns null if none. The object can be accessed later via
	 * {@link FieldType#getDataTypeConfigObj()}.
	 */
	public Object makeConfigObject(FieldType fieldType) throws SQLException;

	/**
	 * Return true if this type can be auto-generated by the database. Probably only numbers will return true.
	 */
	public boolean isValidGeneratedType();

	/**
	 * Return the class most associated with this persister or null if none.
	 */
	public Class<?> getPrimaryClass();

	/**
	 * Return whether this field's default value should be escaped in SQL.
	 */
	public boolean isEscapedDefaultValue();

	/**
	 * Return whether we need to escape this value in SQL expressions. Numbers _must_ not be escaped but most other
	 * values should be.
	 */
	public boolean isEscapedValue();

	/**
	 * Return whether this field is a primitive type or not. This is used to know if we should throw if the field value
	 * is null.
	 */
	public boolean isPrimitive();

	/**
	 * Return true if this data type be compared in SQL statements.
	 */
	public boolean isComparable();

	/**
	 * Must use {@link ArgumentHolder} when querying for values of this type.
	 */
	public boolean isArgumentHolderRequired();

	/**
	 * Return the default width associated with this type or 0 if none.
	 */
	public int getDefaultWidth();
}
