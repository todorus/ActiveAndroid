package com.activeandroid;

/*
 * Copyright (C) 2010 Michael Pardo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.activeandroid.util.Log;

public final class ActiveAndroid {

	private static Cache cache;

	//////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	//////////////////////////////////////////////////////////////////////////////////////

	public static Cache initialize(Context context) {
		return initialize(new Configuration.Builder(context).create());
	}

	public static Cache initialize(Configuration configuration) {
		return initialize(configuration, false);
	}

	public static Cache initialize(Context context, boolean loggingEnabled) {
		return initialize(new Configuration.Builder(context).create(), loggingEnabled);
	}

	public static Cache initialize(Configuration configuration, boolean loggingEnabled) {
		// Set logging enabled first
		setLoggingEnabled(loggingEnabled);
		cache = new Cache(configuration);
		return cache;
	}

	public static Cache getCache(){
		return cache;
	}

	public static void clearCache() {
		clearCache(cache);
	}

	public static void clearCache(Cache cache) {
		cache.clear();
	}

	public static void dispose() {
		dispose(cache);
	}

	public static void dispose(Cache cache) {
		cache.dispose();
	}

	public static void setLoggingEnabled(boolean enabled) {
		Log.setEnabled(enabled);
	}

	public static SQLiteDatabase getDatabase() {
		return getDatabase(cache);
	}

	public static SQLiteDatabase getDatabase(Cache cache) {
		return cache.openDatabase();
	}

	public static void beginTransaction() {
		beginTransaction(cache);
	}

	public static void beginTransaction(Cache cache) {
		cache.openDatabase().beginTransaction();
	}

	public static void endTransaction() {
		endTransaction(cache);
	}

	public static void endTransaction(Cache cache) {
		cache.openDatabase().endTransaction();
	}

	public static void setTransactionSuccessful() {
		setTransactionSuccessful(cache);
	}

	public static void setTransactionSuccessful(Cache cache) {
		cache.openDatabase().setTransactionSuccessful();
	}

	public static boolean inTransaction() {
		return inTransaction(cache);
	}

	public static boolean inTransaction(Cache cache) {
		return cache.openDatabase().inTransaction();
	}

	public static void execSQL(String sql) {
		execSQL(cache, sql);
	}

	public static void execSQL(Cache cache, String sql) {
		cache.openDatabase().execSQL(sql);
	}

	public static void execSQL(String sql, Object[] bindArgs) {
		execSQL(cache, sql, bindArgs);
	}

	public static void execSQL(Cache cache, String sql, Object[] bindArgs) {
		cache.openDatabase().execSQL(sql, bindArgs);
	}
}
