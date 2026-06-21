<template>
  <view>
    <!-- 头部导航栏 -->
    <view class="header" style="background-image: url('/static/image/nav.png'); background-size: cover; background-repeat: no-repeat; height: 60px;">
      <view class="dropdown">
        <button class="dropbtn">=</button>
        <view class="dropdown-content">
          <button @click="goToPage('index')">首页</button>
          <button @click="goToPage('mine')">我的</button>
          <button @click="goToPage('xia')">夏朝</button>
          <button @click="goToPage('shang')">商朝</button>
          <button @click="goToPage('zhou')">西周</button>
          <button @click="goToPage('zou')">东周</button>
          <button @click="goToPage('qin')">秦朝</button>
          <button @click="goToPage('han')">汉朝</button>
          <button @click="goToPage('three')">三国</button>
          <button @click="goToPage('jin')">西晋</button>
          <button @click="goToPage('ji')">东晋</button>
          <button @click="goToPage('six')">十六国</button>
          <button @click="goToPage('bei')">北朝</button>
          <button @click="goToPage('nan')">南朝</button>
          <button @click="goToPage('sui')">隋朝</button>
          <button @click="goToPage('tang')">唐朝</button>
          <button @click="goToPage('five')">五代十国</button>
          <button @click="goToPage('song')">宋朝</button>
          <button @click="goToPage('liao')">辽朝</button>
          <button @click="goToPage('xa')">西夏</button>
          <button @click="goToPage('jing')">金朝</button>
          <button @click="goToPage('yuan')">元朝</button>
          <button @click="goToPage('ming')">明朝</button>
          <button @click="goToPage('qing')">清朝</button>
        </view>
      </view>
      <view class="header-title">
        <p>青编玉牒</p>
      </view>
      <view class="header-search">
        <input type="search" class="check" placeholder="搜索相关内容" @click="goToSearch">
      </view>
    </view>
    <!-- 个人信息区域 -->
    <view class="my">
      <view class="pic" @click="changeAvatar">
        <image :src="avatarUrl" class="avatar-img"></image>
      </view>
      <view class="profile-info">
        <view class="name">
          <p>{{ userInfo.name }}</p> <!-- 显示用户名 -->
        </view>
      </view>
    
	<view class=".profile-button">
	    <view class="logout-btn" @click="logout">退出登录</view>
	    <view class="update-btn" @click="openUpdateModal">修改用户名和密码</view>
	  </view>
	</view>
    <!-- 修改用户名和密码的模态框 -->
    <view v-if="isUpdateModalVisible" class="update-modal">
      <view class="modal-content">
        <h3>修改用户名和密码</h3>
        <input v-model="newUserName" type="text" placeholder="请输入新用户名">
        <input v-model="newPassword" type="password" placeholder="请输入新密码">
        <button @click="updateUserInfo">确定</button>
        <button @click="closeUpdateModal">取消</button>
      </view>
    </view>
    <!-- 主要内容区域 -->
    <view class="container">
      <!-- 我的书签区域 -->
      <view class="MyBookmark">
        <h5 style="font-size: 20px; font-weight: 100;">我的书签</h5>
        <view class="bookmark-buttons">
          <view v-for="(bookmark, index) in bookmarkNames" :key="index" class="bookmark-item">
            <button  class="bookmark-btn">
              {{ bookmark }}
            </button>
            <button @click="removeBookmark(index)" class="delete-btn">删除</button>
          </view>
        </view>
      </view>
      <!-- 我的评论区域 -->
      <view class="Mycomment">
        <h5 style="font-size: 20px; font-weight: 100;">我的评论</h5>
        <view v-for="(group, groupIndex) in groupedComments" :key="groupIndex">
          <div class="comment-event-title">{{ group.eventName }}</div>
          <ul>
            <li v-for="(comment, commentIndex) in group.comments" :key="commentIndex">
              <span>{{ comment.content }}</span>
              <button @click="deleteComment(comment.id)">删除</button> <!-- 修改为传递评论id -->
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
      avatarUrl: "/static/image/初始头像.jpg",
      userInfo: {},
      userComments: {},
      groupedComments: [],
      bookmarkNames: [], // 用于存储书签事件的名称
      userId: null,
      bookmarkIds: [], // 用于存储书签事件的 ID
      isUpdateModalVisible: false, // 控制修改模态框的显示状态
      newUserName: '', // 新用户名
      newPassword: '' // 新密码
    };
  },
  onLoad() {
    this.userId = uni.getStorageSync('userId'); // 获取用户id
    if (this.userId) {
      this.getUserInfo();
      this.getUserComments();
      this.getBookmarkedEventIds(); // 加载页面时获取已加入书签的事件id
    } else {
      console.log('用户 ID 未获取到');
    }
  },
  methods: {
    // 修改头像方法
    changeAvatar() {
      uni.chooseImage({
        count: 1,
        sizeType: ["compressed"],
        sourceType: ["album"],
        success: (res) => {
          const tempFilePath = res.tempFilePaths[0];
          this.avatarUrl = tempFilePath;
          uni.setStorageSync("userAvatar", tempFilePath);
        },
        fail: (err) => {
          console.log("选择图片失败", err);
          uni.showToast({
            title: "选择图片失败",
            icon: "none"
          });
        }
      });
    },
    // 跳转到搜索页面方法
    goToSearch() {
      uni.navigateTo({
        url: "/pages/searchpage/searchpage"
      });
    },
    // 通用页面跳转方法
    goToPage(pageName) {
      uni.navigateTo({
        url: `/pages/${pageName}/${pageName}`
      });
    },
    
    // mine.vue 中的 goToEventDetail 方法
    /*goToEventDetail(eventIndex) {
      const eventId = this.bookmarkIds[eventIndex];
      const eventName = this.bookmarkNames[eventIndex];
      uni.navigateTo({
        url: `/pages/eventdetail/eventdetail?eventId=${eventId}&eventName=${encodeURIComponent(eventName)}&page=mine`
      });
    },*/
    async getUserInfo() {
      if (!this.userId) {
        console.log('用户 ID 未获取到');
        return;
      }
      const baseUrl = 'http://localhost:5173';
      try {
        const response = await fetch(`${baseUrl}/user/selectById`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            id: this.userId
          })
        });
        const responseData = await response.json();
        if (responseData.code === '200') {
          this.userInfo = responseData.data; // 假设接口返回的用户信息在 data 字段中
        } else {
          console.error('获取用户信息失败:', responseData.msg);
        }
      } catch (error) {
        console.error('获取用户信息请求出错:', error);
      }
    },
    async getUserComments() {
      if (!this.userId) {
        console.log('用户 ID 未获取到');
        return;
      }
      const baseUrl = 'http://localhost:5173';
      try {
        // 获取用户评论
        const response = await fetch(`${baseUrl}/user/selectCommentsById`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            id: this.userId
          })
        });
        const responseDataComments = await response.json();
        if (responseDataComments.code === '200') {
          this.userComments = responseDataComments.data;
          const commentGroups = {};
          for (const comment of this.userComments) {
            const eventId = comment.event_id;
            // 根据事件 ID 获取事件名
            const eventNameResponse = await fetch(`${baseUrl}/event/selectNameById`, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify({
                id: eventId
              })
            });
            const eventNameData = await eventNameResponse.json();
            if (eventNameData.code === '200') {
              const eventName = eventNameData.data.name;
              if (!commentGroups[eventId]) {
                commentGroups[eventId] = {
                  eventId: eventId,
                  eventName: eventName,
                  comments: []
                };
              }
              commentGroups[eventId].comments.push(comment);
            } else {
              console.error('获取事件名失败:', eventNameData.msg);
            }
          }
          this.groupedComments = Object.values(commentGroups);
        } else {
          console.error('获取用户评论失败:', responseDataComments.msg);
        }
      } catch (error) {
        console.error('获取用户评论请求出错:', error);
      }
    },
    // 删除评论方法
    async deleteComment(commentId) { 
      const baseUrl = 'http://localhost:5173';
      uni.showModal({
        title: "确认删除",
        content: "是否确认删除该评论？",
        success: async (res) => {
          if (res.confirm) {
            try {
              const response = await fetch(`${baseUrl}/user/deleteCommentById`, {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                  id: commentId
                })
              });
              const responseData = await response.json();
              if (responseData.code === '200') {
                // 成功删除后，重新获取用户评论
                await this.getUserComments();
              } else {
                console.error('删除评论失败:', responseData.msg);
              }
            } catch (error) {
              console.error('删除评论请求出错:', error);
            }
          }
        }
      });
    },
    async getBookmarkedEventIds() {
      const baseUrl = 'http://localhost:5173';
      try {
        const response = await fetch(`${baseUrl}/user/selectCollectIdsById`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            id: this.userId
          })
        });
        const responseData = await response.json();
        if (responseData.code === '200') {
          const eventIds = responseData.data; 
          this.bookmarkIds = eventIds;
          // 根据获取到的事件id去获取事件名并更新bookmarkNames数组
          await this.getBookmarkNames(eventIds);
        } else {
          console.error('获取已加入书签的事件id失败:', responseData.msg);
        }
      } catch (error) {
        console.error('获取已加入书签的事件id请求出错:', error);
      }
    },
    async getBookmarkNames(eventIds) {
      const baseUrl = 'http://localhost:5173';
      const names = [];
      for (const eventId of eventIds) {
        try {
          const response = await fetch(`${baseUrl}/event/selectNameById`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              id: eventId
            })
          });
          const responseData = await response.json();
          if (responseData.code === '200') {
            names.push(responseData.data.name);
          } else {
            console.error('获取事件名失败:', responseData.msg);
          }
        } catch (error) {
          console.error('获取事件名请求出错:', error);
        }
      }
      this.bookmarkNames = names;
    },
    async removeBookmark(index) {
      const eventId = this.bookmarkIds[index];
      const baseUrl = 'http://localhost:5173';
      try {
        const response = await fetch(`${baseUrl}/user/deleteCollection`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            user_id: this.userId,
            event_id: eventId
          })
        });
        const responseData = await response.json();
        if (responseData.code === '200') {
          // 成功删除后，更新书签列表
          this.bookmarkNames.splice(index, 1);
          this.bookmarkIds.splice(index, 1);
        } else {
          console.error('删除书签失败:', responseData.msg);
        }
      } catch (error) {
        console.error('删除书签请求出错:', error);
      }
    },
    logout() {
      uni.removeStorageSync('userId'); // 清除用户 ID
      uni.removeStorageSync('userInfo'); // 清除其他用户信息
      uni.showToast({ title: '登出成功', icon: 'success' });
      uni.navigateTo({ url: '/pages/login/login' });
    },
    openUpdateModal() {
      this.isUpdateModalVisible = true;
    },
    closeUpdateModal() {
      this.isUpdateModalVisible = false;
    },
    async updateUserInfo() {
      const baseUrl = 'http://localhost:5173';
      try {
        const response = await fetch(`${baseUrl}/user/updateById`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            id: this.userId,
            name: this.newUserName,
            password: this.newPassword
          })
        });
        const responseData = await response.json();
        if (responseData.code === '200') {
          uni.showToast({ title: '修改成功', icon: 'success' });
          this.closeUpdateModal();
          // 更新用户信息显示
          this.getUserInfo();
        } else {
          console.error('修改用户信息失败:', responseData.msg);
          uni.showToast({ title: '修改失败', icon: 'none' });
        }
      } catch (error) {
        console.error('修改用户信息请求出错:', error);
        uni.showToast({ title: '请求出错', icon: 'none' });
      }
    }
  }
};
</script>

<style scoped>
.pic {
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
  width: 75px;
  height: 75px;
  margin-left: 10px;
  border: 2px solid #ddd;
}

.avatar-img {
  width: 100%;
  height: 100%;
  display: block;
}

.pic:active {
  opacity: 0.8;
  transform: scale(0.95);
  transition: all 0.2s;
}

template {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
}

.header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #f1f1f1;
  display: flex;
  align-items: center;
  height: 60px;
  z-index: 999;
  box-sizing: border-box;
}

.dropdown {
  width: 20%;
  background-color: #f1f1f1;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 10%;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
  max-height: 415px;
  overflow-y: auto;
}

.dropdown-content a {
  color: black;
  padding: 8px 10px;
  text-decoration: none;
  display: block;
  text-align: center;
}

.dropdown-content a:hover {
  background-color: #f1f1f1;
}

.dropdown:hover .dropdown-content {
  display: block;
}

.dropdown:hover .dropbtn {
  background-color: #f1f1f1;
}

.dropbtn {
  height: 30px;
}

.header-title {
  width: 100%;
  text-align: center;
  font-size: 25px;
  font-weight: normal;
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

.my {
  position: fixed;
  width: 100%;
  background-color: #f1f1f1;
  display: flex;
  margin-top: 20px;
  height: 65px;
  z-index: 0;
}

.name {
  margin-top: 20px;
  margin-left: 5px;
}

.my {
  align-items: flex-start;
  padding: 10px;
  height: auto;
}



.motto-input {
  width: 50%;
  padding: 6px 12px;
  margin-top: 8px;
  border: 1px solid #e0e0e0;
  border-radius: 16px;
  font-size: 12px;
  color: #666;
  background: #fff;
  transition: all 0.3s;
}

.motto-input:focus {
  border-color: #007AFF;
  box-shadow: 0 0 5px rgba(0, 122, 255, 0.2);
  outline: none;
}

.container {
  margin-top: 125px;
  position: fixed;
  width: 100%;
  display: flex;
  height: 100%;
  z-index: 0;
}

.MyBookmark {
  width: 45%;
  background-color: #f1f1f1;
}

.Mycomment {
  width: 54%;
  background-color: #f1f1f1;
  margin-left: 1%;
}

.bookmark-btn {
  width: calc(100% - 10px);
  margin: 5px;
  font-size: 14px;
  white-space: normal;
  word-wrap: break-word;
}

.delete-btn {
  background-color: indianred;
  width: 40%;
  margin: 10px auto;
  font-size: 12px;
  padding: 1px 8px;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.bookmark-item {
  width: calc(25% - 10px);
  margin: 5px;
  display: flex;
  flex-direction: column;
}

.bookmark-buttons {
  display: flex;
  flex-wrap: wrap;
}

.comment-event-title {
  text-align: center;
  font-size: 18px;
  margin-bottom: 10px;
  background-color: #ddd;
  padding: 5px;
}

.logout-btn {
  background-color: #ff4d4f;
  color: white;
  padding: 6px 12px;
  border-radius: 4px;
  text-align: center;
  cursor: pointer;
  width: fit-content; 
  margin: 5px 0 0 5px; 
  font-size: 14px; 
  font-weight: 500; 
  position: relative; 
  transition: background-color 0.3s ease; 
}

.logout-btn:hover {
  background-color: #ff7875; 
}

.profile-info {
  flex: 1;
  margin-top: 25px;
  margin-left: 15px;
  display: flex;
  flex-direction: column; /* 改为垂直排列 */
  justify-content: center; /* 垂直方向居中对齐，可按需调整 */
  font-size: 20px;
}

.profile-button {
  flex: 1;
  margin-right: 50px;
  display: flex;
  flex-direction: column; 
  justify-content: center; 
}

.name {
  margin-top: 0; /* 取消顶部外边距 */
  margin-left: 5px;
  flex: 1; /* 让用户名区域占据剩余空间 */
}

.update-btn {
  background-color: #1890ff; 
  color: white; 
  padding: 6px 12px; 
  border-radius: 4px; 
  cursor: pointer; 
  width: fit-content; 
  margin: 0 5px; 
  font-size: 14px; 
  font-weight: 500; 
  position: relative; 
  transition: background-color 0.3s ease; /* 背景颜色过渡效果 */
}

.update-btn:hover {
  background-color: #40a9ff; /* 鼠标悬停时的背景颜色 */
}
</style>    