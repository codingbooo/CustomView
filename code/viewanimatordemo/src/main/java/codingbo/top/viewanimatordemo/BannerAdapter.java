package codingbo.top.viewanimatordemo;

import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bob
 * on 2018/10/12.
 */
public class BannerAdapter extends BannerBaseAdapter<PicNewsEntry> {

    public BannerAdapter(Banner banner) {
        super(banner);
    }

    @Override
    public List<String> getTitles(List<PicNewsEntry> data) {
        if (isEmpty(data)) {
            return null;
        }
        List<String> titles = new ArrayList<>();
        for (PicNewsEntry picInfo : data) {
            titles.add(getTitle(picInfo));
        }
        return titles;

    }

    private String getTitle(PicNewsEntry picInfo) {
        return picInfo.getTitle();
    }

    @Override
    public List<?> getImages(List<PicNewsEntry> data) {
        if (isEmpty(data)) {
            return null;
        }
        List<String> images = new ArrayList<>();
        for (PicNewsEntry picInfo : data) {
            images.add(getImage(picInfo));
        }
        return images;
    }

    private String getImage(PicNewsEntry picInfo) {
        return picInfo.getUrl();
    }
}
