<template>
    <view class="main">
        <view class="login-box">
            <view class="input-item">
                <text class="label">用户名：</text>
                <input 
                    class="input" 
                    type="text" 
                    placeholder="请输入用户名"
                    v-model="formData.username"
                    placeholder-style="color: #999;"
                />
            </view>
            <view class="input-item">
                <text class="label">密码：</text>
                <input 
                    class="input" 
                    type="password" 
                    placeholder="请输入密码"
                    v-model="formData.password"
                    placeholder-style="color: #999;"
                />
            </view>
            <view class="button-group">
                <button 
                    class="btn login-btn" 
                    type="button"
                    @click="handleLogin"
                >登录</button>
                <button 
                    class="btn register-btn" 
                    type="button"
                    @click="goToPage"
                >注册</button>
            </view>
        </view>
    </view>
</template>

<script>
export default {
    data() {
        return {
            formData: {
                username: '',
                password: ''
            }
        };
    },
    methods: {
        goToPage() {
            uni.navigateTo({
                url: "/pages/register/register"
            });
        },
        async handleLogin() {
            const baseUrl = 'http://localhost:5173'; // 假设的接口基础地址
            try {
                const response = await fetch(`${baseUrl}/user/admin`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        name: this.formData.username,
                        password: this.formData.password
                    })
                });
				const responsedata = await response.json();
                if (responsedata.code=='200') {
					// 存储用户 ID 到本地缓存（全局可访问）
                    uni.showToast({
                        title: '登录成功',
                        icon: 'success'
                    });
                    uni.navigateTo({
                        url: "/pages/index/index"
                    });
					// 存储用户 ID 到本地缓存（全局可访问）
					uni.setStorageSync('userId', responsedata.data.id);
					uni.setStorageSync('userInfo', {
					    username: this.formData.username,
					    userpassword:this.formData.password                    
					});
                } else {
                    uni.showToast({
                        title: responsedata.msg,
                        icon: 'none'
                    });
                }
            } catch (error) {
                console.error('登录请求出错:', error);
                uni.showToast({
                    title: '网络错误，请稍后重试',
                    icon: 'none'
                });
            }
        }
    }
};
</script>

<style>
.main {
    width: 100vw;
    height: 100vh;
    background: linear-gradient(135deg, #6B73FF 0%, #000DFF 100%);
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 40rpx;
}

.login-box {
    width: 100%;
    padding: 60rpx 40rpx;
    background: rgba(255, 255, 255, 0.95);
    border-radius: 24rpx;
    box-shadow: 0 10rpx 30rpx rgba(0, 0, 0, 0.1);
}

.input-item {
    margin-bottom: 48rpx;
}

.label {
    display: block;
    margin-bottom: 16rpx;
    font-size: 32rpx;
    color: #333;
    font-weight: 500;
}

.input {
    width: 100%;
    height: 96rpx;
    padding: 0 28rpx;
    border: 2rpx solid #e5e5e5;
    border-radius: 12rpx;
    font-size: 30rpx;
    transition: border-color 0.3s;
}

.input:focus {
    border-color: #007AFF;
}

.button-group {
    display: flex;
    gap: 24rpx;
    margin-top: 80rpx;
}

.btn {
    flex: 1;
    height: 88rpx;
    line-height: 88rpx;
    font-size: 34rpx;
    border-radius: 12rpx;
    transition: all 0.3s;
}

.login-btn {
    background-color: #007AFF;
    color: white;
}

.login-btn:active {
    background-color: #0051ff;
    transform: scale(0.98);
}

.register-btn {
    background-color: #4CD964;
    color: white;
}

.register-btn:active {
    background-color: #2ac845;
    transform: scale(0.98);
}
</style>    