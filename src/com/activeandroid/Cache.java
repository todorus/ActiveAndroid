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

import java.util.Collection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.LruCache;

import com.activeandroid.serializer.TypeSerializer;
import com.activeandroid.util.Log;

public final class Cache {
	//////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC CONSTANTS
	//////////////////////////////////////////////////////////////////////////////////////

	public static final int DEFAULT_CACHE_SIZE = 1024;

	//////////////////////////////////////////////////////////////////////////////////////
	// PRIVATE MEMBERS
	//////////////////////////////////////////////////////////////////////////////////////

	private Context sContext;

	private ModelInfo sModelInfo;
	private DatabaseHelper sDatabaseHelper;

	private boolean sIsInitialized = false;

	//////////////////////////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	//////////////////////////////////////////////////////////////////////////////////////

	public Cache(Configuration configuration) {
		if (sIsInitialized) {
			Log.v("ActiveAndroid already initialized.");
			return;
		}

		sContext = configuration.getContext();
		sModelInfo = new ModelInfo(configuration);
		sDatabaseHelper = new DatabaseHelper(this, configuration);

		openDatabase();

		sIsInitialized = true;

		Log.v("ActiveAndroid initialized successfully.");
	}

	public synchronized void clear() {
		Log.v("Cache cleared.");
	}

	public synchronized void dispose() {
		closeDatabase();

		sModelInfo = null;
		sDatabaseHelper = null;

		sIsInitialized = false;

		Log.v("ActiveAndroid disposed. Call initialize to use library.");
	}

	// Database access
	
	public boolean isInitialized() {
		return sIsInitialized;
	}

	public synchronized SQLiteDatabase openDatabase() {
		return sDatabaseHelper.getWritableDatabase();
	}

	public synchronized void closeDatabase() {
		sDatabaseHelper.close();
	}

	// Context access

	public Context getContext() {
		return sContext;
	}

	// Entity cache

	public String getIdentifier(Class<? extends Model> type, Long id) {
		return getTableName(type) + "@" + id;
	}

	public String getIdentifier(Model entity) {
		return getIdentifier(entity.getClass(), entity.getId());
	}

//	public synchronized void addEntity(Model entity) {
//	}
//
//	public synchronized Model getEntity(Class<? extends Model> type, long id) {
//		return null;
//	}
//
//	public synchronized void removeEntity(Model entity) {
//	}

	// Model cache

	public synchronized Collection<TableInfo> getTableInfos() {
		return sModelInfo.getTableInfos();
	}

	public synchronized TableInfo getTableInfo(Class<? extends Model> type) {
		return sModelInfo.getTableInfo(type);
	}

	public synchronized TypeSerializer getParserForType(Class<?> type) {
		return sModelInfo.getTypeSerializer(type);
	}

	public synchronized String getTableName(Class<? extends Model> type) {
		return sModelInfo.getTableInfo(type).getTableName();
	}
}
