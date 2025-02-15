package ch.beerpro.presentation.details.createrating;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yalantis.ucrop.UCrop;


import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.GlideApp;
import ch.beerpro.R;
import ch.beerpro.domain.models.Beer;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

import static ch.beerpro.GoogleMapsKey.API_KEY_GOOGLE_MAPS;

public class CreateRatingActivity extends AppCompatActivity {

    public static final String ITEM = "item";
    public static final String RATING = "rating";
    private static final String TAG = "CreateRatingActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.addRatingBar)
    RatingBar addRatingBar;

    @BindView(R.id.photo)
    ImageView photo;

    @BindView(R.id.avatar)
    ImageView avatar;

    @BindView(R.id.ratingText)
    EditText ratingText;

    @BindView(R.id.photoExplanation)
    TextView photoExplanation;

    private CreateRatingViewModel model;

    /*google places*/
    @BindView(R.id.placeText)
            TextView placeText;

    int AUTOCOMPLETE_REQUEST_CODE = 1;
    /* ---- */
    /*advanced Rating*/
    @BindView(R.id.colorFrothRating)
    RatingBar colorFrothRating;

    @BindView(R.id.designRating)
    RatingBar designRating;

    @BindView(R.id.banana)
    CheckBox banana;

    @BindView(R.id.lemon)
    CheckBox lemon;

    @BindView(R.id.toast)
    CheckBox toast;

    @BindView(R.id.bred)
    CheckBox bred;

    @BindView(R.id.pear)
    CheckBox pear;

    @BindView(R.id.melon)
    CheckBox melon;

    @BindView(R.id.grass)
    CheckBox grass;

    @BindView(R.id.malt)
    CheckBox malt;

    @BindView(R.id.hazelnut)
    CheckBox hazelnut;

    @BindView(R.id.hay)
    CheckBox hay;

    @BindView(R.id.butter)
    CheckBox butter;

    @BindView(R.id.apple)
    CheckBox apple;
    /*---*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);
        Nammu.init(this);

        /* Google Places*/
        Button placeButton = findViewById(R.id.button);

        placeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String apiKey = API_KEY_GOOGLE_MAPS;

                Places.initialize(getApplicationContext(), apiKey);

                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        //Set LocationBias to coordinates of Switzerland
                       .setLocationBias((RectangularBounds.newInstance(
                                new LatLng(46.04414, 6.14675),
                                new LatLng(47.67131, 9.82598))))
                        .build(CreateRatingActivity.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });
        /*---*/

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_activity_rating));

        Beer item = (Beer) getIntent().getExtras().getSerializable(ITEM);
        float rating = getIntent().getExtras().getFloat(RATING);

        model = ViewModelProviders.of(this).get(CreateRatingViewModel.class);
        model.setItem(item);

        addRatingBar.setRating(rating);

        int permissionCheck =
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Nammu.askForPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                }

                @Override
                public void permissionRefused() {
                }
            });
        }

        EasyImage.configuration(this).setImagesFolderName("BeerPro");

        photo.setOnClickListener(view -> {
            EasyImage.openChooserWithDocuments(CreateRatingActivity.this, "", 0);
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            Uri photoUrl = user.getPhotoUrl();
            GlideApp.with(this).load(photoUrl).apply(new RequestOptions().circleCrop()).into(avatar);
        }
        if (model.getPhoto() != null) {
            photo.setImageURI(model.getPhoto());
            photoExplanation.setText(null);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*Google Places*/
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                placeText.setText(place.getName());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        /*---*/

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                Log.i("CreateRatingActivity", imageFiles.toString());

                UCrop.Options options = new UCrop.Options() {
                    {
                        setToolbarTitle("Foto zuschneiden");
                        setToolbarColor(getResources().getColor(R.color.colorPrimary));
                        setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                        setActiveWidgetColor(getResources().getColor(R.color.colorAccent));
                        setCropFrameColor(getResources().getColor(R.color.colorAccent));
                        setCropGridColor(getResources().getColor(R.color.colorAccent));
                        setDimmedLayerColor(getResources().getColor(R.color.windowBackgroundColor));
                        setHideBottomControls(true);
                    }
                };
                // TODO store the image name in the viewmodel or instance state!
                UCrop.of(Uri.fromFile(imageFiles.get(0)),
                        Uri.fromFile(new File(getCacheDir(), "image_" + UUID.randomUUID().toString())))
                        .withAspectRatio(1, 1).withMaxResultSize(1024, 1024).withOptions(options)
                        .start(CreateRatingActivity.this);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA_IMAGE) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(CreateRatingActivity.this);
                    if (photoFile != null)
                        photoFile.delete();
                }
            }
        });

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            handleCropResult(data);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            model.setPhoto(resultUri);
            photo.setImageURI(resultUri);
            photoExplanation.setText(null);
        }
    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_rating_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                saveRating();
                return true;
            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                    onBackPressed();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveRating() {
        float rating = addRatingBar.getRating();
        /*Advanced Rating*/
        float colour = colorFrothRating.getRating();
        float design = designRating.getRating();
        String flavour = addFlavours();
        /*---*/
        String comment = ratingText.getText().toString();
        /* Google Places */
        String place = placeText.getText().toString();
        /* --- */
        // TODO show a spinner!
        // TODO return the new rating to update the new average immediately
        model.saveRating(model.getItem(), rating, comment, model.getPhoto(), place, flavour, design, colour)
                .addOnSuccessListener(task -> onBackPressed())
                .addOnFailureListener(error -> Log.e(TAG, "Could not save rating", error));


    }
    /*Advanced Rating*/
    private String addFlavours(){
        String result ="";
        if(lemon.isChecked()){
            result += lemon.getText() + ", ";
        }
        if(apple.isChecked()){
            result += apple.getText()  + ", ";
        }
        if(pear.isChecked()){
            result += pear.getText() + ", ";
        }
        if(melon.isChecked()){
            result += melon.getText() + ", ";
        }
        if(hay.isChecked()){
            result += hay.getText() + ", ";
        }
        if(bred.isChecked()){
            result += bred.getText() + ", ";
        }
        if(hazelnut.isChecked()){
            result += hazelnut.getText() + ", ";
        }
        if(malt.isChecked()){
            result += malt.getText() + ", ";
        }
        if(toast.isChecked()){
            result += toast.getText() + ", ";
        }
        if(banana.isChecked()){
            result += banana.getText() + ", ";
        }
        if(butter.isChecked()){
            result += butter.getText() + ", ";
        }
        if(grass.isChecked()){
            result += grass.getText() + ", ";
        }
        if(result != ""){
            result = result.substring(0,result.length()-2);
        }
        return result;
    }
    /*---*/
}
