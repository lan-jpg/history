<template>
  <view>
    <!-- 头部 -->
    <view class="header">
      <view class="dropdown">
        <button class="dropbtn" aria-haspopup="true" :aria-expanded="showDropdown">=</button>
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
        <input 
          type="search" 
          class="check" 
          placeholder="搜索相关内容" 
          @click="goToSearch"
        >
      </view>
    </view>

    <!-- 内容区域 -->
    <view class="content-container">
      <view class="container">
        <view 
          class="pic"
          v-for="dynasty in dynasties"
          :key="dynasty.path"
        >
          <view 
            @click.prevent="goToDynasty(dynasty.path)"
            role="button"
            :aria-label="`查看${dynasty.name}朝代详情`"
          >
            <view class="image-wrapper">
              <img
                :src="dynasty.image"
                :alt="`${dynasty.name}朝代代表性图片`"
                class="image"
                @mousemove="showIntro($event, dynasty.intro)"
                @mouseout="hideIntro"
                loading="lazy"
              />
              <span class="dynasty-text">{{ dynasty.name }}</span>
            </view>
          </view>
        </view>
      </view>
    </view>
    <view v-if="showIntroBox" class="intro-box" :style="{ left: introX + 'px', top: introY + 'px' }">
      {{ introText }}
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      showDropdown: false,
      dynasties: [
        { name: '夏朝', path: 'xia', image: '/static/image/xia.jpg', intro: '夏朝是中国史书中记载的第一个世袭制朝代。一般认为夏朝共传十四代，十七后 ，延续约471年，为商朝所灭。后人常以“华夏”自称，使之成为中国的代名词。' },
        { name: '商朝', path: 'shang', image: '/static/image/shang.jpg', intro: '商朝是中国历史上的第二个朝代，是中国第一个有直接的同时期的文字记载的王朝。商朝经历了三个大的阶段。第一阶段是“先商”；第二阶段是“早商”；第三阶段是“晚商”，前后相传17世31王，延续500余年。' },
        { name: '西周', path: 'zhou', image: '/static/image/zhou.jpg', intro: '西周是由周文王之子周武王灭商后所建立，至公元前771年周幽王被申侯和犬戎所杀为止，共经历11代12王，大约历经270年。定都于镐京和丰京（今陕西西安西南），成王五年营建东都成周洛邑（今河南洛阳）。' },
        { name: '东周', path: 'zou', image: '/static/image/zou.jpg', intro: '东周是中国历史上的朝代，诸侯拥立原先被废的太子宜臼为王，史称周平王，建立东周， 即位第二年定都洛邑，史称“东周”，以别于西周。东周的前半期，诸侯争相称霸，称为“春秋时代”；东周的后半期，周天子名存实亡，各诸侯相互征伐，称为“战国时代”。' },
        { name: '秦朝', path: 'qin', image: '/static/image/qin.jpg', intro: '秦朝是由战国时期的秦国发展起来的中国历史上第一个大一统王朝，秦人的祖先大费是黄帝之孙颛顼的后裔，舜赐其嬴姓。秦穆公时，任贤使能，虚心纳谏，灭国十二，开地千里，国力日盛。前361年，秦孝公继位，重用商鞅两次变法，使秦国的经济得到发展，军队战斗力不断加强，发展成为战国后期最富强的诸侯国。' },
        { name: '汉朝', path: 'han', image: '/static/image/han.jpg', intro: '汉朝是继秦朝之后的大一统王朝，主要分为西汉、东汉时期，共历29帝，享国四百零五年。秦末农民起义后，刘邦击败众多对手，于公元前202年二月初三，在汜水之北的定陶（今山东菏泽市定陶区）称帝，建国号为汉。' },
        { name: '三国', path: 'three', image: '/static/image/three.jpg', intro: '三国（220年－280年）是上承东汉下启西晋的一段历史时期，分为曹魏、蜀汉、东吴三个政权。赤壁之战时，曹操被孙刘联军击败，奠定了三国鼎立的雏型。' },
        { name: '西晋', path: 'jin', image: '/static/image/jin.jpg', intro: '西晋是中国历史上三国时期之后的统一王朝，首都洛阳。从晋武帝建国开始，传四帝，国祚51年，与后来的东晋合称晋朝。西晋是魏晋南北朝长期分裂时期中的短暂统一，所谓“昙花一现”。' },
        { name: '东晋', path: 'ji', image: '/static/image/ji.jpg', intro: '东晋是由西晋皇族司马睿南迁后建立起来的王朝。此外，史书中又仿东汉称中汉，称东晋为中晋，寓以晋室中兴之意；又东晋统治地区大部分在江东，古称江左，因此以江左代指东晋。' },
        { name: '十六国', path: 'six', image: '/static/image/six.jpg', intro: '十六国是中国历史上的一段大分裂时期。该时期自304年李雄和刘渊分别在汉地巴蜀建立成国（成汉）、在中原建立汉赵（前赵）时起，至439年北魏太武帝拓跋焘灭北凉为止。' },
        { name: '北朝', path: 'bei', image: '/static/image/bei.jpg', intro: '北朝是中国历史上与南朝同时代的北方王朝的总称，其中包括了北魏、东魏、西魏、北齐、北周等数个王朝。北朝结束了我国从八王之乱起将近一百五十年的中原混战的局面。后世的隋唐两朝都是继承了北朝，他们的开国皇帝们的祖先都是北朝名贵，并且又从军事和政治制度等各个领域都沿袭北朝并加以更好的发展和创新。' },
        { name: '南朝', path: 'nan', image: '/static/image/nan.jpg', intro: '南朝是东晋灭亡之后隋朝统一之前存在于中国南方以建康（今南京）为都城的四个朝代的总称。南朝上承东晋、五胡十六国，下接隋朝，由公元420年刘裕代东晋建立刘宋始，至公元589年隋灭陈而终。' },
        { name: '隋朝', path: 'sui', image: '/static/image/sui.jpg', intro: '隋朝是中国历史上承南北朝，下启唐朝的大一统朝代，享国三十八年。公元581年二月，北周静帝禅让于丞相杨坚，北周覆亡。隋文帝杨坚定国号为“隋”，定都大兴城（今陕西省西安市）。' },
        { name: '唐朝', path: 'tang', image: '/static/image/tang.jpg', intro: '唐朝是继隋朝之后的大一统中原王朝，共历二十一帝，享国二百八十九年。隋末天下群雄并起，617年唐国公李渊于晋阳起兵，次年称帝建立唐朝，定都长安。唐太宗继位后开创贞观之治，为盛唐奠定基础。唐高宗承贞观遗风开创“永徽之治”。' },
        { name: '五代十国', path: 'five', image: '/static/image/five.jpg', intro: '五代十国是中国历史上的一段大分裂时期。这一称谓出自《新五代史》，是对五代（907年—960年）与十国（902年—979年）的合称。' },
        { name: '宋朝', path: 'song', image: '/static/image/song.jpg', intro: '宋朝是中国历史上承五代十国下启元朝的朝代，分北宋和南宋两个阶段，共历十八帝，享国三百一十九年。960年，后周诸将发动陈桥兵变，拥立宋州归德军节度使赵匡胤为帝，建立宋朝。' },
        { name: '辽朝', path: 'liao', image: '/static/image/liao.jpg', intro: '辽朝是中国历史上由契丹族建立的朝代，共传九帝，享国二百一十八年。907年，辽太祖耶律阿保机成为契丹部落联盟首领，916年始建年号，建国号“契丹”，定都上京临潢府（今内蒙古赤峰市巴林左旗南波罗城）。' },
        { name: '西夏', path: 'xa', image: '/static/image/xa.jpg', intro: '西夏是中国历史上由党项人在中国西北部建立的朝代，自称邦泥定国或大白高国。因其在西方，宋人称之为西夏。前期和辽、北宋，后期与金朝并立。历经十帝，享国189年。' },
        { name: '金朝', path: 'jing', image: '/static/image/jing.jpg', intro: '金朝是中国历史上由女真族建立的北方政权，共传十帝，享国119年。金太祖完颜阿骨打统一女真诸部后起兵反辽。1115年在上京会宁府（今黑龙江省哈尔滨市阿城区）立国，国号金，建元“收国”。' },
        { name: '元朝', path: 'yuan', image: '/static/image/yuan.jpg', intro: '元朝是中国历史上首次由少数民族建立的大一统王朝，统治者为蒙古孛儿只斤氏。传五世十一帝，从1206年成吉思汗建立大蒙古国始为162年，从忽必烈定国号元开始历时98年。' },
        { name: '明朝', path: 'ming', image: '/static/image/ming.jpg', intro: '明朝是中国历史上一个由汉族建立的王朝。初期建都南京，明成祖时期迁都北京。传十六帝，共计276年。元末爆发红巾起义，朱元璋加入郭子兴队伍。1364年称吴王，史称西吴。1368年初称帝，国号大明，定都于应天府。' },
        { name: '清朝', path: 'qing', image: '/static/image/qing.jpg', intro: '清朝是中国历史上最后一个封建王朝，共传十二帝，统治者为满洲爱新觉罗氏。从努尔哈赤建立后金起，总计296年。从皇太极改国号为清起，国祚276年。从清兵入关，建立全国性政权算起为268年。' }
      ],
      showIntroBox: false,
      introText: '',
      introX: 0,
      introY: 0
    };
  },
  computed: {
    menuItems() {
      return [
        { name: '首页', url: '/pages/index/index' },
        { name: '我的', url: '/pages/mine/mine' },
        ...this.dynasties.map(d => ({
          name: d.name,
          url: `/pages/${d.path}/${d.path}`
        }))
      ];
    }
  },
  methods: {
    goToPage(pageName) {
      uni.navigateTo({
        url: `/pages/${pageName}/${pageName}`
      });  
    },
    handleDropdownClick(e) {
      const url = e.target.dataset.url;
      if (url) this.navigateTo(url);
    },
    navigateTo(url) {
      uni.navigateTo({ url });
      this.showDropdown = false;
    },
    goToDynasty(path) {
      this.navigateTo(`/pages/${path}/${path}`);
    },
    goToSearch() {
      this.navigateTo('/pages/searchpage/searchpage');
    },
    showIntro(event, intro) {
      this.showIntroBox = true;
      this.introText = intro;
      this.introX = event.clientX + 10;
      this.introY = event.clientY + 10;
    },
    hideIntro() {
      this.showIntroBox = false;
    }
  },
  mounted() {
    document.addEventListener('click', () => {
      this.showDropdown = false;
    });
  }
};
</script>

<style scoped>
/* 头部样式 */
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
  max-height: 415px; /* 设置最大高度 */
  overflow-y: auto; /* 超出高度时显示滚动条 */
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
.dropbtn{
  height: 30px;
}


.header-title {
  width: 100%;
  
  text-align: center;
  font-size: 20px;
  font-weight: bold;
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

/* 内容区域 */

.container {
  display: flex;
  flex-wrap: wrap;
  margin-top: 25px;
}

.pic {
  width: calc(100% / 3);
  text-align: center;
  padding: 0 1px;
  box-sizing: border-box;
  margin-bottom: 10px;
}

.image-wrapper {
  position: relative;
  overflow: hidden;
}

.image {
  height: 200px;
  width: 100%;
  object-fit: cover;
  filter: grayscale(100%);
  transition: filter 0.3s ease;
}

.image:hover {
  filter: grayscale(0%);
}

.dynasty-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 24px;
  text-shadow: 2px 2px 4px rgba(0,0,0,0.5);
}

.intro-box {
  position: absolute;
  background-color: rgba(0, 0, 0, 0.8);
  color: white;
  padding: 10px;
  border-radius: 5px;
  z-index: 1000;
  max-width: 200px;
}
</style>  