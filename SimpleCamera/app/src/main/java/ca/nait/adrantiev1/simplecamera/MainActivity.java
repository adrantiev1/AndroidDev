package ca.nait.adrantiev1.simplecamera;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    public static String filePath;
    public static String fileName;
    Bitmap bitmap;

    private static final int FIND_IMAGE = 1;
    private static final int TAKE_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = (ImageView) findViewById(R.id.image_view);
        filePath = Environment.getExternalStorageDirectory() + "/images/";
        fileName = "example.jpg";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (resultCode == Activity.RESULT_OK && requestCode == TAKE_IMAGE) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4; //represents the denominator in the fraction of the size

            String finalFilename = filePath + fileName;
            Bitmap bitmap = BitmapFactory.decodeFile(finalFilename, options);
            imageView.setImageBitmap(bitmap);
        } else if (resultCode == Activity.RESULT_OK && requestCode == FIND_IMAGE) {
            try {
                if (bitmap != null) {
                    bitmap.recycle();
                }else{
                    InputStream stream = getContentResolver().openInputStream(intent.getData());
                    bitmap = BitmapFactory.decodeStream(stream);
                    stream.close();
                    imageView.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error" + e, Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public void OnButtonClick(View view) {
        switch (view.getId()) {
            case R.id.button_find_image: {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, FIND_IMAGE);
                break;
            }
            case R.id.button_start_camera: {
                File file = new File(fileName + fileName);
                Uri fileUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, TAKE_IMAGE);
                break;
            }
            case R.id.button_set_wallpaper: {
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                WallpaperManager manager = WallpaperManager.getInstance(this);
                try {
                    manager.setBitmap(bitmap);
                    Toast.makeText(this, "Loaded wallpaper ", Toast.LENGTH_LONG).show();

                } catch (IOException ieo) {
                    Toast.makeText(this, "Error loading wallpaper " + ieo, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }


}
