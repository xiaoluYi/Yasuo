package com.sjl.yuehu.data.bean;

import java.util.List;

/**
 * Created by 小鹿 on 2017/2/9.
 */

public class ThemesBean {


    /**
     * background : http://pic1.zhimg.com/a5128188ed788005ad50840a42079c41.jpg
     * color : 8307764
     * description : 为你发现最有趣的新鲜事，建议在 WiFi 下查看
     * editors : [{"avatar":"http://pic4.zhimg.com/068311926_m.jpg","bio":"微在 Wezeit 主编","id":70,"name":"益康糯米","url":"http://www.zhihu.com/people/wezeit"},{"avatar":"http://pic4.zhimg.com/43d10de2b46c918a9ffe5d0472947b67_m.jpg","bio":"明月般俊朗","id":71,"name":"顾惜朝","url":"http://www.zhihu.com/people/kuangzhou"}]
     * image : http://pic3.zhimg.com/da1fcaf6a02d1223d130d5b106e828b9.jpg
     * image_source :
     * name : 不许无聊
     * stories : [{"id":7468668,"images":["http://pic1.zhimg.com/e3f596c7ed9e470733f0637adb6124e4.jpg"],"title":"不许无聊在读读日报里等你哟","type":0},{"id":7315220,"images":["http://pic4.zhimg.com/b5bb0754c6bab0adec4edd1256efbef7_t.jpg"],"title":"第一天上班 超激动，可惜后来被打了脸\u2026","type":2},{"id":7310300,"images":["http://pic3.zhimg.com/508c01ace291bd839dfdf13a8487ea92_t.jpg"],"title":"群众都是重口味，世上再无主流色情杂志这回事","type":2},{"id":7307922,"images":["http://pic4.zhimg.com/63a262791c4e2b156d932c7a03b137df_t.jpg"],"title":"11 个关于漫威的冷知识，原来圣诞老人是变种人！","type":2},{"id":7305993,"images":["http://pic1.zhimg.com/58bce639a1fea757e3b9053d061936dc_t.jpg"],"title":"7 个日本高阶旅行地，没有中国游客","type":2},{"id":7303298,"images":["http://pic1.zhimg.com/4edd2b5b75775da684dab887be15de00_t.jpg"],"title":"11 张本周最热节操图，担心的事情终于发生了","type":2},{"id":7300302,"images":["http://pic4.zhimg.com/1b8e2e93aef140349407d2580b16f887_t.jpg"],"title":"我们真的发现了外星人的太空飞船吗？","type":2},{"id":7299037,"images":["http://pic4.zhimg.com/1039533776aafebadd11ad377eebde47_t.jpg"],"title":"问题：单身的人可以是幸福的吗？","type":2},{"id":7295366,"images":["http://pic1.zhimg.com/cb2045505a5ea2e8e2b3896db181fe90_t.jpg"],"title":"10 个理由告诉你我为什么要停用朋友圈","type":2},{"id":7292710,"images":["http://pic1.zhimg.com/8f4f1762130eefa0a310daf324ff3454_t.jpg"],"title":"17 个叼叼的人类隐藏技能，一定是吃了恶魔果实","type":2},{"id":7288286,"images":["http://pic1.zhimg.com/8f652c700593e10f4e5ce8ec15774874.jpg"],"title":"《小王子》早就教你该怎么谈恋爱了，单身怪自己","type":2},{"id":7285920,"images":["http://pic1.zhimg.com/1750ca43e2a2534dddcca3a813cd7fb4_t.jpg"],"title":"其实每个人内心都想成为一张小浴巾！！","type":2},{"id":7281623,"images":["http://pic1.zhimg.com/e512e59a4311e1d9a489fac8144fd670_t.jpg"],"title":"23 个男生永远理解不了的画面","type":2},{"id":7279286,"images":["http://pic3.zhimg.com/01d8ab485233cc00631d813d93b51746_t.jpg"],"title":"8 张图完美阐释你和你妈的非凡关系","type":2},{"id":7274499,"images":["http://pic2.zhimg.com/2ae9218b5b2ae0de917ca5327f1a62d9_t.jpg"],"title":"王菲小女儿李嫣上了微博热门，因为她实在太可爱了","type":2},{"id":7271705,"images":["http://pic1.zhimg.com/5e7e245530ca61296a175c385414982c_t.jpg"],"title":"14 个直播记者比你今天烦躁多了","type":2},{"id":7268620,"images":["http://pic4.zhimg.com/2a8b736ff65ac13dfb54dd8eab7d0353_t.jpg"],"title":"有了它们你再也不用吃安利补 VC 了","type":2},{"id":7267355,"images":["http://pic3.zhimg.com/fd7eb52781ea04ba6e6962eef86450ce_t.jpg"],"title":"11 个本周冷知识，婚礼除了公主梦其实也可以很酷","type":2},{"id":7262019,"images":["http://pic3.zhimg.com/cfe264c81fae5c1438aa9e27a4e3d5a2_t.jpg"],"title":"11 张本周最热节操图，琅琊榜的导演根本不给演员活路啊","type":2}]
     */

    private String background;
    private int color;
    private String description;
    private String image;
    private String image_source;
    private String name;
    private List<EditorsBean> editors;
    private List<StoriesBean> stories;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EditorsBean> getEditors() {
        return editors;
    }

    public void setEditors(List<EditorsBean> editors) {
        this.editors = editors;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public static class EditorsBean {
        /**
         * avatar : http://pic4.zhimg.com/068311926_m.jpg
         * bio : 微在 Wezeit 主编
         * id : 70
         * name : 益康糯米
         * url : http://www.zhihu.com/people/wezeit
         */

        private String avatar;
        private String bio;
        private int id;
        private String name;
        private String url;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class StoriesBean {
        /**
         * id : 7468668
         * images : ["http://pic1.zhimg.com/e3f596c7ed9e470733f0637adb6124e4.jpg"]
         * title : 不许无聊在读读日报里等你哟
         * type : 0
         */

        private int id;
        private String title;
        private int type;
        private List<String> images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
