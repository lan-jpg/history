<template>
    <view class='first' >
        <view class="header" style="background-image: url('/static/image/nav.png'); background-size: cover; background-repeat: no-repeat; height: 60px;">
            <view class="dropdown">
                <button class="dropbtn">=</button>
                <view class="dropdown-content">
                    <button @click="goToPage('index')">首页</button>
                    <button @click="goToPage('mine')">我的</button>
                </view>
            </view>
            <view class="header-title">
                <p>青编玉牒</p>
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

        <!-- 二级导航栏，根据政权数量动态显示 -->
               <view v-if="powers.length >= 1" class="secondary-landscape">
                   <button 
                       v-for="(power, index) in powers" 
                       :key="index"
                       :class="{ 'active': currentPowerIndex === index }"
                       @click="switchPower(index)"
                   >
				   {{power.name}}
                   </button>
               </view>
		<view class="separator"></view> 
        <view class="monarch">
            <view v-for="(person, index) in currentMonarchData" 
                  :key="index"
                  class="in-view">
                <view @mouseover="shownamebasic(index)" @mouseout="hideMonarchInfo" class='name'>
                    <text>{{ person.name }}<br>一一一一一<br></text>
					<text id = persontime>{{ person.year }}</text>
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
            Page: 'xia',
            monarchData: [],
            showMonarchInfo: null,
            showEventInfo: null,
            powers: [], // 存储政权信息
            currentPowerIndex: 0, // 当前选中的政权索引
            currentMonarchData: [] // 当前政权的人物数据
        };
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
            const page = this.Page;
            const event = this.currentMonarchData[monarchIndex].event[eventIndex];
            const eventinfo = this.currentMonarchData[monarchIndex].eventInfo[eventIndex];
            const eventdetails = this.currentMonarchData[monarchIndex].eventdetails[eventIndex];
            const imagePath = this.currentMonarchData[monarchIndex].imagePath[eventIndex];
            // 传递图片路径参数给事件详情页面
            uni.navigateTo({
                url: `/pages/eventdetail/eventdetail?page=${encodeURIComponent(page)}&eventinfo=${encodeURIComponent(eventinfo)}&eventdetails=${encodeURIComponent(eventdetails)}&imagePath=${encodeURIComponent(imagePath)}&event=${encodeURIComponent(event)}`
            });
        },
        goToPage(pageName) {
            uni.navigateTo({
                url: `/pages/${pageName}/${pageName}`
            });  
        },
        switchPower(index) {
            this.currentPowerIndex = index;
            this.updateCurrentMonarchData();
        },
        updateCurrentMonarchData() {
            const currentPowerId = this.powers[this.currentPowerIndex].id;
            this.currentMonarchData = this.monarchData.filter(person => person.powerId === currentPowerId);
        },
        async fetchMonarchDataByDynastyName(dynastyName) {
            const baseUrl = 'http://localhost:5173';
            try {
                // 第一步：通过朝代名获取朝代 ID
                const dynastyIdResponse = await fetch(`${baseUrl}/dynasty/selectIdByName`, {  
                    method: 'POST',  
                    headers: {  
                        'Content-Type': 'application/json' 
                    },
                    mode:'cors',
                    credentials:'include',
                    body: JSON.stringify({ name: dynastyName }) 
                });
                if (!dynastyIdResponse.ok) {  
                    throw new Error(`HTTP error! status: ${dynastyIdResponse.status}`);  
                }
                const dynastyIdData = await dynastyIdResponse.json();
                const dynastyId = dynastyIdData.data.id;

                // 第二步：通过朝代 ID 获取所属的政权 ID
                const powersResponse = await fetch(`${baseUrl}/dynasty/selectPowersById`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ id: dynastyId })
                });
                if (!powersResponse.ok) {
                    throw new Error('网络响应不正常');
                }
                const powersData = await powersResponse.json();
                const powerIds = powersData.data.map(power => power.id);
                // 获取每个政权的名字
                const powerNamePromises = powerIds.map(async powerId => {
                    const powerNameResponse = await fetch(`${baseUrl}/power/selectNameById`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({ id: powerId })
                    });
					const powerNameData = await powerNameResponse.json();
                    if (powerNameData.code!='200') {
                        throw new Error('获取政权名时网络响应不正常');
                    }
					
                    return {
                        id: powerId,
                        name: powerNameData.data.name
                    };
                });
                this.powers = await Promise.all(powerNamePromises);
                // 第三步：通过政权 ID 获取所属的所有人物
                const personRequests = powerIds.map(powerId => {
                    return fetch(`${baseUrl}/power/selectPersonsById`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({ id: powerId })
                    });
                });
                const personResponses = await Promise.all(personRequests);
                const personDataList = await Promise.all(personResponses.map(response => {
                    if (!response.ok) {
                        throw new Error('网络响应不正常');
                    }
                    return response.json();
                }));
                const allPersons = [].concat(...personDataList.map(item => item.data));

                // 第四步：为每个人物获取详细信息
                const detailedPersons = await Promise.all(allPersons.map(async person => {
                    const personId = person.id;
                    // 获取人物所在的政权 id
                    const powerIdResponse = await fetch(`${baseUrl}/person/selectPowerById`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({ id: personId })
                    });
                    const powerIdData = await powerIdResponse.json();
                    if (!powerIdResponse.ok) {
                        throw new Error('获取人物所在政权 ID 时网络响应不正常');
                    }

                    const powerId = powerIdData.data.id;

                    // 获取在位时间
                    const yearResponse = await fetch(`${baseUrl}/person/selectTimeById`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({ id: personId })
                    });
                    const yearData = await yearResponse.json();
                    if (yearData.code!== '200') {
                        throw new Error(yearData.code + ' ' + yearData.msg);
                    }
                    const year = yearData.data.time;

                    // 获取事件
                    const eventResponse = await fetch(`${baseUrl}/person/selectEventsById`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({ id: personId })
                    });
                    const eventData = await eventResponse.json();
                    const events = eventData.data;
                    if (!events || events.length === 0) {
                        return {
                            name: person.name,
                            year,
                            event: [],
                            introduction: person.introduction,
                            eventInfo: [],
                            eventdetails: [],
                            imagePath: [],
                            powerId
                        };
                    }

                    // 获取事件简介和详情
                    const eventInfoPromises = events.map(async event => {
                        const eventId = event.id;
                        // 获取事件简介
                        const eventInfoResponse = await fetch(`${baseUrl}/event/selectIntroductionById`, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({ id: eventId })
                        });
                        const eventInfoData = await eventInfoResponse.json();
                        if (eventInfoData.code!== '200') {
                            throw new Error(eventInfoData.code + ' ' + eventInfoData.msg);
                        }
                        const eventInfo = eventInfoData.data.introduction;
                        // 获取事件详情
                        const eventDetailsResponse = await fetch(`${baseUrl}/event/selectDetailById`, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({ id: eventId })
                        });
                        const eventDetailsData = await eventDetailsResponse.json();
                        if (eventDetailsData.code!== '200') {
                            throw new Error(eventDetailsData.code + ' ' + eventDetailsData.msg);
                        }
                        const eventDetails = eventDetailsData.data.detail;

                        return { eventInfo, eventDetails };
                    });
                    const eventInfoResults = await Promise.all(eventInfoPromises);
                    const eventInfoList = eventInfoResults.map(result => result.eventInfo);
                    const eventDetailsList = eventInfoResults.map(result => result.eventDetails);

                    return {
                        name: person.name,
                        year,
                        event: events.map(event => event.name),
                        introduction: person.introduction,
                        eventInfo: eventInfoList,
                        eventdetails: eventDetailsList,
                        imagePath: [],
                        powerId
                    };
                }));

                this.monarchData = detailedPersons;
                this.updateCurrentMonarchData();
            } catch (error) {
                console.error('请求出错:', error);
            }
        }
    },
    mounted() {
        this.fetchMonarchDataByDynastyName('五代十国');
    }
};
</script>

<style scoped>

.separator {  
    height: 2px;  
    background-color: #ccdacc;  
}  

.header {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1000;
    display: flex;
    align-items: center;
    height: 60px; 
    background-color: #e6f0e6;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    width: 100%;
}

/* 横向导航栏样式 */
.landscape {
    width: 100%;
    display: flex;
    position: fixed;
    top: 60px; 
    overflow-x: auto;
    white-space: nowrap;
    padding: 0;
    background-color: #e6f0e6;
    z-index: 999;
}

/* 滚动条整体部分 */  
.landscape::-webkit-scrollbar {  
  height: 8px; /* 垂直滚动条时的高度 */  
  width: 8px; /* 水平滚动条的宽度 */  
}  

/* 滚动条滑块部分 */  
.landscape::-webkit-scrollbar-thumb {  
  background-color: #a0c4b1; /* 设置滑块颜色，这里用浅绿色调，可以替换 */  
  border-radius: 4px; /* 让滑块圆角，看起来更美观 */  
}  

/* 滚动条轨道部分 */  
.landscape::-webkit-scrollbar-track {  
  background-color: #e6f0e6; /* 轨道背景色，保持一致或做微调 */  
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
    background-color: #e6f0e8;
}

/* 二级导航栏样式 */
.secondary-landscape {
    width: 100%;
    display: flex;
    position: fixed;
    top: 115px; 
    overflow-x: auto;
    white-space: nowrap;
    padding: 0;
    background-color: #e6f0e6;
    z-index: 998;
}

.secondary-landscape button {
    min-width: 110px; 
    padding: 18px 16px; 
	
    font-size: 14px; 
    white-space: normal; 
    word-wrap: break-word; 
    text-align: center;
    border: 1px solid #C0C0C0;
    background-color: transparent;
    cursor: pointer;
    
	align-items: center;
}

.secondary-landscape button.active {
    background-color: #ccdacc;
}

.secondary-landscape button:hover {
    background-color: #f1f1f1;
}

#persontime{
	font-size: 12px;
}

.monarch {
    margin-top: 140px;
    overflow-x: auto;
    white-space: nowrap;
    padding: 0;
	background-color: #e6f0e8;
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

.info-box {
    position: absolute;
    background-color: #fff;
    border: 1px solid #ccc;
    padding: 10px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    z-index: 100;
    max-width: 40%;
    white-space: normal; 
}
</style>