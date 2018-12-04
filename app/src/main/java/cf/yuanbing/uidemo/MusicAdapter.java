package cf.yuanbing.uidemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private ArrayList<String> musicNameList;
    private ArrayList<Long> musicCountList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView musicImage;
        TextView musicName;
        TextView musicCount;

        public ViewHolder(View v) {
            super(v);
            musicImage = v.findViewById(R.id.iv_music_image);
            musicName = v.findViewById(R.id.tv_music_name);
            musicCount = v.findViewById(R.id.tv_music_count);
        }
    }
    public MusicAdapter(ArrayList<String> musicNameList, ArrayList<Long> musicCountList) {
        this.musicNameList = musicNameList;
        this.musicCountList = musicCountList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_each_music, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.musicImage.setImageResource(R.drawable.bg_main);
        holder.musicName.setText(musicNameList.get(position));
        holder.musicCount.setText(musicCountList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return this.musicNameList.size();
    }
}
