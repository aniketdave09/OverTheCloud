package com.aniket.overthecloud.UI.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aniket.overthecloud.R;
import com.aniket.overthecloud.UI.Adapter.RowAdapter;
import com.aniket.overthecloud.data.repository.MovieRepository;
import com.aniket.overthecloud.databinding.ActivityMainBinding;
import com.aniket.overthecloud.viewmodel.MainActvityViewModel;
import com.aniket.overthecloud.viewmodel.ViewModelProviderFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActvityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUI();
        setupViewModel();
        observeData();

        viewModel.loadMovies();
    }

    private void initUI() {
        // Logo logic
        String text = getString(R.string.app_logo_text);
        SpannableString spannable = new SpannableString(text);
        spannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 8, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor(String.valueOf(R.color.heading))), 8, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.tvAppLogo.setText(spannable);

        // RecyclerView
        binding.rvRows.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRows.setClipChildren(false);
        binding.rvRows.setClipToPadding(false);
        binding.rvRows.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
    }

    private void setupViewModel() {
        MovieRepository repository = new MovieRepository(getApplicationContext());
        ViewModelProviderFactory factory = new ViewModelProviderFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(MainActvityViewModel.class);
    }

    private void observeData() {
        viewModel.getMovieRows().observe(this, rows -> {
            if (rows != null) {
                binding.rvRows.setAdapter(new RowAdapter(this, rows));
            }
        });
    }
}