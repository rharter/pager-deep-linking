package com.ryanharter.deeplinkdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends AppCompatActivity {

  private static final String EXTRA_ITEM = "item";

  private ViewPager pager;

  private CategoryAdapter adapter;
  private String item;

  public static Intent intentWith(Context context, String item) {
    Intent i = new Intent(context, BrowseActivity.class);
    if (item != null) {
      i.putExtra(EXTRA_ITEM, item);
    }
    return i;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_browse);
    pager = (ViewPager) findViewById(R.id.pager);

    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      item = extras.getString(EXTRA_ITEM);
    }
  }

  @Override protected void onResume() {
    super.onResume();
    adapter = new CategoryAdapter(getSupportFragmentManager(), DataStore.getCategories());
    pager.setAdapter(adapter);

    if (item != null) {
      adapter.scrollToItem(pager, item);
    }
  }

  private static class CategoryAdapter extends FragmentStatePagerAdapter {

    private final SparseArray<CategoryFragment> fragments = new SparseArray<>();
    private final List<String> categories = new ArrayList<>();
    private int selectedCategoryIndex = -1, selectedItemIndex = -1;

    public CategoryAdapter(FragmentManager fm, List<String> categories) {
      super(fm);
      this.categories.addAll(categories);
    }

    @Override public Fragment getItem(int position) {
      CategoryFragment f;
      if (position == selectedCategoryIndex) {
        f = CategoryFragment.with(categories.get(position), selectedItemIndex);
        selectedCategoryIndex = -1;
        selectedItemIndex = -1;
      } else {
        f = CategoryFragment.with(categories.get(position));
      }
      fragments.put(position, f);
      return f;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
      super.destroyItem(container, position, object);
      fragments.remove(position);
    }

    @Override public CharSequence getPageTitle(int position) {
      return categories.get(position);
    }

    @Override public int getCount() {
      return categories.size();
    }

    public void scrollToItem(ViewPager pager, String item) {
      int catIndex = -1;
      int itemIndex = -1;
      for (int i = 0; i < categories.size(); i++) {
        List<String> cat = DataStore.getItems(categories.get(i));
        for (int j = 0; j < cat.size(); j++) {
          String thing = cat.get(j);
          if (thing.equals(item)) {
            catIndex = i;
            itemIndex = j;
            break;
          }
        }
      }

      if (catIndex == -1 || itemIndex == -1) {
        return;
      }

      // If we're already showing the fragment, select the item
      if (catIndex == pager.getCurrentItem()) {
        CategoryFragment f = fragments.get(catIndex);
        if (f != null) {
          f.selectItem(itemIndex);
          return;
        }
      }

      selectedCategoryIndex = catIndex;
      selectedItemIndex = itemIndex;
      pager.setCurrentItem(catIndex);
    }
  }
}
