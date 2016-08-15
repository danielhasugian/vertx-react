package com.organization.project.controller;

import com.organization.project.service.BaseSQLClient;
import com.organization.project.service.HandleService;
import com.organization.project.type.OperationQuery;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.RoutingContext;

public class SamplingController {
	private SQLConnection sqlConnection;
	private JDBCClient jdbc;
	private JsonObject jsonObject;
	private JsonArray jsonArray;
	
	private HandleService handleService = new HandleService();

	public SamplingController(JDBCClient jdbc) {
		super();
		this.jdbc = jdbc;
	}

	protected void getDataSampling(RoutingContext routingContext) {
		BaseSQLClient baseClient = new BaseSQLClient();
		HttpServerResponse response = routingContext.response();
		sqlConnection = null;
		jdbc.getConnection(res -> {
			if (res.succeeded()) {
				sqlConnection = res.result();
				baseClient.executeQuery(routingContext, "select nama from t_testing", sqlConnection, null,
						OperationQuery.SELECT);
			} else {
				handleService.sendError(404, response);
			}
		});

	}
	
	protected void handleAddSampling(RoutingContext routingContext) {
		BaseSQLClient baseClient = new BaseSQLClient();
		HttpServerResponse response = routingContext.response();
		jsonObject = routingContext.getBodyAsJson();
		
		jsonArray = new JsonArray();
		jsonArray.add(jsonObject.getInteger("id"));
		jsonArray.add(jsonObject.getString("nama"));

		sqlConnection = null;
		jdbc.getConnection(res -> {
			if (res.succeeded()) {
				sqlConnection = res.result();
				baseClient.executeQuery(routingContext, "insert into t_testing(id, nama) values(?,?)", sqlConnection, jsonArray,
						OperationQuery.UPDATE);
			} else {
				handleService.sendError(404, response);
			}
		});

	}
	
	protected void handleUpdateSampling(RoutingContext routingContext) {
		BaseSQLClient baseClient = new BaseSQLClient();
		HttpServerResponse response = routingContext.response();
		jsonObject = routingContext.getBodyAsJson();
		
		jsonArray = new JsonArray();
		jsonArray.add(jsonObject.getString("nama"));
		jsonArray.add(jsonObject.getInteger("id"));
		
		sqlConnection = null;
		jdbc.getConnection(res -> {
			if (res.succeeded()) {
				sqlConnection = res.result();
				baseClient.executeQuery(routingContext, "update t_testing "
						+ "set nama = ? "
						+ "where id = ?", sqlConnection, jsonArray,
						OperationQuery.UPDATE);
			} else {
				handleService.sendError(404, response);
			}
		});
	}
	
	protected void handleDeleteSampling(RoutingContext routingContext) {
		BaseSQLClient baseClient = new BaseSQLClient();
		HttpServerResponse response = routingContext.response();
		jsonObject = routingContext.getBodyAsJson();
		
		jsonArray = new JsonArray();
		jsonArray.add(jsonObject.getInteger("id"));
	
		sqlConnection = null;
		jdbc.getConnection(res -> {
			if (res.succeeded()) {
				sqlConnection = res.result();
				baseClient.executeQuery(routingContext, "delete from t_testing "
						+ "where id = ?", sqlConnection, jsonArray,
						OperationQuery.DELETE);
			} else {
				handleService.sendError(404, response);
			}
		});
	}
}
