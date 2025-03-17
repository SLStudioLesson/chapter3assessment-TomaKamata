package com.recipeapp.datahandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

public class CSVDataHandler implements DataHandler {
    private String filePath;

    public CSVDataHandler() {
        filePath = "app/src/main/resources/recipes.csv";
    }

    public CSVDataHandler(String filePath) {
        this.filePath = filePath;
    }

    public String getMode() {
        return "CSV";
    }

    public ArrayList<Recipe> readData() throws IOException {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                String name = "";
                ArrayList<Ingredient> ingredient = new ArrayList<>();
                // 行に項目が1追加の場合飛ばす
                if (words.length <= 1) {
                    continue;
                }
                // １つ目はレシピ名、２つ目以降は材料
                for (int i = 0; i < words.length; i++) {
                    if (i == 0) {
                        name = words[i];
                    } else {
                        ingredient.add(new Ingredient(words[i]));
                    }
                }
                // レシピ追加
                recipes.add(new Recipe(name, ingredient));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public void writerData(Recipe recipe) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String line;
            line = recipe.getName();
            for (Ingredient ingredient : recipe.getIngredients()) {
                line += "," + ingredient.getName();
            }
            line += "\n";
            writer.write(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Recipe> searchData(String keyword) throws IOException {
        String[] keys = keyword.split("&");
        ArrayList<Recipe> recipes = readData();
        for (String key : keys) {
            String[] words = key.split("=");
            if (words.length != 2) {
                System.out.println("No matching recipes found.");
            } else if (words[0].equals("name")) {
                // レシピ名に指定ワードが含まれていない場合削除
                for (int i = recipes.size() - 1; i >= 0; i--) {
                    if (!recipes.get(i).getName().contains(words[1])) {
                        recipes.remove(i);
                    }
                }
            } else if (words[0].equals("ingredient")) {
                // 材料に指定ワードが含まれていない場合削除
                for (int i = recipes.size() - 1; i >= 0; i--) {
                    Boolean search = false;
                    for (Ingredient ingredient : recipes.get(i).getIngredients()) {
                        if (ingredient.getName().contains(words[1])) {
                            search = true;
                        }
                    }
                    if (!search) {
                        recipes.remove(i);
                    }
                }
            }
        }
        return recipes;
    }
}
