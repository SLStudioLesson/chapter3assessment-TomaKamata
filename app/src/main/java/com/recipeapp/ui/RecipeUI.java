package com.recipeapp.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.recipeapp.datahandler.DataHandler;
import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

public class RecipeUI {
    private BufferedReader reader;
    private DataHandler dataHandler;

    public RecipeUI(DataHandler dataHandler) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        this.dataHandler = dataHandler;
    }

    public void displayMenu() {

        System.out.println("Current mode: " + dataHandler.getMode());

        while (true) {
            try {
                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");

                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        displayRecipes();
                        break;
                    case "2":
                        addNewRecipe();
                        break;
                    case "3":
                        searchRecipe();
                        break;
                    case "4":
                        System.out.println("Exiting the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading input from user: " + e.getMessage());
            }
        }
    }

    private void displayRecipes() {
        try {
            ArrayList<Recipe> recipes = dataHandler.readData();
            System.out.println();
            if (recipes.size() < 1) {
                System.out.println("No recipes available.");
                return;
            }
            System.out.println("Recipes: ");
            System.out.println("-----------------------------------");
            for (Recipe recipe : recipes) {
                System.out.println("Recipe Name: " + recipe.getName());
                System.out.print("Main Ingredients: ");
                int i = 0; // 末尾カウント用
                for (Ingredient ingredient : recipe.getIngredients()) {
                    i++;
                    if (i == recipe.getIngredients().size()) {
                        System.out.print(ingredient.getName()); // 末尾だけ「,」いらない
                    } else {
                        System.out.print(ingredient.getName() + ",");
                    }
                }
                System.out.println();
                System.out.println("-----------------------------------");
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void addNewRecipe() {
        try {
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            System.out.println();
            System.out.println("Adding a new recipe.");
            // レシピ名入力
            System.out.print("Enter recipe name: ");
            String name = reader.readLine();

            // 材料入力
            System.out.println("Enter ingredients (type 'done' when finished): ");
            while (true) {
                System.out.print("Ingredient: ");
                String ingredient = reader.readLine();
                // 「done」が入力されたら終了
                if (ingredient.equals("done")) {
                    break;
                }
                ingredients.add(new Ingredient(ingredient));
            }
            // ハンドラーに書き込み
            dataHandler.writerData(new Recipe(name, ingredients));
            System.out.println("Recipe added successfully.");
        } catch (IOException e) {
            System.out.println("Failed to add new recipe: " + e.getMessage());
        }
    }

    private void searchRecipe() {
        try {
            System.out.print("Enter search query (e.g., 'name=Tomato&ingredient=Garlic'): ");
            String keyword = reader.readLine();
            ArrayList<Recipe> recipes = dataHandler.searchData(keyword);
            System.out.println();
            // 検索結果0県の場合
            if (recipes.size() < 1) {
                System.out.println("No matching recipes found.");
                return;
            } else {
                System.out.println("Matching Recipes: ");
                for (Recipe recipe : recipes) {
                    System.out.println("Name: " + recipe.getName());
                    int i = 0; // 末尾カウント用
                    System.out.print("Ingredients: ");
                    for (Ingredient ingredient : recipe.getIngredients()) {
                        i++;
                        if (i == recipe.getIngredients().size()) {
                            System.out.print(ingredient.getName()); // 末尾だけ「,」いらない
                        } else {
                            System.out.print(ingredient.getName() + ",");
                        }
                    }
                    System.out.println();
                    System.out.println();
                }
            }

        } catch (IOException e) {
            System.out.println("Failed to search recipes: " + e.getMessage());
        }
    }
}
