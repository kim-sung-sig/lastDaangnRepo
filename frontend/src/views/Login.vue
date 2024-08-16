<template>
    <div>
        <h2>Login</h2>
		<form @submit.prevent="login">
			<div>
				<label for="username">Username:</label>
				<input class="uk-input" v-model="username" id="username" ref="usernameInput" placeholder="Username" />
				<span v-if="usernameError">{{ usernameError }}</span>
			</div>
			<div>
				<label for="password">Password:</label>
				<input class="uk-input" v-model="password" id="password" type="password" ref="passwordInput" placeholder="Password" autocomplete="false"/>
				<span v-if="passwordError">{{ passwordError }}</span>
			</div>
			<button class="uk-button" type="submit">Login</button>
		</form>
    </div>
</template>

<script>
import store from '@/store';

export default {
    name: 'Login-main',
    data() {
        return {
            username : '',
            password : '',
            usernameError : '',
            passwordError : '',
        }
    },

    methods: {
        async login() {
            try {
                // 입력값 유효성 검사
                this.loginValidation(this.username, this.password);

                // Vuex 액션 호출 및 결과 처리
                const loginSuccess = await store.dispatch('login', {
                    username: this.username,
                    password: this.password
                });

                if (loginSuccess) {
                    // 로그인 성공 시 홈으로 리다이렉트
                    this.$router.push({ name: 'Home' });
                } else {
                    // 로그인 실패 시 오류 처리
                    alert('로그인 실패');
                    this.clearForm();
                }

            } catch (error) {
                console.error('로그인 오류:', error);
                this.clearForm();
            }
        },

        /**
         * 로그인 유효성 검사
         * @param {string} username 사용자 이름
         * @param {string} password 비밀번호
         */
        loginValidation(username, password) {
            if (username.trim().length === 0) {
                alert('아이디를 입력해주세요.');
                this.usernameError = '아이디를 입력해주세요.';
                this.username = '';
                this.$refs.usernameInput.focus();
                throw new Error('아이디를 입력해주세요.');
            } else {
                this.usernameError = '';
            }
            if (password.trim().length === 0) {
                alert('비밀번호를 입력해주세요.');
                this.passwordError = '비밀번호를 입력해주세요.';
                this.password = '';
                this.$refs.passwordInput.focus();
                throw new Error('비밀번호를 입력해주세요.');
            } else {
                this.passwordError = '';
            }
        },

        clearForm() {
            this.username = '';
            this.password = '';
            this.$refs.usernameInput.focus();
        }
    },

    mounted() {
        this.$refs.usernameInput.focus();
    }
}
</script>

<style lang="scss" scoped>

</style>