package com.openclassrooms.entrevoisins;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

public class FocusNeighbourActivity extends AppCompatActivity {

	ImageView favorites;
	ImageView avatarUser;
	ImageButton comeBackButton;
	TextView nameUserAvatar;

	CardView cardView1;
	TextView nameUserContainer;
	ImageView mapIcon;
	TextView addressUser;
	ImageView phoneIcon;
	TextView phoneUser;
	ImageView fluxIcon;
	TextView fluxUser;

	CardView cardView2;
	TextView infoUser;
	TextView descriptionUser;

	private NeighbourApiService mApiService;
	private Neighbour neighbour;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_focus_neighbour);
		mApiService = DI.getNeighbourApiService();

		favorites = findViewById(R.id.favorites);
		avatarUser = findViewById(R.id.avatarUser);
		comeBackButton = findViewById(R.id.comeBackButton);
		nameUserAvatar = findViewById(R.id.nameUserAvatar);

		cardView1 = findViewById(R.id.cardView1);
		nameUserContainer = findViewById(R.id.nameUserContainer);
		mapIcon = findViewById(R.id.mapIcon);
		addressUser = findViewById(R.id.addressUser);
		phoneIcon = findViewById(R.id.phoneIcon);
		phoneUser = findViewById(R.id.phoneUser);
		fluxIcon = findViewById(R.id.fluxIcon);
		fluxUser = findViewById(R.id.fluxUser);

		cardView2 = findViewById(R.id.cardView2);
		infoUser = findViewById(R.id.infoUser);
		descriptionUser = findViewById(R.id.descriptionUser);

		neighbour = (Neighbour) getIntent().getSerializableExtra("neighbour");

		//  Event back

		comeBackButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		//  Avatar screen

		nameUserAvatar.setText(neighbour.getName());
		Glide.with(this).load(neighbour.getAvatarUrl()).into(avatarUser);

		// Favorites icon

		ImageView imageFavorites;

		imageFavorites = findViewById(R.id.favorites);
		if(isFavorite()) {
			imageFavorites.setImageResource(R.drawable.star_add_favorites);
		} else {
			imageFavorites.setImageResource(R.drawable.star_no_favorites);
		}

		// CardView1

		nameUserContainer.setText(neighbour.getName());
		addressUser.setText(neighbour.getAddress());
		phoneUser.setText(neighbour.getPhoneNumber());
		fluxUser.setText(neighbour.getAddress());

		// CardView2

		descriptionUser.setText(neighbour.getAboutMe());


		favorites.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(isFavorite() == false) {
					mApiService.createFavoritesNeighbours(neighbour);
				} else {
					mApiService.deleteFavoritesNeighbours(neighbour);
				}
				if(isFavorite()) {
					imageFavorites.setImageResource(R.drawable.star_add_favorites);
				} else {
					imageFavorites.setImageResource(R.drawable.star_no_favorites);
				}
			}
		});

	}
	private boolean isFavorite() {
		return mApiService.inspectFavoritesNeighbours(neighbour);
	}
}