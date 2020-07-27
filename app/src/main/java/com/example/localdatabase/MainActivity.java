package com.example.localdatabase;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localdatabase.models.WordModel;
import com.example.localdatabase.room.WordRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    public RecyclerView recyclerView;

    private List<WordModel> wordList;
    private WordListAdapter adapter;
    private OnItemClickListener<WordModel> listenerDelete, listenerUpdate;

    private WordRepository wordRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        wordList = new ArrayList<>();
        wordRepository = new WordRepository(getApplication());

        fetchDataFromDB();
        initListener();
        initAdapter();
    }

    private void fetchDataFromDB() {
        wordRepository.getAllWords().observe(this, new Observer<List<WordModel>>() {
            @Override
            public void onChanged(List<WordModel> wordModels) {
                wordList.clear();
                wordList.addAll(wordModels);
                if (adapter != null)
                    adapter.notifyDataSetChanged();
            }
        });
    }

    private void initAdapter() {
        adapter = new WordListAdapter(this, wordList, listenerDelete, listenerUpdate);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        listenerUpdate = new OnItemClickListener<WordModel>() {
            @Override
            public void onClickNotify(WordModel model, int pos) {
                AddDataActivity.start(MainActivity.this, model);
            }
        };
        listenerDelete = new OnItemClickListener<WordModel>() {
            @Override
            public void onClickNotify(WordModel model, int pos) {
                wordList.remove(pos);
                adapter.notifyItemRemoved(pos);
                wordRepository.delete(model);
            }
        };
    }

    public void onClickFAB(View view) {
        AddDataActivity.start(this);
    }

}