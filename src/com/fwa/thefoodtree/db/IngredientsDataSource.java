package com.fwa.thefoodtree.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class IngredientsDataSource {

	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	String mQuery;

	private String[] allColumns = { DatabaseHelper.COLUMN_ID,
			DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_METRIC,
			DatabaseHelper.COLUMN_COST_PER_ONE,
			DatabaseHelper.COLUMN_MEASURE_BY_QUANTITY,
			DatabaseHelper.COLUMN_CATEGORY_ID };

	public IngredientsDataSource(Context context, String query) {
		dbHelper = new DatabaseHelper(context);
		mQuery = query;
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	// public Ingredient createIngredient(String name, String metric, double
	// cost, int measureByQuantity, int catId) {
	// ContentValues values = new ContentValues();
	//
	// values.put(MySQLiteHelper.COLUMN_NAME, name);
	// values.put(MySQLiteHelper.COLUMN_METRIC, metric);
	// values.put(MySQLiteHelper.COLUMN_COST_PER_ONE, cost);
	// values.put(MySQLiteHelper.COLUMN_MEASURE_BY_QUANTITY, measureByQuantity);
	// values.put(MySQLiteHelper.COLUMN_CATEGORY_ID, catId);
	//
	//
	// long insertId = database.insert(MySQLiteHelper.TABLE_INGREDIENTS,
	// null,values);
	// Cursor cursor = database.query(MySQLiteHelper.TABLE_INGREDIENTS,
	// allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	// null, null, null);
	// cursor.moveToFirst();
	// Ingredient newIngredient = cursorToIngredient(cursor);
	// cursor.close();
	// return newIngredient;
	// }

	// public void deleteIngredient(Ingredient Ingredient) {
	// long id = Ingredient.getId();
	// System.out.println("Ingredient deleted with id: " + id);
	// database.delete(MySQLiteHelper.TABLE_IngredientS,
	// MySQLiteHelper.COLUMN_ID
	// + " = " + id, null);
	// }

	

	public List<Ingredient> getAllIngredients() {
		List<Ingredient> Ingredients = new ArrayList<Ingredient>();

		Cursor cursor = database.query(DatabaseHelper.TABLE_INGREDIENTS,
				allColumns, this.mQuery, null, null, null,
				null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Ingredient Ingredient = cursorToIngredient(cursor);
			Ingredients.add(Ingredient);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return Ingredients;
	}

	private Ingredient cursorToIngredient(Cursor cursor) {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(cursor.getLong(0));
		ingredient.setIngredient(cursor.getString(1));
		return ingredient;
	}
}