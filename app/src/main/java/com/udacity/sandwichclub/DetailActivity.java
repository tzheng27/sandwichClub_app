package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String EMPTY_STRING = "";
    private static final String DELIMITER = ", ";

    private TextView mOriginTextView;
    private TextView mDescriptionTextView;
    private TextView mIngredientsTextView;
    private TextView mAlsoKnownAsTextView;
    private ImageView mSandwichPicImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mOriginTextView = (TextView) findViewById(R.id.origin_tv);
        mDescriptionTextView = (TextView) findViewById(R.id.description_tv);
        mIngredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        mAlsoKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);
        mSandwichPicImageView = (ImageView) findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try{
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);

        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich srcSandwich) {
        setTitle(srcSandwich.getMainName());
        Picasso.with(this)
                .load(srcSandwich.getImage())
                .into(mSandwichPicImageView);

        List<String> alsoKnownNames = srcSandwich.getAlsoKnownAs();
        if(!((List) alsoKnownNames).isEmpty()){
            mAlsoKnownAsTextView.setText(EMPTY_STRING);
            for(String alsoKnownName : alsoKnownNames){
                mAlsoKnownAsTextView.append(alsoKnownName);
                if (alsoKnownName != alsoKnownNames.get(alsoKnownNames.size() - 1)){
                    mAlsoKnownAsTextView.append(DELIMITER);
                }
            }
        }

        if(!srcSandwich.getPlaceOfOrigin().isEmpty()){
            mOriginTextView.setText(srcSandwich.getPlaceOfOrigin());
        }

        if(!srcSandwich.getDescription().isEmpty()){
            mDescriptionTextView.setText(srcSandwich.getDescription());
        }

        List<String> ingredients = srcSandwich.getIngredients();
        if(!ingredients.isEmpty()){
            mIngredientsTextView.setText(EMPTY_STRING);
            for(String ingredient : ingredients){
                mIngredientsTextView.append(ingredient);
                if(ingredient != ingredients.get(ingredients.size() - 1)){
                    mIngredientsTextView.append(DELIMITER);
                }
            }

        }
    }
}
