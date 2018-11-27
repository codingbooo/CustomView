package codingbo.top.viewanimatordemo;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

/**
 * Created by bob
 * on 2018/10/12.
 */
public abstract class BannerBaseAdapter<T> implements BannerDataRepository<T> {

    private final Banner mBanner;
    private List<T> mData;

    public BannerBaseAdapter(Banner banner) {

        mBanner = banner;
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);


        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用

    }


    public void setData(List<T> data) {
        mData = data;

        //设置图片集合
        mBanner.setImages(getImages(data));
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(getTitles(data));
    }

    @Override
    public abstract List<String> getTitles(List<T> data);
    @Override
    public abstract List<?> getImages(List<T> data);

    boolean isEmpty(List<PicNewsEntry> data) {
        return data == null || data.size() <= 0;
    }
}
