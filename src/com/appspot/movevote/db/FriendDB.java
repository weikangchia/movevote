package com.appspot.movevote.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.appspot.movevote.entity.Constant;
import com.appspot.movevote.entity.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class FriendDB {
	public static boolean isFriend(String userId, String friendId) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Filter userIdFilter = new FilterPredicate("user_id", FilterOperator.EQUAL, userId);
		Filter friendIdFilter = new FilterPredicate("friend_id", FilterOperator.EQUAL, friendId);
		CompositeFilter compFilter = CompositeFilterOperator.and(userIdFilter, friendIdFilter);

		Query query = new Query(Constant.DS_TABLE_FRIEND).setFilter(compFilter);
		PreparedQuery pq = dataStore.prepare(query);

		try {
			Entity result = pq.asSingleEntity();
			if (result == null) {
				return false;
			}
		} catch (TooManyResultsException manyEx) {

		}

		return true;
	}

	public static void addFriend(String userId, String userName, String friendId, String friendName) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Entity friendEntity1 = new Entity(Constant.DS_TABLE_FRIEND);
		friendEntity1.setProperty("user_id", userId);
		friendEntity1.setProperty("user_name", userName);
		friendEntity1.setProperty("friend_id", friendId);
		friendEntity1.setProperty("friend_name", friendName);

		Entity friendEntity2 = new Entity(Constant.DS_TABLE_FRIEND);
		friendEntity2.setProperty("user_id", friendId);
		friendEntity2.setProperty("user_name", friendName);
		friendEntity2.setProperty("friend_id", userId);
		friendEntity2.setProperty("friend_name", userName);

		List<Entity> friendList = Arrays.asList(friendEntity1, friendEntity2);
		dataStore.put(friendList);
	}

	public static ArrayList<User> getFriendList(String userId) {
		ArrayList<User> userList = new ArrayList<User>();

		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Filter userIdFilter = new FilterPredicate("user_id", FilterOperator.EQUAL, userId);
		Query query = new Query(Constant.DS_TABLE_FRIEND).setFilter(userIdFilter);
		PreparedQuery pq = dataStore.prepare(query);

		List<Entity> entityList = pq.asList(FetchOptions.Builder.withDefaults());

		for (Entity userEntity : entityList) {
			String friendId = userEntity.getProperty("friend_id").toString();
			String name = userEntity.getProperty("friend_name").toString();
			userList.add(new User(friendId, name));
		}

		return userList;
	}

	public static void removeFriend(String userId, String friendId) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		Filter userIdFilter = new FilterPredicate("user_id", FilterOperator.EQUAL, userId);
		Filter friendIdFilter = new FilterPredicate("friend_id", FilterOperator.EQUAL, friendId);
		CompositeFilter compFilter = CompositeFilterOperator.and(userIdFilter, friendIdFilter);

		Query query = new Query(Constant.DS_TABLE_FRIEND).setFilter(compFilter);
		PreparedQuery pq = dataStore.prepare(query);
		Entity deleteEntity = pq.asSingleEntity();

		dataStore.delete(deleteEntity.getKey());

		userIdFilter = new FilterPredicate("user_id", FilterOperator.EQUAL, friendId);
		friendIdFilter = new FilterPredicate("friend_id", FilterOperator.EQUAL, userId);
		compFilter = CompositeFilterOperator.and(userIdFilter, friendIdFilter);

		query = new Query(Constant.DS_TABLE_FRIEND).setFilter(compFilter);
		pq = dataStore.prepare(query);
		deleteEntity = pq.asSingleEntity();

		dataStore.delete(deleteEntity.getKey());
	}
}