package net.thesis.vglibvol;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.NoteHolder>  {

    private List<Wishlist_Item> wishlist_items = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist_item, parent, false);
        return new NoteHolder(itemView);
    }

    public void setWishlist_items (List<Wishlist_Item> wishlist_items){
        this.wishlist_items=wishlist_items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return wishlist_items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Wishlist_Item currentwishlist = wishlist_items.get(position);
        holder.textViewTitle.setText(currentwishlist.getTitle());
        holder.textViewDescription.setText("Genre:\n"+currentwishlist.getGenres());


        String test = currentwishlist.getGuid();


        File f = new File("/data/user/0/net.thesis.vglibvol/app_imageDir/"+test+".png");

        Picasso.get().load(f).error(R.drawable.image_loading).into(holder.wishlistgameimage);

    }

    public Wishlist_Item getwishlistAt(int position) {

        return wishlist_items.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private ImageView wishlistgameimage;


        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_wishlist_title);
            textViewDescription = itemView.findViewById(R.id.text_view_wishlist_genres);
            wishlistgameimage = itemView.findViewById(R.id.wishlist_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(wishlist_items.get(position));
                    }
                }
            });


        }


    }

    public interface OnItemClickListener {
        void onItemClick(Wishlist_Item wishlist_item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void filterList(List<Wishlist_Item> filteredList) {
        wishlist_items = filteredList;
        notifyDataSetChanged();
    }


}