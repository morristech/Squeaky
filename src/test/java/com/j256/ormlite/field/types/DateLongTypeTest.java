package com.j256.ormlite.field.types;

import com.j256.ormlite.android.squeaky.Dao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.table.DatabaseTable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class DateLongTypeTest extends BaseTypeTest {

	private static final String DATE_COLUMN = "date";
	private SimpleHelper helper;

	@Before
	public void before()
	{
		helper = getHelper();
	}

	@Before
	public void after()
	{
		helper.close();
	}

	@Test
	public void testDateLong() throws Exception {
		Class<LocalDateLong> clazz = LocalDateLong.class;
		Dao<LocalDateLong, Object> dao = helper.getDao(clazz);
		Date val = new Date();
		long sqlVal = val.getTime();
		String valStr = Long.toString(val.getTime());
		LocalDateLong foo = new LocalDateLong();
		foo.date = val;
		dao.create(foo);

		assertTrue(EqualsBuilder.reflectionEquals(foo, dao.queryForAll().get(0)));
	}

	@Test
	public void testDateLongNull() throws Exception {
		Class<LocalDateLong> clazz = LocalDateLong.class;
		Dao<LocalDateLong, Object> dao = helper.getDao(clazz);
		LocalDateLong foo = new LocalDateLong();
		dao.create(foo);
		assertTrue(EqualsBuilder.reflectionEquals(foo, dao.queryForAll().get(0)));
	}

	@Test
	public void testCoverage() {
		new DateLongType(SqlType.LONG, new Class[0]);
	}

	@DatabaseTable
	protected static class LocalDateLong {
		@DatabaseField(columnName = DATE_COLUMN, dataType = DataType.DATE_LONG)
		Date date;
	}

	private SimpleHelper getHelper()
	{
		return createHelper(
				LocalDateLong.class
		);
	}
}