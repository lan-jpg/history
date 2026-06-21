<template>
  <view class="eventdetail" >
    <!-- 头部导航栏 -->
    <view class="header" style="background-image: url('/static/image/nav.png'); background-size: cover; background-repeat: no-repeat; height: 60px;">
      <view class="dropdown" @click="goToPage(page)">
        <button class="dropbtn"><</button>
      </view>
      <view class="header-title" >
        <p>青编玉牒</p>
      </view>
      <view class="header-search">
        <input type="search" class="check" placeholder="搜索相关内容" @click="goToPage('searchpage')">
      </view>
    </view>
    <!-- 事件名称及书签按钮区域 -->
    <view class="eventname">
      <view class="null"></view>
      <view class="text">
        <text style="font-size: 22px">{{ event }}</text>
      </view>
      <button :class="isCollected? 'collected' : 'collect'" @click="handleCollect">{{ isCollected? '已加书签' : '加入书签' }}</button>
    </view>
    <!-- 事件详情及评论区域 -->
    <view class="event-detail-page">
      <view class="event-basic" style="width: 57%; float: left;">
        <text style="font-size: 20px">&nbsp;&nbsp;&nbsp;简介<br>一一一一<br></text>
        <text style="font-size: 15px; font-weight: 100;">{{ eventinfo }}</text>
      </view>
      <view class="event-detail" style="width: 57%; float: left;">
        <text style="font-size: 20px">&nbsp;&nbsp;&nbsp;详情<br>一一一一<br></text>
        <text style="font-size: 15px; font-weight: 100;">{{ eventdetails }}</text>
      </view>
      <view class="right" style="width: 40%; float: right; padding: 10px;">
        <view class="image" style="height: 40%;">
          <img :src="imagePath" alt="事件相关图片" style="width: 100%; height: 100%; object-fit: contain;">
        </view>
        <view class="comment-section" style="margin-top: 20px;">
          <text id="comment">评论区</text>
          <view style="display: flex;">
            <input
              type="text"
              v-model="newComment"
              placeholder="输入评论"
              style="flex: 1; margin-right: 10px;"
            />
            <button @click="submitComment">提交</button>
          </view>
          <ul>
            <li v-for="(comment, index) in comments" :key="index">
              {{ comment.content }}
            </li>
          </ul>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      page: "",
      event: "",
      eventinfo: "",
      eventdetails: "",
      newComment: "",
      comments: [],
      imagePath: "",
      userId: null,
      eventId: null,
      isCollected: false // 用于存储收藏状态
    };
  },
  onLoad(options) {
    this.page = options.page;
    this.event = options.event;
    this.eventinfo = options.eventinfo;
    this.eventdetails = options.eventdetails;
    // 根据事件名设置图片路径
    this.imagePath = `/static/image/${this.event}.jpg`; 
    this.userId = uni.getStorageSync('userId');
    this.getEventIdByName(); // 先获取事件 ID
    this.initEventIdAndloadComments(); // 然后加载评论
    this.initEventIdAndCheckCollectStatus(); // 在页面加载时检查收藏状态
  },
  methods: {
    async initEventIdAndCheckCollectStatus() {
      await this.getEventIdByName();
      this.checkCollectStatus();
    },  
    async initEventIdAndloadComments() {
      await this.getEventIdByName();
      this.loadComments();
    },  
    async loadComments() {
      if (!this.eventId) {
        console.log('事件 ID 未获取到，无法加载评论');
        return;
      }
      const baseUrl = 'http://localhost:5173';
      try {
        const response = await fetch(`${baseUrl}/event/selectCommentsById`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            id: this.eventId
          })
        });
        const responseData = await response.json();
        if (responseData.code === '200') {
          // 直接将 responseData.data 赋值给 this.comments
          this.comments = responseData.data;
          console.log(responseData.data);
        } else {
          console.error('获取评论失败:', responseData.msg);
        }
      } catch (error) {
        console.error('获取评论请求出错:', error);
      }
    },
    async getEventIdByName() {
      const baseUrl = 'http://localhost:5173';
      try {
        const response = await fetch(`${baseUrl}/event/selectIdByName`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            name: this.event
          })
        });

        const responseData = await response.json();

        if (responseData.code === '200') {
          this.eventId = responseData.data.id;
        } else {
          console.error('获取事件 ID 失败:', responseData.msg);
        }
      } catch (error) {
        console.error('获取事件 ID 请求出错:', error);
      }
    },
    async submitComment() {
      if (!this.userId) {
        uni.showToast({
          title: '请先登录',
          icon: 'none'
        });
        return;
      }
      if (!this.eventId) {
        uni.showToast({
          title: '事件 ID 未获取到，请重试',
          icon: 'none'
        });
        return;
      }
      if (this.newComment) {
        const baseUrl = 'http://localhost:5173';
        try {
          const response = await fetch(`${baseUrl}/user/comment`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              user_id: this.userId,
              event_id: this.eventId,
              content: this.newComment
            })
          });
          const responseData = await response.json();
          if (responseData.code === '200') {
            const comment = {
              event: this.event,
              content: this.newComment
            };
            
            let allComments = uni.getStorageSync("comments") || [];
            allComments.push(comment);
            uni.setStorageSync("comments", allComments);
            this.comments.push(comment);
            this.newComment = "";
            uni.showToast({
              title: '评论提交成功',
              icon:'success'
            });
          } else {
            uni.showToast({
              title: responseData.msg || '评论提交失败',
              icon: 'none'
            });
          }
        } catch (error) {
          console.error('提交评论请求出错:', error);
          uni.showToast({
            title: '网络错误，请稍后重试',
            icon: 'none'
          });
        }
      }
    },
    goToPage(pageName) {
      uni.navigateTo({
        url: `/pages/${pageName}/${pageName}`
      });
    },
   
    async checkCollectStatus() {
      const baseUrl = 'http://localhost:5173';
      try {
        const response = await fetch(`${baseUrl}/user/selectCollect`, {//获取书签状态
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            user_id: this.userId,
            event_id: this.eventId
          })
        });
        const responseData = await response.json();
        if (responseData.code === '500') {
          this.isCollected = false;
        } else {
          this.isCollected = true;
        }
      } catch (error) {
        console.error('检查收藏状态请求出错:', error);
      }
    },
    async handleCollect() {
      if (!this.userId) {
        uni.showToast({
          title: '请先登录',
          icon: 'none'
        });
        return;
      }
      if (!this.eventId) {
        uni.showToast({
          title: '事件 ID 未获取到，请重试',
          icon: 'none'
        });
        return;
      }

      if (!this.isCollected) {
        const baseUrl = 'http://localhost:5173';
        try {
          const response = await fetch(`${baseUrl}/user/collect`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              user_id: this.userId,
              event_id: this.eventId
            })
          });
          const responseData = await response.json();

          if (responseData.code === '200') {
            this.isCollected = true;
            uni.showToast({
              title: '收藏成功',
              icon:'success'
            });
          } else {
            uni.showToast({
              title: responseData.msg || '收藏失败',
              icon: 'none'
            });
          }
        } catch (error) {
          console.error('收藏请求出错:', error);
          uni.showToast({
            title: '网络错误，请稍后重试',
            icon: 'none'
          });
        }
      }
    }
  }
};
</script>

<style scoped>
.header {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1000;
  display: flex;
  align-items: center;
  height: 60px;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  width: 100%;
}

.dropdown {
  width: 20%;
  display: flex;
}

.dropbtn {
  width: 50%;
  margin-right: 50%;
}

.header-title {
  width: 100%;
  text-align: center;
  font-size: 25px;
}

.header-search {
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  margin-top: 10px;
  margin-bottom: 10px;
  width: 20%;
}

.header-search input {
  width: 100%;
  text-align: left;
}

#comment{
	font-size:25px;
}

.eventname {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.null {
  width: 10%;
}

.text {
  width: 80%;
  text-align: center;
}

.add {
  width: 10%;
  font-size: 15px;
}

.collect {
  width: 10%;
  font-size: 15px;
  background-color: #FF9800;
  color: white;
}

.collect:active {
  background-color: #FF6F00;
  transform: scale(0.98);
}

.collected {
  width: 10%;
  font-size: 15px;
  background-color: #ccc;
  color: white;
  cursor: not-allowed;
}
.event-detail-page {
  margin-top: 30px;
  padding-left: 5px;
}

.event-detail {
  margin-top: 40px;
  padding-right: 10px;
  padding-left: 5px;
}

.comment-section {
  border-left: 1px solid #ccc;
  border-top: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
  padding: 10px;
  height: 350px;
  
}

.comment-section input {
  margin-top: 10px;
  margin-bottom: 10px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
}

.comment-section button {
  width: auto;
  font-size: 15px;
}
</style>