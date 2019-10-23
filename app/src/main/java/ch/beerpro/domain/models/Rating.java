package ch.beerpro.domain.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.Map;

public class Rating implements Entity {
    public static final String COLLECTION = "ratings";
    public static final String FIELD_BEER_ID = "beerId";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_LIKES = "likes";
    public static final String FIELD_CREATION_DATE = "creationDate";

    @Exclude
    private String id;
    private String beerId;
    private String beerName;
    private String userId;
    private String userName;
    private String userPhoto;
    private String photo;
    private float rating;
    private String comment;
    private String place;
    private String flavour;
    private float colourRating;
    private float designRating;

    /**
     * We use a Map instead of an Array to be able to query it.
     *
     * @see <a href="https://firebase.google.com/docs/firestore/solutions/arrays#solution_a_map_of_values"/>
     */
    private Map<String, Boolean> likes;
    private Date creationDate;

    public Rating(String id, String beerId, String beerName, String userId, String userName, String userPhoto, String photo, float rating, String comment, String place, String flavour, float design, float colour, Map<String, Boolean> likes, Date creationDate) {
        this.id = id;
        this.beerId = beerId;
        this.beerName = beerName;
        this.userId = userId;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.photo = photo;
        this.rating = rating;
        this.comment = comment;
        /*Advanced Rating*/
        this.place = place;
        this.flavour = flavour;
        this.designRating = design;
        this.colourRating = colour;
        /*---*/
        this.likes = likes;
        this.creationDate = creationDate;
    }

    public Rating() {
    }

    public String getId() {
        return this.id;
    }

    public String getBeerId() {
        return this.beerId;
    }

    public String getBeerName() {
        return this.beerName;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserPhoto() {
        return this.userPhoto;
    }

    public String getPhoto() {
        return this.photo;
    }

    public float getRating() {
        return this.rating;
    }

    public String getComment() {
        return this.comment;
    }
    /* Advanced Rating*/
    public String getPlace() {
        return this.place;
    }

    public String getFlavour() {
        return this.flavour;
    }

    public float getDesignRating() {
        return this.designRating;
    }

    public float getColourRating() {
        return this.colourRating;
    }
    /*---*/

    public Map<String, Boolean> getLikes() {
        return this.likes;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBeerId(String beerId) {
        this.beerId = beerId;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    /*Advanced Rating*/
    public void setPlace(String place) {
        this.place = place;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public void setColourRating(float rating) {
        this.colourRating = rating;
    }

    public void setDesignRating(float rating) {
        this.designRating = rating;
    }
    /*---*/

    public void setLikes(Map<String, Boolean> likes) {
        this.likes = likes;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Rating)) return false;
        final Rating other = (Rating) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$beerId = this.getBeerId();
        final Object other$beerId = other.getBeerId();
        if (this$beerId == null ? other$beerId != null : !this$beerId.equals(other$beerId))
            return false;
        final Object this$beerName = this.getBeerName();
        final Object other$beerName = other.getBeerName();
        if (this$beerName == null ? other$beerName != null : !this$beerName.equals(other$beerName))
            return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId))
            return false;
        final Object this$userName = this.getUserName();
        final Object other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName))
            return false;
        final Object this$userPhoto = this.getUserPhoto();
        final Object other$userPhoto = other.getUserPhoto();
        if (this$userPhoto == null ? other$userPhoto != null : !this$userPhoto.equals(other$userPhoto))
            return false;
        final Object this$photo = this.getPhoto();
        final Object other$photo = other.getPhoto();
        if (this$photo == null ? other$photo != null : !this$photo.equals(other$photo))
            return false;
        if (Float.compare(this.getRating(), other.getRating()) != 0) return false;
        final Object this$comment = this.getComment();
        final Object other$comment = other.getComment();
        if (this$comment == null ? other$comment != null : !this$comment.equals(other$comment))
            return false;
        /*Advanced Rating*/
        final Object this$place = this.getPlace();
        final Object other$place = other.getPlace();
        if (this$place == null ? other$place != null : !this$place.equals(other$place))
            return false;
        final Object this$flavour = this.getFlavour();
        final Object other$flavour = other.getFlavour();
        if (this$flavour == null ? other$flavour != null : !this$flavour.equals(other$flavour))
            return false;
        final Object this$designRating = this.getDesignRating();
        final Object other$designRating = other.getDesignRating();
        if (this$designRating == null ? other$designRating != null : !this$designRating.equals(other$designRating))
            return false;
        final Object this$colourRating = this.getColourRating();
        final Object other$colourRating = other.getColourRating();
        if (this$colourRating == null ? other$colourRating != null : !this$colourRating.equals(other$colourRating))
            return false;
        /*---*/
        final Object this$likes = this.getLikes();
        final Object other$likes = other.getLikes();
        if (this$likes == null ? other$likes != null : !this$likes.equals(other$likes))
            return false;
        final Object this$creationDate = this.getCreationDate();
        final Object other$creationDate = other.getCreationDate();
        return this$creationDate == null ? other$creationDate == null : this$creationDate.equals(other$creationDate);
    }

    private boolean canEqual(final Object other) {
        return other instanceof Rating;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $beerId = this.getBeerId();
        result = result * PRIME + ($beerId == null ? 43 : $beerId.hashCode());
        final Object $beerName = this.getBeerName();
        result = result * PRIME + ($beerName == null ? 43 : $beerName.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $userName = this.getUserName();
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $userPhoto = this.getUserPhoto();
        result = result * PRIME + ($userPhoto == null ? 43 : $userPhoto.hashCode());
        final Object $photo = this.getPhoto();
        result = result * PRIME + ($photo == null ? 43 : $photo.hashCode());
        result = result * PRIME + Float.floatToIntBits(this.getRating());
        final Object $comment = this.getComment();
        result = result * PRIME + ($comment == null ? 43 : $comment.hashCode());
        /*Advanced Rating*/
        final Object $place = this.getPlace();
        result = result * PRIME + ($place == null ? 43 : $place.hashCode());
        final Object $designRating = this.getDesignRating();
        result = result * PRIME + ($designRating == null ? 43 : $designRating.hashCode());
        final Object $colourRating = this.getColourRating();
        result = result * PRIME + ($colourRating == null ? 43 : $colourRating.hashCode());
        final Object $flavour = this.getFlavour();
        result = result * PRIME + ($flavour == null ? 43 : $flavour.hashCode());
        /*---*/
        final Object $likes = this.getLikes();
        result = result * PRIME + ($likes == null ? 43 : $likes.hashCode());
        final Object $creationDate = this.getCreationDate();
        result = result * PRIME + ($creationDate == null ? 43 : $creationDate.hashCode());
        return result;
    }

    @NonNull
    public String toString() {
        return "Rating(id=" + this.getId() + ", beerId=" + this.getBeerId() + ", beerName=" + this.getBeerName() + ", userId=" + this.getUserId() + ", userName=" + this.getUserName() + ", userPhoto=" + this.getUserPhoto() + ", photo=" + this.getPhoto() + ", rating=" + this.getRating() +", design rating=" + this.getDesignRating() + ", colour rating=" + this.getColourRating() + ", flavours ="+ this.getFlavour()+", comment=" + this.getComment() + ", place=" + this.getPlace() + ", likes=" + this.getLikes() + ", creationDate=" + this.getCreationDate() + ")";
    }
}
