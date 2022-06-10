package IGNORE___db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Statement;

public class V1__Initial_setup extends BaseJavaMigration {

	@Override
	public void migrate(Context context) throws Exception {
		Statement statement = context.getConnection().createStatement();
		statement.execute("CREATE TABLE PERSON (ID INT PRIMARY KEY, NAME VARCHAR(40))");
	}

}
