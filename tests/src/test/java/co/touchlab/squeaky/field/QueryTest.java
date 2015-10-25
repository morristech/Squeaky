package co.touchlab.squeaky.field;

import co.touchlab.squeaky.dao.Dao;
import co.touchlab.squeaky.field.types.BaseTypeTest;
import co.touchlab.squeaky.table.DatabaseQuery;
import co.touchlab.squeaky.table.DatabaseTable;
import co.touchlab.squeaky.table.DatabaseView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kgalligan on 7/26/15.
 */
@RunWith(RobolectricTestRunner.class)
public class QueryTest extends BaseTypeTest
{
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
	public void testQuery() throws Exception
	{
		Dao<Parent, Integer> parentDao = helper.getDao(Parent.class);
		Dao<Child, Integer> childDao = helper.getDao(Child.class);

		for (int p = 0; p < 3; p++)
		{
			Parent parent = new Parent();
			parent.name = "p " + p;
			parentDao.create(parent);

			Random random = new Random();
			List<Child> children = new ArrayList<Child>();

			for (int i = 0; i < 20; i++)
			{
				Child child = new Child();
				child.asdf = "Hello " + random.nextInt(10000);
				child.parent = parent;
				childDao.create(child);
				children.add(child);
			}
		}

		Dao<ParentChildQuery, Integer> parentChildViewDao = helper.getDao(ParentChildQuery.class);

		List<ParentChildQuery> parentChildViews = parentChildViewDao.queryForAll().list();

		Assert.assertEquals("Not enough view results", parentChildViews.size(), 60);

		for (ParentChildQuery parentChildView : parentChildViews)
		{
			Assert.assertTrue(parentChildView.childId > 0);
			Assert.assertTrue(parentChildView.parentName.startsWith("p "));
		}

		/*Parent parentDb = parentdao.queryForAll().list().get(0);
		parentDao.fillForeignCollection(parentDb, "children");

		assertTrue(parentDb.children.equals(children));*/
	}

	@DatabaseTable
	protected static class Parent
	{
		@DatabaseField(generatedId = true)
		int id;

		@DatabaseField
		String name;

		@ForeignCollectionField(foreignFieldName = "parent")
		List<Child> children;
	}

	@DatabaseTable
	protected static class Child
	{
		@DatabaseField(generatedId = true)
		int id;

		@DatabaseField
		String asdf;

		@DatabaseField(foreign = true)
		Parent parent;
	}

	@DatabaseQuery(fromQuery = "select p.id parentId, p.name parentName, c.id childId, c.asdf from Parent p join Child c on p.id = c.parent_id")
	protected static class ParentChildQuery
	{
		@DatabaseField
		public final int parentId;

		@DatabaseField
		public final String parentName;

		@DatabaseField
		public final int childId;

		@DatabaseField
		public final String asdf;

		public ParentChildQuery(int parentId, String parentName, int childId, String asdf)
		{
			this.parentId = parentId;
			this.parentName = parentName;
			this.childId = childId;
			this.asdf = asdf;
		}
	}

	private SimpleHelper getHelper()
	{
		return createHelper(
				Child.class,
				Parent.class
		);
	}
}