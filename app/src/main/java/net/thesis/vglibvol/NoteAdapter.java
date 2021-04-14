package net.thesis.vglibvol;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_item, parent, false);
        return new NoteHolder(itemView);
    }


    public void setNotes (List<Note> notes){
        this.notes=notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText("Genre:\n"+currentNote.getGenres());

        String test = currentNote.getGuid();


        File f = new File("/data/user/0/net.thesis.vglibvol/app_imageDir/"+test+".png");

        Picasso.get().load(f).error(R.drawable.image_loading).into(holder.collectiongameimage);

    }

    public Note getNoteAt(int position) {

        return notes.get(position);

    }


    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private ImageView collectiongameimage;


        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_sgame_title);
            textViewDescription = itemView.findViewById(R.id.text_view_sgame_genres);
            collectiongameimage = itemView.findViewById(R.id.collection_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });


        }

    }




    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void filterList(List<Note> filteredList) {
        notes = filteredList;
        notifyDataSetChanged();
    }







    /*correct adapter backup


    private OnItemClickListener listener;

    public NoteAdapter() {


        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(Note oldItem, Note newItem) {
            return oldItem.getTitle() == newItem.getTitle();
        }

        @Override
        public boolean areContentsTheSame(Note oldItem, Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getGenres().equals(newItem.getGenres());
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getGenres());

        String test = currentNote.getGuid();


        File f = new File("/data/user/0/net.thesis.vglibvol/app_imageDir/"+test+".png");

        Picasso.get().load(f).into(holder.collectiongameimage);


        //Note cnote = mNotes.get(position); //test
      //  holder.textViewTitle.setText(cnote.getTitle());
       // holder.textViewDescription.setText(cnote.getGenres());

    }

    public Note getNoteAt(int position) {

        return getItem(position);

    }


    //@Override
    //public int getItemCount() {
      //  return mNotes.size();
   //}


   // public void setmNotes(List<Note> mNotes) {
     //   this.mNotes = mNotes;
       // notifyDataSetChanged();
    //}


    //public void filterList()


    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private ImageView collectiongameimage;


        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_sgame_title);
            textViewDescription = itemView.findViewById(R.id.text_view_sgame_genres);
            collectiongameimage = itemView.findViewById(R.id.collection_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });


        }

    }




    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }





     */




}
