package codingbo.top.viewanimatordemo;

import java.util.List;

/**
 * Created by bob
 * on 2018/10/12.
 */
public interface BannerDataRepository<T> {

    public abstract List<String> getTitles(List<T> data);

    public abstract List<?> getImages(List<T> data);
}
