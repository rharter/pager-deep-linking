package com.ryanharter.deeplinkdemo;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class CategoryFragment extends Fragment {

  private static final String EXTRA_CATEGORY = "category";
  private static final String EXTRA_ITEM_INDEX = "itemIndex";

  private RecyclerView list;

  private int itemPosition = -1;

  public static CategoryFragment with(@Nullable String category) {
    return with(category, -1);
  }

  public static CategoryFragment with(@Nullable String category, int itemIndex) {
    CategoryFragment f = new CategoryFragment();

    Bundle args = new Bundle();
    args.putString(EXTRA_CATEGORY, category);
    args.putInt(EXTRA_ITEM_INDEX, itemIndex);
    f.setArguments(args);

    return f;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    list = new RecyclerView(container.getContext());
    return list;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    itemPosition = getArguments().getInt(EXTRA_ITEM_INDEX);

    String catId = getArguments().getString(EXTRA_CATEGORY);

    list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    list.setAdapter(new CategoryAdapter(DataStore.getItems(catId)));
    list.addItemDecoration(new RecyclerView.ItemDecoration() {
      @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
          RecyclerView.State state) {
        outRect.set(16, 16, 16, 16);
      }
    });
  }

  @Override public void onResume() {
    super.onResume();
    if (itemPosition != -1) {
      selectItem(itemPosition);
      itemPosition = -1; // one time only
    }
  }

  public void selectItem(int itemIndex) {
    if (itemIndex >= 0) {
      list.scrollToPosition(itemIndex);
    }
  }

  private static class CategoryAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<String> items;

    public CategoryAdapter(List<String> items) {
      this.items = items;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
      holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
      return items.size();
    }
  }

  private static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView text;

    public ViewHolder(View itemView) {
      super(itemView);
      text = (TextView) itemView.findViewById(android.R.id.text1);
    }

    public void bind(String value) {
      text.setText(value);
    }
  }
}
