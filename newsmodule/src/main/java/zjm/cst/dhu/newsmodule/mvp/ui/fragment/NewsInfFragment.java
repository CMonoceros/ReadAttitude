package zjm.cst.dhu.newsmodule.mvp.ui.fragment;

import android.databinding.DataBindingUtil;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import javax.inject.Inject;

import zjm.cst.dhu.library.Constant;
import zjm.cst.dhu.library.mvp.model.NewsInf;
import zjm.cst.dhu.newsmodule.utils.UrlImageGetter;
import zjm.cst.dhu.newsmodule.R;
import zjm.cst.dhu.newsmodule.databinding.ModuleNewsFragmentNewsInfBinding;
import zjm.cst.dhu.newsmodule.mvp.presenter.NewsInfPresenter;
import zjm.cst.dhu.newsmodule.mvp.ui.fragment.base.BaseFragment;
import zjm.cst.dhu.newsmodule.mvp.view.NewsInfContract;

import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static android.text.Html.FROM_HTML_MODE_LEGACY;

/**
 * Created by zjm on 2017/5/14.
 */

public class NewsInfFragment extends BaseFragment<NewsInfPresenter> implements NewsInfContract.View {

    @Inject
    NewsInfPresenter newsInfPresenter;

    private ModuleNewsFragmentNewsInfBinding moduleNewsFragmentNewsInfBinding;
    private UrlImageGetter urlImageGetter;

    private TextView module_news_tv_news_inf, module_news_tv_news_inf_date;
    private ImageView module_news_iv_news_inf;
    private AppBarLayout module_news_abl_inf;
    private Toolbar module_news_tb_inf;
    private CollapsingToolbarLayout module_news_ctl_inf;

    private NewsInf newsInf;
    private String postId;


    @Override
    public void dataBinding(View rootView) {
        moduleNewsFragmentNewsInfBinding = DataBindingUtil.bind(rootView);
        module_news_tv_news_inf = moduleNewsFragmentNewsInfBinding.moduleNewsTvNewsInf;
        module_news_tv_news_inf_date = moduleNewsFragmentNewsInfBinding.moduleNewsTvNewsInfDate;
        module_news_iv_news_inf = moduleNewsFragmentNewsInfBinding.moduleNewsIvNewsInf;
        module_news_ctl_inf = moduleNewsFragmentNewsInfBinding.moduleNewsCtlInf;
        module_news_abl_inf = moduleNewsFragmentNewsInfBinding.moduleNewsAblInf;
        module_news_tb_inf = moduleNewsFragmentNewsInfBinding.moduleNewsTbInf;
        moduleNewsFragmentNewsInfBinding.setIsLoading(true);
        moduleNewsFragmentNewsInfBinding.setIsSuccess(true);
        moduleNewsFragmentNewsInfBinding.setReloadClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPresenter.loadNewsInf(postId);
            }
        });
    }

    @Override
    public void setupInjector() {
        myFragmentComponent.inject(this);
    }

    @Override
    public void setupViews(View rootView) {
        postId = getArguments().getString(Constant.NEWS_INF_POST_ID);
        myPresenter = newsInfPresenter;
        myPresenter.attachView(this);
        myPresenter.loadNewsInf(postId);

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(module_news_tb_inf);
    }

    @Override
    public int getFragmentId() {
        return R.layout.module_news_fragment_news_inf;
    }

    @Override
    public void loadNewsInfSuccess(NewsInf inf) {
        moduleNewsFragmentNewsInfBinding.setIsLoading(false);
        moduleNewsFragmentNewsInfBinding.setIsSuccess(true);
        this.newsInf = inf;
        module_news_ctl_inf.setTitle(newsInf.getTitle());
        module_news_ctl_inf.setExpandedTitleColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        module_news_ctl_inf.setCollapsedTitleTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        module_news_tv_news_inf_date.setText(newsInf.getSource() + " " + newsInf.getPtime());
        setupImageView();
        List<NewsInf.ImgBean> imgList = newsInf.getImg();
        setupTextView(imgList);
    }

    private void setupImageView() {
        String imgSrc = getArguments().getString(Constant.NEWS_INF_IMG_BASE);
        Glide.with(NewsInfFragment.this).load(imgSrc)
                .asBitmap()
                .placeholder(R.drawable.module_news_ic_loading)
                .error(R.drawable.module_news_ic_loading_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(module_news_iv_news_inf);
    }

    private void setupTextView(List<NewsInf.ImgBean> imgList) {
        if (imgList != null && imgList.size() > 1) {
            urlImageGetter = new UrlImageGetter(module_news_tv_news_inf, newsInf.getBody(), imgList.size(), this);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                module_news_tv_news_inf.setText(Html.fromHtml(newsInf.getBody(),
                        FROM_HTML_MODE_LEGACY, urlImageGetter, null));
            } else {
                module_news_tv_news_inf.setText(Html.fromHtml(newsInf.getBody(), urlImageGetter, null));
            }
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                module_news_tv_news_inf.setText(Html.fromHtml(newsInf.getBody(), FROM_HTML_MODE_COMPACT));
            } else {
                module_news_tv_news_inf.setText(Html.fromHtml(newsInf.getBody()));
            }
        }
    }

    @Override
    public void loadNewsInfError() {
        moduleNewsFragmentNewsInfBinding.setIsLoading(false);
        moduleNewsFragmentNewsInfBinding.setIsSuccess(false);
        showSnakeBarMessage(Constant.NEWS_INF_LOAD_ERROR);
    }

    private void showSnakeBarMessage(String message) {
        Snackbar.make(module_news_abl_inf, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                System.out.println("press");
                getActivity().onBackPressed();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
