<template>
    <view>
        <view class="header">
            <view class="dropdown">
                <button class="dropbtn">=</button>
                <view class="dropdown-content">
                    <button @click="goToPage('index')">首页</button>
                    <button @click="goToPage('mine')">我的</button>
                </view>
            </view>
            <view class="header-title">
                <p>中国朝代历史概览</p>
            </view>
            <view class="header-search">
                <input type="search" class="check" placeholder="搜索相关内容" @click="goToPage('searchpage')">
            </view>
        </view>

        <!-- 横向导航栏 -->
        <view class="landscape">
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
        <view class="monarch">
            <view v-for="(person, index) in monarchData" 
                  :key="index"
                  class="in-view">
				<view @mouseover="shownamebasic(index)" @mouseout="hideMonarchInfo" class='name'>
					<text>{{ person.name }}<br>一一一一一<br>{{ person.year }}</text>
					<view v-if="showMonarchInfo === index" class="info-box">{{ person.introduction }}</view>
				</view>
                <view v-for="(eventItem, eventIndex) in person.event" :key="eventIndex">
                    <button class="eventbtn" @mouseover="showeventbasic(index, eventIndex)" @mouseout="hideEventInfo" @click="showeventdetail(index, eventIndex)">{{eventItem}}</button>
                    <view v-if="showEventInfo === `${index}-${eventIndex}`" class="info-box">{{ person.eventInfo[eventIndex] }}</view>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                monarchData: [
                    { 
                        name: '禹', 
                        year: '生卒年不详', 
                        event: ['大禹治水', '建立夏朝'],
                        introduction: '禹是夏朝的开国君主，他最著名的功绩是治水，三过家门而不入，成功治理了水患，后建立夏朝。',
                        eventInfo: ['大禹治水是中国古代的神话传说故事，大禹率领民众，与自然灾害中的洪水斗争，最终获得了胜利。', '禹建立夏朝，标志着中国历史上第一个王朝的诞生，开启了家天下的时代。'],
                        eventdetails: ['大禹治水', '禹建立夏朝'],
                        imagePath: ['/static/image/tang.jpg','/static/image/song.jpg'] 
                    },
                    { 
                        name: '姒启', 
                        year: '生卒年不详', 
                        event: ['巩固夏朝统治'],
                        introduction: '姒启是禹的儿子，他继承了禹的王位，巩固了夏朝的统治，使世袭制得以确立。',
                        eventInfo: ['姒启在继承王位后，通过一系列措施巩固了夏朝的统治，如平定内乱等。'],
                        eventdetails: ['姒启'],
                        imagePath: ['/static/image/yuan.jpg'] 
                    },
                    // 其他君主数据...
                ],
                showMonarchInfo: null,
                showEventInfo: null
            }
        },
        methods: {
            shownamebasic(index) {
                this.showMonarchInfo = index;
            },
            hideMonarchInfo() {
                this.showMonarchInfo = null;
            },
            showeventbasic(monarchIndex, eventIndex) {
                this.showEventInfo = `${monarchIndex}-${eventIndex}`;
            },
            hideEventInfo() {
                this.showEventInfo = null;
            },
            showeventdetail(monarchIndex, eventIndex) {
				const event = this.monarchData[monarchIndex].event[eventIndex];
				const eventinfo = this.monarchData[monarchIndex].eventInfo[eventIndex];
                const eventdetails = this.monarchData[monarchIndex].eventdetails[eventIndex];
                const imagePath = this.monarchData[monarchIndex].imagePath[eventIndex];
                // 传递图片路径参数给事件详情页面
                uni.navigateTo({
                    url: `/pages/eventdetail/eventdetail?eventinfo=${encodeURIComponent(eventinfo)}&eventdetails=${encodeURIComponent(eventdetails)}&imagePath=${encodeURIComponent(imagePath)}&event=${encodeURIComponent(event)}`
                });
            },
            goToPage(pageName) {
                uni.navigateTo({
                    url: `/pages/${pageName}/${pageName}`
                });  
            }
        }
    };
</script>

<style scoped>
/* 修复header显示问题 */
.header {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1000;
    display: flex;
    align-items: center;
    height: 60px; /* 增加header的高度以适应变大的标题 */
    background: #fff;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    width: 100%;
}

/* 横向导航栏样式 */
.landscape {
    width: 100%;
    display: flex;
    position: fixed;
    top: 60px; /* 根据header高度调整横向导航栏的位置 */
    overflow-x: auto;
    white-space: nowrap;
    padding: 0;
    background-color: #fff;
    z-index: 999;
}

.landscape button {
    min-width: 120px; 
    padding: 12px 16px; 
    font-size: 16px; 
    white-space: normal; 
    word-wrap: break-word; 
    text-align: center;
    border: none;
    background-color: transparent;
    cursor: pointer;
    transition: background-color 0.3s;
}

.landscape button:hover {
    background-color: #f1f1f1;
}

.monarch {
    margin-top: 90px;
    overflow-x: auto;
    white-space: nowrap;
    padding: 0;
}

.name{
	margin-left: 5px;
    width: 80px;
}

.eventbtn {
    margin-left: 40px;
    width: 100px;
    height: 45px;
    font-size: 12px;
}

/* 导航按钮样式 */
.nav-btn {
    flex: 0 0 auto;
    margin: 0px;
    padding: 4px 3.7%;
    transition: all 0.3s;
    font-size: 14px;
}

.in-view {
    flex: 0 0 auto;
    margin: 0px;
    padding: 20px 5px;
    transition: all 0.3s;
    font-size: 14px;
    display: flex;
    align-items: center;
}

.nav-btn:active {
    color: white;
    transform: scale(0.95);
}

template {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
}

.dropdown {
    width: 20%;
}

.dropdown-content {
    display: none;
    position: absolute;
    min-width: 10%;
    z-index: 1;
}

.dropdown:hover .dropdown-content {
    display: block;
}

.dropbtn{
    height: 30px;
}

.header-title {
    width: 100%;
    text-align: center;
    font-size: 20px; /* 增大标题的字体大小 */
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

.info-box {
    position: absolute;
    background-color: #fff;
    border: 1px solid #ccc;
    padding: 10px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    z-index: 100;
    max-width: 40%;
    white-space: normal; /* 允许文本换行 */
}
</style>