<template>
    <view>
        <view class="header">
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
                <p>中国朝代历史概览</p>
            </view>
            <view class="header-search">
                <input type="search" class="check" placeholder="搜索相关内容" @click="goToSearch">
            </view>
        </view>
        <view class="my">
            <view class="pic" @click="changeAvatar">
                <image :src="avatarUrl" class="avatar-img"></image>
            </view>
            <view class="profile-info">
                <view class="name">
                    <p>逆天而行</p>
                </view>
                <input 
                    class="motto-input" 
                    type="text" 
                    placeholder="请输入座右铭"
                    v-model="motto"
                    @blur="saveMotto"
                />
            </view>
        </view>
        <view class="container">
            <view class="MyBookmark">
                <h5 style="font-size: 20px; font-weight: 100;">我的书签</h5>
                <view class="bookmark-buttons">
                    <view v-for="(bookmark, index) in bookmarks" :key="index" class="bookmark-item">
                        <button @click="goToEventDetail(bookmark)" class="bookmark-btn">
                            {{ bookmark.event }}
                        </button>
                        <button @click="confirmDelete(index)" class="delete-btn">删除</button>
                    </view>
                </view>
            </view>
            <view class="Mycomment">
                <h5 style="font-size: 20px; font-weight: 100;">我的评论</h5>
                <!-- 这里可以添加展示评论的逻辑 -->
            </view>
        </view>
    </view>
</template>

<script>
export default {
    data() {
        return {
            avatarUrl: '/static/image/初始头像.jpg',
            motto: '',
            bookmarks: []
        };
    },
    methods: {
        changeAvatar() {
            uni.chooseImage({
                count: 1,
                sizeType: ['compressed'],
                sourceType: ['album'],
                success: (res) => {
                    const tempFilePath = res.tempFilePaths[0];
                    this.avatarUrl = tempFilePath;
                    uni.setStorageSync('userAvatar', tempFilePath);
                },
                fail: (err) => {
                    console.log('选择图片失败', err);
                    uni.showToast({
                        title: '选择图片失败',
                        icon: 'none'
                    });
                }
            });
        },
        saveMotto() {
            uni.setStorageSync('userMotto', this.motto);
        },
        goToSearch() {
            uni.navigateTo({
                url: '/pages/searchpage/searchpage'
            });
        },
        goToPage(pageName) {
            uni.navigateTo({
                url: `/pages/${pageName}/${pageName}`
            });
        },
        goToEventDetail(bookmark) {
            uni.navigateTo({
                url: `/pages/eventdetail/eventdetail?event=${encodeURIComponent(bookmark.event)}&eventinfo=${encodeURIComponent(bookmark.eventinfo)}&eventdetails=${encodeURIComponent(bookmark.eventdetails)}&imagePath=${encodeURIComponent(bookmark.imagePath)}&page=mine`
            });
        },
        confirmDelete(index) {
            uni.showModal({
                title: '确认删除',
                content: '是否确认删除该书签？',
                success: (res) => {
                    if (res.confirm) {
                        this.deleteBookmark(index);
                    }
                }
            });
        },
        deleteBookmark(index) {
            this.bookmarks.splice(index, 1);
            uni.setStorageSync('bookmarks', this.bookmarks);
            uni.showToast({
                title: '删除书签成功',
                icon: 'success'
            });
        }
    },
    onLoad() {
        const savedAvatar = uni.getStorageSync('userAvatar');
        if (savedAvatar) {
            this.avatarUrl = savedAvatar;
        }
        const savedMotto = uni.getStorageSync('userMotto');
        if (savedMotto) {
            this.motto = savedMotto;
        }
        this.bookmarks = uni.getStorageSync('bookmarks') || [];
    },
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

.profile-info {
    flex: 1;
    margin-left: 15px;
    display: flex;
    flex-direction: column;
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
</style>    