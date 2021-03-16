package xhj.zime.com.mymaptest.bean;

import java.util.List;

public class BaseDataBean {
    /**
     * code : 1
     * msg : 请求成功
     * data : {"flawDisposes":[{"flaw_dispose_type_id":101,"flaw_dispose_type_name":"已处理"},{"flaw_dispose_type_id":102,"flaw_dispose_type_name":"不处理"},{"flaw_dispose_type_id":103,"flaw_dispose_type_name":"未处理"}],"flawLevels":[{"flaw_level_id":201,"flaw_level_name":"一般缺陷"},{"flaw_level_id":202,"flaw_level_name":"严重缺陷"},{"flaw_level_id":203,"flaw_level_name":"紧急缺陷"}],"flawTypes":[{"flaw_type_id":301,"flaw_type_name":"地面有水","flaw_type_template":"需要及时清理水渍，保持地面干燥","obj_type_id":1001},{"flaw_type_id":302,"flaw_type_name":"地下管道破损","flaw_type_template":"需要及时更新管道，并查清破损原因","obj_type_id":1002},{"flaw_type_id":303,"flaw_type_name":"地面塌陷","flaw_type_template":"需要联系市政人员及时修补路面，并检查相关设备是否损坏","obj_type_id":1001},{"flaw_type_id":304,"flaw_type_name":"地面凸起","flaw_type_template":"需要联系市政人员及时修补路面，并检查相关设备是否损坏","obj_type_id":1001},{"flaw_type_id":305,"flaw_type_name":"管道氧化","flaw_type_template":"定期更换","obj_type_id":1002},{"flaw_type_id":306,"flaw_type_name":"变电箱接头破损","flaw_type_template":"及时更换","obj_type_id":1003},{"flaw_type_id":307,"flaw_type_name":"变电箱栅栏缺失","flaw_type_template":"及时增加栅栏","obj_type_id":1003}],"objectTypes":[{"object_id":1001,"object_name":"隧道"},{"object_id":1002,"object_name":"地下管道"},{"object_id":1003,"object_name":"变电箱"}],"workTypes":[{"type_id":401,"type_name":"巡检任务"},{"type_id":402,"type_name":"消缺任务"},{"type_id":501,"type_name":"输电班组"},{"type_id":502,"type_name":"设备巡检班组"},{"type_id":5,"type_name":"baseDataVersion"}]}
     */
    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<FlawDisposesBean> flawDisposes;
        private List<FlawLevelsBean> flawLevels;
        private List<FlawTypesBean> flawTypes;
        private List<ObjectTypesBean> objectTypes;
        private List<WorkTypesBean> workTypes;

        public List<FlawDisposesBean> getFlawDisposes() {
            return flawDisposes;
        }

        public void setFlawDisposes(List<FlawDisposesBean> flawDisposes) {
            this.flawDisposes = flawDisposes;
        }

        public List<FlawLevelsBean> getFlawLevels() {
            return flawLevels;
        }

        public void setFlawLevels(List<FlawLevelsBean> flawLevels) {
            this.flawLevels = flawLevels;
        }

        public List<FlawTypesBean> getFlawTypes() {
            return flawTypes;
        }

        public void setFlawTypes(List<FlawTypesBean> flawTypes) {
            this.flawTypes = flawTypes;
        }

        public List<ObjectTypesBean> getObjectTypes() {
            return objectTypes;
        }

        public void setObjectTypes(List<ObjectTypesBean> objectTypes) {
            this.objectTypes = objectTypes;
        }

        public List<WorkTypesBean> getWorkTypes() {
            return workTypes;
        }

        public void setWorkTypes(List<WorkTypesBean> workTypes) {
            this.workTypes = workTypes;
        }

        public static class FlawDisposesBean {
            /**
             * flaw_dispose_type_id : 101
             * flaw_dispose_type_name : 已处理
             */

            private int flaw_dispose_type_id;
            private String flaw_dispose_type_name;

            public int getFlaw_dispose_type_id() {
                return flaw_dispose_type_id;
            }

            public void setFlaw_dispose_type_id(int flaw_dispose_type_id) {
                this.flaw_dispose_type_id = flaw_dispose_type_id;
            }

            public String getFlaw_dispose_type_name() {
                return flaw_dispose_type_name;
            }

            public void setFlaw_dispose_type_name(String flaw_dispose_type_name) {
                this.flaw_dispose_type_name = flaw_dispose_type_name;
            }
        }

        public static class FlawLevelsBean {
            /**
             * flaw_level_id : 201
             * flaw_level_name : 一般缺陷
             */

            private int flaw_level_id;
            private String flaw_level_name;

            public int getFlaw_level_id() {
                return flaw_level_id;
            }

            public void setFlaw_level_id(int flaw_level_id) {
                this.flaw_level_id = flaw_level_id;
            }

            public String getFlaw_level_name() {
                return flaw_level_name;
            }

            public void setFlaw_level_name(String flaw_level_name) {
                this.flaw_level_name = flaw_level_name;
            }
        }

        public static class FlawTypesBean {
            /**
             * flaw_type_id : 301
             * flaw_type_name : 地面有水
             * flaw_type_template : 需要及时清理水渍，保持地面干燥
             * obj_type_id : 1001
             */

            private int flaw_type_id;
            private String flaw_type_name;
            private String flaw_type_template;
            private int obj_type_id;

            public int getFlaw_type_id() {
                return flaw_type_id;
            }

            public void setFlaw_type_id(int flaw_type_id) {
                this.flaw_type_id = flaw_type_id;
            }

            public String getFlaw_type_name() {
                return flaw_type_name;
            }

            public void setFlaw_type_name(String flaw_type_name) {
                this.flaw_type_name = flaw_type_name;
            }

            public String getFlaw_type_template() {
                return flaw_type_template;
            }

            public void setFlaw_type_template(String flaw_type_template) {
                this.flaw_type_template = flaw_type_template;
            }

            public int getObj_type_id() {
                return obj_type_id;
            }

            public void setObj_type_id(int obj_type_id) {
                this.obj_type_id = obj_type_id;
            }
        }

        public static class ObjectTypesBean {
            /**
             * object_id : 1001
             * object_name : 隧道
             */

            private int object_id;
            private String object_name;

            public int getObject_id() {
                return object_id;
            }

            public void setObject_id(int object_id) {
                this.object_id = object_id;
            }

            public String getObject_name() {
                return object_name;
            }

            public void setObject_name(String object_name) {
                this.object_name = object_name;
            }
        }

        public static class WorkTypesBean {
            /**
             * type_id : 401
             * type_name : 巡检任务
             */

            private int type_id;
            private String type_name;

            public int getType_id() {
                return type_id;
            }

            public void setType_id(int type_id) {
                this.type_id = type_id;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }
        }
    }
}
