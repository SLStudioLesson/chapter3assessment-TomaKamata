package com.recipeapp.datahandler;

import java.io.IOException;
import java.util.ArrayList;

import com.recipeapp.model.Recipe;

public class JSONDataHandler implements DataHandler {
        private String filePath;

    public JSONDataHandler(){
        filePath = "app/src/main/resources/recipes.json";
    }

    public JSONDataHandler(String filePath) {
        this.filePath = filePath;
    }
    public String getMode(){
        return "JSON";
    }
    public ArrayList<Recipe> readData() throws IOException{
        return null;
    }
    public void writerData(Recipe recipe) throws IOException{

    }
    public ArrayList<Recipe> searchData(String keyword) throws IOException{
        return null;
    }
}
