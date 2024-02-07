package com.example.cardibalgameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class duckgame extends AppCompatActivity implements Runnable {

    private Handler handler;
    private ImageView duck;
    private int screenWidth;
    private int screenHeight;
    private Random random;
    private int duckSpeedX = 10; // Adjust the speed of duck's movement
    private int duckSpeedY = 10; // Adjust the speed of duck's movement
    private static final int FRAME_RATE = 30; // Adjust the frame rate for smoother animation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duckgame);

        duck = findViewById(R.id.duck1); // Assuming duck1 is the ID of your ImageView

        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        // Initialize random number generator
        random = new Random();

        // Initialize handler and start updating duck position
        handler = new Handler();
        handler.post(this);
    }

    @Override
    public void run() {
        // Update duck position
        updateDuckPosition();

        // Delayed action to continue updating position periodically
        handler.postDelayed(this, 1000 / FRAME_RATE); // Update at specified frame rate
    }

    private void updateDuckPosition() {
        // Calculate new position based on current position and speed
        float newX = duck.getX() + duckSpeedX;
        float newY = duck.getY() + duckSpeedY;

        // Reverse direction if the duck reaches the edge of the screen
        if (newX <= 0) {
            duckSpeedX = Math.abs(duckSpeedX); // Change direction to move right
            duck.setImageResource(R.drawable.rubberduck1); // Change image to rubberduck1
        } else if (newX >= screenWidth - duck.getWidth()) {
            duckSpeedX = -Math.abs(duckSpeedX); // Change direction to move left
            duck.setImageResource(R.drawable.rubberduck2); // Change image to rubberduck2
        }
        if (newY <= 0 || newY >= screenHeight - duck.getHeight()) {
            duckSpeedY = -duckSpeedY; // Reverse vertical direction if hitting top or bottom
        }

        // Set new position for the duck with interpolation for smoother animation
        duck.animate().x(newX).y(newY).setDuration(1000 / FRAME_RATE).start();
    }
    public void BackToMain(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
    public void duckclicked(View view) {
        // Get the ID of the clicked duck
        int clickedDuckId = view.getId();

        // Generate a random number between 1 and 9 (inclusive)
        int randomImageIndex = random.nextInt(9) + 1;

        // Form the resource ID for the randomly chosen duck image
        int resourceId = getResources().getIdentifier("duck" + randomImageIndex, "drawable", getPackageName());

        // Find the ImageView with ID equivalent and set its image resource
        ImageView equivalentImageView = findViewById(R.id.equivalent);
        equivalentImageView.setImageResource(resourceId);

        // Make the equivalent image visible
        equivalentImageView.setVisibility(View.VISIBLE);
    }
}
