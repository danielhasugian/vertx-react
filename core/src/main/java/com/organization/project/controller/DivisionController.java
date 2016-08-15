package com.organization.project.controller;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.organization.project.service.CassandraEmployeeService;
import com.organization.project.service.HandleService;
import com.organization.project.type.OperationQuery;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * Class to respond HTTP request
 */
public class DivisionController {
	private final String keyspace = "tes";
	private final String table = "employee";
	private final String homeAddress = "127.0.0.1";

	private JsonObject jsonObject;
	private HandleService handleService = new HandleService();
	private Statement statement;
	private CassandraEmployeeService cassandraEmployeeService = new CassandraEmployeeService();

	private int id;
	private String name;
	private String division;

	/**
	 * method to get all data from database
	 * 
	 * @param routingContext
	 *            {@link RoutingContext}
	 */
	protected void getListDivision(RoutingContext routingContext) {
		try {
			statement = QueryBuilder.select().all().from(keyspace, table);
			jsonObject = cassandraEmployeeService.queryExecution(statement, OperationQuery.SELECT, homeAddress,
					keyspace);

			handleService.handlingResponse(routingContext, jsonObject);
		} catch (Exception ex) {
			handleService.sendError(404, routingContext.response());
		}
	}

	/**
	 * method to get one data from database
	 * 
	 * @param routingContext
	 *            {@link RoutingContext}
	 */
	protected void getOneBasedOnId(RoutingContext routingContext) {
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));

			statement = QueryBuilder.select().from(keyspace, table).where(QueryBuilder.eq("id", id));
			jsonObject = cassandraEmployeeService.queryExecution(statement, OperationQuery.SELECT, homeAddress,
					keyspace);

			handleService.handlingResponse(routingContext, jsonObject);
		} catch (Exception ex) {
			handleService.sendError(404, routingContext.response());
		}
	}

	/**
	 * method to delete content from database
	 * 
	 * @param routingContext
	 */
	protected void deleteOne(RoutingContext routingContext) {
		try {
			id = routingContext.getBodyAsJson().getInteger("id");

			statement = QueryBuilder.delete().from(keyspace, table).where(QueryBuilder.eq("id", id)).ifExists();
			jsonObject = cassandraEmployeeService.queryExecution(statement, OperationQuery.DELETE, homeAddress,
					keyspace);

			handleService.handlingResponse(routingContext, jsonObject);
		} catch (Exception ex) {
			handleService.sendError(404, routingContext.response());
		}
	}

	/**
	 * method to Insert new data to database
	 * 
	 * @param routingContext
	 *            {@link RoutingContext}
	 */
	protected void Create(RoutingContext routingContext) {
		try {
			id = routingContext.getBodyAsJson().getInteger("id");
			String name = routingContext.getBodyAsJson().getString("name");
			String division = routingContext.getBodyAsJson().getString("division");

			statement = QueryBuilder.insertInto(keyspace, table).value("id", id).value("name", name)
					.value("division", division).ifNotExists();

			jsonObject = cassandraEmployeeService.queryExecution(statement, OperationQuery.CREATE, homeAddress,
					keyspace);
			handleService.handlingResponse(routingContext, jsonObject);
		} catch (Exception ex) {
			handleService.sendError(404, routingContext.response());
		}
	}

	/**
	 * method to Update database Content
	 * 
	 * @param routingContext
	 *            {@link RoutingContext}
	 */
	protected void Update(RoutingContext routingContext) {
		try {
			id = routingContext.getBodyAsJson().getInteger("id");
			name = routingContext.getBodyAsJson().getString("name");
			division = routingContext.getBodyAsJson().getString("division");

			statement = QueryBuilder.update(keyspace, table).with(QueryBuilder.set("name", name))
					.and(QueryBuilder.set("division", division)).where(QueryBuilder.eq("id", id)).ifExists();

			jsonObject = cassandraEmployeeService.queryExecution(statement, OperationQuery.UPDATE, homeAddress,
					keyspace);
			handleService.handlingResponse(routingContext, jsonObject);
		} catch (Exception ex) {
			handleService.sendError(404, routingContext.response());
		}
	}

}
