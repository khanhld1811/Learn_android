package com.duykhanh.recordingappdemo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duykhanh.recordingappdemo.R;
import com.duykhanh.recordingappdemo.database.DBHelper;
import com.duykhanh.recordingappdemo.fragments.PlaybackFragment;
import com.duykhanh.recordingappdemo.listeners.OnDatabaseChangedListener;
import com.duykhanh.recordingappdemo.model.RecordingItem;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FileViewerAdapter extends RecyclerView.Adapter<FileViewerAdapter.RecordingsViewHolder>
    implements OnDatabaseChangedListener {

    private static final String LOG_TAG = "FileViewerAdapter";

    private DBHelper mDatabase;

    RecordingItem item;
    Context mContext;
    LinearLayoutManager llm;

    public FileViewerAdapter(Context context, LinearLayoutManager linearLayoutManager) {
        mContext = context;
        mDatabase = new DBHelper(mContext);
        mDatabase.setOnDatabaseChangedListener(this);
        llm = linearLayoutManager;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecordingsViewHolder holder, int position) {

        item = getItem(position);
        long itemDuration = item.getLength();

        long minutes = TimeUnit.MICROSECONDS.toMinutes(itemDuration);
        long seconds = TimeUnit.MICROSECONDS.toSeconds(itemDuration)
                - TimeUnit.MINUTES.toSeconds(minutes);

        holder.tvName.setText(item.getName());
        holder.tvLength.setText(String.format("%02d:%02d",minutes,seconds));
        holder.tvDateAdded.setText(
                DateUtils.formatDateTime(
                        mContext,
                        item.getTime(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR
                )
        );

        //define an on click listener to open PlaybackFragment
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlaybackFragment playbackFragment = new PlaybackFragment().newInstace(getItem(holder.getPosition()));

                    FragmentTransaction transaction = ((FragmentActivity) mContext)
                            .getSupportFragmentManager()
                            .beginTransaction();
                    playbackFragment.show(transaction, "dialog_playback");
                }
                catch (Exception e){
                    Log.e(LOG_TAG, "execaption: ",e );
                }
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ArrayList<String> entrys = new ArrayList<>();
                entrys.add(mContext.getString(R.string.dialog_file_share));
                entrys.add(mContext.getString(R.string.dialog_file_rename));
                entrys.add(mContext.getString(R.string.dialog_file_delete));

                final CharSequence[] items = entrys.toArray(new CharSequence[entrys.size()]);

                //File delete confirm
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(mContext.getString(R.string.dialog_title_options));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if(item == 0){
                            shareFileDialog(holder.getPosition());
                        }
                        // fix
                        if(item == 1){
                            renameFileDialog(holder.getPosition());
                        }
                        else if(item == 2){
                            deleteFileDialog(holder.getPosition());
                        }
                    }
                });
                builder.setCancelable(true);
                builder.setNegativeButton(mContext.getString(R.string.dialog_action_cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

                return false;
            }
        });
    }

    private void shareFileDialog(int position) {
    }

    @Override
    public int getItemCount() {
        return mDatabase.getCount();
    }

    @Override
    public void onNewDatabaseEntryAdded() {
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public void onDatabaseEntryRenamed() {

    }

    @NonNull
    @Override
    public RecordingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_view,parent,false);

        mContext = parent.getContext();

        return new RecordingsViewHolder(itemView);
    }
    public class RecordingsViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvName;
        protected TextView tvLength;
        protected TextView tvDateAdded;
        protected View cardView;

        public RecordingsViewHolder(@NonNull View v) {
            super(v);
            tvName = v.findViewById(R.id.file_name_text);
            tvLength = v.findViewById(R.id.file_date_added_text);
            tvDateAdded = v.findViewById(R.id.file_date_added_text);
            cardView = v.findViewById(R.id.card_view);
        }
    }

    public RecordingItem getItem(int position){
        return mDatabase.getItemAt(position);
    }

    public void remove(int position){
        //remove item from database, recyclerview and storage

        //delete file from storage
        File file = new File(getItem(position).getFilePath());
        file.delete();

        Toast.makeText(mContext, String.format(mContext.getString(R.string.toast_file_delete),getItem(position).getName()), Toast.LENGTH_SHORT).show();

        mDatabase.removeItemWithId(getItem(position).getId());
        notifyItemRemoved(position);
    }

    public void removeOutOfApp(String filePath){
        //user deletes a saved recording oout of the application through another application
    }

    public void rename(int position, String name){
        //rename a file

        String mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilePath += "/SoundRecorder/" + name;
        File f = new File(mFilePath);

        if(f.exists() && !f.isDirectory()){
            //file name is not unique, cannot rename file.
            Toast.makeText(mContext, String.format(mContext.getString(R.string.toast_file_exists),name), Toast.LENGTH_SHORT).show();

        }else{
            //file name is unique, rename file
            File oldFilePath = new File(getItem(position).getFilePath());
            oldFilePath.renameTo(f);
            mDatabase.renameItem(getItem(position),name,mFilePath);
            notifyItemChanged(position);
        }
    }

    public void sahreFileDialog(int position){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(getItem(position).getFilePath())));
        shareIntent.setType("audio/mp4");
        mContext.startActivity(Intent.createChooser(shareIntent,mContext.getText(R.string.send_to)));
    }

    public void renameFileDialog (final int position){
        //File rename dialog
        AlertDialog.Builder renameFileBuilder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_rename_file,null);

        final EditText input = view.findViewById(R.id.new_name);

        renameFileBuilder.setTitle(mContext.getString(R.string.dialog_title_rename));
        renameFileBuilder.setCancelable(true);
        renameFileBuilder.setPositiveButton(mContext.getString(R.string.dialog_action_ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            String value = input.getText().toString().trim() + ".mp4";
                            rename(position, value);
                        }catch (Exception e){
                            Log.e(LOG_TAG, "exeption",e );
                        }
                        dialog.cancel();
                    }
                });

        renameFileBuilder.setNegativeButton(mContext.getString(R.string.dialog_action_cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                             dialog.cancel();
                    }
                });

        renameFileBuilder.setView(view);
        AlertDialog alert = renameFileBuilder.create();
        alert.show();
    }

    public void deleteFileDialog (final int position){
        // File delete confirm
        AlertDialog.Builder confirmDelete = new AlertDialog.Builder(mContext);
        confirmDelete.setTitle(mContext.getString(R.string.dialog_title_delete));
        confirmDelete.setMessage(mContext.getString(R.string.dialog_text_delete));
        confirmDelete.setCancelable(true);
        confirmDelete.setPositiveButton(mContext.getString(R.string.dialog_action_yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try{
                            //remove item from database, recyclerview, and storage
                            remove(position);
                        }
                        catch(Exception e){
                            Log.e(LOG_TAG, "onClick: ",e );
                        }
                        dialog.cancel();
                    }
                });

        confirmDelete.setNegativeButton(mContext.getString(R.string.dialog_action_no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = confirmDelete.create();
        alert.show();
    }

}
