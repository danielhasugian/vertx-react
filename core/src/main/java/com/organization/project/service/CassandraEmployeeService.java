package com.organization.project.service;

import java.util.HashMap;
import java.util.Map;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.organization.project.model.Employee;
import com.organization.project.type.OperationQuery;

import io.vertx.core.json.JsonObject;

/**
 * class to connect database and controller
 */
public class CassandraEmployeeService {

	private JsonObject jsonObject;

	private Cluster cluster;
	private Session session;
	private Employee employee;
	private ResultSet resultSets;

	/**
	 * method to initiate and terminate connection to cluster and pick which
	 * method to use
	 * 
	 * @param statement
	 *            {@link Statement}
	 * @param operation
	 *            {@link OperationQuery}
	 * @param homeAddress
	 *            {@link String}
	 * @param keyspace
	 *            {@link String}
	 * @return {@link JsonObject}
	 */
	public JsonObject queryExecution(Statement statement, OperationQuery operation, String homeAddress,
			String keyspace) {
		jsonObject = new JsonObject();

		buildCluster(homeAddress, keyspace);

		if (operation.equals(OperationQuery.SELECT)) {
			Select(statement);
		} else {
			CreateUpdateDelete(statement);
		}

		cluster.close();
		return jsonObject;
	}

	/**
	 * method to build connection to cluster
	 * 
	 * @param homeAddress
	 *            {@link String}
	 * @param keyspace
	 *            {@link String}
	 */
	private void buildCluster(String homeAddress, String keyspace) {

		cluster = Cluster.builder().addContactPoint(homeAddress).build();
		session = cluster.connect(keyspace);

	}

	/**
	 * method to execute select statement
	 * 
	 * @param statement
	 *            {@link Statement}
	 */
	private void Select(Statement statement) {
		Map<Integer, Employee> resultEmployee = new HashMap<>();

		resultSets = session.execute(statement);
		for (Row row : resultSets) {
			employee = new Employee(row.getInt("id"), row.getString("name"), row.getString("division"));
			resultEmployee.put(employee.getId(), employee);
		}

		if (resultEmployee.isEmpty()) {
			jsonObject.put("failed", true);
		} else {
			jsonObject.put("failed", false);
			jsonObject.put("result", resultEmployee);
		}

	}

	/**
	 * method to execute Create Update and Delete statement
	 * 
	 * @param statement
	 *            {@link Statement}
	 */
	private void CreateUpdateDelete(Statement statement) {
		resultSets = session.execute(statement);

		jsonObject.put("failed", !resultSets.wasApplied());
	}
}
