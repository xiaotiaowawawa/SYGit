package com.example.sydemo.model.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sydemo.R;
import com.example.sydemo.model.dlfilemodel.DownFileInfo;
import com.example.sydemo.model.dlfilemodel.IFileInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInfoRVAdapter extends RecyclerView.Adapter {
    private List<IFileInfo> downFileInfos;
    private Map<Integer,ContentLoadingProgressBar> integerContentLoadingProgressBarMap;
    public FileInfoRVAdapter(List<IFileInfo> downFileInfos) {
        this.downFileInfos = downFileInfos;
        integerContentLoadingProgressBarMap=new HashMap<>();
    }
public onclick onclick;
 public void setOnclick(FileInfoRVAdapter.onclick onclick) {
        this.onclick = onclick;
    }

    public class FileInfoViewHolder extends RecyclerView.ViewHolder{
        public TextView file_name,download_digital;
        public Button file_downloadbn;
        public ContentLoadingProgressBar download_progress;
        public FileInfoViewHolder(@NonNull View itemView) {
        super(itemView);
            file_name=itemView.findViewById(R.id.file_name);
            download_digital=itemView.findViewById(R.id.download_digital);
            file_downloadbn=itemView.findViewById(R.id.file_downloadbn);
            download_progress=itemView.findViewById(R.id.download_progress);
        }
}
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       FileInfoViewHolder f =new FileInfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.downfile_list,parent,false));
    //    Log.i("msg1",f.toString());
        return f;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final FileInfoViewHolder fileInfoViewHolder=(FileInfoViewHolder)holder;
     //   Log.i("msg1","position"+position+"  "+fileInfoViewHolder.toString()+"layout position为"+ fileInfoViewHolder.getLayoutPosition());
        String name=downFileInfos.get(position).getFileName();
        SpannableString spannableString=new SpannableString("文件名:"+name);
        spannableString.setSpan(new StyleSpan(Color.parseColor("#D81B60")),4,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        fileInfoViewHolder.file_name.setText(spannableString);
        int progress=(int)(downFileInfos.get(position).getCurrentProcess()*1.0/downFileInfos.get(position).getTotalLength())*100;
        fileInfoViewHolder.download_progress.setProgress(progress);
        fileInfoViewHolder.download_progress.setTag(R.id.fileCode,downFileInfos.get(position).getCode());
        fileInfoViewHolder.download_progress.setTag(R.id.fileTotal,downFileInfos.get(position).getTotalLength());
        fileInfoViewHolder.download_progress.setTag(R.id.fileCurrent,downFileInfos.get(position).getCurrentProcess());
        fileInfoViewHolder.download_digital.setText("%"+progress);
        fileInfoViewHolder.file_downloadbn.setOnClickListener((View v)-> {
                if (onclick!=null){
                    onclick.onclick(downFileInfos.get(position),fileInfoViewHolder.download_progress);
                }
            }
        );
        if (!integerContentLoadingProgressBarMap.containsKey(downFileInfos.get(position).getCode())){
            integerContentLoadingProgressBarMap.put((int)downFileInfos.get(position).getCode(),fileInfoViewHolder.download_progress);
        }
    }

    public Map<Integer, ContentLoadingProgressBar> getIntegerContentLoadingProgressBarMap() {
        return integerContentLoadingProgressBarMap;
    }

    @Override
    public int getItemCount() {
        if (downFileInfos!=null)
        return downFileInfos.size();
        return 0;
    }

    public interface  onclick{
        public void onclick(IFileInfo downFileInfo,ContentLoadingProgressBar contentLoadingProgressBar);
    }
}
