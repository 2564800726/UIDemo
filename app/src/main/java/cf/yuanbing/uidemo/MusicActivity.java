package cf.yuanbing.uidemo;

import android.content.res.AssetManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class MusicActivity extends AppCompatActivity {
    private ArrayList<String> musicName = new ArrayList<>();
    private ArrayList<Long> musicCount = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_music);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        loadResource();
        RecyclerView recyclerView = findViewById(R.id.rv_music);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MusicAdapter musicAdapter = new MusicAdapter(musicName, musicCount);
        recyclerView.setAdapter(musicAdapter);
    }
    private void loadResource() {
        MyJson myJson = new MyJson();
        try {
            InputStream inputStream = getAssets().open("json.txt");
            InputStreamReader input = new InputStreamReader(inputStream);
            myJson.load(input);
            ArrayList<HashMap> musicList = (ArrayList<HashMap>) myJson.file.get("data");
            assert musicList != null;
            for (HashMap hashMap : musicList) {
                musicName.add((String) hashMap.get("name"));
                musicCount.add((Long) hashMap.get("num"));
            }
        } catch (IOException ioException) {
            Toast.makeText(MusicActivity.this, "未读取到内容", Toast.LENGTH_SHORT).show();
        }
    }
}
