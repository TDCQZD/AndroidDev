package com.example.zhangdai.androidjson.javaBean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/17.
 */

public class FilmData {

    /**
     * code : 0
     * list : {"0":{"aid":"6008965","author":"哔哩哔哩番剧","coins":170,"copyright":"Copy","create":"2016-08-25 21:34"},"1":{"aid":"6008938","author":"哔哩哔哩番剧","coins":404,"copyright":"Copy","create":"2016-08-25 21:33"}}
     */

    private int code;
    private ListBean list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * 0 : {"aid":"6008965","author":"哔哩哔哩番剧","coins":170,"copyright":"Copy","create":"2016-08-25 21:34"}
         * 1 : {"aid":"6008938","author":"哔哩哔哩番剧","coins":404,"copyright":"Copy","create":"2016-08-25 21:33"}
         */

        @SerializedName("0")
        private _$0Bean _$0;
        @SerializedName("1")
        private _$1Bean _$1;

        public _$0Bean get_$0() {
            return _$0;
        }

        public void set_$0(_$0Bean _$0) {
            this._$0 = _$0;
        }

        public _$1Bean get_$1() {
            return _$1;
        }

        public void set_$1(_$1Bean _$1) {
            this._$1 = _$1;
        }

        public static class _$0Bean {
            /**
             * aid : 6008965
             * author : 哔哩哔哩番剧
             * coins : 170
             * copyright : Copy
             * create : 2016-08-25 21:34
             */

            private String aid;
            private String author;
            private int coins;
            private String copyright;
            private String create;

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getCoins() {
                return coins;
            }

            public void setCoins(int coins) {
                this.coins = coins;
            }

            public String getCopyright() {
                return copyright;
            }

            public void setCopyright(String copyright) {
                this.copyright = copyright;
            }

            public String getCreate() {
                return create;
            }

            public void setCreate(String create) {
                this.create = create;
            }
        }

        public static class _$1Bean {
            /**
             * aid : 6008938
             * author : 哔哩哔哩番剧
             * coins : 404
             * copyright : Copy
             * create : 2016-08-25 21:33
             */

            private String aid;
            private String author;
            private int coins;
            private String copyright;
            private String create;

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getCoins() {
                return coins;
            }

            public void setCoins(int coins) {
                this.coins = coins;
            }

            public String getCopyright() {
                return copyright;
            }

            public void setCopyright(String copyright) {
                this.copyright = copyright;
            }

            public String getCreate() {
                return create;
            }

            public void setCreate(String create) {
                this.create = create;
            }
        }
    }
}
