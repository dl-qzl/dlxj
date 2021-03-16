package xhj.zime.com.mymaptest.bean;

public class TaskPointBeansBean {

    /**
     * hasflaw : null
     * object_type_id : 1001
     * task_id : 1011
     * task_point_id : 101101
     * task_point_location : 120.172212,30.289513
     * task_point_name : 工井 GJ001
     * task_type : 401
     */

    private Object hasflaw;
    private int object_type_id;
    private int task_id;
    private int task_point_id;
    private String task_point_location;
    private String task_point_name;
    private int task_type;
    private String attr_json;

    public String getAttr_json() {
        return attr_json;
    }

    public void setAttr_json(String attr_json) {
        this.attr_json = attr_json;
    }

    public Object getHasflaw() {
        return hasflaw;
    }

    public void setHasflaw(Object hasflaw) {
        this.hasflaw = hasflaw;
    }

    public int getObject_type_id() {
        return object_type_id;
    }

    public void setObject_type_id(int object_type_id) {
        this.object_type_id = object_type_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getTask_point_id() {
        return task_point_id;
    }

    public void setTask_point_id(int task_point_id) {
        this.task_point_id = task_point_id;
    }

    public String getTask_point_location() {
        return task_point_location;
    }

    public void setTask_point_location(String task_point_location) {
        this.task_point_location = task_point_location;
    }

    public String getTask_point_name() {
        return task_point_name;
    }

    public void setTask_point_name(String task_point_name) {
        this.task_point_name = task_point_name;
    }

    public int getTask_type() {
        return task_type;
    }

    public void setTask_type(int task_type) {
        this.task_type = task_type;
    }
}
