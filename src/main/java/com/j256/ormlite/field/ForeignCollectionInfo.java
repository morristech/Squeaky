package com.j256.ormlite.field;

/**
 * Created by kgalligan on 7/26/15.
 */
public class ForeignCollectionInfo
{
	public final boolean eager;
	public final int maxEagerLevel;
	public final String orderColumnName;
	public final boolean orderAscending;
	public final String foreignFieldName;
	public final String variableName;

	public ForeignCollectionInfo(boolean eager, int maxEagerLevel, String orderColumnName, boolean orderAscending, String foreignFieldName, String variableName)
	{
		this.eager = eager;
		this.maxEagerLevel = maxEagerLevel;
		this.orderColumnName = orderColumnName;
		this.orderAscending = orderAscending;
		this.foreignFieldName = foreignFieldName;
		this.variableName = variableName;
	}
}