package ch.beerpro.presentation.explore.search;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.common.base.Strings;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import ch.beerpro.R;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Search;
import ch.beerpro.presentation.details.DetailsActivity;
import ch.beerpro.presentation.explore.search.beers.SearchResultFragment;
import ch.beerpro.presentation.explore.search.suggestions.SearchSuggestionsFragment;
import ch.beerpro.presentation.profile.mybeers.MyBeersViewModel;
import ch.beerpro.presentation.profile.mybeers.OnMyBeerItemInteractionListener;

public class SearchActivity extends AppCompatActivity
        implements SearchResultFragment.OnItemSelectedListener, SearchSuggestionsFragment.OnItemSelectedListener,
        OnMyBeerItemInteractionListener {

    private SearchViewModel searchViewModel;
    private ViewPagerAdapter adapter;
    private EditText searchEditText;
    private MyBeersViewModel myBeersViewModel;
    private TabLayout tabLayout;
    private RelativeLayout chipLayout;
    private ChipGroup chipGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String text = searchEditText.getText().toString();
                handleSearch(text);
                addSearchTermToUserHistory(text);
            }
            return false;
        });

        findViewById(R.id.clearFilterButton).setOnClickListener(view -> {
            searchEditText.setText(null);
            handleSearch(null);
        });



        ViewPager viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setSaveFromParentEnabled(false);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        myBeersViewModel = ViewModelProviders.of(this).get(MyBeersViewModel.class);

        chipLayout = findViewById(R.id.chipRelLayout);
        chipGroup = new ChipGroup(this);
        chipGroup.setSingleSelection(true);
        LiveData<List<Search>> beerSearches = searchViewModel.getMyLatestSearches();
        String[] beers = {"Quöllfrisch", "Lager Hell", "IPA", "Lager"};
        for (String beer : beers) {
            Chip chip = new Chip(this);
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
            chip.setChipDrawable(chipDrawable);
            chip.setText(beer);
            chipGroup.addView(chip);
        }
        chipLayout.addView(chipGroup);
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                Chip chip = chipGroup.findViewById(i);
                if(chip != null){
                   handleSearch(chip.getText().toString());
                }
            }
        });
    }

    private void handleSearch(String text) {
        searchViewModel.setSearchTerm(text);
        myBeersViewModel.setSearchTerm(text);
        adapter.setShowSuggestions(Strings.isNullOrEmpty(text));
        adapter.notifyDataSetChanged();
    }

    private void addSearchTermToUserHistory(String text) {
        searchViewModel.addToSearchHistory(text);
    }

    @Override
    public void onSearchResultListItemSelected(View animationSource, Beer item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.ITEM_ID, item.getId());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, animationSource, "image");
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onSearchSuggestionListItemSelected(String text) {
        searchEditText.setText(text);
        searchEditText.setSelection(text.length());
        hideKeyboard();
        handleSearch(text);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
        }
    }

    @Override
    public void onMoreClickedListener(ImageView photo, Beer item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.ITEM_ID, item.getId());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, photo, "image");
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onWishClickedListener(Beer item) {
        searchViewModel.toggleItemInWishlist(item.getId());
    }
}
