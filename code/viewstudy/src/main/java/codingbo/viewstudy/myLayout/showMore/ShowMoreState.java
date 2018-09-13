package codingbo.viewstudy.myLayout.showMore;

/**
 * 滑动状态枚举类型
 */
public enum ShowMoreState {
    //默认隐藏状态
    NORMAL(1),
    //拉动状态
    DRAGGING(2),
    //刷新状态
    REFRESH(3),
    //完成状态
    COMPLETE(4);

    private int id;

    ShowMoreState(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
