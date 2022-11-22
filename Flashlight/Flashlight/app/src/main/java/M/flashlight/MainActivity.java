package M.flashlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Build;
import androidx.core.content.FileProvider;
import java.io.File;
import android.view.View;
import android.graphics.Typeface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class MainActivity extends  AppCompatActivity  { 
	
	public final int REQ_CD_CAMERA = 101;
	
	private boolean flashcode = false;
	private boolean flashlight = false;
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear_background;
	private LinearLayout linear_switch;
	private TextView textview_instruction;
	private TextView textview1;
	
	private AlertDialog.Builder dialog;
	private Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	private File _file_camera;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear_background = (LinearLayout) findViewById(R.id.linear_background);
		linear_switch = (LinearLayout) findViewById(R.id.linear_switch);
		textview_instruction = (TextView) findViewById(R.id.textview_instruction);
		textview1 = (TextView) findViewById(R.id.textview1);
		dialog = new AlertDialog.Builder(this);
		_file_camera = FileUtil.createNewPictureFile(getApplicationContext());
		Uri _uri_camera = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			_uri_camera= FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_camera);
		}
		else {
			_uri_camera = Uri.fromFile(_file_camera);
		}
		camera.putExtra(MediaStore.EXTRA_OUTPUT, _uri_camera);
		camera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		
		linear_switch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (flashcode) {
					if (flashlight) {
						_off();
					}
					else {
						_on();
					}
				}
			}
		});
	}
	
	private void initializeLogic() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			Window w =MainActivity.this.getWindow();
			w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.TRANSPARENT);
		}
		_Ui_Design();
		flashcode = getPackageManager(). hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		dialog.setTitle("EXIT!");
		dialog.setMessage("Are you want to exit...?");
		dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				finishAffinity();
			}
		});
		dialog.setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		dialog.create().show();
	}
	public void _Ui_Design () {
		linear_background.setElevation((float)3);
		_one_side_radius(200, 200, 200, 200, "#E0E0E0", linear_background);
		_one_side_radius(200, 200, 200, 200, "#FFFFFF", linear_switch);
		textview_instruction.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manrope_medium.ttf"), 0);
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manrope_extrabold.ttf"), 0);
	}
	
	
	public void _one_side_radius (final double _one, final double _two, final double _three, final double _four, final String _color, final View _view) {
		Double left_top = _one;
		Double right_top = _two;
		Double left_bottom = _three;
		Double right_bottom = _four;
		
		
		android.graphics.drawable.GradientDrawable c = new android.graphics.drawable.GradientDrawable();
		
		
		c.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		
		c.setCornerRadii(new float[] {left_top.floatValue(),left_top.floatValue(), right_top.floatValue(),right_top.floatValue(), left_bottom.floatValue(),left_bottom.floatValue(), right_bottom.floatValue(),right_bottom.floatValue()});
		
		c.setColor(Color.parseColor(_color));
		
		_view.setBackground(c);
	}
	
	
	public void _on () {
		_one_side_radius(200, 200, 200, 200, "#00E676", linear_background);
		textview_instruction.setText("OFF");
		android.hardware.camera2.CameraManager cameraManager = (android.hardware.camera2.CameraManager) getSystemService(Context.CAMERA_SERVICE);
				try {
						String cameraId = cameraManager.getCameraIdList()[0]; cameraManager.setTorchMode(cameraId, true);
						flashlight = true; } catch (android.hardware.camera2.CameraAccessException e) { }
	}
	
	
	public void _off () {
		_one_side_radius(200, 200, 200, 200, "#E0E0E0", linear_background);
		textview_instruction.setText("ON");
		android.hardware.camera2.CameraManager cameraManager = (android.hardware.camera2.CameraManager) getSystemService(Context.CAMERA_SERVICE);
				try {
						String cameraId = cameraManager.getCameraIdList()[0]; cameraManager.setTorchMode(cameraId, false);
						flashlight = false; } catch (android.hardware.camera2.CameraAccessException e) { }
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}