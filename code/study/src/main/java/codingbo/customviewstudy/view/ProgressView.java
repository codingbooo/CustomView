package codingbo.customviewstudy.view;

/**
 * Created by bob
 * on 17.9.8.
 */

public interface ProgressView {
    void setMax(int max);

    void setProgress(int progress);

    int getProgress();
}
