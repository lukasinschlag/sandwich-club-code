package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView knownAsTitle = findViewById(R.id.textView),
                originTitle = findViewById(R.id.textView2),
                descTitle = findViewById(R.id.textView3),
                ingredientsTitle = findViewById(R.id.textView4),
                knownAsTV = findViewById(R.id.also_known_tv),
                originTV = findViewById(R.id.origin_tv),
                descTV = findViewById(R.id.description_tv),
                ingredientsTV = findViewById(R.id.ingredients_tv);

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            knownAsTitle.setVisibility(View.GONE);
            knownAsTV.setVisibility(View.GONE);
        } else {
            knownAsTV.setText(sandwich.getAlsoKnownAs().get(0));
            if (sandwich.getAlsoKnownAs().size() > 1) {
                for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                    knownAsTV.append(", " + sandwich.getAlsoKnownAs().get(i));
                }
            }
        }
        if (TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
            originTitle.setVisibility(View.GONE);
            originTV.setVisibility(View.GONE);
        } else {
            originTV.setText(sandwich.getPlaceOfOrigin());
        }
        if (TextUtils.isEmpty(sandwich.getDescription())) {
            descTitle.setVisibility(View.GONE);
            descTV.setVisibility(View.GONE);
        } else {
            descTV.setText(sandwich.getDescription());
        }
        if (sandwich.getIngredients().isEmpty()) {
            ingredientsTitle.setVisibility(View.GONE);
            ingredientsTV.setVisibility(View.GONE);
        } else {
            for (String i : sandwich.getIngredients()) {
                ingredientsTV.append(i + "\n");
            }
        }
    }
}
