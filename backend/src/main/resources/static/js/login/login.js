window.onload = function () {
    new Vue({
        el: '#app',
        data: {
            username: '',
            password: '',
            usernameError: '',
            passwordError: '',
            failCnt : false,
            isLocked : false,
            checker : '',
            checkerVal : '',
            checkerError : '',
        },
        methods: {
            login() {
                try {
                    const loginValidCheker = this.loginValidation(this.username, this.password);
                    this.doLogin();
                } catch (error) {/* nothing to do */}
            },

            async doLogin() {
                try {

                    const formData = new FormData();
                    formData.append('username', this.username);
                    formData.append('password', this.password);
                    debugger;

                    const response = await axios.post('/login', formData,);
                    console.log(response.status);
                    console.log(response.data);
                    // debugger;
                    if(response.data?.success === true){
                        const redirectUrl = response.data?.redirectUrl;
                        if(redirectUrl){
                            location.href = redirectUrl;
                        } else {
                            location.href = '/';
                        }
                    } else {
                        alert('로그인 실패');
                        this.clearForm();
                    }

                } catch (e) {
                    const response = e.response;
                    const msg = response.data?.message;
                    console.log(msg);
                    if ("USERNAME_NOT_FOUND" === msg) {
                        alert('존재하지 않는 사용자입니다.');
                        this.clearForm();
                    }
                    if (msg.startsWith("PASSWORD_NOT_MATCH")) {
                        const message = '비밀번호가 일치하지 않습니다.';

                        alert(message);
                        const count = msg.split(':')[1];
                        if(count >= 5) {
                            this.failCnt = true;
                            this.checker = 'checker';
                            this.checkerVal = '';
                        }
                        this.password = '';
                        this.$refs.passwordInput.focus();
                        this.passwordError = message;
                    }
                    if ("LOCKED" === msg) {
                        alert('계정이 잠겼습니다. 계정 잠금을 해제해주세요.');
                        // locked 페이지로 변경
                    }
                    if ("INTERNAL_SERVER_ERROR" === msg) {
                        alert('서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
                        location.reload();
                    }
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
                if(this.failCnt) {
                    if(this.checkerVal !== this.checker) {
                        alert('checker를 입력해주세요.');
                        this.checkerError = 'checker를 입력해주세요.';
                        this.checkerVal = '';
                        this.$refs.checkerInput.focus();
                        throw new Error('checker를 입력해주세요.');
                    } else {
                        this.checkerError = '';
                    }
                }
            },

            clearForm() {
                this.username = '';
                this.password = '';
                this.$refs.usernameInput.focus();
            }
        },

        mounted() {
            // 자동으로 사용자 이름 입력 필드에 포커스 설정
            this.$refs.usernameInput.focus();
        }
    });
}