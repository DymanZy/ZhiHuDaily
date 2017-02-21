package com.dyman.zhihudaily.entity;

import java.util.List;

/**
 *
 *
 * Created by dyman on 2017/2/19.
 */

public class CommentsInfo {


    private List<CommentsBean> comments;

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * author : Jane
         * content : 咱能取消对知乎日报强制订阅吗，我已经有个app了不需要看重复内容
         * avatar : http://pic4.zhimg.com/16ba11c7f_im.jpg
         * time : 1449658966
         * reply_to :
         * {"content":"读读离线功能不太好做，因为日报的内容都是我们自己的，都存在我们自己服务器上，所以离线做起来简单一点，读读内容大部分都是别的网站的，不在我们服务器上，所以离线会难很多，正在开发中。另外您可以试试从 dudu
         * .zhihu.com 下载一下最新版，看能不能安装，不能的话我反馈给我们技术的同事。","status":0,"id":21953638,"author":"编辑部小李"}
         * id : 22236458
         * likes : 0
         */

        private String author;
        private String content;
        private String avatar;
        private int time;
        private ReplyToBean reply_to;
        private int id;
        private int likes;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public ReplyToBean getReply_to() {
            return reply_to;
        }

        public void setReply_to(ReplyToBean reply_to) {
            this.reply_to = reply_to;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public static class ReplyToBean {
            /**
             * content : 读读离线功能不太好做，因为日报的内容都是我们自己的，都存在我们自己服务器上，所以离线做起来简单一点，读读内容大部分都是别的网站的，不在我们服务器上，所以离线会难很多，正在开发中。另外您可以试试从 dudu
             * .zhihu.com 下载一下最新版，看能不能安装，不能的话我反馈给我们技术的同事。
             * status : 0
             * id : 21953638
             * author : 编辑部小李
             */

            private String content;
            private int status;
            private int id;
            private String author;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }
        }
    }
}
