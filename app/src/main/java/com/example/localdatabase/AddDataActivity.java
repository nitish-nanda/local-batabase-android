package com.example.localdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.localdatabase.models.WordModel;
import com.example.localdatabase.room.WordRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Constants;

public class AddDataActivity extends AppCompatActivity {

    @BindView(R.id.et_string)
    public TextInputEditText editText;

    private WordRepository repository;

    private WordModel wordModel;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AddDataActivity.class);
        activity.startActivity(intent);
    }

    public static void start(Activity activity, WordModel model) {
        Intent intent = new Intent(activity, AddDataActivity.class);
        intent.putExtra("word", model);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        ButterKnife.bind(this);
        repository = new WordRepository(getApplication());

        wordModel = (WordModel) getIntent().getSerializableExtra("word");
        if (wordModel != null) {
            editText.setText(wordModel.getWord());
            setTitle("Update Data");
        } else
            setTitle("Add Data");
    }

    public void onClickSave(View view) {
        String string = editText.getText().toString();
        if (string.isEmpty()) {
            return;
        }
        WordModel model = new WordModel(string.trim());
        if (wordModel == null)
            repository.insert(model);
        else
            repository.update(model);
        finish();
    }
}