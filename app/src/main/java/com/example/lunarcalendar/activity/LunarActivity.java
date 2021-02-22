package com.example.lunarcalendar.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.lunarcalendar.LunarCalendar;
import com.example.lunarcalendar.R;
import com.example.lunarcalendar.gestures.SimpleGestureFilter;
import com.example.lunarcalendar.glide.GlideApp;
import com.example.lunarcalendar.helper.DataBaseHelper;
import com.example.lunarcalendar.helper.LocationHelper;
import com.example.lunarcalendar.model.LunarDate;
import com.example.lunarcalendar.model.WeatherData;
import com.example.lunarcalendar.rest.WeatherAPIClient;
import com.example.lunarcalendar.rest.WeatherAPIInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LunarActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener, SimpleGestureFilter.SimpleGestureListener, LocationHelper.OnUpdateUIListener {

    private Context mContext;
    private ImageView imgView, row_weather_img;
    private BottomNavigationView bottomNavigationView;
    private Button month_year_btn;
    private TextView txtDay, txtDate, row_12_gioHD_txt, txt_label_h, txt_label_d, txt_label_mo, txt_label_gHD;;
    private TextView txtTietkhi, txtDanhngon, txtauth, row_weather_txt, row_tem_txt, row_hour_txt, row_lunar_day_txt, row_lunar_month_txt, tem_minmax_txt,
            row_12_month_txt, row_city, row_12_hour_txt, row_12_day_txt, row_12_year_txt;
    private View.OnClickListener mOnClickListener;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private FirebaseStorage storage;
    private String pToken = null;
    private ArrayList<Uri> paths;
    private ProgressBar mProgressBar;
    private int step_day = 0;
    private int step_danh_ngon = 0;
    private String action = "today";
    private Handler mHandler;
    private Animation fadeIn, fadeOut;
    private SimpleGestureFilter mSimpleGestureFilter;
    private String[] danh_ngon, danh_ngon_auth;
    private static final String PREF_NAME = "Lunar";
    private static final String PREF_SAVE_PTOKEN = "pToken";
    private static final String PREF_SAVE_STEP_IMG = "step";
    private static final String PREF_SAVE_STEP_DN = "step_danh_ngon";
    private LocationHelper mLocationHelper;
    private WeatherAPIInterface apiWeatherService;
    private WeatherData weatherDataCurent;
    private Drawable mPlaceholder;
    private DataBaseHelper mDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mContext = this;

        weatherDataCurent = new WeatherData();
        apiWeatherService = WeatherAPIClient.getClient().create(WeatherAPIInterface.class);
        mLocationHelper = new LocationHelper(this);
        mProgressBar = (ProgressBar)findViewById(R.id.loadingBar);
        imgView = (ImageView) findViewById(R.id.imgView);
        //row_weather_img = (ImageView) findViewById(R.id.row_weather_img);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDay = (TextView) findViewById(R.id.txtDay);
        txtTietkhi = (TextView) findViewById(R.id.txtTietkhi);
        txtDanhngon = (TextView) findViewById(R.id.txtDanhngon);
        row_weather_txt = (TextView) findViewById(R.id.row_weather_txt);
        row_tem_txt = (TextView) findViewById(R.id.row_tem_txt);
        row_hour_txt = (TextView) findViewById(R.id.row_hour_txt);
        row_lunar_day_txt = (TextView) findViewById(R.id.row_lunar_day_txt);
        row_lunar_month_txt = (TextView) findViewById(R.id.row_lunar_month_txt);
        tem_minmax_txt = (TextView) findViewById(R.id.tem_minmax_txt);
        row_12_month_txt = (TextView) findViewById(R.id.row_12_month_txt);
        row_city = (TextView) findViewById(R.id.row_city);
        row_12_hour_txt = (TextView) findViewById(R.id.row_12_hour_txt);
        row_12_day_txt = (TextView) findViewById(R.id.row_12_day_txt);
        row_12_year_txt = (TextView) findViewById(R.id.row_12_year_txt);
        txtauth = (TextView) findViewById(R.id.txtauth);
        txt_label_h = (TextView) findViewById(R.id.txt_label_h);
        txt_label_d = (TextView) findViewById(R.id.txt_label_d);
        txt_label_mo = (TextView) findViewById(R.id.txt_label_mo);
        txt_label_gHD = (TextView) findViewById(R.id.txt_label_gHD);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        month_year_btn = (Button) findViewById(R.id.month_year_btn);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        storage = FirebaseStorage.getInstance();
        paths = new ArrayList<Uri>();
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        row_12_gioHD_txt = (TextView)findViewById(R.id.row_12_gioHD_txt);
        mHandler = new Handler(getMainLooper());
        mSimpleGestureFilter = new SimpleGestureFilter(this, this);
        danh_ngon = getResources().getStringArray(R.array.danh_ngon);
        danh_ngon_auth = getResources().getStringArray(R.array.danh_ngon_auth);
        SharedPreferences mSharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        pToken = mSharedPreferences.getString(PREF_SAVE_PTOKEN, null);
        //step = mSharedPreferences.getInt(PREF_SAVE_STEP_IMG, 0);
        step_danh_ngon = mSharedPreferences.getInt(PREF_SAVE_STEP_DN, 0);
        mLocationHelper.init();
        row_12_gioHD_txt.setSelected(true);
    }

// 123
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.navigation_back_day:
                Toast.makeText(mContext, "Mai Tài Khang", Toast.LENGTH_LONG);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.navigation_back_day:
                listAllPaginated(pToken);
                action = "back";
                return true;
            case R.id.navigation_next_day:
                listAllPaginated(pToken);
                action = "next";
                return true;
            case R.id.navigation_home:
                listAllPaginated(pToken);
                action = "today";
                mDataBaseHelper = new DataBaseHelper(mContext, DataBaseHelper.DB_NAME_TUVI2021, DataBaseHelper.DB_TIVI2021_ASSET);
            case R.id.navigation_dashboard:
                Intent detailAct = new Intent(mContext, DetailActivity.class);
                startActivity(detailAct);
                break;
            default:
                break;
        }
        return false;
    }

    public void listAllPaginated(@Nullable String pageToken) {
        StorageReference listRef = storage.getReference().child("res");
        mProgressBar.setVisibility(View.VISIBLE);
        setNull2View();

        // Fetch the next page of results, using the pageToken if we have one.
        Task<ListResult> listPageTask = pageToken != null
                ? listRef.list(1, pageToken)
                : listRef.list(1);

        listPageTask
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        Task<Uri> uri = listResult.getItems().get(0).getDownloadUrl();
                        uri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                GlideApp.with(mContext).load(uri).transition(DrawableTransitionOptions.withCrossFade(1000))
                                        .listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                mProgressBar.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                mProgressBar.setVisibility(View.GONE);

                                                /*mHandler.post(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                    }
                                                });*/
                                                setCurrentWeather();
                                                return false;
                                            }
                                        })

                                        .into(imgView);
                                paths.add(uri);
                            }
                        });

                        if (listResult.getPageToken() != null) {
                            pToken = listResult.getPageToken();
                        } else {
                            pToken = null;
                        }

                        SharedPreferences mSharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                        mEditor.putString(PREF_SAVE_PTOKEN, pageToken);
                        mEditor.apply();
                    }
                }).addOnFailureListener(e -> imgView.setImageResource(R.drawable.bg184));
    }

    private void setViewsVisibility(int action) {
        month_year_btn.setVisibility(action);
        txtDate.setVisibility(action);
        txtDay.setVisibility(action);
        txtTietkhi.setVisibility(action);
        txtDanhngon.setVisibility(action);
        txtauth.setVisibility(action);
        row_hour_txt.setVisibility(action);
    }

    private void setNull2View() {
        month_year_btn.setText("");
        txtDate.setText("");
        txtDay.setText("");
        txtTietkhi.setText("");
        txtDanhngon.setText("");
        txtauth.setText("");
        row_weather_txt.setText("");
        /*txt_label_h.setText("");
        txt_label_d.setText("");
        txt_label_mo.setText("");*/
        row_tem_txt.setText("");
        row_hour_txt.setText("");
        row_lunar_day_txt.setText("");
        row_lunar_month_txt.setText("");
        tem_minmax_txt.setText("");
        row_12_month_txt.setText("");
        row_city.setText("");
        row_12_hour_txt.setText("");
        row_12_day_txt.setText("");
        row_12_year_txt.setText("");
        //txt_label_gHD.setText("");
        row_12_gioHD_txt.setText("");
    }

    private void setWeather2View() {

    }

    private void setData2View(String action) {
        int year, month, date, day, hour, minute, sec, msec;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        if (action.equals("next")) {
            step_day++;
            step_danh_ngon++;
            if (step_danh_ngon > danh_ngon.length) {
                step_danh_ngon = 0;
            }
        } else if (action.equals("back")) {
            step_day--;
            step_danh_ngon--;
            if (step_danh_ngon < 0) {
                step_danh_ngon = danh_ngon.length;
            }
        } else {
            step_day = 0;
        }

        SharedPreferences mSharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt(PREF_SAVE_STEP_IMG, step_day);
        mEditor.putInt(PREF_SAVE_STEP_DN, step_danh_ngon);
        mEditor.apply();

        calendar.add(Calendar.DAY_OF_YEAR, step_day);
        msec = calendar.get(Calendar.MILLISECOND);
        sec = calendar.get(Calendar.SECOND);
        minute = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        day = calendar.get(Calendar.DAY_OF_WEEK);
        date = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);

        LunarDate lunarDate = LunarCalendar.getLunarDate(date, month, year);
        String can_chi[] = LunarCalendar.getCanChi(lunarDate);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onAnimationStart(Animation animation) {
                month_year_btn.setText("tháng " + month + " - " + year);
                txtDate.setText(date + "");
                txtTietkhi.setText(LunarCalendar.getTietKhi(lunarDate.getJd(), hour, minute, sec, msec, 7.0));
                switch (day) {
                    case 2:
                        txtDate.setTextColor(getResources().getColor(R.color.black));
                        txtDay.setTextColor(getResources().getColor(R.color.black));
                        txtDay.setText("thứ hai");
                        break;
                    case 3:
                        txtDate.setTextColor(getResources().getColor(R.color.black));
                        txtDay.setTextColor(getResources().getColor(R.color.black));
                        txtDay.setText("thứ ba");
                        break;
                    case 4:
                        txtDate.setTextColor(getResources().getColor(R.color.black));
                        txtDay.setTextColor(getResources().getColor(R.color.black));
                        txtDay.setText("thứ tư");
                        break;
                    case 5:
                        txtDate.setTextColor(getResources().getColor(R.color.black));
                        txtDay.setTextColor(getResources().getColor(R.color.black));
                        txtDay.setText("thứ năm");
                        break;
                    case 6:
                        txtDate.setTextColor(getResources().getColor(R.color.black));
                        txtDay.setTextColor(getResources().getColor(R.color.black));
                        txtDay.setText("thứ sáu");
                        break;
                    case 7:
                        txtDate.setTextColor(getResources().getColor(R.color.red1));
                        txtDay.setTextColor(getResources().getColor(R.color.red1));
                        txtDay.setText("thứ bảy");
                        break;
                    default:
                        txtDate.setTextColor(getResources().getColor(R.color.red1));
                        txtDay.setTextColor(getResources().getColor(R.color.red1));
                        txtDay.setText("chủ nhật");
                        break;
                }
                txtDanhngon.setText(danh_ngon[step_danh_ngon]);
                txtauth.setText(danh_ngon_auth[step_danh_ngon]);
                /*Uri link_ic = Uri.parse("https://openweathermap.org/img/wn/" + weatherDataCurent.getWeatherIcon() + ".png");
                GlideApp.with(mContext).load(link_ic).transition(DrawableTransitionOptions.withCrossFade(1000))
                        .into(row_weather_img);*/
                row_weather_txt.setText(addWeatherIc(weatherDataCurent));
                row_tem_txt.setText(weatherDataCurent.getTemperature() + "°C");
                row_hour_txt.setText(hour + ":" + minute);
                row_lunar_day_txt.setText(lunarDate.getDay()+"");
                if (lunarDate.getNumday() == 30) {
                    row_lunar_month_txt.setText(lunarDate.getMonth()+"(Đ)");
                } else {
                    row_lunar_month_txt.setText(lunarDate.getMonth()+"(T)");
                }
                tem_minmax_txt.setText(weatherDataCurent.getTemperatureMin() + "°|" + weatherDataCurent.getTemperatureMax() + "°");
                row_12_month_txt.setText(can_chi[1]);
                row_city.setText(weatherDataCurent.getCityName());
                row_12_hour_txt.setText(LunarCalendar.convertToLunarTime(hour, lunarDate.getJd())+"");
                row_12_day_txt.setText(can_chi[0]);
                row_12_year_txt.setText(can_chi[2]);
                row_12_gioHD_txt.setText(LunarCalendar.getGioHoangDao(lunarDate.getJd()));
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        month_year_btn.startAnimation(fadeOut);
        txtDate.startAnimation(fadeOut);
        txtDay.startAnimation(fadeOut);
        txtTietkhi.startAnimation(fadeOut);
        txtDanhngon.startAnimation(fadeOut);
        txtauth.startAnimation(fadeOut);
        row_weather_txt.startAnimation(fadeOut);
        row_tem_txt.startAnimation(fadeOut);
        row_hour_txt.startAnimation(fadeOut);
        row_lunar_day_txt.startAnimation(fadeOut);
        row_lunar_month_txt.startAnimation(fadeOut);
        tem_minmax_txt.startAnimation(fadeOut);
        row_12_month_txt.startAnimation(fadeOut);
        row_city.startAnimation(fadeOut);
        row_12_hour_txt.startAnimation(fadeOut);
        row_12_day_txt.startAnimation(fadeOut);
        row_12_year_txt.startAnimation(fadeOut);
        row_12_gioHD_txt.startAnimation(fadeOut);
        txtTietkhi.startAnimation(fadeOut);
        txtDanhngon.startAnimation(fadeOut);
        txtauth.startAnimation(fadeOut);

        month_year_btn.startAnimation(fadeIn);
        txtDate.startAnimation(fadeIn);
        txtDay.startAnimation(fadeIn);
        txtTietkhi.startAnimation(fadeIn);
        txtDanhngon.startAnimation(fadeIn);
        txtauth.startAnimation(fadeIn);
        row_weather_txt.startAnimation(fadeIn);
        row_tem_txt.startAnimation(fadeIn);
        row_hour_txt.startAnimation(fadeIn);
        row_lunar_day_txt.startAnimation(fadeIn);
        row_lunar_month_txt.startAnimation(fadeIn);
        tem_minmax_txt.startAnimation(fadeIn);
        row_12_month_txt.startAnimation(fadeIn);
        row_city.startAnimation(fadeIn);
        row_12_hour_txt.startAnimation(fadeIn);
        row_12_day_txt.startAnimation(fadeIn);
        row_12_year_txt.startAnimation(fadeIn);
        row_12_gioHD_txt.startAnimation(fadeIn);
        txtTietkhi.startAnimation(fadeIn);
        txtDanhngon.startAnimation(fadeIn);
        txtauth.startAnimation(fadeIn);
    }

    private void setCurrentWeather() {
        double lat = mLocationHelper.getLocation().getLatitude();
        double lon = mLocationHelper.getLocation().getLongitude();
        Call<ResponseBody> call = apiWeatherService.getCurentCondition(lat, lon, WeatherAPIClient.API_KEY_WEATHER_API);
        /*http://api.openweathermap.org/data/2.5/weather?units=metric&lat=21.0325192&lon=105.7827499&appid=a184bde24c284ec8b749f772d04db678*/
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    weatherDataCurent.setTimeZone(jsonObject.getString("timezone"));
                    weatherDataCurent.setTemperature(jsonObject.getJSONObject("main").getString("temp"));
                    weatherDataCurent.setTemperatureMax(jsonObject.getJSONObject("main").getString("temp_max"));
                    weatherDataCurent.setTemperatureMin(jsonObject.getJSONObject("main").getString("temp_min"));
                    weatherDataCurent.setRealFeelTemperature(jsonObject.getJSONObject("main").getString("feels_like"));
                    weatherDataCurent.setRelativeHumidity(jsonObject.getJSONObject("main").getString("humidity"));
                    weatherDataCurent.setWeatherText(jsonObject.getJSONArray("weather").getJSONObject(0).getString("main"));
                    weatherDataCurent.setWeatherIcon(jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon"));
                    weatherDataCurent.setWeatherDescription(jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"));
                    weatherDataCurent.setMobileLink("https://openweathermap.org/city/" + jsonObject.getJSONArray("weather").getJSONObject(0).getString("id"));
                    weatherDataCurent.setWindSpeed(jsonObject.getJSONObject("wind").getString("speed"));
                    weatherDataCurent.setLocalObservationDateTime(jsonObject.getString("dt"));
                    weatherDataCurent.setCityName(mLocationHelper.getCityName(lat, lon));

                    setData2View(action);

                } catch (IOException | JSONException | NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private String addWeatherIc (WeatherData weatherDataCurent) {
        String weather = weatherDataCurent.getWeatherText();
        String ic_str="";

        if (weather == null) {
            weather = "";
        }

        if (weather.startsWith("Thunderstorm")) {
            ic_str = "wi_thunderstorm";
        } else if (weather.startsWith("Drizzle")) {
            ic_str = getResources().getString(R.string.wi_sleet); //"wi_sleet";
        } else if (weather.startsWith("Rain")) {
            ic_str = getResources().getString(R.string.wi_rain); //"wi_rain";
        } else if (weather.startsWith("Snow")) {
            ic_str = getResources().getString(R.string.wi_snow); //"wi_snow";
        } else if (weather.startsWith("Mist") || weather.startsWith("Smoke") || weather.startsWith("Haze") || weather.startsWith("Dust") || weather.startsWith("Fog")
        || weather.startsWith("Sand") || weather.startsWith("Ash") || weather.startsWith("Squall") || weather.startsWith("Tornado")) {
            ic_str = getResources().getString(R.string.wi_fog); //"wi_fog";
        } else if (weather.startsWith("Clear")) {
            ic_str = getResources().getString(R.string.wi_day_sunny); //"wi_day_sunny";
        } else if (weather.startsWith("Clouds")) {
            ic_str = getResources().getString(R.string.wi_cloudy); //"wi_cloudy";
        }

        return ic_str;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.mSimpleGestureFilter.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {
        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT:
            case SimpleGestureFilter.SWIPE_DOWN:
                listAllPaginated(pToken);
                action = "back";
                break;
            case SimpleGestureFilter.SWIPE_LEFT:
            case SimpleGestureFilter.SWIPE_UP:
                listAllPaginated(pToken);
                action = "next";
                break;
        }
    }

    @Override
    public void onDoubleTap() {
        listAllPaginated(pToken);
        action = "today";
    }

    @Override
    public void updateLocation() {
        listAllPaginated(pToken);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLocationHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationHelper.stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationHelper.startLoadLocation();

    }
}