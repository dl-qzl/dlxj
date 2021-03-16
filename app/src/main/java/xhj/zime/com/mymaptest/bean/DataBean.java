package xhj.zime.com.mymaptest.bean;

import java.util.List;

public class DataBean {
    private List<TaskBeansBean> taskBeans;
    private List<TaskPointBeansBean> taskPointBeans;
    private List<FlawBean> flawBeans;
    private List<AdjunctBean> adjunctBeans;

    public List<TaskBeansBean> getTaskBeans() {
        return taskBeans;
    }

    public void setTaskBeans(List<TaskBeansBean> taskBeans) {
        this.taskBeans = taskBeans;
    }

    public List<TaskPointBeansBean> getTaskPointBeans() {
        return taskPointBeans;
    }

    public void setTaskPointBeans(List<TaskPointBeansBean> taskPointBeans) {
        this.taskPointBeans = taskPointBeans;
    }

    public List<FlawBean> getFlawBeans() {
        return flawBeans;
    }

    public void setFlawBeans(List<FlawBean> flawBeans) {
        this.flawBeans = flawBeans;
    }

    public List<AdjunctBean> getAdjunctBeans() {
        return adjunctBeans;
    }

    public void setAdjunctBeans(List<AdjunctBean> adjunctBeans) {
        this.adjunctBeans = adjunctBeans;
    }
}
