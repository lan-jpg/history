<template>
    <view class="register-container">
        <view class="register-form">
            <form ref="form"> <!-- 移除不必要的 :model 和 :rules 绑定 -->
                <view class="input-group">
                    <label>用户名：</label>
                    <input
                        type="text"
                        placeholder="请输入用户名"
                        v-model="user.username"
                        placeholder-style="color: #999;"
                    />
                </view>
                <view class="input-group">
                    <label>密码：</label>
                    <input
                        type="password"
                        placeholder="请输入密码"
                        v-model="user.password"
                        placeholder-style="color: #999;"
                    />
                </view>
                <view class="input-group">
                    <label>确认密码：</label>
                    <input
                        type="password"
                        placeholder="再次输入密码"
                        v-model="user.passwordconfirm"
                        placeholder-style="color: #999;"
                    />
                </view>
            </form>
            <button @click="submitForm">注册</button>
        </view>
    </view>
</template>

<script>
export default {
    data() {
        return {
            user: {},
            rules: {
                username: {
                    rules: [
                        { required: true, errorMessage: '请输入用户名' },
                        { minLength: 2, maxLength: 20, errorMessage: '用户名长度在 {minLength} 到 {maxLength} 个字符' }
                    ]
                },
                password: {
                    rules: [
                        { required: true, errorMessage: '请输入密码' },
                        { minLength: 2, maxLength: 20, errorMessage: '密码长度在 {minLength} 到 {maxLength} 个字符' }
                    ]
                },
                passwordconfirm: {
                    rules: [
                        { required: true, errorMessage: '请输入确认密码' },
                        { minLength: 2, maxLength: 20, errorMessage: '确认密码长度在 {minLength} 到 {maxLength} 个字符' },
                        { validator: (value, data) => value === data.password || '两次输入的密码不一致' }
                    ]
                }
            }
        };
    },
    methods: {
        async submitForm() {
            const errors = {};
            
            // 手动执行表单验证
            for (const field in this.rules) {
                const fieldRules = this.rules[field];
                const value = this.user[field];
                
                for (const rule of fieldRules.rules) {
                    if (rule.required && !value) {
                        errors[field] = rule.errorMessage;
                        break;
                    }
                    if (rule.minLength && value?.length < rule.minLength) {
                        errors[field] = rule.errorMessage.replace(
                            /{minLength}|{maxLength}/g, 
                            match => rule[match.slice(1, -1)]
                        );
                        break;
                    }
                    if (rule.maxLength && value?.length > rule.maxLength) {
                        errors[field] = rule.errorMessage.replace(
                            /{minLength}|{maxLength}/g, 
                            match => rule[match.slice(1, -1)]
                        );
                        break;
                    }
                    if (rule.validator) {
                        const message = rule.validator(value, this.user);
                        if (typeof message === 'string' && message) {
                            errors[field] = message;
                            break;
                        }
                    }
                }
            }

            if (Object.keys(errors).length > 0) {
                let errorMessage = '';
                for (const field in errors) {
                    errorMessage += errors[field] + '\n';
                }
                uni.showToast({ title: errorMessage.trim(), icon: 'none' });
                return;
            }

            // 验证通过，执行注册逻辑
            Reflect.deleteProperty(this.user, 'passwordconfirm');
            const baseUrl = 'http://localhost:5173';
            try {
                const response = await fetch(`${baseUrl}/user/insert`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        name: this.user.username,
                        password: this.user.password
                    })
                });
                const responsedata = await response.json();
                if (responsedata.code == '200') {
                    uni.showToast({ title: '注册成功', icon: 'success' });
                    this.user = {};
                    uni.navigateTo({ url: '/pages/login/login' });
                } else {
                    uni.showToast({ title: responsedata.msg, icon: 'none' });
                }
            } catch (error) {
                console.error('注册请求出错:', error);
                uni.showToast({ title: '网络错误，请稍后重试', icon: 'none' });
            }
        }
    }
};
</script>

<style scoped>
.register-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background-color: #f4f4f4;
}

.register-form {
    width: 100%;
    max-width: 320px;
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.input-group {
    margin-bottom: 16px;
}

.input-group label {
    display: block;
    margin-bottom: 4px;
    font-size: 14px;
    color: #333;
    font-weight: 500;
}

.input-group input {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    outline: none;
    transition: border-color 0.3s;
}

.input-group input:focus {
    border-color: #007aff;
}

button {
    width: 100%;
    padding: 10px;
    background-color: #007aff;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
}

button:hover {
    background-color: #0056b3;
}
</style>    