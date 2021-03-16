package xhj.zime.com.mymaptest.TaskList;

public class Task {
    private int taskStatus;
    private String title;
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String text5;

    public Task(int taskStatus,String title,String text1, String text2, String text3, String text4, String text5) {
        this.taskStatus = taskStatus;
        this.title = title;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
        this.text5 = text5;
    }

    public String getText5() {
        return text5;
    }

    public void setText5(String text5) {
        this.text5 = text5;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public String getText3() {
        return text3;
    }

    public String getText4() {
        return text4;
    }

    public String getTitle() {
        return title;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public void setText4(String text4) {
        this.text4 = text4;
    }
}
