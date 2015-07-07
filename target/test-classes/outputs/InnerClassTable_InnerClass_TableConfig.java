package com.j256.ormlite.android.processor.inputs;

import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.util.ArrayList;
import java.util.List;

public final class InnerClassTable_InnerClass_TableConfig {
	private InnerClassTable_InnerClass_TableConfig() {
	}

	public static DatabaseTableConfig<?> createConfig() {
		List<DatabaseFieldConfig> databaseFieldConfigs = new ArrayList<DatabaseFieldConfig>();
		DatabaseFieldConfig fieldFieldConfig = new DatabaseFieldConfig("field");
		databaseFieldConfigs.add(fieldFieldConfig);
		return new DatabaseTableConfig<InnerClassTable.InnerClass>(
				InnerClassTable.InnerClass.class, "innerclass",
				databaseFieldConfigs);
	}
}