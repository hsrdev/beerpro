package ch.beerpro.presentation.profile.myratings;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.text.DateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.GlideApp;
import ch.beerpro.R;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.domain.models.Wish;
import ch.beerpro.presentation.utils.EntityPairDiffItemCallback;

import static ch.beerpro.presentation.utils.DrawableHelpers.setDrawableTint;


public class MyRatingsRecyclerViewAdapter
        extends ListAdapter<Pair<Rating, Wish>, MyRatingsRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "WishlistRecyclerViewAda";

    private static final DiffUtil.ItemCallback<Pair<Rating, Wish>> DIFF_CALLBACK = new EntityPairDiffItemCallback<>();

    private final OnMyRatingItemInteractionListener listener;
    private FirebaseUser user;

    public MyRatingsRecyclerViewAdapter(OnMyRatingItemInteractionListener listener, FirebaseUser user) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_ratings_listentry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Pair<Rating, Wish> entry = getItem(position);
        holder.bind(entry.first, entry.second, listener);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.comment)
        TextView comment;

        @BindView(R.id.beerName)
        TextView beerName;

        @BindView(R.id.avatar)
        ImageView avatar;

        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        @BindView(R.id.authorName)
        TextView authorName;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.numLikes)
        TextView numLikes;

        @BindView(R.id.details)
        Button details;

        @BindView(R.id.wishlist)
        Button wishlist;

        @BindView(R.id.like)
        Button like;

        @BindView(R.id.photo)
        ImageView photo;
        /*Advanced Rating*/
        @BindView(R.id.placeText)
        TextView place;

        @BindView(R.id.flavourText)
        TextView flavourText;

        @BindView(R.id.flavourLabel)
        TextView flavourLabel;

        @BindView(R.id.ratingDesignBar)
        RatingBar designRating;

        @BindView(R.id.designRatingLabel)
        TextView designLabel;

        @BindView(R.id.ratingColourBar)
        RatingBar colourRating;

        @BindView(R.id.colourRatingLabel)
        TextView colourLabel;

        @BindView(R.id.parentLayout)
        ConstraintLayout parentLayout;
        /*---*/
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        void bind(Rating item, Wish wish, OnMyRatingItemInteractionListener listener) {
            beerName.setText(item.getBeerName());
            comment.setText(item.getComment());

            ratingBar.setNumStars(5);
            ratingBar.setRating(item.getRating());
            String formattedDate =
                    DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(item.getCreationDate());
            date.setText(formattedDate);

            if (item.getPhoto() != null) {
                // Take a look at https://bumptech.github.io/glide/int/recyclerview.html
                GlideApp.with(itemView).load(item.getPhoto()).into(photo);
                photo.setVisibility(View.VISIBLE);
            } else {
                GlideApp.with(itemView).clear(photo);
                photo.setVisibility(View.GONE);
            }

            authorName.setText(item.getUserName());
            GlideApp.with(itemView).load(item.getUserPhoto()).apply(new RequestOptions().circleCrop()).into(avatar);

            numLikes.setText(itemView.getResources().getString(R.string.fmt_num_ratings, item.getLikes().size()));

            /*Advanced Rating*/
            place.setText(item.getPlace());

            if(item.getFlavour()!="" && item.getFlavour()!=null){
                colourRating.setNumStars(5);
                colourRating.setRating(item.getColourRating());

                designRating.setNumStars(5);
                designRating.setRating(item.getDesignRating());

                flavourText.setText(item.getFlavour());
            } else {
                ConstraintLayout constraintLayout = parentLayout;
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(R.id.ratingColourBar,ConstraintSet.TOP,R.id.comment,ConstraintSet.BOTTOM,0);

                flavourText.setVisibility(View.GONE);
                flavourLabel.setVisibility(View.GONE);

                if(item.getColourRating()!= 0.0 || item.getDesignRating()!=0.0 ) {
                    colourRating.setNumStars(5);
                    colourRating.setRating(item.getColourRating());

                    designRating.setNumStars(5);
                    designRating.setRating(item.getDesignRating());
                } else{
                    constraintSet.connect(R.id.ratingBar,ConstraintSet.TOP,R.id.comment,ConstraintSet.BOTTOM,0);

                    colourRating.setVisibility(View.GONE);
                    colourLabel.setVisibility(View.GONE);
                    designRating.setVisibility(View.GONE);
                    designLabel.setVisibility(View.GONE);

                }
                /*---*/
            }


            // don't need it here
            like.setVisibility(View.GONE);

            if (wish != null) {
                int color = itemView.getResources().getColor(R.color.colorPrimary);
                setDrawableTint(wishlist, color);
            } else {
                int color = itemView.getResources().getColor(android.R.color.darker_gray);
                setDrawableTint(wishlist, color);
            }

            if (listener != null) {
                details.setOnClickListener(v -> listener.onMoreClickedListener(item));
                wishlist.setOnClickListener(v -> listener.onWishClickedListener(item));
            }
        }
    }
}
