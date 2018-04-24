package com.example.marko.myoptionalproject.model;

import java.util.List;

/**
 * @author Marko
 * @date 2018/4/20
 */

public class RecyclerImgResult {
    /**
     * data : {"WallpaperListInfo":[{"WallPaperMiddle":"http://bzpic.spriteapp.cn/250x445/picture1/M00/0A/72/wKiFR1NQ5PqAIfVNAALQ-H2CYwc814.jpg"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<WallpaperListInfoBean> WallpaperListInfo;

        public List<WallpaperListInfoBean> getWallpaperListInfo() {
            return WallpaperListInfo;
        }

        public void setWallpaperListInfo(List<WallpaperListInfoBean> WallpaperListInfo) {
            this.WallpaperListInfo = WallpaperListInfo;
        }

        public static class WallpaperListInfoBean {
            /**
             * WallPaperMiddle : http://bzpic.spriteapp.cn/250x445/picture1/M00/0A/72/wKiFR1NQ5PqAIfVNAALQ-H2CYwc814.jpg
             */

            private String WallPaperMiddle;

            public String getWallPaperMiddle() {
                return WallPaperMiddle;
            }

            public void setWallPaperMiddle(String WallPaperMiddle) {
                this.WallPaperMiddle = WallPaperMiddle;
            }
        }
    }


   /* private DataBean databean;


    public DataBean getDatabean() {
        return databean;
    }

    public void setDatabean(DataBean databean) {
        this.databean = databean;
    }


    public static class DataBean {


        private List<WallpaperListInfo> wallpaperListInfos;

        public List<WallpaperListInfo> getWallpaperListInfos() {
            return wallpaperListInfos;
        }

        public void setWallpaperListInfos(List<WallpaperListInfo> wallpaperListInfos) {
            this.wallpaperListInfos = wallpaperListInfos;
        }

    }

    public static class WallpaperListInfo {

        private String wallPaperMiddle;

        public String getWallPaperMiddle() {
            return wallPaperMiddle;
        }

        public void setWallPaperMiddle(String wallPaperMiddle) {
            this.wallPaperMiddle = wallPaperMiddle;
        }
    }*/


}
