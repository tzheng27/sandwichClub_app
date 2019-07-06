package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String NAME_KEYWORD = "name";
    private static final String MAIN_NAME_KEYWORD ="mainName";
    private static final String ALSO_KNOWN_AS_KEYWORD = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_KEYWORD = "placeOfOrigin" ;
    private static final String DESCRIPTION_KEYWORD  = "description";
    private static final String IMAGE_KEYWORD = "image";
    private static final String INGREDIENTS_KEYWORD   = "ingredients";

    public static Sandwich parseSandwichJson(String jsonString) throws JSONException {
        String mainName;
        List<String> alsoKnownAs = null;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = null;

        JSONObject sandWich = new JSONObject(jsonString);
        JSONObject nameObj = sandWich.getJSONObject(NAME_KEYWORD);
        mainName = nameObj.getString(MAIN_NAME_KEYWORD);
        alsoKnownAs = getStringListFromJsonArray(nameObj.getJSONArray(ALSO_KNOWN_AS_KEYWORD));
        placeOfOrigin = sandWich.getString(PLACE_OF_ORIGIN_KEYWORD);
        description = sandWich.getString(DESCRIPTION_KEYWORD);
        image = sandWich.getString(IMAGE_KEYWORD );
        ingredients = getStringListFromJsonArray(sandWich.getJSONArray(INGREDIENTS_KEYWORD));

        Sandwich result = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        return result;
    }

    public static List<String> getStringListFromJsonArray(JSONArray src) throws JSONException {
        List<String> result = new ArrayList<String>();
        for(int i = 0; i < src.length(); i++){
            String ele = src.getString(i);
            result.add(ele);
        }
        return result;
    }
}
