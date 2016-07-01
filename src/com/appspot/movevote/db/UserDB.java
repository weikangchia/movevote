package com.appspot.movevote.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class UserDB {
	public static boolean userExist(String userId) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(Constant.DS_TABLE_USER).setFilter(new FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
				FilterOperator.EQUAL, KeyFactory.createKey(Constant.DS_TABLE_USER, userId)));

		PreparedQuery pq = dataStore.prepare(query);
		Entity result = pq.asSingleEntity();

		if (result != null) {
			return false;
		}
		return true;
	}

	public static void createNewUser(String userId, String name) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Entity newUser = new Entity(Constant.DS_TABLE_USER, userId);
		newUser.setProperty("name", name);
		newUser.setProperty("last_update", new Date());

		dataStore.put(newUser);
	}

	public static ArrayList<User> getAllUser() {
		ArrayList<User> userList = new ArrayList<User>();

		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(Constant.DS_TABLE_USER).addSort("name");
		PreparedQuery pq = dataStore.prepare(q);

		List<Entity> userEntityList = pq.asList(FetchOptions.Builder.withDefaults());
		for (Entity userEntity : userEntityList) {
			String id = userEntity.getKey().getName();
			String name = userEntity.getProperty("name").toString();
			userList.add(new User(id, name));
		}

		return userList;
	}
}