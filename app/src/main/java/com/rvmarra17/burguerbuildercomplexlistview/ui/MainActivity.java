package com.rvmarra17.burguerbuildercomplexlistview.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rvmarra17.burguerbuildercomplexlistview.core.BurguerConfigurator;
import com.rvmarra17.burguerbuildercomplexlistview.R;
import com.rvmarra17.burguerbuildercomplexlistview.core.ListViewFixedIngredientEntry;
import com.rvmarra17.burguerbuildercomplexlistview.core.ListViewIngredientEntry;
import com.rvmarra17.burguerbuildercomplexlistview.core.ListViewIngredientEntryArrayAdapter;
import com.rvmarra17.burguerbuildercomplexlistview.core.ListViewRegularIngredientEntry;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.cfgBurguer = new BurguerConfigurator();        // Show components and initial total
        this.showFixedIngredients();
        this.showIngredients();
        this.updateTotals();
    }

    private void showIngredients() {
        final int NUM_INGREDIENTS = this.cfgBurguer.getSelected().length;
        final ListView lvIngredients = (ListView) this.findViewById(R.id.lvIngredients);        // Create list
        ListViewIngredientEntry[] ingredientEntryAdapterList = new ListViewRegularIngredientEntry[NUM_INGREDIENTS];
        for (int i = 0; i < NUM_INGREDIENTS; ++i) {
            ingredientEntryAdapterList[i] = new ListViewRegularIngredientEntry(this.cfgBurguer, i);
        }
        this.ingredientAdapterList = new ListViewIngredientEntryArrayAdapter(this, ingredientEntryAdapterList);
        lvIngredients.setAdapter(this.ingredientAdapterList);
        lvIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.this.ingredientAdapterList.getItem(i).invertSelection();
                MainActivity.this.ingredientAdapterList.notifyDataSetChanged();
                MainActivity.this.updateTotals();
            }
        });
    }

    private void showFixedIngredients() {
        ListView lvFixedIngredients = (ListView) this.findViewById(R.id.lvFixedIngredients);
        ListViewIngredientEntry[] fixedIngredientsList = new ListViewFixedIngredientEntry[]{new ListViewFixedIngredientEntry(BurguerConfigurator.FIXED_INGREDIENTS[0], BurguerConfigurator.FIXED_COSTS[0]), new ListViewFixedIngredientEntry(BurguerConfigurator.FIXED_INGREDIENTS[1], BurguerConfigurator.FIXED_COSTS[1])};        // Create list
        ListViewIngredientEntryArrayAdapter fixedIngredientEntryAdapterList = new ListViewIngredientEntryArrayAdapter(this, fixedIngredientsList);
        lvFixedIngredients.setAdapter(fixedIngredientEntryAdapterList);
    }


    private void updateTotals() {
        final TextView lblTotal = (TextView) this.findViewById(R.id.lblTotal);
        lblTotal.setText(String.format("%4.2f", MainActivity.this.cfgBurguer.calculateCost()));
    }

    private BurguerConfigurator cfgBurguer;
    private ListViewIngredientEntryArrayAdapter ingredientAdapterList;
}