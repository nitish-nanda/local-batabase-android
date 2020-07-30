package com.example.localdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.localdatabase.models.WordModel;
import com.example.localdatabase.room.WordRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Constants;

public class AddDataActivity extends AppCompatActivity {

    @BindView(R.id.tv_heading)
    public TextView tvHeading;

    @BindView(R.id.et_string)
    public TextInputEditText editText;

    @BindView(R.id.btn_save)
    public Button button;

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
            editText.setSelection(editText.getText().toString().length());
            button.setText("Update");
            //for set top toolbar
            tvHeading.setText("Update Data");
        } else {
            button.setText("Add");
            tvHeading.setText("Add Data");
        }
    }

    public void onClickBack(View view) {
        onBackPressed();
    }

    public void onClickSave(View view) {
        String string = editText.getText().toString();
        if (string.isEmpty()) {
            return;
        }
        if (wordModel == null) {
            WordModel model = new WordModel(string.trim());
            repository.insert(model);
        } else {
            wordModel.setWord(string.trim());
            repository.update(wordModel);
        }
        finish();
    }

}