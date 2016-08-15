/*
 *  Copyright 2015 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package com.organization.project.service;

import java.util.ArrayList;
import java.util.List;

import com.organization.project.type.OperationQuery;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.RoutingContext;

/**
 *
 */
public class BaseSQLClient {
	private static final Logger logger = LoggerFactory.getLogger(BaseSQLClient.class);

	protected List<JsonArray> jsonArrays;
	protected JsonObject jsonObject;
	private HandleService handleService = new HandleService();

	public void executeQuery(RoutingContext routingContext, String queryString, SQLConnection sqlConnection, JsonArray jsonArray,
			OperationQuery type) {
		executeQuerySuper(routingContext, queryString, sqlConnection, jsonArray, type);
	}

	public void executeQuerySuper(RoutingContext routingContext, String queryString, SQLConnection sqlConnection, JsonArray jsonArray,
			OperationQuery type) {
		jsonObject = new JsonObject();
		jsonArrays = new ArrayList<>();

		switch (type) {
		case SELECT:
			if (jsonArray == null) {
				getSelectedQuery(routingContext, queryString, sqlConnection);
			} else {
				getSelectedQueryWithParams(routingContext, queryString, jsonArray, sqlConnection);
			}
			break;
		case UPDATE:
			if (jsonArray == null) {
				getUpdatedQuery(routingContext, queryString, sqlConnection);
			} else {
				getUpdatedQueryWithParams(routingContext, queryString, jsonArray, sqlConnection);
			}
			break;
		case DELETE:
			if (jsonArray == null) {
				getUpdatedQuery(routingContext, queryString, sqlConnection);
			} else {
				getUpdatedQueryWithParams(routingContext, queryString, jsonArray, sqlConnection);
			}
			break;
		default:
			break;
		}
	}

	private void getSelectedQuery(RoutingContext routingContext, String queryString, SQLConnection sqlConnection) {
		sqlConnection.query(queryString, exec -> {
			if (exec.succeeded()) {
				jsonArrays = exec.result().getResults();
				jsonObject.put("failed", "false");
				jsonObject.put("results", jsonArrays);
			} else {
				jsonObject.put("failed", "true");
				logger.error("getSelectedQuery", exec.cause());
			}
			handleService.handlingResponse(routingContext, jsonObject);
		});
	}

	private void getUpdatedQuery(RoutingContext routingContext, String queryString, SQLConnection sqlConnection) {
		sqlConnection.update(queryString, exec -> {
			if (exec.succeeded()) {
				jsonObject.put("failed", "false");
			} else {
				logger.error("getUpdatedQuery", exec.cause());
				jsonObject.put("failed", "true");
			}
			handleService.handlingResponse(routingContext, jsonObject);
		});
	}

	private void getSelectedQueryWithParams(RoutingContext routingContext, String queryString, JsonArray jsonArray, SQLConnection sqlConnection) {
		sqlConnection.queryWithParams(queryString, jsonArray, exec -> {
			if (exec.succeeded()) {
				jsonArrays = exec.result().getResults();
				jsonObject.put("failed", "false");
				jsonObject.put("results", jsonArrays);
			} else {
				logger.error("getSelectedQuery", exec.cause());
				jsonObject.put("failed", "true");
			}
			handleService.handlingResponse(routingContext, jsonObject);
		});
	}

	private void getUpdatedQueryWithParams(RoutingContext routingContext, String queryString, JsonArray jsonArray, SQLConnection sqlConnection) {
		sqlConnection.updateWithParams(queryString, jsonArray, exec -> {
			if (exec.succeeded()) {
				jsonObject.put("failed", "false");
			} else {
				logger.error("getUpdatedQuery", exec.cause());
				jsonObject.put("failed", "true");
			}
			handleService.handlingResponse(routingContext, jsonObject);
		});
	}

}
