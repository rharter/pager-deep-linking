package com.ryanharter.deeplinkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private static final String ALL = "all";

  private RecyclerView list;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    list = (RecyclerView) findViewById(R.id.list);

    list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    list.setAdapter(new FeaturedAdapter(DataStore.getFeaturedItems()));
  }

  private void onCategorySelected(String category) {
    Intent i;
    if (ALL.equals(category)) {
      i = BrowseActivity.intentWith(this, null);
    } else {
      i = BrowseActivity.intentWith(this, category);
    }
    startActivity(i);
  }

  private class FeaturedAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<String> featuredItems;

    FeaturedAdapter(List<String> featuredItems) {
      this.featuredItems = featuredItems;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
      if (position == 0) {
        holder.bind(ALL);
      } else {
        holder.bind(featuredItems.get(position - 1));
      }
    }

    @Override public int getItemCount() {
      return featuredItems.size() + 1;
    }
  }

  private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    String category;
    TextView text;

    ViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      text = (TextView) itemView.findViewById(android.R.id.text1);
    }

    void bind(String string) {
      category = string;
      text.setText(string);
    }

    @Override public void onClick(View v) {
      onCategorySelected(category);
    }
  }
}
