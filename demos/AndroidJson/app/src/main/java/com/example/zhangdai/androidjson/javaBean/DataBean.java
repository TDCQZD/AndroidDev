package com.example.zhangdai.androidjson.javaBean;

import java.util.List;

/**
 * Created by zhangdai on 2017/2/19.
 */

public class DataBean {

    private List<ItemData> trailers;

    public void setTrailers(List<ItemData> trailers) {
        this.trailers = trailers;
    }

    public List<ItemData> getTrailers() {
        return trailers;
    }

    public static class ItemData {

        /**
         * coverImg : http://img5.mtime.cn/mg/2016/11/05/104110.14289788_120X90X4.jpg
         * hightUrl : http://vfx.mtime.cn/Video/2016/11/05/mp4/161105104216840789.mp4
         * id : 63234
         * movieId : 139838
         * movieName : 《国土安全》第六季先导预告片
         * rating : 8.6
         * summary : 卡莉等主角回归独缺彼得奎恩
         * type : ["剧情"]
         * url : http://vfx.mtime.cn/Video/2016/11/05/mp4/161105104216840789_480.mp4
         * videoLength : 111
         * videoTitle : 国土安全 第六季先导预告
         */

        private String coverImg;
        private String hightUrl;
        private int id;
        private int movieId;
        private String movieName;
        private double rating;
        private String summary;
        private String url;
        private int videoLength;
        private String videoTitle;
        private List<String> type;

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public String getHightUrl() {
            return hightUrl;
        }

        public void setHightUrl(String hightUrl) {
            this.hightUrl = hightUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getVideoLength() {
            return videoLength;
        }

        public void setVideoLength(int videoLength) {
            this.videoLength = videoLength;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }
    }

}
