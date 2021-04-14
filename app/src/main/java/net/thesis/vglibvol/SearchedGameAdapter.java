package net.thesis.vglibvol;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SearchedGameAdapter extends RecyclerView.Adapter<SearchedGameAdapter.SearchedGameViewHolder> {
    private Context mSearchContext;
    private ArrayList<SearchedItem> mSearchedGameList;
    private OnItemClickListerner mListener;

//adapter parameters for onclick action of searched items
    public interface OnItemClickListerner {
        void onItemClick(int position);
    }

    public void setOnItemClickListener (OnItemClickListerner listener) {
        mListener = listener;
    }
//end of adapter parameters for onclick action of searched items

    public SearchedGameAdapter(Context searchcontext, ArrayList<SearchedItem> SearchedGameList) {
        mSearchContext = searchcontext;
        mSearchedGameList = SearchedGameList;
    }

    @Override
    public SearchedGameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mSearchContext).inflate(R.layout.searched_game, parent, false);
        return new SearchedGameViewHolder(v);
    }



    @Override
    public void onBindViewHolder(SearchedGameViewHolder holder, int position) {
        SearchedItem currentItem = mSearchedGameList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String Name = currentItem.getCreator();
        String platforms = currentItem.getNameCount();

        holder.mTextViewCreator.setText(Name);
        holder.mTextViewName.setText("Platforms: " + platforms);
        Picasso.get().load(String.valueOf(imageUrl)).error(R.drawable.image_loading).fit().centerInside().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(holder.mImageView);
    }




    @Override
    public int getItemCount() {
        return mSearchedGameList.size();
    }

    public class SearchedGameViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewName;

        public SearchedGameViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mTextViewName = itemView.findViewById(R.id.text_view_Name);

            //get position
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}